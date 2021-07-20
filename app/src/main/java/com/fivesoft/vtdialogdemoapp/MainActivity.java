package com.fivesoft.vtdialogdemoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fivesoft.dialog.VTDialog;

public class MainActivity extends AppCompatActivity {

    private VTDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    @Override
    public void onBackPressed() {
        dialog = VTDialog.from(this)
                .setTitle("Title")
                .setMessage("This is message text.")
                .setGravity(Gravity.BOTTOM)
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
                .setDismissOnButtonClick(false)
                .setButtonsStyle(VTDialog.BUTTONS_STYLE_VERTICAL)
                .setLeftButton("Add button", v -> {
                    dialog.setCentralButton("Change button", v1 -> {
                        dialog.setCentralButton("This is new Text", null);
                    });
                })
                .setRightButton("Remove button!", R.drawable.ic_baseline_check_24, view -> {
                    dialog.removeCentralButton();
                });

        dialog.show();
    }
}