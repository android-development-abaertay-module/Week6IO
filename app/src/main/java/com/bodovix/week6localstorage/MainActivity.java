package com.bodovix.week6localstorage;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.bodovix.week6localstorage.model.FileHelper;

public class MainActivity extends AppCompatActivity {

    private static final int WRITE_EXTERNAL_STORAGE_REQUEST = 1;
    private static final int READ_EXTERNAL_STORAGE_REQUEST = 2;
    EditText multiTextView;
    CheckBox isExternalCB;
    Button saveBtn;
    Button clearBtn;
    Button loadBtn;

    String fileName = "internal.txt";
    FileHelper fileHelper;
    Handler handler;
    Runnable saveRunnable;
    Runnable loadRunnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        multiTextView = findViewById(R.id.multiLineET);
        saveBtn = findViewById(R.id.saveBtn);
        clearBtn = findViewById(R.id.clearBtn);
        loadBtn = findViewById(R.id.loadBtn);
        isExternalCB = findViewById(R.id.useExternalCB);

        checkPermissions();
        
        fileHelper = new FileHelper(getApplicationContext());
        handler = new Handler(){
            public void handleMessage (Message msg){
                switch (msg.getData().getString("method")){
                    case "saveInternal":
                        multiTextView.setText("");
                        break;
                    case "loadInternal":
                        String result = msg.getData().getString("result");
                        multiTextView.setText(result);
                        break;
                }
            }
        };

        saveRunnable = new Runnable() {
            @Override
            public void run() {
                String result = null;
                if (isExternalCB.isChecked()) {
                    fileHelper.saveToInternalStorage(fileName, multiTextView.getText().toString());
                }else{
                    fileHelper.saveToExternalStorage(fileName,multiTextView.getText().toString());
                }
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("method","saveInternal");

                message.setData(bundle);
                handler.sendMessage(message);
            }
        };

        loadRunnable = new Runnable() {
            @Override
            public void run() {
                String result = null;
                if (isExternalCB.isChecked()) {
                    result = fileHelper.loadFromInternalStorage(fileName);
                }else{
                    result = fileHelper.loadFromExternalStorage(fileName);
                }
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("method","loadInternal");
                bundle.putString("result",result);
                message.setData(bundle);
                handler.sendMessage(message);
            }
        };


    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_EXTERNAL_STORAGE_REQUEST);

        } else {
            //permissions already granted
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    READ_EXTERNAL_STORAGE_REQUEST);

        } else {
            //permissions already granted
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case WRITE_EXTERNAL_STORAGE_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("gs", "granted");
                    //permissions granted
                } else {
                    Toast.makeText(this, "Permission Required. App may not function correctly", Toast.LENGTH_SHORT).show();
                }
                break;
            case READ_EXTERNAL_STORAGE_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("gs", "granted");
                    //permissions granted
                } else {
                    Toast.makeText(this, "Read External Permission Required. App may not function correctly", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    public void saveBtn_Clicked(View view) {
        saveRunnable.run();
    }

    public void clearBtn_Clicked(View view) {
        multiTextView.setText("");
    }

    public void loadBtn_Clicked(View view) {
        loadRunnable.run();
    }
}
