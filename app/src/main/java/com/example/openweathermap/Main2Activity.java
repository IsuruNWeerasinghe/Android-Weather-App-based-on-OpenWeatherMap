package com.example.openweathermap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    TextView date,daytxt,status,tmin,tmax,tmor,tday,teve,tnig,pressure,humidity,wind;
    ImageView image,tempmicon,tempdicon,tempeicon,tempnicon,pressureicon,humidityicon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        date = (TextView)findViewById(R.id.dayname);
        daytxt = (TextView)findViewById(R.id.daytx);
        status = (TextView)findViewById(R.id.description);
        tmax = (TextView)findViewById(R.id.maxtemp);
        tmin = (TextView)findViewById(R.id.mintemp);
        tmor = (TextView)findViewById(R.id.tempmorn);
        tday = (TextView)findViewById(R.id.tempday);
        teve = (TextView)findViewById(R.id.tempeve);
        tnig = (TextView)findViewById(R.id.tempnigh);
        pressure = (TextView)findViewById(R.id.pressure);
        humidity = (TextView)findViewById(R.id.humidity);
        wind = (TextView)findViewById(R.id.windspd);

        Toolbar act2toolbar = (Toolbar)findViewById(R.id.forecasttoolbar);
        act2toolbar.setTitle("Forecast ");
        act2toolbar.setNavigationIcon(R.drawable.ic_android_back_24dp);

        act2toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        image = (ImageView)findViewById(R.id.weathericon);
        tempdicon = (ImageView)findViewById(R.id.tempicon1);
        tempnicon = (ImageView)findViewById(R.id.tempicon2);
        tempmicon = (ImageView)findViewById(R.id.tempicon3);
        tempeicon = (ImageView)findViewById(R.id.tempicon4);
        pressureicon = (ImageView)findViewById(R.id.pressureicon);
        humidityicon = (ImageView)findViewById(R.id.humidityicon);


        Intent intent = getIntent();
        String datetx = intent.getStringExtra("date");
        String countrytx = intent.getStringExtra("country");
        String citytx = intent.getStringExtra("cityname");
        String maintx = intent.getStringExtra("main");
        String temp_day = intent.getStringExtra("temp_day");
        String temp_min = intent.getStringExtra("temp_min");
        String temp_max = intent.getStringExtra("temp_max");
        String temp_nig = intent.getStringExtra("temp_night");
        String temp_eve = intent.getStringExtra("temp_eve");
        String temp_morn = intent.getStringExtra("temp_morn");
        String pressuretx = intent.getStringExtra("pressure");
        String humiditytx = intent.getStringExtra("humidity");
        String descriptiontx = intent.getStringExtra("description");
        String speedtx = intent.getStringExtra("speed");
        String unitid = intent.getStringExtra("unittxt");
        String unitid2 = intent.getStringExtra("unittxt2");

        //Toast.makeText(Main2Activity.this,unitid,Toast.LENGTH_LONG).show();
        image.setImageResource(intent.getIntExtra("imgid",0));
        tempmicon.setImageResource(R.drawable.tempmeter);
        tempdicon.setImageResource(R.drawable.tempmeter);
        tempeicon.setImageResource(R.drawable.tempmeter);
        tempnicon.setImageResource(R.drawable.tempmeter);
        pressureicon.setImageResource(R.drawable.pic_windspd);
        humidityicon.setImageResource(R.drawable.pic_humidity1);


        status.setText(descriptiontx);
        daytxt.setText(datetx);
        tmor.setText(temp_morn + unitid);
        tday.setText(temp_day + unitid);
        teve.setText(temp_eve + unitid);
        tnig.setText(temp_nig + unitid);
        tmin.setText(temp_min + unitid);
        tmax.setText(temp_max + unitid);
        pressure.setText(pressuretx+" hPa");
        humidity.setText(humiditytx+" %");
        wind.setText(speedtx+" "+unitid2);
        date.setText(countrytx+"  -  "+citytx);

        TranslateAnimation tra = new TranslateAnimation(700.0f,0.0f,0.0f,0.0f);
        tra.setDuration(2000);
        tra.setRepeatCount(0);
        tra.setFillAfter(false);
        date.startAnimation(tra);


        TranslateAnimation transdt = new TranslateAnimation(-700.0f,0.0f,0.0f,0.0f);
        transdt.setDuration(2000);
        transdt.setRepeatCount(0);
        transdt.setFillAfter(false);
        daytxt.startAnimation(transdt);

    }
}
