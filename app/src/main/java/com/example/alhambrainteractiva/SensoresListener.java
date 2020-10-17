package com.example.alhambrainteractiva;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SensoresListener extends AppCompatActivity implements SensorEventListener {
    //Declaracion de variables para controlar los sensores anteriores y la secuencia para lanzar el asistente
    private long horaUltimaActualizacion;
    private int contMovimientos, movDetectado;
    private float ultimaGravedadZ;
    private boolean enSecuenciaAsistente;
    static final int RANGO_MAXIMO = 313;// 1.25/4
    static final int RANGO_MINIMO = 186;// 0.75/4
    static final int MINIMA_ACC = 15;
    Context contexto;

    //se obtiene el contexto desde donde se instancia un objeto de la clase
    SensoresListener(Context context) {
        contexto = context;
        //inicialización de las variables anteriores
        horaUltimaActualizacion = 0;
        contMovimientos = 0;
        movDetectado = -1; //-1=sin movimientos interesantes
        //0=movimiento hacia arriba
        //1=movimiento hacia abajo
        enSecuenciaAsistente = false;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        /*Hay que comprobar si registrando a maxima velocidad y luego filtrando aquí va mejor*/
        //-1=sin movimientos interesantes
        //0=movimiento hacia arriba
        //1=movimiento hacia abajo
        //si se tienen datos de gravity se actualiza su variable
        if(sensorEvent.sensor.getType() == Sensor.TYPE_GRAVITY ){
            ultimaGravedadZ = sensorEvent.values[2];
        }
        //para el acelerometro
        else if (sensorEvent.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            long horaActual = sensorEvent.timestamp / 1000000;//paso de nanosegundos a milisegundos

            //si se esta dentro del rango de la secuencia
            if (horaActual - horaUltimaActualizacion > RANGO_MINIMO / 2) {
                //se desechan los valores que no estén dentro de la mitad del rango mínimo de tiempo
                if (!enSecuenciaAsistente && sensorEvent.values[2] < -MINIMA_ACC && ultimaGravedadZ > 9.4) {
                    //si  no se está en secuencia, hay un movimiento brusco hacia arriba y la ultima posición del movil es con la pantalla hacia arriba
                    enSecuenciaAsistente = true;
                    contMovimientos = 1;
                    movDetectado = 0;
                    horaUltimaActualizacion = horaActual;
                } else if (enSecuenciaAsistente && horaActual - horaUltimaActualizacion <= RANGO_MAXIMO) {
                    //en secuencia y dentro del rango de tiempo
                    if (movDetectado == 0) {//el ultimo movimiento detectado es 0, hacia arriba
                        if (sensorEvent.values[2] > MINIMA_ACC && ultimaGravedadZ > 9.0) {//movimiento brusco hacia abajo
                            contMovimientos++;
                            movDetectado = 1;
                            horaUltimaActualizacion = horaActual;
                        }
                        //si se detectan dos movimientos hacia arriba seguidos puede que siga siendo el mismo aunque leido dos veces, y por tanto no se toca nada
                    } else if (movDetectado == 1) {//el ultimo movimiento detectado es 1, hacia abajo
                        if (sensorEvent.values[2] < -MINIMA_ACC && ultimaGravedadZ > 9.0) {//movimiento brusco hacia arriba
                            contMovimientos++;
                            movDetectado = 0;
                            horaUltimaActualizacion = horaActual;
                        }
                        //si se detectan dos movimientos hacia abajo seguidos puede que siga siendo el mismo, y por tanto no se toca nada
                    }
                }
                //si se está en secuencia pero se ha superado el tiempo máximo, y además se detecta un movimiento brusco, se da la pista de que hay que mover más rápido el movil
                else if (enSecuenciaAsistente && horaActual - horaUltimaActualizacion > RANGO_MAXIMO){
                    enSecuenciaAsistente = false;
                    contMovimientos=0;
                }
                //lanzar el asistente si se ha detectado la secuencia de movimientos e inicializar las variables
                if (contMovimientos == 4) {
                    lanzarAsistente();
                    enSecuenciaAsistente = false;
                    contMovimientos = 0;
                }
            }
        }
        //para el sensor de proximidad, cuando se detecte que esta a cero
        else if (sensorEvent.sensor.getType() == Sensor.TYPE_PROXIMITY){
            if (sensorEvent.values[0] == 0) {
                modificarSonido();
            }
        }

    }
    void lanzarAsistente(){
      //  Intent intent = new Intent(SensoresListener.this,Asistente.class);
       // startActivity(intent);
        Toast.makeText(contexto, "Aquí se lanzaría el asistente", Toast.LENGTH_SHORT).show();
    }
    private void modificarSonido(){
        AudioManager audioManager = (AudioManager) contexto.getSystemService(Context.AUDIO_SERVICE);
        if (audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) != 0){//si no esta silenciado
            //Se silencia el stream de música, STREAM_MUSIC silencia el sonido multimedia
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE,0);
        }
        else{//si no tiene sonido, se pone al máximo
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}


