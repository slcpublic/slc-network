package com.liulishuo.okdownload.core;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author slc
 * @date 2020/11/11 16:49
 */
public class UtilsCompat extends Util {
    public static String urlDecode(final String input) {
        return urlDecode(input, "UTF-8");
    }

    public static String urlDecode(final String input, final String charsetName) {
        if (input == null || input.length() == 0) return "";
        try {
            String safeInput = input.replaceAll("%(?![0-9a-fA-F]{2})", "%25").replaceAll("\\+", "%2B");
            return URLDecoder.decode(safeInput, charsetName);
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }
}
