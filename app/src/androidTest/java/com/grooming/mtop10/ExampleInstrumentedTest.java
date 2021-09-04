package com.grooming.mtop10;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.android.material.textfield.TextInputLayout;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    String TAG = "testtag";
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.grooming.mtop10", appContext.getPackageName());

    }
    @Test
    public void checkUri(){
        Uri uri = Uri.parse("custom://yandex.ru");
        Log.d(TAG, "checkUri: " + uri.toString());
        Log.d(TAG, "checkUri host:" + uri.getHost());
        Log.d(TAG, "checkUri last path segment: " + uri.getLastPathSegment());
        Log.d(TAG, "checkUri ends with yandex: " + uri.getHost().endsWith("yandex.ru"));
        Uri uri2 = Uri.parse("custom://evil.com\\\\yandex.ru");
        Log.d(TAG, "checkUri: " + uri2.toString());
        Log.d(TAG, "checkUri host:" + uri2.getHost());
        Log.d(TAG, "checkUri last path segment: " + uri2.getLastPathSegment());
        Log.d(TAG, "checkUri ends with yandex: " + uri.getHost().endsWith("yandex.ru"));

    }
}