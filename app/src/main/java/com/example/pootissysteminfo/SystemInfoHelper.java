package com.example.pootissysteminfo;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

public class SystemInfoHelper {

    public static String getManufacturer() {
        return Build.MANUFACTURER.toUpperCase();
    }

    public static String getCpuHardware() {
        return Build.HARDWARE; // Esto suele decir si es Qualcomm, MediaTek, etc.
    }

    // --- NUEVO: Detectar Núcleos ---
    public static int getNumberOfCores() {
        try {
            File dir = new File("/sys/devices/system/cpu/");
            File[] files = dir.listFiles(pathname -> Pattern.matches("cpu[0-9]+", pathname.getName()));
            return files.length;
        } catch (Exception e) {
            return Runtime.getRuntime().availableProcessors();
        }
    }

    // --- NUEVO: Obtener Velocidad Estimada ---
    public static String getCpuSpeed() {
        return "Frecuencia: " + (Build.SUPPORTED_ABIS[0].contains("64") ? "2.84 GHz" : "2.0 GHz");
    }

    public static String getBatteryInfo(Context context) {
        Intent intent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        if (intent != null) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            float temp = ((float) intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0)) / 10;
            return level + "% | " + temp + "°C";
        }
        return "N/A";
    }

    public static String getStorageInfo() {
        StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
        long bytesAvailable = stat.getBlockSizeLong() * stat.getAvailableBlocksLong();
        return (bytesAvailable / (1024 * 1024 * 1024)) + " GB Libres";
    }

    public static float getCpuSimulated() {
        return (float) (Math.random() * 20) + 10;
    }

    public static int getRamUsage(Context context) {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am != null) {
            am.getMemoryInfo(mi);
            double usedMem = mi.totalMem - mi.availMem;
            return (int) (usedMem / (double) mi.totalMem * 100.0);
        }
        return 0;
    }
}