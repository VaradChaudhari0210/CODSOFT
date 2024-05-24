package com.example.motivational_quotes.quotes_api;

import android.content.Context;
import android.widget.Toast;

import com.example.motivational_quotes.Quotes.Quote_model;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class Request_manager {
    Context context;
    String category;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://zenquotes.io/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public Request_manager(Context context,String category) {
        this.context = context;
        this.category = category;
    }

    public void GetAllQuotes(Quote_response_listener listener){ //need to add parameter for category
        CallQuotes callQuotes = retrofit.create(CallQuotes.class);
        Call<List<Quote_model>> call = callQuotes.callQuotes(category); //need to change the category
        call.enqueue(new Callback<List<Quote_model>>() {
            @Override
            public void onResponse(Call<List<Quote_model>> call, Response<List<Quote_model>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(context, "Request Unsuccessful", Toast.LENGTH_SHORT).show();
                    return;
                }
                listener.didfetch(response.body(),response.message());
            }
            @Override
            public void onFailure(Call<List<Quote_model>> call, Throwable throwable) {
                listener.diderror(throwable.getMessage());
            }
        });
    }
    private interface CallQuotes{
        @GET("quotes/category=")
        Call<List<Quote_model>> callQuotes(@Query(value = "category") String category);
    }
}
