package com.acc;

import com.acc.controller.GroupService;
import com.acc.controller.TagService;
import com.acc.resources.TagResource;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import com.acc.testResources.TestData;
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
        when(service.)

    }

    @Test
    public void getTagAuthFail() {

    }

    @Test
    public void getTagNoEntries() {

    }

    @Test
    public void getTagInternalError() {

    }

    // End getTag Tests
    // Start getAllTags Tests

    @Test
    public void getAllTagsSuccess() {

    }

    @Test
    public void getAllTagsAuthFail() {

    }

    @Test
    public void getAllTagsNoEntries() {

    }

    @Test
    public void getAllTagsInternalError() {

    }

    // End getAllTags Tests
    // Start newTag Tests

    @Test
    public void newTagsSuccess() {

    }

    @Test
    public void newTagsAuthFail() {

    }

    @Test
    public void newTagsNoEntries() {

    }

    @Test
    public void newTagsInternalError() {

    }

    // End newTag Tests
    // Start updateTag Tests

    @Test
    public void updateTagsSuccess() {

    }

    @Test
    public void updateTagsAuthFail() {

    }

    @Test
    public void updateTagsNoEntries() {

    }

    @Test
    public void updateTagsInternalError() {

    }

    // End updateTag Tests
    // Start deleteTag Tests

    @Test
    public void deleteTagsSuccess() {

    }

    @Test
    public void deleteTagsAuthFail() {

    }

    @Test
    public void deleteTagsNoEntries() {

    }

    @Test
    public void deleteTagsInternalError() {

    }

    //End deleteTag Tests

}
