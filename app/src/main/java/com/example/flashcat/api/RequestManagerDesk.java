package com.example.flashcat.api;

import android.content.Context;
import android.util.Log;
import android.webkit.URLUtil;
import android.widget.Toast;

import com.example.flashcat.Model.Desk;
import com.example.flashcat.Model.Dictionary.WordItem;
import com.example.flashcat.Model.Flashcard;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public class RequestManagerDesk {
    private Context context;
    private Retrofit retrofit;



    public RequestManagerDesk(Context context) {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.2.14:5000/")
                .addConverterFactory(GsonConverterFactory.create(OnFetchDataListener.gson))
                .build();
        this.context = context;
    }

    public void getDeskById(OnFetchDataListener listener, int id) {
        String baseUrl = retrofit.baseUrl().toString();
        String fullUrl = baseUrl + "fc/getbyIdDesk/" + id;

        if (!URLUtil.isHttpUrl(fullUrl) && !URLUtil.isHttpsUrl(fullUrl)) {
            Toast.makeText(context, "Invalid URL!", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d("link", "getDeskById: " + fullUrl);
        CallDesk callDesk = retrofit.create(CallDesk.class);
        Call<Desk> call = callDesk.getDeskById(id);

        call.enqueue(new Callback<Desk>() {
            @Override
            public void onResponse(Call<Desk> call, Response<Desk> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
                    return;
                }
                listener.onFetchData(response.body(), id);
            }

            @Override
            public void onFailure(Call<Desk> call, Throwable t) {
                String errorMessage = "Request Failed!";
                if (t != null && t.getMessage() != null) {
                    errorMessage = t.getMessage();
                }
                listener.onError(errorMessage);
            }
        });
    }

    public void getAllDesks(OnFetchDataListener listener) {
        String baseUrl = retrofit.baseUrl().toString();
        String fullUrl = baseUrl + "fc/getallDesk";

        if (!URLUtil.isHttpUrl(fullUrl) && !URLUtil.isHttpsUrl(fullUrl)) {
            Toast.makeText(context, "Invalid URL!", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d("link", "getAllDesks: " + fullUrl);
        CallDesk callDesk = retrofit.create(CallDesk.class);
        Call<List<Desk>> call = callDesk.getAllDesks();

        call.enqueue(new Callback<List<Desk>>() {
            @Override
            public void onResponse(Call<List<Desk>> call, Response<List<Desk>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
                    return;
                }
                listener.onFetchDataList(response.body());
            }

            @Override
            public void onFailure(Call<List<Desk>> call, Throwable t) {
                String errorMessage = "Request Failed!";
                if (t != null && t.getMessage() != null) {
                    errorMessage = t.getMessage();
                }
                listener.onError(errorMessage);
            }
        });
    }

    // Lấy tất cả flashcard theo id desk
    public void getAllFlashcardsByDeskId(OnFetchDataListener listener, int id) {
        String baseUrl = retrofit.baseUrl().toString();
        String fullUrl = baseUrl + "fc/getAllFlashcardByIdDesk/" + id;

        if (!URLUtil.isHttpUrl(fullUrl) && !URLUtil.isHttpsUrl(fullUrl)) {
            Toast.makeText(context, "Invalid URL!", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d("link", "getAllFlashcardsByDeskId: " + fullUrl);
        CallDesk callDesk = retrofit.create(CallDesk.class);
        Call<List<Flashcard>> call = callDesk.getAllFlashcardsByDeskId(id); // Changed return type here

        call.enqueue(new Callback<List<Flashcard>>() { // Changed Callback type here
            @Override
            public void onResponse(Call<List<Flashcard>> call, Response<List<Flashcard>> response) { // Changed response type here
                if (!response.isSuccessful()) {
                    Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
                    return;
                }
                listener.onFetchDataListFlashcard(response.body(), id); // trả về kết quả
            }

            @Override
            public void onFailure(Call<List<Flashcard>> call, Throwable t) { // Changed Call type here
                String errorMessage = "Request Failed!";
                if (t != null && t.getMessage() != null) {
                    errorMessage = t.getMessage();
                }
                listener.onError(errorMessage);
            }
        });
    }


    // Thêm desk
    public void addDesk(OnFetchDataListener listener, Desk desk) {
        String baseUrl = retrofit.baseUrl().toString();
        String fullUrl = baseUrl + "fc/addDesk";

        if (!URLUtil.isHttpUrl(fullUrl) && !URLUtil.isHttpsUrl(fullUrl)) {
            Toast.makeText(context, "Invalid URL!", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d("link", "addDesk: " + fullUrl);
        CallDesk callDesk = retrofit.create(CallDesk.class);
        Call<Void> call = callDesk.addDesk(desk);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
                    return;
                }
                listener.onFetchData(desk, 1);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                String errorMessage = "Request Failed!";
                if (t != null && t.getMessage() != null) {
                    errorMessage = t.getMessage();
                }
                listener.onError(errorMessage);
            }
        });
    }

    // Xóa desk
    public void deleteDesk(OnFetchDataListener listener, int idDesk) {
        String baseUrl = retrofit.baseUrl().toString();
        String fullUrl = baseUrl + "fc/deleteDesk/" + idDesk + "/";

        if (!URLUtil.isHttpUrl(fullUrl) && !URLUtil.isHttpsUrl(fullUrl)) {
            Toast.makeText(context, "Invalid URL!", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d("link", "deleteDesk: " + fullUrl);
        CallDesk callDesk = retrofit.create(CallDesk.class);
        Call<Void> call = callDesk.deleteDesk(idDesk);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
                    return;
                }
                listener.onFetchData((Desk) null, idDesk);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                String errorMessage = "Request Failed!";
                if (t != null && t.getMessage() != null) {
                    errorMessage = t.getMessage();
                }
                listener.onError(errorMessage);
            }
        });
    }

    // update desk
    public void updateDesk(OnFetchDataListener listener, int idDesk, Desk desk) {
        String baseUrl = retrofit.baseUrl().toString();
        String fullUrl = baseUrl + "fc/updateDesk/" + idDesk;

        if (!URLUtil.isHttpUrl(fullUrl) && !URLUtil.isHttpsUrl(fullUrl)) {
            Toast.makeText(context, "Invalid URL!", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d("link", "updateDesk: " + fullUrl);
        CallDesk callDesk = retrofit.create(CallDesk.class);
        Call<Void> call = callDesk.updateDesk(idDesk, desk);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
                    return;
                }
                listener.onFetchData(desk, idDesk);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                String errorMessage = "Request Failed!";
                if (t != null && t.getMessage() != null) {
                    errorMessage = t.getMessage();
                }
                listener.onError(errorMessage);
            }
        });
    }
    // thêm flashcard cho một desk bằng deskId
    public void addFlashcardToDesk(OnFetchDataListener listener, Flashcard flashcard) {
        String baseUrl = retrofit.baseUrl().toString();
        String fullUrl = baseUrl + "fc/addFlashcard";

        if (!URLUtil.isHttpUrl(fullUrl) && !URLUtil.isHttpsUrl(fullUrl)) {
            Toast.makeText(context, "Invalid URL!", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d("link", "addFlashcardToDesk: " + fullUrl);
        CallDesk callDesk = retrofit.create(CallDesk.class);
        Call<Void> call = callDesk.addFlashcardToDesk(flashcard);

        call.enqueue(new Callback<Void>() {
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
                    return;
                }
                listener.onFetchData(flashcard, 1); // Updated method call
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                String errorMessage = "Request Failed!";
                if (t != null && t.getMessage() != null) {
                    errorMessage = t.getMessage();
                }
                listener.onError(errorMessage);
            }
        });
    }

    public void updateFlashcard(OnFetchDataListener listener, int idFlashcard, Flashcard flashcard) {
        String baseUrl = retrofit.baseUrl().toString();
        String fullUrl = baseUrl + "fc/updateFlashcard/" + idFlashcard;

        if (!URLUtil.isHttpUrl(fullUrl) && !URLUtil.isHttpsUrl(fullUrl)) {
            Toast.makeText(context, "Invalid URL!", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d("link", "updateFlashcard: " + fullUrl);
        CallDesk callDesk = retrofit.create(CallDesk.class);
        Call<Void> call = callDesk.updateFlashcard(idFlashcard, flashcard);

        call.enqueue(new Callback<Void>() {
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
                    return;
                }
                listener.onFetchData(flashcard, 1); // Updated method call
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                String errorMessage = "Request Failed!";
                if (t != null && t.getMessage() != null) {
                    errorMessage = t.getMessage();
                }
                listener.onError(errorMessage);
            }
        });
    }

    public interface CallDesk {


        // Lấy tất cả
        // các desks
        @GET("fc/getallDesk")
        Call<List<Desk>> getAllDesks();

        // Lấy desk theo idDesk
        @GET("fc/getbyIdDesk/{id}")
        Call<Desk> getDeskById(@Path("id") int idDesk);

        // Thêm desk
        @POST("fc/addDesk")
        Call<Void> addDesk(@Body Desk desk);

        // Sửa desk
        @PUT("fc/updateDesk/{id}")
        Call<Void> updateDesk(@Path("id") int idDesk, @Body Desk desk);

        // Xóa desk
        @DELETE("fc/deleteDesk/{id}/")
        Call<Void> deleteDesk(@Path("id") int idDesk);

        // Lấy flashcard theo id flashcard
        @GET("fc/getbyIdFlashcard/{id}")
        Call<Flashcard> getFlashcardById(@Path("id") int idFlashcard);
        // Lấy tất cả flashcard theo id desk
        @GET("fc/getAllFlashcardByIdDesk/{id}")
        Call<List<Flashcard>> getAllFlashcardsByDeskId(@Path("id") int idDesk);

        // Thêm flashcard cho một desk
        @POST("fc/addFlashcard")
        Call<Void> addFlashcardToDesk(@Body Flashcard flashcard);

        // Sửa flashcard
        @PUT("fc/updateFlashcard/{id}")
        Call<Void> updateFlashcard(@Path("id") int idFlashcard, @Body Flashcard flashcard);

        // Xóa flashcard
        @DELETE("fc/deleteFlashcard/{id}")
        Call<Void> deleteFlashcard(@Path("id") int idFlashcard);

    }

}
