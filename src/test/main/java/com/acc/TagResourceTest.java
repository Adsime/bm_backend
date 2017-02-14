package main.java.com.acc;

import com.acc.controller.TagService;
import com.acc.database.pojo.Tag;
import com.acc.resources.TagResource;
import main.java.com.acc.testResources.TestData;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.ws.rs.InternalServerErrorException;
import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by melsom.adrian on 03.02.2017.
 */
public class TagResourceTest {

    @Mock
    private TagService service;

    public TagResource tagResource;

    int expected, actual;

    @Before
    public void setup() {
        initMocks(this);
        tagResource = new TagResource();
    }

    // Start getTag Tests

    @Test
    public void getTagSuccess() {
        tagResource.service = service;
        when(service.verify(TestData.credentials)).thenReturn(true);
        when(service.getTag(0)).thenReturn(TestData.testTags().get(0));
        expected = HttpStatus.OK_200;
        actual = tagResource.getTag(0, TestData.testCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void getTagAuthFail() {
        tagResource.service = service;
        when(service.verify(TestData.badCredentials)).thenReturn(false);
        when(service.getTag(0)).thenReturn(TestData.testTags().get(0));
        expected = HttpStatus.UNAUTHORIZED_401;
        actual = tagResource.getTag(0, TestData.testBadCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void getTagNoEntries() {
        tagResource.service = service;
        when(service.verify(TestData.credentials)).thenReturn(true);
        when(service.getTag(0)).thenReturn(null);
        expected = HttpStatus.BAD_REQUEST_400;
        actual = tagResource.getTag(0, TestData.testCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void getTagInternalError() {
        tagResource.service = service;
        when(service.verify(TestData.credentials)).thenThrow(new InternalServerErrorException());
        when(service.getTag(0)).thenReturn(null);
        expected = HttpStatus.INTERNAL_SERVER_ERROR_500;
        actual = tagResource.getTag(0, TestData.testCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    // End getTag Tests
    // Start getAllTags Tests

    @Test
    public void getAllTagsSuccess() {
        tagResource.service = service;
        when(service.verify(TestData.credentials)).thenReturn(true);
        when(service.getAllTags()).thenReturn(TestData.testTags());
        expected = HttpStatus.OK_200;
        actual = tagResource.getAllTags(TestData.testCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void getAllTagsAuthFail() {
        tagResource.service = service;
        when(service.verify(TestData.badCredentials)).thenReturn(false);
        when(service.getAllTags()).thenReturn(TestData.testTags());
        expected = HttpStatus.UNAUTHORIZED_401;
        actual = tagResource.getAllTags(TestData.testBadCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void getAllTagsNoEntries() {
        tagResource.service = service;
        when(service.verify(TestData.credentials)).thenReturn(true);
        when(service.getAllTags()).thenReturn(new ArrayList<Tag>());
        expected = HttpStatus.OK_200;
        actual = tagResource.getAllTags(TestData.testCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void getAllTagsInternalError() {
        tagResource.service = service;
        when(service.verify(TestData.credentials)).thenReturn(true);
        when(service.getAllTags()).thenThrow(new InternalServerErrorException());
        expected = HttpStatus.INTERNAL_SERVER_ERROR_500;
        actual = tagResource.getAllTags(TestData.testCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    // End getAllTags Tests
    // Start newTag Tests

    @Test
    public void newTagsSuccess() {
        tagResource.service = service;
        when(service.verify(TestData.credentials)).thenReturn(true);
        when(service.newTag(any())).thenReturn(true);
        expected = HttpStatus.OK_200;
        actual = tagResource.newTag(TestData.jsonTag(), TestData.testCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void newTagsAuthFail() {
        tagResource.service = service;
        when(service.verify(TestData.credentials)).thenReturn(false);
        when(service.newTag(any())).thenReturn(true);
        expected = HttpStatus.UNAUTHORIZED_401;
        actual = tagResource.newTag(TestData.jsonTag(), TestData.testBadCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void newTagsInternalError() {
        tagResource.service = service;
        when(service.verify(TestData.credentials)).thenReturn(true);
        when(service.newTag(any())).thenThrow(new InternalServerErrorException());
        expected = HttpStatus.INTERNAL_SERVER_ERROR_500;
        actual = tagResource.newTag(TestData.jsonTag(), TestData.testCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    // End newTag Tests
    // Start updateTag Tests

    @Test
    public void updateTagsSuccess() {
        tagResource.service = service;
        when(service.verify(TestData.credentials)).thenReturn(true);
        when(service.updateTag(any())).thenReturn(true);
        expected = HttpStatus.OK_200;
        actual = tagResource.updateTag(TestData.jsonTag(), TestData.testCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void updateTagsAuthFail() {
        tagResource.service = service;
        when(service.verify(TestData.badCredentials)).thenReturn(false);
        when(service.updateTag(any())).thenReturn(true);
        expected = HttpStatus.UNAUTHORIZED_401;
        actual = tagResource.updateTag(TestData.jsonTag(), TestData.testBadCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void updateTagsNoEntries() {
        tagResource.service = service;
        when(service.verify(TestData.credentials)).thenReturn(true);
        when(service.updateTag(any())).thenReturn(false);
        expected = HttpStatus.BAD_REQUEST_400;
        actual = tagResource.updateTag(TestData.jsonTag(), TestData.testCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void updateTagsInternalError() {
        tagResource.service = service;
        when(service.verify(TestData.credentials)).thenReturn(true);
        when(service.updateTag(any())).thenThrow(new InternalServerErrorException());
        expected = HttpStatus.BAD_REQUEST_400;
        actual = tagResource.updateTag(TestData.jsonTag(), TestData.testCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    // End updateTag Tests
    // Start deleteTag Tests

    @Test
    public void deleteTagsSuccess() {
        tagResource.service = service;
        when(service.verify(TestData.credentials)).thenReturn(true);
        when(service.deleteTag(9)).thenReturn(true);
        expected = HttpStatus.OK_200;
        actual = tagResource.deleteTag(0, TestData.testCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void deleteTagsAuthFail() {
        tagResource.service = service;
        when(service.verify(TestData.badCredentials)).thenReturn(false);
        when(service.deleteTag(0)).thenReturn(true);
        expected = HttpStatus.UNAUTHORIZED_401;
        actual = tagResource.deleteTag(0, TestData.testBadCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void deleteTagsNoEntries() {
        tagResource.service = service;
        when(service.verify(TestData.credentials)).thenReturn(true);
        when(service.deleteTag(0)).thenReturn(false);
        expected = HttpStatus.BAD_REQUEST_400;
        actual = tagResource.deleteTag(0, TestData.testCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    @Test
    public void deleteTagsInternalError() {
        tagResource.service = service;
        when(service.verify(TestData.credentials)).thenReturn(true);
        when(service.deleteTag(0)).thenThrow(new InternalServerErrorException());
        expected = HttpStatus.INTERNAL_SERVER_ERROR_500;
        actual = tagResource.deleteTag(0, TestData.testCredentials()).getStatus();
        assertEquals(expected, actual);
    }

    //End deleteTag Tests

}
