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

    private static final String TAG = "GamesViewModel";

    private GameRepository mRepository;

    private LiveData<List<GamesModel>> mAllGames;

    public GamesViewModel(@androidx.annotation.NonNull Application application) {
        super(application);

        mRepository = new GameRepository(application);
        mAllGames = mRepository.getAllGames();

    }


    public void insert(GamesModel gamesModel) {
        mRepository.insert(gamesModel);
    }


    public void deleteAllGames() {
        mRepository.deleteAllGames();
    }

    public LiveData<List<GamesModel>> getAllGames() {
        return mAllGames;
    }


    public void getGamesData(String dates) {

        Observable<Root> observable = GamesClint.getINSTANCE().GetData(dates).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread());


        Observer<Root> observer = new Observer<Root>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Root root) {

                boolean cheak = true;
                if (getAllGames().getValue().size() > 0 && getAllGames().getValue() != null) {
                    for (int i = 0; i < root.getResults().size(); i++) {

                        if (cheak) {
                            if (root.getResults().get(i).getId().equals(getAllGames().getValue().get(i).getId()) &&
                                    root.getResults().get(i).getName().equals(getAllGames().getValue().get(i).getName()) &&
                                    root.getResults().get(i).getReleased().equals(getAllGames().getValue().get(i).getReleased()) &&
                                    root.getResults().get(i).getBackground_image().equals(getAllGames().getValue().get(i).getBackground_image())) {
                                cheak = true;

                                for (int x = 0; x < root.getResults().get(i).getGenres().size(); x++) {
                                    if (root.getResults().get(i).getGenres().get(x).getName().equals(getAllGames().getValue().get(i).getGenres().get(x).getName())) {
                                        cheak = true;
                                    } else {
                                        cheak = false;
                                    }
                                }

                            } else {
                                cheak = false;
                            }

                        }
                    };

                } else {
                    for (int i = 0; i < root.getResults().size(); i++) {
                        GamesModel gamesModel = new GamesModel(root.getResults().get(i).getId(),
                                root.getResults().get(i).getName(),
                                root.getResults().get(i).getReleased(),
                                root.getResults().get(i).getBackground_image(),
                                root.getResults().get(i).getGenres());

                        insert(gamesModel);
                    };
                }

                if (!cheak) {

                    deleteAllGames();
                    for (int i = 0; i < root.getResults().size(); i++) {
                        GamesModel gamesModel = new GamesModel(root.getResults().get(i).getId(),
                                root.getResults().get(i).getName(),
                                root.getResults().get(i).getReleased(),
                                root.getResults().get(i).getBackground_image(),
                                root.getResults().get(i).getGenres());

                        insert(gamesModel);
                    };
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "mymy onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };


        observable.subscribe(observer);

    }


}
