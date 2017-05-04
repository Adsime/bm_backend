package com.acc.service;

import com.acc.database.repository.DocumentRepository;
import com.acc.database.specification.GetDocumentAllSpec;
import com.acc.database.specification.GetDocumentByIdSpec;
import com.acc.google.FileHandler;
import com.acc.models.Document;

import javax.ejb.NoSuchEntityException;
import javax.inject.Inject;
import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by melsom.adrian on 10.02.2017.
 */
public class DocumentService extends GeneralService {

    @Inject
    public FileHandler fileHandler;

    @Inject
    public DocumentRepository documentRepository;

    public Document getDocument(int id) {
        try {
            Document document = documentRepository.getQuery(new GetDocumentByIdSpec((long)id)).get(0);
            if(document == null) return null;
            return fileHandler.insertFileContent(document, "");
        } catch (NoSuchEntityException nsee) {

        } catch (EntityNotFoundException enfe) {

        }
        return null;
    }

    public List<Document> getAllDocuments() {
        try {
            return documentRepository.getQuery(new GetDocumentAllSpec());
        } catch (NoSuchEntityException nsee) {

        } catch (EntityNotFoundException enfe) {

        }
        return Collections.emptyList();
    }

    public Document newDocument(Document document) {
        String path = fileHandler.createFile(document.getTitle(), document.getContent(), Arrays.asList("0ByI1HjM5emiFcXRtSVplQmJ6YjA"));
        if(path != null) {
            document.setPath(path);
            try {
                return documentRepository.add(document);
            } catch (EntityNotFoundException enfe) {
                fileHandler.deleteFile(path);
                return null;
            }

        }
        return null;
    }

    public boolean deleteDocument(int id) {
        try {
            Document document = getDocument(id);
            if(document == null) return false;
            boolean deleted = documentRepository.remove(id);
            if(deleted) {
                fileHandler.deleteFile(document.getPath());
            }
            return deleted;
        } catch (NoSuchEntityException nsee) {

        } catch (EntityNotFoundException enfe) {

        }
        return false;

    }

    public boolean updateDocument(Document document) {
        try {
            Document old = getDocument(document.getId());
            if(old == null) {
                return false;
            }
            boolean updated = documentRepository.update(document);
            if(updated) {
                fileHandler.updateFile(old.getPath(), document.getTitle(), document.getContent(), "");
            }
            return updated;
        } catch (NoSuchEntityException nsee) {

        } catch (EntityNotFoundException enfe) {

        }
        return false;
    }
}
