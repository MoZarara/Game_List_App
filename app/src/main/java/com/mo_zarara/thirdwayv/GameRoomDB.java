package com.mo_zarara.thirdwayv;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

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
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            new PopulatDataAsyncTask(instance).execute();
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };



    private static class PopulatDataAsyncTask extends AsyncTask<Void, Void, Void> {

        private GameDao gameDao;

        public PopulatDataAsyncTask(GameRoomDB db) {
            gameDao = db.gameDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }
    }

}
