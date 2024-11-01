package com.example.flashcat.Activity.SettingUser;
import android.Manifest;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.flashcat.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.File;

public class StorageActivity extends AppCompatActivity {
    private static final int REQUEST_STORAGE_PERMISSION = 1;
    private ImageButton btnBack;
    private Button btnClear;
    private TextView txtData;
    private TextView txtCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);
        findID();
        requestStoragePermission();
    }
    public void findID(){

        btnBack = findViewById(R.id.back_storage);
        btnClear = findViewById(R.id.action_clear);
        txtData = findViewById(R.id.txt_data);
        txtCache = findViewById(R.id.txt_cache);
    }
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.MANAGE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.MANAGE_EXTERNAL_STORAGE},
                    REQUEST_STORAGE_PERMISSION);
        } else {
            // Permission already granted, proceed with file operations
            initializeStorage();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with file operations
                initializeStorage();
            } else {
                // Permission denied, handle accordingly
                // You can show a message or take appropriate action
                Log.e("StorageActivity", "Storage permission denied");
                openAppDetailsSettings();
            }
        }
    }
    private void initializeStorage() {
        // Initialize views and set click listeners after permission is granted
        // Lấy và hiển thị dung lượng data
        long dataSize = getAppDataSize();
        String formattedDataSize = formatSize(dataSize);
        txtData.setText(formattedDataSize);

        // Dung lượng cache
        long cacheSize = getCacheSize();
        String formattedCacheSize = formatSize(cacheSize);
        txtCache.setText(formattedCacheSize);

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogClear();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void openAppDetailsSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }

    private long getCacheSize() {
        File cacheDir = getCacheDir(); // Thư mục cache của ứng dụng
        return getDirectorySize(cacheDir);
    }
    private long getAppDataSize() {
        PackageManager packageManager = getPackageManager();
        String packageName = getPackageName();
        try {
            ApplicationInfo appInfo = packageManager.getApplicationInfo(packageName, 0);
            String appDir = appInfo.sourceDir;
            long appSize = new File(appDir).length(); // Dung lượng ứng dụng

            String dataDir = appInfo.dataDir;
            File dataDirectory = new File(dataDir);
            long dataSize = getDirectorySize(dataDirectory); // Dung lượng dữ liệu người dùng

            // Lấy dung lượng cache của ứng dụng
            File cacheDir = getCacheDir();
            long cacheSize = getDirectorySize(cacheDir);

            return appSize + dataSize + cacheSize; // Tổng dung lượng (ứng dụng + dữ liệu + cache)
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }


    private long getDirectorySize(File directory) {
        long directorySize = 0;
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        directorySize += file.length();
                    } else if (file.isDirectory()) {
                        directorySize += getDirectorySize(file);
                    }
                }
            }
        }
        return directorySize;
    }

    private void showDialogClear() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_clear_storage);

        Button btnClearData = dialog.findViewById(R.id.btn_delete_data);
        Button btnClearDataCache = dialog.findViewById(R.id.btn_delete_data_cache);


        btnClearData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                View overlay = findViewById(R.id.overlay);
                overlay.setVisibility(View.VISIBLE);
                new MaterialAlertDialogBuilder(StorageActivity.this) // Chú ý cần thêm "this" hoặc tên Activity
                        .setTitle(getResources().getString(R.string.title_dialog))
                        .setMessage(getResources().getString(R.string.supporting_text))
                        .setNeutralButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Xử lý khi nhấn nút neutral

                                overlay.setVisibility(View.GONE);

                            }
                        })

                        .setPositiveButton(getResources().getString(R.string.accept), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Xử lý khi nhấn nút positive
                                deleteAppData();
                                overlay.setVisibility(View.GONE);

                            }
                        })
                        .show();


            }
        });
        btnClearDataCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                View overlay = findViewById(R.id.overlay);
                overlay.setVisibility(View.VISIBLE);
                new MaterialAlertDialogBuilder(StorageActivity.this) // Chú ý cần thêm "this" hoặc tên Activity
                        .setTitle(getResources().getString(R.string.title_dialog))
                        .setMessage(getResources().getString(R.string.supporting_text))
                        .setNeutralButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Xử lý khi nhấn nút neutral


                                overlay.setVisibility(View.GONE);

                            }
                        })

                        .setPositiveButton(getResources().getString(R.string.accept), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Xử lý khi nhấn nút positive
                                deleteAppCache();
                                overlay.setVisibility(View.GONE);

                            }
                        })
                        .show();


            }
        });
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        View overlay = findViewById(R.id.overlay);
        overlay.setVisibility(View.VISIBLE);

    }
    private String formatSize(long size) {
        if (size <= 0) return "0 KB";

        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));


        double adjustedSize = size;
        int unitIndex = 0;
        while (adjustedSize >= 1024 && unitIndex < units.length - 1) {
            adjustedSize /= 1024;
            unitIndex++;
        }

        // Định dạng kết quả với đơn vị và số lượng chữ số thập phân phù hợp
        return String.format("%.2f %s", adjustedSize, units[unitIndex]);
    }
    private void deleteAppData() {
        try {
            File appDirectory = getFilesDir().getParentFile(); // Lấy thư mục gốc của ứng dụng
            String appPath = appDirectory.getAbsolutePath();
            if (appDirectory != null && appDirectory.isDirectory()) {
                deleteRecursive(appDirectory);
                initializeStorage();
                Log.d("StorageActivity", "Error deleting app data: " + appDirectory);

            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("Storage", "Error deleting app data: " + e.getMessage());
        }
    }


    private void deleteAppCache() {
        try {
            File cacheDir = getCacheDir(); // Thư mục cache của ứng dụng
            deleteRecursive(cacheDir);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteRecursive(File fileOrDirectory) {
        try {
            if (fileOrDirectory.exists()) {
                if (fileOrDirectory.isDirectory()) {
                    File[] files = fileOrDirectory.listFiles();
                    if (files != null) {
                        for (File child : files) {
                            deleteRecursive(child);
                        }
                    }
                }
                boolean deletionResult = fileOrDirectory.delete();
                if (!deletionResult) {
                    Log.e("StorageActivity", "Failed to delete: " + fileOrDirectory.getAbsolutePath());
                }
                Log.e("S", "Failed to delete: " + fileOrDirectory.getAbsolutePath());

            }

        } catch (SecurityException e) {
            Log.e("StorageActivity", "SecurityException while deleting: " + fileOrDirectory.getAbsolutePath(), e);
        } catch (Exception e) {
            Log.e("StorageActivity", "Exception while deleting: " + fileOrDirectory.getAbsolutePath(), e);
        }
    }




}