package com.example.quirquirutas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.nio.channels.InterruptedByTimeoutException;

public class MainActivity extends AppCompatActivity {
    Button btn_ing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void siguiente(View view){
        Intent i = new Intent(this,MapsActivity1.class);
        startActivity(i);
    }
}
