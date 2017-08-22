package com.labouardy.a9gag.service;

import com.labouardy.a9gag.model.Meme;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Mohamed on 22/08/2017.
 */

public interface MemeService {

    @GET("/memes/{tag}")
    Call<List<Meme>> findByTag(@Path("tag") String tag);
}
