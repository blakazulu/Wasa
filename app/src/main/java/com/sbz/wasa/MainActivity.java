package com.sbz.wasa;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;
import com.hbb20.CountryCodePicker;

public class MainActivity extends AppCompatActivity {
    ConstraintLayout mainLayout;
    CountryCodePicker ccp;
    EditText etCarrierNumber;
    EditText etMessage;
    ImageButton imgWhatsapp;
    ClipboardManager clipboard;
    String pastedData = "";

    private TextView.OnEditorActionListener enterPressedListener =
            new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        if (etCarrierNumber.length() > 5) {
                            goToWhatsapp();
                        } else {
                            showMessage("I need a valid number");
                        }
                        return true;
                    }
                    return false;
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        // hide action bar
        getSupportActionBar().hide();

        // only portrait mode
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // transparent status bar
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        mainLayout = (ConstraintLayout) findViewById(R.id.mainLayout);
        imgWhatsapp = (ImageButton) findViewById(R.id.imgWhatsapp);
        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        etCarrierNumber = (EditText) findViewById(R.id.etCarrierNumber);
        etMessage = (EditText) findViewById(R.id.etMessage);

        ccp.registerCarrierNumberEditText(etCarrierNumber);

        getDataFromClipboard();
        addClipboardListener();

        imgWhatsapp.setOnClickListener(new ImageClick());

        etCarrierNumber.setOnEditorActionListener(enterPressedListener);
        etCarrierNumber.addTextChangedListener(new phoneNumberListener());

        etMessage.setOnEditorActionListener(enterPressedListener);
    }

    // hiding bottom android buttons
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataFromClipboard();
    }

    // show clipboard paste inside the edit text -> this is for android 10 or higher
    private void addClipboardListener() {
        clipboard = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
            public void onPrimaryClipChanged() {
                ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
                pastedData = (String) item.getText();
                if (checkLeadingZero(pastedData)) {
                    pastedData.substring(1);
                }
                etCarrierNumber.setText(pastedData);
            }
        });
    }

    // copy the data directly to the edit text -> only works for android < 10
    // google updated it's clipboard policy's
    private void getDataFromClipboard() {
        try {
            clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
            pastedData = (String) item.getText();
            if (checkLeadingZero(pastedData)) {
                pastedData.substring(1);
            }
            etCarrierNumber.setText(pastedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void goToWhatsapp() {
        String number = ccp.getFullNumber();
        Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=" + number + "&text=" +
                etMessage.getText().toString());
        try {
            PackageManager pm = this.getPackageManager();

            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            Intent whatsappIntent = new Intent(Intent.ACTION_VIEW, uri);

            if (whatsappIntent.resolveActivity(getPackageManager()) == null) {
                showMessage("Whatsapp not installed.");
                return;
            }

            startActivity(whatsappIntent);
        } catch (PackageManager.NameNotFoundException e) {
            showMessage("Whatsapp app not installed in your phone");
            e.printStackTrace();
        }
    }

    private boolean checkLeadingZero(String str) {
        return str.length() == 1 && str.startsWith("0");
    }

    private void showMessage(String message) {
        Snackbar snackbar = Snackbar
                .make(mainLayout, message, Snackbar.LENGTH_LONG);
        TextView mainTextView = (TextView) (snackbar.getView()).findViewById(com.google.android.material.R.id.snackbar_text);
        mainTextView.setTextColor(Color.parseColor("#22B24C"));
        mainTextView.setGravity(Gravity.CENTER_VERTICAL);
        snackbar.show();
    }

    class ImageClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (etCarrierNumber.length() > 5) {
                goToWhatsapp();
            } else {
                showMessage("I need a valid number");
            }
        }
    }

    class phoneNumberListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (checkLeadingZero(s.toString())) {
                s.clear();
            }
        }
    }
}