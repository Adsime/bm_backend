package main.java.com.acc.testResources;

import com.acc.database.entity.HbnBachelorGroup;
import com.acc.database.entity.HbnDocument;
import com.acc.database.entity.HbnTag;
import com.acc.database.entity.HbnUser;
import com.acc.models.Group;
import com.acc.models.Document;
import com.acc.models.Tag;
import com.acc.models.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by nguyen.duy.j.khac on 14.03.2017.
 */

// TODO: 15.03.2017 Revise, a lot here is probably not necesarry, especially the getEmpty...() methods
public class TestHibernateData {
    public static HbnTag getHbnTag(){
        return new HbnTag("Captain","Selected captain of the team","Role");
    }
    public static Set<HbnTag> getHbnTagSet() {
        Set<HbnTag> hbnTagSet = new HashSet<>();
            hbnTagSet.add(new HbnTag("POTY","Player Of The Year","Award"));
            hbnTagSet.add(new HbnTag("POTM","Player Of The Match","Award"));
            hbnTagSet.add(new HbnTag("Captain","Selected captain of the team","Role"));
        return hbnTagSet;
    }

    public static List<Tag> getTagList(){
        List<Tag> tagList = new ArrayList<>();
            tagList.add(new Tag(1,"POTY","Player Of The Year","Award"));
            tagList.add(new Tag(2,"Captain","Selected captain of the team","Role"));
        return tagList;
    }

    public static Tag getTag(){
        return new Tag(1,"POTY","Player Of The Year","Award");
    }

    public static User getUser(){
        return new User(1,"David Josué Jiménez", "Silva", "merlin@mcfc.co.uk", "99999999","silva.david.j.j","1", getTagList());
    }

    public static User getUserWrongId(){
        return new User(0,"Yaya", "Toure", "nocake@mcfc.co.uk", "99999999","toure.yaya", "2" ,getTagList());
    }

    public static List<HbnTag> getHbnTagList(){
        List<HbnTag> hbnTagList = new ArrayList<>();
        HbnTag hbnTag = new HbnTag("POTY","Player Of The Year","Award");
        hbnTag.setId(1);
        hbnTagList.add(hbnTag);
        return hbnTagList;
    }

    public static List<HbnUser> getHbnUserList(){
        List<HbnUser> hbnUserList = new ArrayList<>();
        HbnUser hbnUser = new HbnUser("David Josué Jiménez", "Silva", "merlin@mcfc.co.uk","99999999","silva.david.j.j","1");
        hbnUser.setId(1);
        hbnUser.setTags(getHbnTagSet());
        hbnUserList.add(hbnUser);
        return hbnUserList;
    }

    public static List<HbnUser> getEmptyHbnUserList(){
        return new ArrayList<>();
    }

    public static List<HbnTag> getEmptyHbnTagList(){
        List<HbnTag> hbnTagList = new ArrayList<>();
        return hbnTagList;
    }

    public static Tag getTagWrongId() {
        return new Tag(0,"POTY","Player Of The Year","Award");
    }


    public static Document getDocument() {
        return new Document(1, 1, "From Zlatan, Love Zlatan","Zlatan", "zlatan's path", getTagList());
    }

    public static List<Document> getDocumentList() {
        List<Document> documentList = new ArrayList<>();
        Document document = new Document(1, 1, "From Zlatan, Love Zlatan","Zlatan", "zlatan's path", getTagList());
        documentList.add(document);
        return documentList;
    }

    public static List<HbnDocument> getHbnDocumentList() {
        List<HbnDocument> hbnDocumentList = new ArrayList<>();
        HbnDocument hbnDocument = new HbnDocument("path",getHbnUserList().get(0),"title");
        hbnDocument.setId(1);
        hbnDocumentList.add(hbnDocument);
        return hbnDocumentList;
    }

    public static Document getDocumentWrongId() {
        return new Document(0, 1, "From Zlatan, Love Zlatan","Zlatan", "zlatan's path", getTagList());
    }

    public static Document getDocumentNoAuthor() {
        return new Document(1, 0, "From Zlatan, Love Zlatan", "Zlatan", "zlatan's path", getTagList());
    }

    public static List<HbnDocument> getEmptyHbnDocumentList() {
        return new ArrayList<>();
    }

    public static List<User> getUserList(){
        List<User> userList = new ArrayList<>();
        userList.add(getUser());
        return userList;
    }

    public static Set<HbnUser> getHbnUserSet(){
        Set<HbnUser> userList = new HashSet<>();
        userList.add(getHbnUserList().get(0));
        return userList;
    }

    public static Group getGroup() {
        return new Group(1, "Group", getUserList(), getUserList(), getDocument());
    }

    public static Group getGroupWrongId() {
        return new Group(0, "Group", getUserList(), getUserList(), getDocument());
    }

    public static List<HbnBachelorGroup> getHbnGroupList() {
        List<HbnBachelorGroup> hbnBachelorGroupList = new ArrayList<>();
        HbnBachelorGroup hbnBachelorGroup = new HbnBachelorGroup("Group");
        hbnBachelorGroup.setId(1);
        hbnBachelorGroup.setUsers(getHbnUserSet());
        hbnBachelorGroupList.add(hbnBachelorGroup);
        return hbnBachelorGroupList;
    }
}
