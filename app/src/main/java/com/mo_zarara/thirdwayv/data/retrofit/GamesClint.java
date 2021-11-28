package com.mo_zarara.thirdwayv.data.retrofit;

import com.mo_zarara.thirdwayv.pojo.Root;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GamesClint {

    private static final String BASE_URL = "https://api.rawg.io/api/";
    private static final String KEY = "2669816058804b86921544a33c64cd49";
    private GamesInterface gamesInterface;
    private static GamesClint INSTANCE;

    public GamesClint() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();

        gamesInterface =  retrofit.create(GamesInterface.class);


    }


    public static GamesClint getINSTANCE() {

        if (null == INSTANCE)
            INSTANCE = new GamesClint();

        return INSTANCE;
    }


    public Observable<Root> GetData (String dates){
        return gamesInterface.getGames(KEY, dates);
    }






}
