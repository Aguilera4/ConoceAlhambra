package com.example.alhambrainteractiva;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Historia extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView texto;
    Spinner spinner;
    // Se crea la actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historia);
        texto = (TextView) findViewById(R.id.textViewHistoria);
        texto.setMovementMethod(new ScrollingMovementMethod());
        spinner = (Spinner) findViewById(R.id.spinnerHistoria);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        texto.scrollTo(0,0);
        switch (position){
            case 0:
                texto.setText(R.string.sigloA);
                break;
            case 1:
                texto.setText(R.string.sigloB);
                break;
            case 2:
                texto.setText(R.string.sigloC);
                break;
            case 3:
                texto.setText(R.string.sigloD);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
