package com.bodovix.week6localstorage;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bodovix.week6localstorage.model.FileHelper;

public class MainActivity extends AppCompatActivity {

    EditText multiTextView;
    Button saveBtn;
    Button clearBtn;
    Button loadBtn;

    String internalStorage = "internal.txt";
    FileHelper fileHelper;
    Handler handler;
    Runnable saveInternalRunnable;
    Runnable loadInternalRunnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        multiTextView = findViewById(R.id.multiLineET);
        saveBtn = findViewById(R.id.saveBtn);
        clearBtn = findViewById(R.id.clearBtn);
        loadBtn = findViewById(R.id.loadBtn);

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

        saveInternalRunnable = new Runnable() {
            @Override
            public void run() {
                fileHelper.saveToInternalStorage(internalStorage,multiTextView.getText().toString());

                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("method","saveInternal");

                message.setData(bundle);
                handler.sendMessage(message);
            }
        };
        loadInternalRunnable = new Runnable() {
            @Override
            public void run() {
                String result = fileHelper.loadFromInternalStorage(internalStorage);
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("method","loadInternal");
                bundle.putString("result",result);
                message.setData(bundle);
                handler.sendMessage(message);
            }
        };


    }

    public void saveBtn_Clicked(View view) {
        saveInternalRunnable.run();
    }

    public void clearBtn_Clicked(View view) {
        multiTextView.setText("");
    }

    public void loadBtn_Clicked(View view) {
        loadInternalRunnable.run();
    }
}
