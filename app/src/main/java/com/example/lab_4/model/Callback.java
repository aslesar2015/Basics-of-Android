package com.example.lab_4.model;

public interface Callback<T> {

    void onError(Throwable error);

    void onResults(T data);
}

