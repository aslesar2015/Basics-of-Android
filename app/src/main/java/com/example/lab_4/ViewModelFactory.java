package com.example.lab_4;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.lab_4.model.DateNagerService;

import java.lang.reflect.Constructor;

public class ViewModelFactory implements ViewModelProvider.Factory {
    public static final String TAG = ViewModelFactory.class.getSimpleName();
    private DateNagerService dateNagerService;

    public ViewModelFactory(DateNagerService dateNagerService) {
        this.dateNagerService = dateNagerService;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        Constructor<T> constructor = null;
        try {
            constructor = modelClass.getConstructor(DateNagerService.class);
            return constructor.newInstance(dateNagerService);
        } catch (ReflectiveOperationException e) {
            Log.e(TAG,"Error",e);
            RuntimeException exception = new RuntimeException();
            exception.initCause(e);
            throw exception;
        }
    }
}
