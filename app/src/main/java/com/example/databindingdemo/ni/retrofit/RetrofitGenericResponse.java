package com.example.databindingdemo.ni.retrofit;


import android.util.Log;

import com.example.databindingdemo.util.log.CustomLogger;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitGenericResponse {
    private static String TAG = RetrofitGenericResponse.class.getSimpleName();

    public static <T> void callRetrofit(Call<T> call, final RetrofitResponseCallback retrofitResponseCallback) {

        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {

                    if (response.raw().networkResponse() != null) {
                        CustomLogger.INSTANCE.d(TAG, "onResponse: response is from NETWORK...");
                        if (response.isSuccessful()) {
                            //CustomLogger.INSTANCE.d(TAG, "onResponse: " + response.body().toString());
                            retrofitResponseCallback.success(response);
                        } else {
                            try {
                                CustomLogger.INSTANCE.d(TAG, "onResponse: " + response.errorBody().string());
                                retrofitResponseCallback.error(response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } else if (response.raw().cacheResponse() != null
                            && response.raw().networkResponse() == null) {
                        CustomLogger.INSTANCE.d(TAG, "onResponse:response is from CACHE...");
                        if (response.isSuccessful()) {
                            CustomLogger.INSTANCE.d(TAG, "onResponse: " + response.body().toString());
                            retrofitResponseCallback.success(response);
                        } else {
                            try {
                                CustomLogger.INSTANCE.d(TAG, "onResponse: " + response.errorBody().string());
                                retrofitResponseCallback.error(response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        retrofitResponseCallback.error("");
                    }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
                    retrofitResponseCallback.failure(t.toString());
            }
        });
    }
}
