package com.mo_zarara.thirdwayv.data;

import com.mo_zarara.thirdwayv.pojo.Root;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GamesInterface {

    @GET("games")
    Observable<Root> getGames(@Query("key") String key, @Query("dates") String date);

}
