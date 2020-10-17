package com.example.alhambrainteractiva;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import co.gofynd.gravityview.GravityView;

public class Giroscopio extends AppCompatActivity implements SensorEventListener {

    private GravityView gravityView;
    private boolean esSoportado;
    private ImageView img;
    private int foto = R.drawable.im1;
    private Button btn;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giroscopio);
        init();
        comprobarSoportado();
        btn = (Button) findViewById(R.id.botonSiguienteFoto);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                siguienteFoto(v);
            }
        });

        textView = (TextView) findViewById(R.id.textView12);
        textView.setText(R.string.patioLeones);
    }


    // Establecemos la imagen
    public void comprobarSoportado(){
        if(esSoportado){
            this.gravityView.setImage(img,foto).center();
        }else{
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),foto);
            img.setImageBitmap(bitmap);
        }
    }

    // Inicializamos los objetos
    private void init(){
        this.img = findViewById(R.id.imageView);
        this.gravityView = GravityView.getInstance((getBaseContext()));
        this.esSoportado = gravityView.deviceSupported();
    }


    @Override
    protected void onResume(){
        super.onResume();
        gravityView.registerListener();
    }

    @Override
    protected void onPause(){
        super.onPause();
        gravityView.unRegisterListener();;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    // Cambiamos de imagen
    private void siguienteFoto(View v){
        if (foto == R.drawable.im1){
            foto = R.drawable.im2;
            textView.setText(R.string.salaReyes);
        }else if(foto == R.drawable.im2){
            foto = R.drawable.im3;
            textView.setText(R.string.generalife);
        }else if(foto == R.drawable.im3){
            foto = R.drawable.im4;
            textView.setText(R.string.salaMocarabes);
        }else if(foto == R.drawable.im4){
            foto = R.drawable.im5;
            textView.setText(R.string.salaBarca);
        }else{
            foto = R.drawable.im1;
            textView.setText(R.string.patioLeones);
        }
        comprobarSoportado();
    }
}
