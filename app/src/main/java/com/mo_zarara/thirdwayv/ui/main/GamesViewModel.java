package com.mo_zarara.thirdwayv.ui.main;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mo_zarara.thirdwayv.repo.GameRepository;
import com.mo_zarara.thirdwayv.data.retrofit.GamesClint;
import com.mo_zarara.thirdwayv.pojo.GamesModel;
import com.mo_zarara.thirdwayv.pojo.Root;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GamesViewModel extends AndroidViewModel {

    private GameRepository mRepository;

    private LiveData<List<GamesModel>> mAllGames;

    public GamesViewModel(@androidx.annotation.NonNull Application application) {
        super(application);

        mRepository = new GameRepository(application);
        mAllGames = mRepository.getAllGames();

    }

    public void insert(String dates) {
        mRepository.insert(dates);
    }


    public LiveData<List<GamesModel>> getAllGames() {
        return mAllGames;
    }

}
