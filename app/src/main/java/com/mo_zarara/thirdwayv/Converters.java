package com.mo_zarara.thirdwayv;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mo_zarara.thirdwayv.pojo.GenresModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Converters {

    @TypeConverter
    public String fromListToString(List<GenresModel> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    @TypeConverter
    public List<GenresModel> fromStringToList(String s) {
        Type listType = new TypeToken<List<GenresModel>>() {}.getType();

        return new Gson().fromJson(s, listType);
    }

}
