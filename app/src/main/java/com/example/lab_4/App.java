package com.example.lab_4;

import android.app.Application;

import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import com.example.lab_4.logger.AndroidLogger;
import com.example.lab_4.logger.Logger;
import com.example.lab_4.model.DateNagerService;
import com.example.lab_4.model.db.AppDB;
import com.example.lab_4.model.db.HolidayDAO;
import com.example.lab_4.model.network.DateNagerApi;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    private ViewModelProvider.Factory factory;
    private static final String BASE_URL = "https://date.nager.at/";

    @Override
    public void onCreate() {
        super.onCreate();
        Logger logger = new AndroidLogger();
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DateNagerApi dateNagerApi = retrofit.create(DateNagerApi.class);
        ExecutorService executorService = Executors.newCachedThreadPool();

        AppDB appDB = Room.databaseBuilder(this, AppDB.class, "database.db")
                .build();
        HolidayDAO holidayDAO = appDB.getHolidayDAO();
        DateNagerService dateNagerService = new DateNagerService(dateNagerApi, holidayDAO, executorService, logger);
        factory = new ViewModelFactory(dateNagerService);
    }

    public ViewModelProvider.Factory getViewModelFactory() {
        return factory;
    }
}
