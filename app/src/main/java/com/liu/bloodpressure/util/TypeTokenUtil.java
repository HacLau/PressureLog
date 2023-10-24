package com.liu.bloodpressure.util;

import com.google.gson.reflect.TypeToken;
import com.liu.bloodpressure.model.News;

import java.util.List;

public class TypeTokenUtil {
    public static TypeToken<List<News>> getTokenNews(){
        return new TypeToken<List<News>>() {};
    }
}
