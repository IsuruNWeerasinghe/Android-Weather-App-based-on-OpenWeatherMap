package com.example.openweathermap;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class Settings extends AppCompatActivity {

    String tempunit,unittxt,unittxt2,towmnm,unitbtn;
    private Button savebutton;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    Intent intentdata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar mtoolbar = (Toolbar)findViewById(R.id.settinbar);
        mtoolbar.setTitle("Settings");
        mtoolbar.setNavigationIcon(R.drawable.ic_android_back_24dp);

        mtoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        radioGroup = (RadioGroup)findViewById(R.id.radiogrp);
        savebutton = (Button) findViewById(R.id.savebtn);
        final EditText citynm = (EditText)findViewById(R.id.cityname);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int cheid) {
                if (cheid == R.id.kelvin){
                    unitbtn = "Kelvin";
                }else if (cheid == R.id.fehrenheit){
                    unitbtn = "Fahrenheit";
                }else {
                    unitbtn = "Celsius";
                }
            }
        });

        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                towmnm = citynm.getText().toString();
                if (towmnm.matches("")){
                    towmnm ="Colombo";
                }

                if(unitbtn == "Celsius"){
                    tempunit = "metric";
                    unittxt = "\u00B0C";
                    unittxt2 = "m/s";
                }else if (unitbtn =="Kelvin"){
                    tempunit = "";
                    unittxt = "K";
                    unittxt2 = "m/s";
                }else if(unitbtn == "Fahrenheit"){
                    tempunit = "imperial";
                    unittxt = "\u00B0F";
                    unittxt2 = "miles/hour";
                }

                intentdata = new Intent(getApplicationContext(),MainActivity.class);
                intentdata.putExtra("unitdata",tempunit);
                intentdata.putExtra("citydata",towmnm);
                intentdata.putExtra("unittxt",unittxt);
                intentdata.putExtra("unittxt2",unittxt2);
                startActivity(intentdata);
                //Toast.makeText(savebutton.getContext(),"Saved succeed city as " +towmnm + " and unit as " + unitdata,Toast.LENGTH_LONG).show();

            }
        });

    }

}

