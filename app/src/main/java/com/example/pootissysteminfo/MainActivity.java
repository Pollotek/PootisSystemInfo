package com.example.pootissysteminfo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private TextView cpuText, ramText, txtFabricante, txtCpuModel, txtBattery, txtStorage, txtCores, txtSpeed;
    private CustomGraphView cpuGraph;
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Enlazar todo
        txtFabricante = findViewById(R.id.txtFabricante);
        txtCpuModel = findViewById(R.id.txtCpuModel);
        cpuText = findViewById(R.id.cpuText);
        ramText = findViewById(R.id.ramText);
        cpuGraph = findViewById(R.id.cpuGraph);
        txtBattery = findViewById(R.id.txtBattery);
        txtStorage = findViewById(R.id.txtStorage);
        txtCores = findViewById(R.id.txtCores);
        txtSpeed = findViewById(R.id.txtSpeed);

        // Datos que no cambian
        txtFabricante.setText(SystemInfoHelper.getManufacturer());
        txtCpuModel.setText(SystemInfoHelper.getCpuHardware());
        txtCores.setText("Núcleos: " + SystemInfoHelper.getNumberOfCores());
        txtSpeed.setText(SystemInfoHelper.getCpuSpeed());

        iniciarMonitoreo();
    }

    private void iniciarMonitoreo() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                float cpuVal = SystemInfoHelper.getCpuSimulated();
                int ramVal = SystemInfoHelper.getRamUsage(MainActivity.this);

                cpuText.setText((int)cpuVal + "%");
                ramText.setText(ramVal + "%");
                cpuGraph.addDataPoint(cpuVal);

                txtBattery.setText("Batería: " + SystemInfoHelper.getBatteryInfo(MainActivity.this));
                txtStorage.setText("Disco: " + SystemInfoHelper.getStorageInfo());

                handler.postDelayed(this, 500);
            }
        });
    }
}