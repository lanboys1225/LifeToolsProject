package com.bing.lan.core.api;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by 蓝兵 on 2018/6/13.
 */

public class HttpParamUtil {

    public static MultipartBody.Part createMultipartBodyPart(String name, File file) {
        if (file != null) {
            //text/plain
            //text/html
            //application/x-www-form-urlencoded
            //multipart/form-data
            return MultipartBody.Part.createFormData(name, file.getName(),
                    createMultipartRequestBody(file));
            //return MultipartBody.Part.createFormData("Upload[file]", file.getName(), requestBody);
        }
        return null;
    }

    public static RequestBody createMultipartRequestBody(File file) {
        if (file != null) {
            return RequestBody.create(MediaType.parse("multipart/form-data"), file);
        }
        return null;
    }

    public static RequestBody createMultipartRequestBody(String value) {
        if (value != null) {
            return RequestBody.create(null, value);
        }
        return null;
    }

    public static void checkNotEmptyAdd(Map<String, String> map, String value, String name) {

        if (value != null && value.length() > 0) {
            map.put(name, value);
        }
    }

    public static void checkNotNullAdd(Map<String, String> map, String value, String name) {
        if (null != value) {
            map.put(name, value);
        }
    }
}
