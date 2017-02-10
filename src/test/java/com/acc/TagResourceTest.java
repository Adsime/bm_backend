package com.acc;

import com.acc.controller.GroupService;
import com.acc.resources.TagResource;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by melsom.adrian on 03.02.2017.
 */
public class TagResourceTest {

    @Mock
    private GroupService controller;

    public TagResource TagResource;

    @Before
    public void setup() {
        initMocks(this);
        TagResource = new TagResource();
    }

    // Start getTag Tests

    @Test
    public void getTagSuccess() {

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
