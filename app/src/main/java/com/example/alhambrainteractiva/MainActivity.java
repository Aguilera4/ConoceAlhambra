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
import android.view.View;
import androidx.annotation.NonNull;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener{

    // Datos miembro

    private GestureDetectorCompat detector;
    int GLOBAL_TOUCH_POSITION_Y = 0;
    int GLOBAL_TOUCH_CURRENT_POSITION_Y = 0;

    // Metodos

    // Se crea la actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toast.makeText(this, "Main OnCreate", Toast.LENGTH_SHORT).show();

        // Icono de la app en el ActionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);

        // Activamos deteccion de gestos
        detector = new GestureDetectorCompat(this, this);

        // Navegacion en menu inferior
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_home);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        break;
                    case R.id.navigation_adulto:
                        Intent a = new Intent(MainActivity.this, Modo_Adulto.class);
                        startActivity(a);
                        finish();
                        overridePendingTransition(R.anim.from_right_in, R.anim.from_left_out);
                        break;
                    case R.id.navigation_infantil:
                        Intent b = new Intent(MainActivity.this, Modo_Infantil.class);
                        startActivity(b);
                        finish();
                        overridePendingTransition(R.anim.from_left_in, R.anim.from_right_out);
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
        //Toast.makeText(this, "Main OnStart", Toast.LENGTH_SHORT).show();
    }

    // La actividad se ha vuelto visible (ahora se "reanuda").
    @Override
    protected void onResume() {
        super.onResume();
        //Toast.makeText(this, "Main OnResume", Toast.LENGTH_SHORT).show();
    }

    // Enfocarse en otra actividad  (esta actividad está a punto de ser "detenida").
    @Override
    protected void onPause() {
        super.onPause();
        //Toast.makeText(this, "Main OnPause", Toast.LENGTH_SHORT).show();
    }

    // La actividad ya no es visible (ahora está "detenida")
    @Override
    protected void onStop() {
        super.onStop();
        //Toast.makeText(this, "Main OnStop", Toast.LENGTH_SHORT).show();
    }

    // La actividad está a punto de ser destruida.
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Toast.makeText(this, "Main OnDestroy", Toast.LENGTH_SHORT).show();
    }

    // Metodo para cambiar a Modo Adulto utilizando una animacion en la transición
    public void desplazarIzquierda(){
        Intent intent = new Intent(this,Modo_Adulto.class);
        // Cambiamos de activity
        startActivity(intent);
        // Destruimos el activity actual
        finish();
        // Animacion
        overridePendingTransition(R.anim.from_right_in, R.anim.from_left_out);
    }

    // Metodo para cambiar a Modo Infantil utilizando una animacion en la transición
    public void desplazarDerecha(){
        Intent intent = new Intent(this,Modo_Infantil.class);
        // Cambiamos de activity
        startActivity(intent);
        // Destruimos el activity actual
        finish();
        // Animacion
        overridePendingTransition(R.anim.from_left_in, R.anim.from_right_out);
    }

    // ************************************************************************** //
    // ********************* Gestion de eventos táctiles ************************ //
    // ************************************************************************** //

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Si detectamos multiples punteros
        if (event.getPointerCount() == 2) {
            int accion = event.getActionMasked();
            switch (accion)
            {
                case MotionEvent.ACTION_DOWN:
                    GLOBAL_TOUCH_POSITION_Y = (int) event.getY(1);
                    break;
                case MotionEvent.ACTION_MOVE:
                    GLOBAL_TOUCH_CURRENT_POSITION_Y = (int) event.getY(1);
                    int diff_y = GLOBAL_TOUCH_POSITION_Y-GLOBAL_TOUCH_CURRENT_POSITION_Y;
                    if (diff_y < -200) {
                        accionMultitouch();
                    }
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    GLOBAL_TOUCH_POSITION_Y = (int) event.getY(1);
                    break;
                case MotionEvent.ACTION_UP:
                    GLOBAL_TOUCH_CURRENT_POSITION_Y = 0;
                    break;
            }
        }else{
            if(detector.onTouchEvent(event)){return true;}
        }
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
            //Toast.makeText(MainActivity.this,"Mover izquierda", Toast.LENGTH_SHORT).show();
        }else if((e2.getX() - e1.getX()) > sensibilidad){
            desplazarDerecha();
            //Toast.makeText(MainActivity.this,"Mover derecha", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    public void accionMultitouch(){
        Toast.makeText(MainActivity.this,"Detectado Multitouch", Toast.LENGTH_SHORT).show();
        Intent activity = new Intent(MainActivity.this, LectorActivity.class);
        startActivity(activity);
    }


    // ************************************************************************** //
    // ********************* Fin de eventos táctiles **************************** //
    // ************************************************************************** //

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
        Intent activity;
        switch(item.getItemId()){
            case R.id.Idioma:
                activity = new Intent(MainActivity.this, OpcionesIdioma.class);
                startActivity(activity);
                //finish();
                //Toast.makeText(getBaseContext(), "Has seleccionado Idioma", Toast.LENGTH_SHORT).show();
                break;

            case R.id.Accesibilidad:
                activity = new Intent(MainActivity.this, OpcionesAccesibilidad.class);
                startActivity(activity);
                //finish();
                //Toast.makeText(getBaseContext(), "Has seleccionado Accesibilidad", Toast.LENGTH_SHORT).show();
                break;

            case R.id.Info:
                activity = new Intent(MainActivity.this, OpcionesInformacion.class);
                startActivity(activity);
                //finish();
                //Toast.makeText(getBaseContext(), "Has seleccionado Info", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    public void mapa(View v){
        Intent activity = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(activity);
        //finish();
    }

    public void tutorial(View v){
        Intent activity = new Intent(MainActivity.this, Tutorial.class);
        startActivity(activity);
        //finish();
    }

    public void alhambrapedia(View v){
        Intent activity = new Intent(MainActivity.this, Alhambrapedia.class);
        startActivity(activity);
        //finish();
    }
}