package com.zhenlin.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;

    final float orientation_data [] = new float[3];

    private TextView textViewX;
    private TextView textViewY;
    private TextView textViewZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewX = (TextView)findViewById(R.id.textView);
        textViewY = (TextView)findViewById(R.id.textView2);
        textViewZ = (TextView)findViewById(R.id.textView3);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor s : deviceSensors) {
            Log.d("Name", s.getName());  // Obtain the sensor’s name
            Log.d("Type", String.valueOf(s.getType()));     // Obtain the sensor’s types
            Log.d("vendor", s.getVendor());    // Obtain the sensor’s vendors
            Log.d("Version", String.valueOf(s.getVersion()));    // Obtain the sensor’s version
            Log.d("Resolution", String.valueOf(s.getResolution()));   // Obtain the sensor’s resolution
            Log.d("Range", String.valueOf(s.getMaximumRange()));   // Obtain the sensor’s maximum range
            Log.d("Power", String.valueOf(s.getPower()));        // Obtain the sensor’s power consumption
        }

        if (sensorManager == null) {
            Log.d("error", "device does not support SensorManager");
        } else {
            //  G-Sensor
            Sensor SensorOrientation = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
            sensorManager.registerListener(this, SensorOrientation , sensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == null)
            return;
        if (event.sensor.getType() == Sensor.TYPE_ORIENTATION){
            System.arraycopy(event.values, 0 ,orientation_data, 0, event.values.length);
        }

        textViewX.setText("Azimuth:"+String.valueOf(orientation_data[0]));
        textViewY.setText("Pitch:"+String.valueOf(orientation_data[1]));
        textViewZ.setText("Roll:"+String.valueOf(orientation_data[2]));

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (sensorManager != null){
            sensorManager.unregisterListener(this);
            sensorManager = null;
        }
    }
}
