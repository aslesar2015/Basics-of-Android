package com.example.lab_4.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.lab_4.BaseViewModel;
import com.example.lab_4.model.Callback;
import com.example.lab_4.model.Cancellable;
import com.example.lab_4.model.DateNagerService;
import com.example.lab_4.model.Holiday;
import com.example.lab_4.model.Result;

import java.util.List;

public class MainViewModel extends BaseViewModel {

    private Result<List<Holiday>> holidaysResult = Result.empty();
    private MutableLiveData<ViewState> stateMutableLiveData = new MutableLiveData<>();
    private Cancellable cancellable;

    {
        updateViewState(Result.empty());
    }

    public MainViewModel(DateNagerService dateNagerService) {
        super(dateNagerService);
    }

    private void updateViewState(Result<List<Holiday>> holidaysResult) {
        this.holidaysResult = holidaysResult;
        ViewState state = new ViewState();
        state.enableSearchButton = holidaysResult.getStatus() != Result.Status.LOADING;
        state.showList = holidaysResult.getStatus() == Result.Status.SUCCESS;
        state.showEmptyHint = holidaysResult.getStatus() == Result.Status.EMPTY;
        state.showError = holidaysResult.getStatus() == Result.Status.ERROR;
        state.showProgress = holidaysResult.getStatus() == Result.Status.LOADING;
        state.holidayList = holidaysResult.getData();
        stateMutableLiveData.postValue(state);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (cancellable != null) cancellable.cancel();
    }

    public LiveData<ViewState> getViewState() {
        return stateMutableLiveData;
    }

    public void getHolidays(String holidayCountryCode) {
        updateViewState(Result.loading());
        cancellable = getDateNagerService().getHolidays(holidayCountryCode, new Callback<List<Holiday>>() {
            @Override
            public void onError(Throwable error) {
                if (holidaysResult.getStatus() != Result.Status.SUCCESS)
                    updateViewState(Result.error(error));
            }

            @Override
            public void onResults(List<Holiday> data) {
                updateViewState(Result.success(data));
            }
        });
    }

    static class ViewState {
        private boolean enableSearchButton;
        private boolean showList;
        private boolean showEmptyHint;
        private boolean showError;
        private boolean showProgress;
        private List<Holiday> holidayList;

        public boolean isEnableSearchButton() {
            return enableSearchButton;
        }

        public boolean isShowList() {
            return showList;
        }

        public boolean isShowEmptyHint() {
            return showEmptyHint;
        }

        public boolean isShowError() {
            return showError;
        }

        public boolean isShowProgress() {
            return showProgress;
        }

        public List<Holiday> getHolidayList() {
            return holidayList;
        }
    }
}
