package com.example.weatherapptest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


public class AppSettingsActivity extends AppCompatActivity {

    public static String settingsFileName = "weather_app_settings";

    private SharedPreferences sharedPreferences;

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
        unitDialog.show();;
        });

        CardView cardViewCity = findViewById(R.id.cardViewCity);
        cardViewCity.setOnClickListener(v -> {
            /*TODO
            Create new activity and add fragment there, open here new activty, start activity for result*/
        });
    }

    private void saveUnitSettings(boolean isCelsius) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        TextView textViewUnitsParams = findViewById(R.id.textViewUnitsParams);
        if(isCelsius) {
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
    }

    private class UnitDialog extends Dialog {

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
}