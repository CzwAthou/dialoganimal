package com.athou.diloaganimal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onShowDialogStyle1(View v) {
        new LoadDialogStyle1(this).show();
    }

    public void onShowDialogStyle2(View v) {
        new LoadDialogStyle2(this).show();
    }

    public void onShowDialogStyle3(View v) {
        new LoadDialogStyle3(this).show();
    }

    public void onShowWaveDialogStyle(View v) {
        startActivity(new Intent(this, WaveLoadActivity.class));
    }
}

