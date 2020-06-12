package com.example.openweathermap;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.softmoore.android.graphlib.Graph;
import com.softmoore.android.graphlib.GraphView;
import com.softmoore.android.graphlib.Label;
import com.softmoore.android.graphlib.Point;

import java.util.ArrayList;
import java.util.List;

public class GraphViewActivity extends AppCompatActivity {

    public List<Label> labelList = new ArrayList<Label>();

    TextView dayText, minText, preText, humText, spdText, date1, daytxt;
    Button btntomain;
    short dp = 50;
    double mt, mh, mp, ms;

    double[] temprange = new double[dp];
    double[] humidityrange = new double[dp];
    double[] speedrange = new double[dp];
    double[] pressurerange = new double[dp];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_view);

        Toolbar graphtoolbar = (Toolbar)findViewById(R.id.graphtoolbar);
        graphtoolbar.setTitle("Graphical View");
        setSupportActionBar(graphtoolbar);

        dayText = (TextView)findViewById(R.id.daygraph);
        minText = (TextView)findViewById(R.id.mingraph);
        preText = (TextView)findViewById(R.id.pressuregraph);
        humText = (TextView)findViewById(R.id.humiditygraph);
        spdText = (TextView)findViewById(R.id.speedgraph);
        date1 = (TextView)findViewById(R.id.graphdayname);
        daytxt = (TextView)findViewById(R.id.graphdaytx);

        btntomain = (Button)findViewById(R.id.homebtn1);
        btntomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intentmain = new Intent(GraphViewActivity.this,MainActivity.class);
                //startActivity(intentmain);
                finish();
            }
        });

        Intent intent1 = getIntent();

        String[] date = intent1.getStringArrayExtra("date");
        String[] day = intent1.getStringArrayExtra("day");
        String country = intent1.getStringExtra("country");
        String city = intent1.getStringExtra("cityname");
        String unit1 = intent1.getStringExtra("unittxt");
        String unit2 = intent1.getStringExtra("unittxt2");
        String[] tempDay = intent1.getStringArrayExtra("temp_day");
        String[] tempMin = intent1.getStringArrayExtra("temp_min");
        String[] tempMax = intent1.getStringArrayExtra("temp_max");
        String[] tempNight = intent1.getStringArrayExtra("temp_night");
        String[] tempEve = intent1.getStringArrayExtra("temp_eve");
        String[] tempMorn = intent1.getStringArrayExtra("temp_morn");
        String[] pressure = intent1.getStringArrayExtra("pressure");
        String[] humidity = intent1.getStringArrayExtra("humidity");
        String[] speed = intent1.getStringArrayExtra("speed");

        if (unit1 == null){
            unit1 = "\u00B0C";
        }else {
            unit1 = unit1;
        }
        if (unit2 == null){
            unit2 = "meter/sec";
        }else {
            unit2 = unit2;
        }

        Point[] temp_day_point = new Point[tempDay.length];
        Point[] temp_morn_point = new Point[tempDay.length];
        Point[] temp_eve_point = new Point[tempDay.length];
        Point[] temp_nigh_point = new Point[tempDay.length];
        Point[] temp_max_point = new Point[tempDay.length];
        Point[] temp_min_point = new Point[tempDay.length];
        Point[] pressure_point = new Point[tempDay.length];
        Point[] humidity_point = new Point[tempDay.length];
        Point[] speed_point = new Point[tempDay.length];

        double min_day = Double.parseDouble(tempDay[0]);
        double max_day = Double.parseDouble(tempDay[0]);
        double min_tmin = Double.parseDouble(tempMin[0]);
        double max_tmax = Double.parseDouble(tempMax[0]);
        double min_pressure = Double.parseDouble(pressure[0]);
        double max_pressure = Double.parseDouble(pressure[0]);
        double min_humidity = Double.parseDouble(humidity[0]);
        double max_humidity = Double.parseDouble(humidity[0]);
        double min_speed = Double.parseDouble(speed[0]);
        double max_speed = Double.parseDouble(speed[0]);


        for (short i=0;i<tempDay.length;i++){
            temp_morn_point[i] = new Point((double) i+1, Double.parseDouble(tempMorn[i]));
            temp_day_point[i] = new Point((double) i+1, Double.parseDouble(tempDay[i]));
            temp_eve_point[i] = new Point((double) i+1, Double.parseDouble(tempEve[i]));
            temp_nigh_point[i] = new Point((double) i+1, Double.parseDouble(tempNight[i]));
            temp_max_point[i] = new Point((double) i+1, Double.parseDouble(tempMax[i]));
            temp_min_point[i] = new Point((double) i+1, Double.parseDouble(tempMin[i]));
            pressure_point[i] = new Point((double) i+1, Double.parseDouble(pressure[i]));
            humidity_point[i] = new Point((double) i+1, Double.parseDouble(humidity[i]));
            speed_point[i] = new Point((double) i+1, Double.parseDouble(speed[i]));

            if(i==0){Label label0 = new Label(i+1,day[i]);
                labelList.add(label0);
            }if(i==1){Label label1 = new Label(i+1,day[i]);
                labelList.add(label1);
            }if(i==2){Label label2 = new Label(i+1,day[i]);
                labelList.add(label2);
            }if(i==3){Label label3 = new Label(i+1,day[i]);
                labelList.add(label3);
            }if(i==4){Label label4 = new Label(i+1,day[i]);
                labelList.add(label4);
            }if(i==5){Label label5 = new Label(i+1,day[i]);
                labelList.add(label5);
            }if(i==6){Label label6 = new Label(i+1,day[i]);
                labelList.add(label6);
            }

            if (max_day < Double.parseDouble(tempMorn[i])){
                max_day = Double.parseDouble(tempMorn[i]);
            }
            if (max_day < Double.parseDouble(tempDay[i])){
                max_day = Double.parseDouble(tempDay[i]);
            }
            if (max_day < Double.parseDouble(tempEve[i])){
                max_day = Double.parseDouble(tempEve[i]);
            }
            if (max_day < Double.parseDouble(tempNight[i])){
                max_day = Double.parseDouble(tempNight[i]);
            }
            if (max_tmax < Double.parseDouble(tempMax[i])){
                max_tmax = Double.parseDouble(tempMax[i]);
            }
            if (max_pressure < Double.parseDouble(pressure[i])){
                max_pressure = Double.parseDouble(pressure[i]);
            }
            if (max_humidity < Double.parseDouble(humidity[i])){
                max_humidity = Double.parseDouble(humidity[i]);
            }
            if (max_speed < Double.parseDouble(speed[i])){
                max_speed = Double.parseDouble(speed[i]);
            }


            if (min_day > Double.parseDouble(tempMorn[i])){
                min_day = Double.parseDouble(tempMorn[i]);
            }
            if (min_day > Double.parseDouble(tempDay[i])){
                min_day = Double.parseDouble(tempDay[i]);
            }
            if (min_day > Double.parseDouble(tempEve[i])){
                min_day = Double.parseDouble(tempEve[i]);
            }
            if (min_day > Double.parseDouble(tempNight[i])){
                min_day = Double.parseDouble(tempNight[i]);
            }
            if (min_tmin > Double.parseDouble(tempMin[i])){
                min_tmin = Double.parseDouble(tempMin[i]);
            }
            if (min_pressure > Double.parseDouble(pressure[i])){
                min_pressure = Double.parseDouble(pressure[i]);
            }
            if (min_humidity > Double.parseDouble(humidity[i])){
                min_humidity = Double.parseDouble(humidity[i]);
            }
            if (min_speed > Double.parseDouble(speed[i])){
                min_speed = Double.parseDouble(speed[i]);
            }

        }

        for (short j=0;j<dp;j++) {
            for (short k = 0; k < (short)(Math.round((max_tmax - min_tmin)+1)); k++) {
                if ((max_tmax-min_tmin)>=10) {
                    if(j%4==0){
                        temprange[j] = (min_tmin + j);
                        mt = 4;
                    }
                }else if ((max_tmax-min_tmin) >7){
                    if (j%3 ==0){
                        temprange[j] = (min_tmin + j);
                        mt = 3;
                    }
                }else if ((max_tmax-min_tmin) > 4){
                    if (j%2 ==0) {
                        temprange[j] = (min_tmin + j);
                        mt = 2;
                    }
                }else{
                    temprange[j] = (min_tmin + j);
                    mt = 1;
                }


            }for (short l = 0; l < (short)Math.round((max_pressure-min_pressure)+1); l++) {
                if ((max_pressure-min_pressure)>20) {
                    if (j % 6 == 0){
                        pressurerange[j] = Math.round(min_pressure + j);
                        mp = 6;
                    }
                }else if ((max_pressure-min_pressure)>16) {
                    if (j % 5 == 0){
                        pressurerange[j] = Math.round(min_pressure + j);
                        mp = 5;
                    }
                }else if ((max_pressure-min_pressure)>12) {
                    if (j % 4 == 0){
                        pressurerange[j] = Math.round(min_pressure + j);
                        mp = 4;
                    }
                }else if ((max_pressure-min_pressure)>8) {
                    if (j % 3 == 0){
                        pressurerange[j] = Math.round(min_pressure + j);
                        mp = 3;
                    }
                }else if ((max_pressure-min_pressure)>4) {
                    if (j % 2 == 0){
                        pressurerange[j] = Math.round(min_pressure + j);
                        mp = 2;
                    }
                }else {
                    pressurerange[j] = Math.round(min_pressure + j);
                    mp = 1;
                }


            }for (short m = 0; m < (short)(Math.round((max_humidity-min_humidity)+1)); m++) {
                if ((max_humidity-min_humidity) > 40) {
                    if (j % 12== 0){
                        humidityrange[j] = Math.round(min_humidity + j);
                        mh = 12;
                    }
                }else if ((max_humidity-min_humidity) > 30) {
                    if (j % 10 == 0){
                        humidityrange[j] = Math.round(min_humidity + j);
                        mh = 10;
                    }
                }else if ((max_humidity-min_humidity) > 20) {
                    if (j % 5 == 0){
                        humidityrange[j] = Math.round(min_humidity + j);
                        mh = 5;
                    }
                }else if ((max_humidity-min_humidity) > 14) {
                    if (j % 4 == 0){
                        humidityrange[j] = Math.round(min_humidity + j);
                        mh = 4;
                    }
                }else if ((max_humidity-min_humidity) > 7) {
                    if (j % 3 == 0){
                        humidityrange[j] = Math.round(min_humidity + j);
                        mh = 3;
                    }
                }else {
                    if (j % 2 == 0)humidityrange[j] = Math.round(min_humidity + j);
                    mh = 2;
                }


            }for (short n = 0; n < (short)(Math.round((max_speed-min_speed)+1)); n++) {
                if ((max_speed-min_speed)>25) {
                    if (j % 7== 0){
                        speedrange[j] = Math.round(min_speed + j);
                        ms = 7;
                    }
                }else if ((max_speed-min_speed)>20) {
                    if (j % 5 == 0){
                        speedrange[j] = Math.round(min_speed + j);
                        ms = 5;
                    }
                }else if ((max_speed-min_speed)>15) {
                    if (j % 4 == 0){
                        speedrange[j] = Math.round(min_speed + j);
                        ms = 4;
                    }
                }else if ((max_speed-min_speed)>10) {
                    if (j % 3 == 0){
                        speedrange[j] = Math.round(min_speed + j);
                        ms = 3;
                    }
                }else if ((max_speed-min_speed)>5) {
                    if (j % 2 == 0){
                        speedrange[j] = Math.round(min_speed + j);
                        ms = 2;
                    }
                }else {
                    speedrange[j] = Math.round(min_speed + j);
                    ms = 1;
                }
            }
        }
        //String aaa = Double.toString(max_tmax - min_tmin);
        //Toast.makeText(GraphViewActivity.this,unit, Toast.LENGTH_SHORT).show();
        //Toast.makeText(GraphViewActivity.this,aaa, Toast.LENGTH_SHORT).show();


        Graph graph1 = new Graph.Builder()
                .setWorldCoordinates(-0.7,7.5,min_day-mt,max_day+(mt/2))
                .setAxes(0,min_day-(mt/2))
                .addLineGraph(temp_morn_point, Color.RED)
                .addLineGraph(temp_day_point, Color.BLUE)
                .addLineGraph(temp_eve_point, Color.YELLOW)
                .addLineGraph(temp_nigh_point, Color.GREEN)
                .setBackgroundColor(R.drawable.box_coorners_set)
                .setXLabels(labelList)
                .setYTicks(temprange)
                .setAxesColor(R.color.day)
                .build();

        GraphView graphView1 = (GraphView)findViewById(R.id.graph1);
        graphView1.setBackgroundColor(getResources().getColor(R.color.blurgraph));
        graphView1.setGraph(graph1);

        dayText.setText("Day Temperature  " + "("+unit1+")");


        Graph graph2 = new Graph.Builder()
                .setWorldCoordinates(-0.7,7.5,min_tmin-mt,max_tmax+(mt/2))
                .setAxes(0,min_tmin-(mt/2))
                .addLineGraph(temp_max_point, Color.YELLOW)
                .addLineGraph(temp_min_point, Color.GREEN)
                .setAxesColor(R.color.day)
                .setBackgroundColor(R.drawable.box_coorners_set)
                .setXLabels(labelList)
                .setYTicks(temprange)
                .build();

        GraphView graphView2 = (GraphView)findViewById(R.id.graph2);
        graphView2.setBackgroundColor(getResources().getColor(R.color.blurgraph));
        graphView2.setGraph(graph2);

        minText.setText("Minimum & Maximum Temperature  "+ "("+unit1+")");


        Graph graph3 = new Graph.Builder()
                .setWorldCoordinates(-0.7,7.5,min_pressure-mp,max_pressure+(mp/2))
                .setAxes(0,min_pressure-(mp/2))
                .addLineGraph(pressure_point, Color.GREEN)
                .setBackgroundColor(R.drawable.box_coorners_set)
                .setXLabels(labelList)
                .setAxesColor(R.color.day)
                .setYTicks(pressurerange)
                .build();

        GraphView graphView3 = (GraphView)findViewById(R.id.graph3);
        graphView3.setBackgroundColor(getResources().getColor(R.color.blurgraph));
        graphView3.setGraph(graph3);

        preText.setText("Pressure  " + "(hPa)");


        Graph graph4 = new Graph.Builder()
                .setWorldCoordinates(-0.7,7.5,min_humidity-mh,max_humidity+(mh/2))
                .setAxes(0,min_humidity-(mh/2))
                .addLineGraph(humidity_point, Color.GREEN)
                .setBackgroundColor(R.drawable.box_coorners_set)
                .setXLabels(labelList)
                .setYTicks(humidityrange)
                .setAxesColor(R.color.day)
                .build();

        GraphView graphView4 = (GraphView)findViewById(R.id.graph4);
        graphView4.setBackgroundColor(getResources().getColor(R.color.blurgraph));
        graphView4.setGraph(graph4);

        humText.setText("Humidity  " + "(%)");


        Graph graph5 = new Graph.Builder()
                .setWorldCoordinates(-0.7,7.5,min_speed-ms,max_speed+(ms/2))
                .setAxes(0,min_speed-(ms/2))
                .addLineGraph(speed_point, Color.GREEN)
                .setBackgroundColor(R.drawable.box_coorners_set)
                .setXLabels(labelList)
                .setYTicks(speedrange)
                .setAxesColor(R.color.day)
                .build();

        GraphView graphView5 = (GraphView)findViewById(R.id.graph5);
        graphView5.setBackgroundColor(getResources().getColor(R.color.blurgraph));
        graphView5.setGraph(graph5);

        spdText.setText("Wind Speed  " + "(" + unit2 + ")");


        date1.setText(country+"  -  "+city);
        daytxt.setText(date[0]);

        TranslateAnimation tra = new TranslateAnimation(700.0f,0.0f,0.0f,0.0f);
        tra.setDuration(2000);
        tra.setRepeatCount(0);
        tra.setFillAfter(false);
        date1.startAnimation(tra);


        TranslateAnimation transdt = new TranslateAnimation(-700.0f,0.0f,0.0f,0.0f);
        transdt.setDuration(2000);
        transdt.setRepeatCount(0);
        transdt.setFillAfter(false);
        daytxt.startAnimation(transdt);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu1){
        getMenuInflater().inflate(R.menu.menu_graph_view,menu1);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        Intent intentgrp;
        switch (item.getItemId()) {
            case R.id.settingsgrp:
                intentgrp = new Intent(this, Settings.class);
                break;
            //Toast.makeText(MainActivity.this,item.getTitle(), Toast.LENGTH_LONG).show();
            case R.id.aboutgrp:
                AlertDialog.Builder aboutmsg = new AlertDialog.Builder(this);
                aboutmsg.setMessage(R.string.about);
                aboutmsg.setTitle("About...");
                aboutmsg.setPositiveButton("Ok",null);
                aboutmsg.create().show();
                //Toast.makeText(MainActivity.this,"++++++++++++", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        startActivity(intentgrp);
        return true;
    }
}
