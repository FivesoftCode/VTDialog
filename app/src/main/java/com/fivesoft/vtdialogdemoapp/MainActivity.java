package com.fivesoft.vtdialogdemoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fivesoft.dialog.VTDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onBackPressed() {
        VTDialog.from(this)
                .setTitle("Title")
                .setMessage("This is message text.")
                .setCancelable(true)
                .setDialogIcon(null)
                .customize(new VTDialog.DialogCustomization(){
                    @Override
                    public void customizeDialogBackground(CardView dialogBackground) {
                        super.customizeDialogBackground(dialogBackground);
                    }

                    @Override
                    public void customizeTitleTextView(TextView title) {
                        super.customizeTitleTextView(title);
                    }

                    @Override
                    public void customizeButtons(TextView button, int buttonId) {
                        button.setBackgroundResource(R.drawable.simple_button);
                    }
                })
                .setDismissOnButtonClick(true)
                .setButtonsStyle(VTDialog.BUTTONS_STYLE_VERTICAL)
                .setLeftButton("Great", view -> {
                    Toast.makeText(this, "Left button clicked!", Toast.LENGTH_LONG).show();
                })
                .setRightButton("Cool!", null)
                .show();
    }
}