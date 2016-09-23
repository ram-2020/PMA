package com.themartinsierra.popularmovieapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {

    @Mock
    Context mMockContext;
    @Test
    public void test() throws Exception {
        when(mMockContext.getString(R.string.image_size)).thenReturn("w185");
        when(mMockContext.getString(R.string.image_path)).thenReturn("http://image.tmdb.org/t/p/");

        String ov = "After the re-emergence of the world's first mutant, world-destroyer Apocalypse, the X-Men must unite to defeat his extinction level plan.";
        PopularMovie pm = new PopularMovie("Captain America: Civil War", "/zSouWWrySXshPCT4t3UKCQGayyo.jpg", ov, "6.84", "2016-05-18", mMockContext);

        assertEquals("Captain America: Civil War",pm.originalTitle);
        assertEquals("http://image.tmdb.org/t/p/w185/zSouWWrySXshPCT4t3UKCQGayyo.jpg",pm.moviePosterPath);
        assertEquals(ov,pm.overview);
        assertEquals("6.84",pm.voteAverage);
        assertEquals("2016-05-18",pm.releaseDate);

    }
}