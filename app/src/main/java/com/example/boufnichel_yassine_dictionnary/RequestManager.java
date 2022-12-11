package com.example.boufnichel_yassine_dictionnary;

import android.content.Context;
import android.widget.Toast;

import com.example.boufnichel_yassine_dictionnary.Models.APIResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class RequestManager {
    Context context;

    public RequestManager(Context context) {
        this.context = context;
    }

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.dictionaryapi.dev/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    public void getWordMeaning(onFetchDataListener listener, String word){
        CallDictionary callDictionary = retrofit.create(CallDictionary.class);
        Call<List<APIResponse>> call = callDictionary.callMeanings(word);
        try{
            call.enqueue(new Callback<List<APIResponse>>() {
                @Override
                public void onResponse(Call<List<APIResponse>> call, Response<List<APIResponse>> response) {
                    if(!response.isSuccessful()){
                        Toast.makeText(context, "Error !!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    listener.onFetchData(response.body().get(0), response.message());

                }

                @Override
                public void onFailure(Call<List<APIResponse>> call, Throwable t) {
                    listener.onError("Request failed !!");
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(context, "An error has been detected !!", Toast.LENGTH_SHORT).show();
        }
    }
    public  interface CallDictionary{
        @GET("entries/en/{word}")
        Call<List<APIResponse>> callMeanings(
                @Path("word") String word
        );
    }
}
