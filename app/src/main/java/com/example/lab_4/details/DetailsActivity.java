package com.example.lab_4.details;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.lab_4.App;
import com.example.lab_4.R;

public class DetailsActivity extends AppCompatActivity {

    public static final String EXTRA_HOLIDAYS_NAME = "HOLIDAYS_NAME";
    public static final String EXTRA_COUNTY_CODE = "COUNTY_CODE";
    public static final String EXTRA_HOLIDAYS_DATE = "HOLIDAYS_DATE";
    public static final String EXTRA_LAUNCH_YEAR = "LAUNCH_YEAR";

    private TextView nameTextView;
    private TextView dateTextView;
    private TextView launchYearTextView;
    private TextView countryCodeTextView;

    private DetailsViewModel detailsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        nameTextView = findViewById(R.id.nameTextView);
        dateTextView = findViewById(R.id.dateTextView);
        launchYearTextView = findViewById(R.id.launchYearTextView);
        countryCodeTextView = findViewById(R.id.countryCodeTextView);

        String holidayName = getIntent().getStringExtra(EXTRA_HOLIDAYS_NAME);
        String holidayCountryCode = getIntent().getStringExtra(EXTRA_COUNTY_CODE);
        String holidayDate = getIntent().getStringExtra(EXTRA_HOLIDAYS_DATE);
        int holidayLaunchYear = getIntent().getExtras().getInt(EXTRA_LAUNCH_YEAR);

        if (holidayName.isEmpty() && holidayCountryCode.isEmpty() && holidayDate.isEmpty()) {
            throw new RuntimeException("There is holiday haven`t name...");
        }
        nameTextView.setText(holidayName);
        dateTextView.setText(holidayDate);
        countryCodeTextView.setText(holidayCountryCode);
        launchYearTextView.setText(holidayLaunchYear == 0 ? "Unknown" : String.valueOf(holidayLaunchYear));

        App app = (App) getApplication();
        ViewModelProvider viewModelProvider = new ViewModelProvider(this, app.getViewModelFactory());
        detailsViewModel = viewModelProvider.get(DetailsViewModel.class);
    }
}
