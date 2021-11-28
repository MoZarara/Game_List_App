package com.mo_zarara.thirdwayv;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mo_zarara.thirdwayv.pojo.GamesModel;

import java.util.List;

@Dao
interface GameDao {

    @Insert
    void insert(GamesModel gamesModel);

    @Query("DELETE From gameTable")
    void deleteAll();

    @Query("SELECT * From gameTable")
    LiveData<List<GamesModel>> getAllGames();


}
