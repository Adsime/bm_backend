package main.java.com.acc;

import com.acc.database.repository.TagRepository;
import com.acc.models.Tag;
import com.acc.service.TagService;
import main.java.com.acc.testResources.TestData;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.ejb.NoSuchEntityException;
import javax.ws.rs.InternalServerErrorException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by melsom.adrian on 14.02.2017.
 */
public class TagServiceTest {
    @Mock
    TagRepository tagRepository;

    TagService service;



    @Before
    public void setup() {
        initMocks(this);
        service = new TagService();
        service.tagRepository = tagRepository;
    }

    //Start getTag()

    @Test
    public void getTagSuccessTest() {
        when(tagRepository.getQuery(any())).thenReturn(TestData.testTags());
        Tag expected = TestData.testTags().get(0);
        Tag actual = service.getTag(0);
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void getTagNoEntryException() {
        when(tagRepository.getQuery(any())).thenThrow(new NoSuchEntityException());
        Tag expected = null;
        Tag actual = service.getTag(0);
        assertEquals(expected, actual);
    }

    @Test(expected = InternalServerErrorException.class)
    public void getTagInternalError() {
        when(tagRepository.getQuery(any())).thenThrow(new InternalServerErrorException());
        service.getTag(0);
    }

    //End getTag()
    //Start getAllTags()

    @Test
    public void getAllTagsSuccess() {
        when(tagRepository.getQuery(any())).thenReturn(TestData.testTags());
        String expected = TestData.testTags().toString();
        String actual = service.getAllTags().toString();
        assertEquals(expected, actual);
    }

    @Test
    public void getAllTagsEmptyList() {
        when(tagRepository.getQuery(any())).thenReturn(new ArrayList<Tag>());
        boolean expected = service.getAllTags().isEmpty();
        assertTrue(expected);
    }

    @Test
    public void getAllTagsNoSuchEntity() {
        when(tagRepository.getQuery(any())).thenThrow(new NoSuchEntityException());
        Object expected = Arrays.asList();
        Object actual = service.getAllTags();
        assertEquals(expected, actual);
    }

    @Test(expected = InternalServerErrorException.class)
    public void getAllTagsInternalError() {
        when(tagRepository.getQuery(any())).thenThrow(new InternalServerErrorException());
        service.getAllTags();
    }

    //End getAllTags()
    //Start newTag()

    @Test
    public void newTagSuccess() {
        when(tagRepository.add(any())).thenReturn(TestData.testTags().get(0));
        String expected = TestData.testTags().get(0).toString();
        String actual = service.newTag(TestData.testTags().get(0)).toString();
        assertEquals(expected, actual);
    }

    @Test(expected = InternalServerErrorException.class)
    public void newTagFail() {
        when(tagRepository.add(any())).thenThrow(new InternalServerErrorException());
        service.newTag(TestData.testTags().get(0));
    }

    //End newTag()
    //Start deleteTag()

    @Test
    public void deleteTagSuccess() {
        when(tagRepository.remove(0)).thenReturn(true);
        boolean acutal = service.deleteTag(0);
        assertTrue(acutal);
    }

    @Test
    public void deleteTagNoEntry() {
        when(tagRepository.remove(0)).thenReturn(false);
        boolean actual = service.deleteTag(0);
        assertFalse(actual);
    }

    @Test
    public void deleteTagNoEntryNull() {
        when(tagRepository.remove(0)).thenThrow(new NoSuchEntityException());
        boolean actual = service.deleteTag(0);
        assertFalse(actual);
    }

    @Test(expected = InternalServerErrorException.class)
    public void deleteTagInternalError() {
        when(tagRepository.remove(0)).thenThrow(new InternalServerErrorException());
        service.deleteTag(0);
    }

    //End deleteTag()
    //Start updateTag()

    @Test
    public void updateTagSuccess() {
        when(tagRepository.update(any())).thenReturn(true);
        boolean actual = service.updateTag(TestData.testTags().get(0));
        assertTrue(actual);
    }

    @Test
    public void updateTagFail() {
        when(tagRepository.update(any())).thenReturn(false);
        boolean actual = service.updateTag(TestData.testTags().get(0));
        assertFalse(actual);
    }

    @Test
    public void updateTagNoEntry() {
        when(tagRepository.update(any())).thenThrow(new NoSuchEntityException());
        boolean actual = service.updateTag(TestData.testTags().get(0));
        assertFalse(actual);
    }

    @Test(expected = InternalServerErrorException.class)
    public void updateTagInternalError() {
        when(tagRepository.update(any())).thenThrow(new InternalServerErrorException());
        service.updateTag(TestData.testTags().get(0));
    }
}
