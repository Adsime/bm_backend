package com.acc.google;

import com.acc.models.Document;
import com.acc.models.Folder;
import com.acc.models.GoogleItem;
import com.acc.models.User;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static com.acc.google.GoogleService.getDriveService;


/**
 * Created by melsom.adrian on 09.03.2017.
 */
public class FileHandler {

    private static Logger LOGGER = Logger.getLogger("application");

    /**
     * Folder and file status codes
     */
    public static final int EXISTS = 400;
    public static final int AVAILABLE = 200;
    public static final int CREATED = 201;
    public static final int ERROR = 500;
    public static final int DELETED = 204;
    public static final int MULTIPLE_CHOICES = 300;
    public static final int NOT_FOUND = 404;

    @Context
    private ContainerRequestContext context;

    /**
     * Grabs all folders from the application Google Drive. It then creates a tree structure
     * by comparing the parents of the items in the list.
     * @return List of Folders
     */
    public List<GoogleFolder> getTreeStructure() {
        try {
            Drive service = getDriveService();
            FileList result = service.files().list()
                    .setQ("trashed = false and mimeType = 'application/vnd.google-apps.folder'")
                    .setFields("nextPageToken, files(id, name, parents, mimeType)")
                    .execute();
            List<File> files = result.getFiles();
            Collections.reverse(files);
            return build(files, files.get(0).getParents().get(0));
        } catch (IOException ioe) {
            return Collections.emptyList();
        }
    }

    /**
     * Allows for any file to be uploaded to Drive
     * @param file java.io.File
     * @param name String
     * @param type String
     * @param originalType String
     * @param parent String
     * @return String
     */
    public String uploadAnyFile(java.io.File file, String name, String type, String originalType, String parent) {
        try {
            Drive service = getDriveService();
            File googleFile = new File();
            googleFile.setName(name);
            googleFile.setMimeType(type);
            googleFile.setParents(Arrays.asList(parent));
            FileContent fileContent = new FileContent(originalType, file);
            File retFile = service.files().create(googleFile, fileContent)
                    .setFields("id")
                    .execute();
            return retFile.getId();
        } catch (Exception e) {
            LOGGER.error("Unable to upload to Drive.", e);
        }
        return null;
    }

    public OutputStream downloadAnyFile(String id) {
        try {
            Drive service = getDriveService();
            File file = service.files().get(id).setFields("mimeType").execute();
            OutputStream os = new ByteArrayOutputStream();
            service.files().export(id, "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                    .executeMediaAndDownloadTo(os);
            return os;
        }catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }

    /**
     * Responsible for creating a folder on Drive
     * @param folder Folder
     * @return int
     */
    public int createFolder(Folder folder) {
        try {
            Drive service = getDriveService();
            if(exists(folder.getName(), folder.getParent(), true) != null && !folder.isForced()) {
                return EXISTS;
            }
            File fileMetadata = new File();
            fileMetadata.setName(folder.getName());
            fileMetadata.setParents(Lists.newArrayList(folder.getParent()));
            fileMetadata.setMimeType("application/vnd.google-apps.folder");

            service.files().create(fileMetadata)
                    .setFields("id")
                    .execute();
            return CREATED;
        } catch (IOException ioe) {
            LOGGER.error("Unexpected error in FileHandler.createFolder", ioe);
        }
        return ERROR;
    }

    public String exists(String name, String parent, boolean folder) {
        try {
            Drive service = getDriveService();
            FileList files = service.files().list()
                    .setQ("'" + parent + "' in parents" + " and trashed = false and name = '" + name + "'" +
                            (folder ? " and mimeType = 'application/vnd.google-apps.folder'" : ""))
                    .execute();
            return (files.getFiles().size() > 0) ? "exists" : null;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return "error";
    }

    /**
     * Finds the child items of a folder based on ID
     * @param id String
     * @param user User
     * @param isAdmin isAdmin
     * @return List of google files, which includes folders
     */
    public List<GoogleItem> getFolder(String id, User user, boolean isAdmin) {
        try {
            Drive service = getDriveService();
            if(id == null) {
                id = service.files().get("root").setFields("id").execute().getId();
            }
            FileList res = service.files().list()
                    .setQ("'" + id + "'" + " in parents" + " and trashed = false")
                    .setFields("nextPageToken, files(id, name, mimeType, iconLink, " +
                            "webViewLink, thumbnailLink, parents, hasThumbnail)")
                    .execute();
            File parent = service.files().get(id)
                    .setFields("id, name")
                    .execute();
            List<File> files = res.getFiles();
            files.add(parent);
            ArrayList<GoogleItem> items = new ArrayList<>();
            files.forEach(file -> {
                boolean canDelete = isAdmin || user.getFiles().contains(file.getId());
                items.add(new GoogleItem(file, canDelete));
            });
            return Lists.reverse(items);
        } catch (IOException ioe) {
            LOGGER.error("Unexpected error in FileHandler.getFolder", ioe);
            return Collections.emptyList();
        }
    }

    /**
     * Will update an existing file on Google Drive based on content and name.
     * @param id String
     * @param newName
     * @param newContent
     * @return boolean indicating if the action was successfull.
     */
    public boolean updateFile(String id, String newName, String newContent, String type) {
        Path path = null;
        try {
            Drive service = getDriveService();

            /** Downloading a specific file from Google Drive */
            OutputStream outputStream = getFileContent(id, service, type);
            File file = service
                    .files()
                    .get(id)
                    .execute();

            /** Creating a file locally to update the file stored on Google Drive */
            File updatedFile = new File()
                    .setName(newName == null ? file.getName() : newName);
            FileContent fileContent = createFileContent(file.getName(), outputStream.toString(), newName, newContent, "text/plain");
            path = fileContent.getFile().toPath();

            /** Uploading the new file to replace the content of the current file */
            service.files().update(id, updatedFile, fileContent).setUseContentAsIndexableText(true).execute();

            /** Cleaning up file from cache */
            Files.delete(path);
            return true;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            if(path != null) {
                try {
                    Files.delete(path);
                } catch (IOException ioEx) {

                }
            }
            return false;
        }
    }

    public int updateAnyFile(java.io.File file, String id, String type) {
        try {
            Drive service = getDriveService();
            File googleFile = service.files().get(id).execute();
            FileContent fileContent = new FileContent(type, file);
            File updatedFile = new File();
            updatedFile.setName(googleFile.getName());
            service.files().update(id, updatedFile, fileContent).execute();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return -1;
    }

    public OutputStream getFileContent(String id, String type) {
        OutputStream os = null;
        try {
            Drive service = getDriveService();
            os = getFileContent(id, service, type);
        } catch (IOException ioe) {

        }
        return os;
    }

    private OutputStream getFileContent(String id, Drive service, String type) throws IOException {
        OutputStream outputStream = new ByteArrayOutputStream();
        service.files().export(id, type).executeMediaAndDownloadTo(outputStream);
        return outputStream;
    }

    public Document insertFileContent(Document document, String type) {
        try {
            Drive service = getDriveService();
            document.setContent(getFileContent(document.getPath(), service, type).toString());
            return document;
        } catch (IOException ioe) {
            return null;
        }
    }

    /**
     * Creates a new file which is pushed on to Google Drive.
     * @param name
     * @param content
     * @param parents
     * @return boolean indicating if the action was successful.
     */
    public String createFile(String name, String content, List<String> parents) {
        Path path = null;
        String id = null;
        try {
            Drive service = getDriveService();

            /** Creates a file and sets appropriate values */
            FileContent fileContent = createFileContent(name, content, null, null, "text/plain");
            path = fileContent.getFile().toPath();
            File file = new File();
            file.setName(name);
            file.setParents(parents);
            file.setMimeType("application/vnd.google-apps.document");

            /** Tried to upload the file to Google drive */
            File temp = service.files().create(file, fileContent).execute();

            /** Deletes the file from local cache */
            Files.delete(path);
            id = temp.getId();
            return id;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            try {
                Files.delete(path);
                deleteFile(id);

            } catch (IOException ioEx) {

            }
            return null;
        }
    }

    public boolean deleteFile(String id) {
        try {
            Drive service = getDriveService();
            service.files().delete(id).execute();
            return true;
        } catch (IOException ioe) {
            return false;
        }
    }

    private FileContent createFileContent(String oldName, String oldContent, String newName, String newContent, String type) throws IOException {
        String name = (newName == null || newName.isEmpty() ? oldName : newName);
        List<String> content = new ArrayList<>();
        content.add(newContent == null ? oldContent : newContent);
        java.io.File file = java.io.File.createTempFile(name, null); // new java.io.File(Config.getCacheDirectory(), name);
        Files.write(Paths.get(file.getPath()), content, Charset.forName("UTF-8"));
        return new FileContent(type, file);
    }

    /**
     * Responsible for deleting an item defined by an id
     * @param id String
     * @param forced boolean
     * @return int
     */
     public int deleteItem(String id, boolean forced) {
        try {
            Drive service = getDriveService();
            FileList files = service.files().list()
                    .setQ("'" + id + "' in parents" + " and trashed = false")
                    .execute();
            if(files.getFiles().size() < 1 || forced) {
                service.files().delete(id).execute();
                return DELETED;
            } else if(files.size() > 0) {
                return MULTIPLE_CHOICES;
            }
        } catch (IOException ioe) {
            LOGGER.error("Unexpected error in FileHandler.deleteItem", ioe);
            return ERROR;
        }
        return NOT_FOUND;
     }

    private List<GoogleFolder> build(List<File> list, String root) {
        ArrayList<GoogleFolder> googleFolders = new ArrayList<>();
        if(list.isEmpty()) return googleFolders;
        File file;
        Iterator<File> it = list.iterator();
        while(it.hasNext() && (file = it.next()) != null) {
            if(file.getParents().get(0).equals(root)) {
                googleFolders.add(new GoogleFolder(file));
                list.remove(file);
                it = list.iterator();
            }

        }
        for(GoogleFolder f : googleFolders) {
            f.setChildren(build(list, f.getFolder().getId()));
        }
        return googleFolders;
    }

    public void createFile(String id, java.io.File file) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            Drive service = getDriveService();
            service.files().export(id, "application/vnd.openxmlformats-officedocument.wordprocessingml.document").executeMediaAndDownloadTo(fos);
            fos.close();
        } catch (IOException ioe) {

        }
    }

    public File getMetadata(String id) {
        try {
            Drive service = getDriveService();
            File file = service.files()
                    .get(id)
                    .setFields("name, mimeType").execute();
            return file;
        } catch (IOException ioe) {

        }
        return null;
    }

    public byte[] downloadTest(){
        try {
            Drive service = getDriveService();
            String fileId = "1T8-RViclMSVXEoj4BE6-rp3lcPCPMS722S2ibQjxZCE";
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            service.files().export(fileId, "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                    .executeMediaAndDownloadTo(outputStream);
            return outputStream.toByteArray();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return null;
    }

    public java.io.File createFile(File file, ByteArrayOutputStream outputStream) {
        try {
            java.io.File temp = java.io.File.createTempFile(file.getName(), file.getName());
            FileUtils.writeByteArrayToFile(temp, outputStream.toByteArray());
            return temp;
        } catch (IOException e) {

        }
        return null;
    }
}
