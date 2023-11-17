package com.example.myapplication;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
public class NuevoRetoActivity extends AppCompatActivity implements SensorEventListener{
    private SensorManager sensorManager;
    private Sensor stepSensor;
    private TextView stepCountTextView, distanceTextView;
    private int stepCount = 0;
    private boolean isChronometerRunning = false;
    private Chronometer chronometer;
    private float acceleration;
    private float previousAcceleration;
    private float distance;

    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_nuevo_reto);
        stepCountTextView = findViewById(R.id.stepCountTextView);
        distanceTextView = findViewById(R.id.distanceTextView);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        if (stepSensor == null) {
            stepCountTextView.setText("Este dispositivo no soporta el contador de pasos");
        }

        chronometer = findViewById(R.id.chronometer);
        Button startStopButton = findViewById(R.id.buttonStartStop);
        startStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleChronometer();
            }
        });
    }
    public void onCardViewClick(View view) {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void toggleChronometer() {
        if (isChronometerRunning) {
            chronometer.stop();
            chronometer.setBase(SystemClock.elapsedRealtime());
            stepCount = 0;
            distance = 0;
            isChronometerRunning = false;
        } else {
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
            isChronometerRunning = true;
        }
        updateUI();
    }
    private void updateUI() {
        stepCountTextView.setText("Pasos: " + stepCount);
        distanceTextView.setText("Distancia: " + String.format("%.2f", distance));
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            stepCount = (int) event.values[0];
        } else if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            float[] values = event.values;
            acceleration = Math.abs(values[0] + values[1] + values[2]);
            float deltaAcceleration = acceleration - previousAcceleration;
            distance += 0.5 * deltaAcceleration * 0.01 * 0.01;

            previousAcceleration = acceleration;

            Log.d("Acceleration", "Acceleration: " + acceleration);
            Log.d("Distance", "Distance: " + distance);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (stepSensor != null) {
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        // Registra el sensor de aceleración para calcular la distancia
        Sensor accelerationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        if (accelerationSensor != null) {
            sensorManager.registerListener(this, accelerationSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        new UpdateTask().execute();
        if (isChronometerRunning) {
            chronometer.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Desregistra los listeners de los sensores cuando la actividad está en segundo plano
        if (stepSensor != null) {
            sensorManager.unregisterListener(this, stepSensor);
        }

        Sensor accelerationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        if (accelerationSensor != null) {
            sensorManager.unregisterListener(this, accelerationSensor);
        }

        if (isChronometerRunning) {
            chronometer.stop();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    private class UpdateTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            while (!isCancelled()) {
                publishProgress();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            updateUI();
        }
    }

}
