package com.example.flashcat.api;

import android.content.Context;
import android.util.Log;
import android.webkit.URLUtil;
import android.widget.Toast;

import com.example.flashcat.Model.Dictionary.WordItem;
import com.example.flashcat.api.OnFetchDataListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public class RequestManager {
    private Context context;
    private Retrofit retrofit;

    public RequestManager(Context context) {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.dictionaryapi.dev/api/v2/entries/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.context = context;
    }

    public void getWordMeaning(OnFetchDataListener listener, String word) {
        // Kiểm tra tính hợp lệ của đường dẫn URL
        String baseUrl = retrofit.baseUrl().toString();
        String fullUrl = baseUrl + "en/" + word;

        if (!URLUtil.isHttpUrl(fullUrl) && !URLUtil.isHttpsUrl(fullUrl)) {
            Toast.makeText(context, "Invalid URL!", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.d("link", "getWordMeaning: " + fullUrl);
        CallDictionary callDictionary = retrofit.create(CallDictionary.class);
        Call<List<WordItem>> call = callDictionary.callMeanings(word);
        try {
            call.enqueue(new Callback<List<WordItem>>() {
                @Override
                public void onResponse(Call<List<WordItem>> call, Response<List<WordItem>> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    listener.onFetchData(response.body().get(0), response.message());
                }

                @Override
                public void onFailure(Call<List<WordItem>> call, Throwable t) {
                    String errorMessage = "Request Failed!";
                    if (t != null && t.getMessage() != null) {
                        errorMessage = t.getMessage(); // Lấy thông tin lỗi từ Throwable
                    }
                    listener.onError(errorMessage); // Gọi callback để báo lỗi cho người dùng
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "An Error Occurred!", Toast.LENGTH_SHORT).show();
        }
    }

    public interface CallDictionary {
        @GET("en/{word}")
        Call<List<WordItem>> callMeanings(@Path("word") String word);
    }
}
