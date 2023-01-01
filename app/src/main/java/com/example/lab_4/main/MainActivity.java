package com.example.lab_4.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.lab_4.App;
import com.example.lab_4.R;
import com.example.lab_4.details.DetailsActivity;

public class MainActivity extends BaseActivity {

    private Button searchButton;
    private RecyclerView holidaysViewList;
    private EditText countryCodeHolidayEditText;
    private ProgressBar progressBar;
    private TextView emptyTextView;
    private TextView errorTextView;

    private MainViewModel mainViewModel;
    private HolidaysAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // устанавливается разметка

        searchButton = findViewById(R.id.searchButton);
        holidaysViewList = findViewById(R.id.holidaysList);
        countryCodeHolidayEditText = findViewById(R.id.countryCodeHolidayEditText);
        progressBar = findViewById(R.id.progress);
        emptyTextView = findViewById(R.id.emptyTextView);
        errorTextView = findViewById(R.id.errorTextView);

        App app = (App) getApplication();
        ViewModelProvider viewModelProvider = new ViewModelProvider(this, app.getViewModelFactory());
        mainViewModel = viewModelProvider.get(MainViewModel.class);

        mainViewModel.getViewState().observe(this, state -> {
            searchButton.setEnabled(state.isEnableSearchButton());
            holidaysViewList.setVisibility(toVisibility(state.isShowList()));
            progressBar.setVisibility(toVisibility(state.isShowProgress()));
            emptyTextView.setVisibility(toVisibility(state.isShowEmptyHint()));
            errorTextView.setVisibility(toVisibility(state.isShowError()));

            adapter.setHolidays(state.getHolidayList());
        });

        searchButton.setOnClickListener(v -> {
            String countryCodeHoliday = countryCodeHolidayEditText.getText().toString();
            mainViewModel.getHolidays(countryCodeHoliday);
        });

        initHolidaysList();
    }

    private void initHolidaysList() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        holidaysViewList.setLayoutManager(layoutManager);
        adapter = new HolidaysAdapter(holiday -> {
            System.out.println(holiday);
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra(DetailsActivity.EXTRA_HOLIDAYS_NAME, holiday.getName());
            intent.putExtra(DetailsActivity.EXTRA_COUNTY_CODE, holiday.getCountryCode());
            intent.putExtra(DetailsActivity.EXTRA_HOLIDAYS_DATE, holiday.getDate());
            intent.putExtra(DetailsActivity.EXTRA_LAUNCH_YEAR, holiday.getLaunchYear());
            startActivity(intent);
        });
        holidaysViewList.setAdapter(adapter);
    }

    static int toVisibility(boolean show) {
        return show ? View.VISIBLE : View.GONE;
    }
}