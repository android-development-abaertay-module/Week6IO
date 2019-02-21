package com.bodovix.week6localstorage;

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

    FileHelper fileHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        multiTextView = findViewById(R.id.multiLineET);
        saveBtn = findViewById(R.id.saveBtn);
        clearBtn = findViewById(R.id.clearBtn);
        loadBtn = findViewById(R.id.loadBtn);

        fileHelper = new FileHelper(getApplicationContext());
    }

    public void saveBtn_Clicked(View view) {
    }

    public void clearBtn_Clicked(View view) {
    }

    public void loadBtn_Clicked(View view) {
    }
}
