package org.desciclopedia.util;

import android.webkit.JavascriptInterface;

import org.desciclopedia.WikiHelper;

public class JSUtils {
    @JavascriptInterface public String predef(String name) {
        String result = Linux.curl(null, WikiHelper.internal("Predefinição:" + name));

        return result;
    }
}
