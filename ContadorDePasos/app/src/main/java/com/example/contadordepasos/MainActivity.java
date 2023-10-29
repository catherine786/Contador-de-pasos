package com.example.contadordepasos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SensorManager mSensorManager = null;
    private Sensor stepSensor;
    private int pasosTotales = 0;
    private int previosPasosTotales;
    private ProgressBar progressBar;
    private TextView pasos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        pasos = findViewById(R.id.pasos);

        resetSteps();
        loadData();
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        stepSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);


    }
    protected void onResume(){
        super.onResume();

        if (stepSensor == null){
            Toast.makeText(this, "Este dispositivo no tiene Sensor", Toast.LENGTH_SHORT).show();
        }
        else {
            mSensorManager.registerListener((SensorEventListener) this,stepSensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
    protected void onPause(){
        super.onPause();
        mSensorManager.unregisterListener((SensorEventListener) this);
    }


    public void  onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER){
            pasosTotales = (int) event.values[0];
            int pasosActuales = pasosTotales-previosPasosTotales;
            pasos.setText(String.valueOf(pasosActuales));

            progressBar.setProgress(pasosActuales);
        }
    }

    private void resetSteps(){
        pasos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Mantenga presiionado para resetear los pasos", Toast.LENGTH_SHORT).show();
            }
        });

        pasos.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                    previosPasosTotales = pasosTotales;
                    pasos.setText("0");
                    progressBar.setProgress(0);
                    saveData();
                    return true;
            }
        });
    }

    private void saveData(){
        SharedPreferences sharedPref = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("key1", String.valueOf(previosPasosTotales));
        editor.apply();

    }

    private void loadData(){
        SharedPreferences sharedPref = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        int savedNumber = (int) sharedPref.getFloat("key1", 0f);
        previosPasosTotales = savedNumber;
    }




    //public void onAccuracyChanged(Sensor sensor, int accuracy);

}