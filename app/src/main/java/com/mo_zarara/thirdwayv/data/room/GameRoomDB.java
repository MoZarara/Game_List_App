package com.mo_zarara.thirdwayv.data.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.mo_zarara.thirdwayv.data.retrofit.Converters;
import com.mo_zarara.thirdwayv.pojo.GamesModel;

@Database(entities = GamesModel.class, version = 1)
@TypeConverters(Converters.class)
public abstract class GameRoomDB extends RoomDatabase {

    private static GameRoomDB instance;
    public abstract GameDao gameDao();

    //Singlton
    public static synchronized GameRoomDB getInstance(Context context) {

        if (instance == null) {

            instance = Room.databaseBuilder(context.getApplicationContext(),
                    GameRoomDB.class,
                    "Games_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }


}
