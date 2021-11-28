package com.mo_zarara.thirdwayv.repo;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.mo_zarara.thirdwayv.data.room.GameDao;
import com.mo_zarara.thirdwayv.data.room.GameRoomDB;
import com.mo_zarara.thirdwayv.pojo.GamesModel;

import java.util.List;

public class GameRepository {

    private GameDao mGameDao;

    private LiveData<List<GamesModel>> getAllGames;

    public GameRepository(Application app) {
        GameRoomDB db = GameRoomDB.getInstance(app);
        mGameDao = db.gameDao();
        getAllGames = mGameDao.getAllGames();
    }


    public void insert(GamesModel gamesModel){
        new InsertAsyncTask(mGameDao).execute(gamesModel);
    }

    public LiveData<List<GamesModel>> getAllGames(){
        return getAllGames;
    }

    public void deleteAllGames(){
        new DeleteAllNewsAsyncTask(mGameDao).execute();
    }


    private static class InsertAsyncTask extends AsyncTask<GamesModel, Void, Void> {

        private GameDao mGameDao;

        public InsertAsyncTask(GameDao mNewsDao) {
            this.mGameDao = mNewsDao;
        }

        @Override
        protected Void doInBackground(GamesModel... gamesModels) {

            mGameDao.insert(gamesModels[0]);
            return null;
        }
    }



    private static class DeleteAllNewsAsyncTask extends AsyncTask<Void, Void, Void> {

        private GameDao mGameDao;

        public DeleteAllNewsAsyncTask(GameDao mGameDao) {
            this.mGameDao = mGameDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mGameDao.deleteAll();
            return null;
        }
    }

}
