package com.july.pigeon.engine;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @author Administrator 处理回调数据
 */
public class GsonParser {

    public static <T> T parseList(String result, TypeToken<T> typeToken) {
        return new Gson().fromJson(result, typeToken.getType());
    }

    public static <T> T parseObject(String result, Class<T> clazz) {
        return new Gson().fromJson(result, clazz);
    }

}
