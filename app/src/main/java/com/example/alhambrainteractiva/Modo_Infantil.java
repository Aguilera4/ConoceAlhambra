package com.example.alhambrainteractiva;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

// Necesario para cambiar de Activities
import android.content.Intent;

// Necesario para deteccion de movimientos táctiles
import androidx.core.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;

// Necesario para el menu de opciones
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

// Necesario para comunicación con el usuario
import android.widget.Toast;

// Necesario para gestionar ActionBar
import androidx.appcompat.app.ActionBar;

// Necesario para el menu inferior
import com.google.android.material.bottomnavigation.BottomNavigationView;

// Otros
import androidx.annotation.NonNull;

public class Modo_Infantil extends AppCompatActivity implements GestureDetector.OnGestureListener {

    // Datos miembro

    private GestureDetectorCompat detector;

    // Metodos

    // La actividad está creada
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modo__infantil);

        //Toast.makeText(this, "Infantil OnCreate", Toast.LENGTH_SHORT).show();

        // Icono de la app en el ActionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);

        // Activamos deteccion de gestos
        detector = new GestureDetectorCompat(this,this);

        // Navegacion en menu inferior
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_infantil);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent a = new Intent(Modo_Infantil.this, MainActivity.class);
                        startActivity(a);
                        finish();
                        overridePendingTransition(R.anim.from_right_in, R.anim.from_left_out);
                        break;
                    case R.id.navigation_adulto:
                        Intent b = new Intent(Modo_Infantil.this, Modo_Adulto.class);
                        startActivity(b);
                        finish();
                        overridePendingTransition(R.anim.from_right_in, R.anim.from_left_out);
                        break;
                    case R.id.navigation_infantil:
                        break;
                }
                return false;
            }
        });

    }

    // La actividad está a punto de hacerse visible
    @Override
    protected void onStart() {
        super.onStart();
        //Toast.makeText(this, "Infantil OnStart", Toast.LENGTH_SHORT).show();

    }

    // La actividad se ha vuelto visible (ahora se "reanuda").
    @Override
    protected void onResume() {
        super.onResume();
        //Toast.makeText(this, "Infantil OnResume", Toast.LENGTH_SHORT).show();
    }

    // Enfocarse en otra actividad  (esta actividad está a punto de ser "detenida").
    @Override
    protected void onPause() {
        super.onPause();
        //Toast.makeText(this, "Infantil OnPause", Toast.LENGTH_SHORT).show();
    }

    // La actividad ya no es visible (ahora está "detenida")
    @Override
    protected void onStop() {
        super.onStop();
        //Toast.makeText(this, "Infantil OnStop", Toast.LENGTH_SHORT).show();
    }

    // La actividad está a punto de ser destruida.
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Toast.makeText(this, "Infantil OnDestroy", Toast.LENGTH_SHORT).show();
    }

    // Metodo para cambiar a Main utilizando una animacion en la transición
    public void desplazarIzquierda(){
        Intent intent = new Intent(this,MainActivity.class);
        // Cambiamos de activity
        startActivity(intent);
        // Destruimos el activity actual
        finish();
        // Animacion
        overridePendingTransition(R.anim.from_right_in, R.anim.from_left_out);
    }

    // ************************************************************************** //
    // ********************* Gestion de eventos táctiles ************************ //
    // ************************************************************************** //

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        detector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        int sensibilidad = 500;
        if((e1.getX() - e2.getX()) > sensibilidad){
            desplazarIzquierda();
            //Toast.makeText(Modo_Infantil.this,"Mover izquierda", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    // Metodo para añadir un menu en el ActionBar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.opciones_main_activity,menu);
        return true;
    }

    // Metodo para controlar las acciones del menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        super.onOptionsItemSelected(item);

        switch(item.getItemId()){
            case R.id.Idioma:
                Toast.makeText(getBaseContext(), "Has seleccionado Idioma", Toast.LENGTH_SHORT).show();
                break;

            case R.id.Accesibilidad:
                Toast.makeText(getBaseContext(), "Has seleccionado Accesibilidad", Toast.LENGTH_SHORT).show();
                break;

            case R.id.Info:
                Toast.makeText(getBaseContext(), "Has seleccionado Info", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}
