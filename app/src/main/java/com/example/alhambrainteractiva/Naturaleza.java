package com.example.alhambrainteractiva;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Naturaleza extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {
    TextView texto;
    Spinner spinner;

    // Se crea la actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_naturaleza);
        texto = (TextView) findViewById(R.id.textViewNaturaleza);
        texto.setMovementMethod(new ScrollingMovementMethod());
        spinner = (Spinner) findViewById(R.id.spinnerNaturaleza);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        texto.scrollTo(0,0);
        switch (position) {
            case 0:
                texto.setText(R.string.introNaturaleza);
                break;
            case 1:
                texto.setText(R.string.bio);
                break;
            case 2:
                texto.setText(R.string.bosques);
                break;
            case 3:
                texto.setText(R.string.huertas);
                break;
            case 4:
                texto.setText(R.string.jardines);
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
