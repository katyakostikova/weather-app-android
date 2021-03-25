package com.example.weatherapptest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class AppSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_settings);

        ImageButton imageButtonBack = findViewById(R.id.buttonBackInSettings);
        imageButtonBack.setOnClickListener(v -> finish());

        CardView cardViewUnits = findViewById(R.id.cardViewUnits);
        cardViewUnits.setOnClickListener(v -> {
            UnitDialog unitDialog = new UnitDialog(this);
            unitDialog.setOnPressedCelsiusListener(v1 -> {
                //save C to settings
                TextView textViewUnitsParams = findViewById(R.id.textViewUnitsParams);
                textViewUnitsParams.setText(R.string.celsius);
                unitDialog.cancel();
            });
            unitDialog.setOnPressedFahrenheitListener(v12 -> {
                //save F to settings
                TextView textViewUnitsParams = findViewById(R.id.textViewUnitsParams);
                textViewUnitsParams.setText(R.string.fahrenheit);
                unitDialog.cancel();
            });
        unitDialog.show();;
        });
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