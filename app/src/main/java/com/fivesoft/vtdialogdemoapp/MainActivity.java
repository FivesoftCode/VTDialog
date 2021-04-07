package com.fivesoft.vtdialogdemoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.fivesoft.dialog.VTDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VTDialog.from(this)
                .setTitle("Title")
                .setMessage("This is message text.")
                .setCancelable(true)
                .setDialogIcon(null)
                .setDismissOnButtonClick(true)
                .setLeftButton("Great", view -> {
                    Toast.makeText(this, "Left button clicked!", Toast.LENGTH_LONG).show();
                })
                .setRightButton("Cool!", null)
                .show();
    }
}