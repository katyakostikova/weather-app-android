package com.example.weatherapptest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;


public class AppSettingsActivity extends AppCompatActivity {

    public static String settingsFileName = "weather_app_settings";

    private SharedPreferences sharedPreferences;

    private static final int AUTOCOMPLETE_REQUEST_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_settings);
        sharedPreferences = getSharedPreferences(settingsFileName, MODE_PRIVATE);

        ImageButton imageButtonBack = findViewById(R.id.buttonBackInSettings);
        imageButtonBack.setOnClickListener(v -> finish());

        //settings load
        loadSettings(sharedPreferences);

        //units settings
        CardView cardViewUnits = findViewById(R.id.cardViewUnits);
        cardViewUnits.setOnClickListener(v -> {
            UnitDialog unitDialog = new UnitDialog(this);
            unitDialog.setOnPressedCelsiusListener(v1 -> {
                saveUnitSettings(true);
                unitDialog.cancel();
            });
            unitDialog.setOnPressedFahrenheitListener(v12 -> {
                saveUnitSettings(false);
                unitDialog.cancel();
            });
            unitDialog.show();
        });

        //city settings
        CardView cardViewCity = findViewById(R.id.cardViewCity);
        cardViewCity.setOnClickListener(v -> {

            if (!Places.isInitialized()) {
                Places.initialize(getApplicationContext(), BuildConfig.PLACES_API_KEY);
            }

            List<Place.Field> fields = Arrays.asList(Place.Field.LAT_LNG, Place.Field.NAME);
            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                    .setTypeFilter(TypeFilter.CITIES)
                    .build(this);
            startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
        });

        //theme seettings
        Switch switchNightMode = findViewById(R.id.switchNightMode);
        switchNightMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                saveThemeSettings(isChecked);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                saveThemeSettings(isChecked);
            }
        });
    }

    private void saveThemeSettings(boolean isChecked) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("NIGHT_MODE", isChecked);
        editor.apply();
    }

    private void saveUnitSettings(boolean isCelsius) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        TextView textViewUnitsParams = findViewById(R.id.textViewUnitsParams);
        if (isCelsius) {
            editor.putString("UNIT_PARAMS", getResources().getString(R.string.celsius));
            textViewUnitsParams.setText(R.string.celsius);
        } else {
            editor.putString("UNIT_PARAMS", getResources().getString(R.string.fahrenheit));
            textViewUnitsParams.setText(R.string.fahrenheit);
        }
        editor.apply();
    }

    private void loadSettings(SharedPreferences sharedPreferences) {
        TextView textViewUnitsParams = findViewById(R.id.textViewUnitsParams);
        textViewUnitsParams.setText(sharedPreferences.getString("UNIT_PARAMS", getResources().getString(R.string.celsius)));

        TextView textViewCityParams = findViewById(R.id.textViewCityParams);
        textViewCityParams.setText(sharedPreferences.getString("CITY_NAME", getResources().getString(R.string.kyiv)));

        Switch switchNightMode = findViewById(R.id.switchNightMode);
        if (sharedPreferences.getBoolean("NIGHT_MODE", false)) {
            switchNightMode.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            switchNightMode.setChecked(false);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private static class UnitDialog extends Dialog {

        public UnitDialog(@NonNull Context context) {
            super(context);
            initDialog(context);
        }

        private void initDialog(Context context) {
            setContentView(R.layout.dialog_units);
            setCancelable(true);
        }

        public void setOnPressedCelsiusListener(View.OnClickListener onClickListener) {
            findViewById(R.id.buttonC).setOnClickListener(onClickListener);
        }

        public void setOnPressedFahrenheitListener(View.OnClickListener onClickListener) {
            findViewById(R.id.buttonF).setOnClickListener(onClickListener);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    Place place = Autocomplete.getPlaceFromIntent(data);
                    TextView textViewCityParams = findViewById(R.id.textViewCityParams);
                    textViewCityParams.setText(place.getName());

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("CITY_LAT", Double.toString(place.getLatLng().latitude));
                    editor.putString("CITY_LON", Double.toString(place.getLatLng().longitude));
                    editor.putString("CITY_NAME", place.getName());
                    editor.apply();

                    Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);


                }
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                if (data != null) {
                    Status status = Autocomplete.getStatusFromIntent(data);
                    Toast.makeText(this, status.getStatusMessage(), Toast.LENGTH_LONG).show();
                }
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}