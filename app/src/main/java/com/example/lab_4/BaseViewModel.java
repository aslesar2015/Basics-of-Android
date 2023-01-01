package com.example.lab_4;

import androidx.lifecycle.ViewModel;

import com.example.lab_4.model.DateNagerService;

public class BaseViewModel extends ViewModel {

    private DateNagerService dateNagerService;

    public BaseViewModel(DateNagerService dateNagerService) {
        this.dateNagerService = dateNagerService;
    }

    protected DateNagerService getDateNagerService() {
        return dateNagerService;
    }
}
