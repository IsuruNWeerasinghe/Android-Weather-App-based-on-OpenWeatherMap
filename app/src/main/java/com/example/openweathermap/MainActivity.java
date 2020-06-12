package com.example.openweathermap;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.format.TextStyle;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    public static final int RequestPermissionCode =1;
    private Toolbar mTopToolbar;

    ListView list;
    Integer[] imgid;
    String[] dayofweek;
    String[] dayofweekshort;
    String[] dateofweek;
    String citydata,unitid, unit,unittxt,unittxt2;
    String[] date;
    String[] temp_day;
    String[] temp_conv;
    String[] temp_min;
    String[] temp_max;
    String[] temp_night;
    String[] temp_eve;
    String[] temp_morn;
    String[] tempaverage;
    String[] pressure;
    String[] humidity;
    String[] speed;
    String[] main;
    String[] description;
    String[] icon;
    String[] main1;
    String[] description1;
    String[] icon1;
    String cityname,country;

    Intent graphintent;
    Button btnGraph;

    short[] temp_avg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EnableRuntimePermission();

        mTopToolbar = (Toolbar)findViewById(R.id.mytoolbar);
        setSupportActionBar(mTopToolbar);



        if (ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.INTERNET)==PackageManager.PERMISSION_GRANTED){
            FetchData fetchData = new FetchData();
            fetchData.execute("a18b978603316d47c572d98d52a420f6");
        }

    }

    @Override
    public void onBackPressed(){
        new AlertDialog.Builder(this)
                .setTitle("Exit")
                .setMessage("Do you need to exit from the App ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent exit = new Intent(Intent.ACTION_MAIN);
                        exit.addCategory(Intent.CATEGORY_HOME);
                        exit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(exit);
                    }
                })
                .setNegativeButton("Cancel",null)
                .show();
    }

    public void EnableRuntimePermission(){
        String[] permissions ={
                Manifest.permission.INTERNET
        };
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.INTERNET)){
            Toast.makeText(MainActivity.this,"Internet Permission allows us to Access Internet",Toast.LENGTH_LONG).show();
        }else {
            ActivityCompat.requestPermissions(MainActivity.this,permissions,RequestPermissionCode);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        Intent intentset;
        switch (item.getItemId()) {
            case R.id.settings:
                intentset = new Intent(this, Settings.class);
                break;
            //Toast.makeText(MainActivity.this,item.getTitle(), Toast.LENGTH_LONG).show();
            case R.id.about:
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
        startActivity(intentset);
        return true;
    }

    public class FetchData extends AsyncTask<String,Void, String>{
        HttpURLConnection urlConnection = null;
        BufferedReader bufferReader = null;
        String forecastJsonStr = null;
        ProgressDialog ProgressDialog =null;

        @Override
        protected String doInBackground(String... params) {

            try {
                final String BASE_URL="http://api.openweathermap.org/data/2.5/forecast/daily?q="+citydata+"&mode=json&units="+unitid+"&cnt=7";
                final String KEY = "APPID";
                Uri uriBuild1 = Uri.parse(BASE_URL).buildUpon().appendQueryParameter(KEY,params[0]).build();        //BASE url ekata KEY id eka ekathu karanawa
                URL url1= new URL(uriBuild1.toString());
                urlConnection = (HttpURLConnection) url1.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer stringBuffer = new StringBuffer();

                if (inputStream == null ) {
                    return null;
                }
                bufferReader = new BufferedReader(new InputStreamReader(inputStream));
                String line1;
                while ((line1 = bufferReader.readLine()) != null) {
                    stringBuffer.append(line1 + "\n");
                }
                if (stringBuffer.length() == 0) {
                    return null;
                }forecastJsonStr = stringBuffer.toString();
            }
            catch (Exception e){
                Log.e("Gayan", "Error closing stream", e);
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if(bufferReader != null) {
                    try {bufferReader.close();
                    }
                    catch (final IOException e) {
                        Log.e("Gayan", "Error closing stream", e);
                    }
                }
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressDialog = new ProgressDialog(MainActivity.this);
            ProgressDialog.setMessage("Loading Weather Data");  //setting msg
            ProgressDialog.setTitle("Loading...");  //setting title
            ProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);  //style
            ProgressDialog.show();  //display progress dialog
            ProgressDialog.setCancelable(false);

            intentdata();

        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            /*TextView txtJson = (TextView)findViewById(R.id.jsontxt);
            txtJson.setText(forecastJsonStr);
            ProgressDialog.dismiss();   //stop display progress dialog*/

            String jsoncode = forecastJsonStr;


            try {
                JSONObject code = new JSONObject(jsoncode);
                JSONObject city = code.getJSONObject("city");

                cityname = city.getString("name");
                country = city.getString("country");

                JSONArray list = code.getJSONArray("list");

                date = new String[list.length()];

                double[] maxt;
                double[] mint;

                mint = new double[list.length()];
                maxt = new double[list.length()];
                temp_day = new String[list.length()];
                temp_min = new String[list.length()];
                temp_max = new String[list.length()];
                temp_night = new String[list.length()];
                temp_eve = new String[list.length()];
                tempaverage = new String[list.length()];
                temp_avg = new short[list.length()];
                temp_morn = new String[list.length()];
                pressure = new String[list.length()];
                humidity = new String[list.length()];
                temp_conv = new String[list.length()];
                dayofweek = new String[list.length()];
                dayofweekshort = new String[list.length()];
                dateofweek = new String[list.length()];


                speed = new String[list.length()];
                main = new String[list.length()];
                description = new String[list.length()];
                icon = new String[list.length()];
                imgid = new Integer[list.length()];

                for (short i = 0; i<list.length(); i++){
                    JSONObject item = list.getJSONObject(i);
                    date[i] = item.getString("dt");
                    pressure[i] = item.getString("pressure");
                    humidity[i] = item.getString("humidity");
                    speed[i] = item.getString("speed");

                    JSONObject tempe = item.getJSONObject("temp");

                    temp_day[i] = tempe.getString("day");
                    temp_min[i] = tempe.getString("min");
                    temp_max[i] = tempe.getString("max");
                    temp_night[i] = tempe.getString("night");
                    temp_eve[i] = tempe.getString("eve");
                    temp_morn[i] = tempe.getString("morn");


                    maxt[i] =  Double.parseDouble(temp_max[i]);
                    temp_max[i] = String.valueOf(Math.round(maxt[i]));
                    mint[i] =  Double.parseDouble(temp_min[i]);
                    temp_min[i] = String.valueOf(Math.round(mint[i]));

                    JSONArray weather = item.getJSONArray("weather");

                    main1 = new String[weather.length()];
                    description1 = new String[weather.length()];
                    icon1 = new String[weather.length()];

                    temp_avg[i] = (short) ((Short.parseShort(temp_min[i]) + Short.parseShort(temp_max[i]))/2);
                    tempaverage[i] = String.valueOf(Math.round(temp_avg[i]));

                    for (short j=0; j <weather.length(); j++){
                        JSONObject index = weather.getJSONObject(j);

                        main1[j] = index.getString("main");
                        description1[j] = index.getString("description");
                        icon1[j] = index.getString("icon");

                        main[i] = main1[0];
                        description[i] = description1[0];
                        icon[i] = icon1[0];
                    }

                    //Toast.makeText(MainActivity.this,unit, Toast.LENGTH_SHORT).show();

                    if (icon[i].contains("01d")) {
                        imgid[i] = R.drawable.pic_01d;
                        //Toast.makeText(MainActivity.this,"111", Toast.LENGTH_LONG).show();
                    } else if (icon[i].contains("02d")) {
                        imgid[i] = R.drawable.pic_02d;
                        //Toast.makeText(MainActivity.this,"222", Toast.LENGTH_LONG).show();
                    } else if (icon[i].contains("03d")) {
                        imgid[i] = R.drawable.pic_03d;
                        //Toast.makeText(MainActivity.this,"333", Toast.LENGTH_LONG).show();
                    } else if (icon[i].contains("04d")) {
                        imgid[i] = R.drawable.pic_04d;
                        //Toast.makeText(MainActivity.this,"444", Toast.LENGTH_LONG).show();
                    } else if (icon[i].contains("09d")) {
                        imgid[i] = R.drawable.pic_09d;
                        //Toast.makeText(MainActivity.this,"555", Toast.LENGTH_LONG).show();
                    } else if (icon[i].contains("10d")) {
                        imgid[i] = R.drawable.pic_10d;
                        //Toast.makeText(MainActivity.this,"666", Toast.LENGTH_LONG).show();
                    } else if (icon[i].contains("11d")) {
                        imgid[i] = R.drawable.pic_11d;
                        //Toast.makeText(MainActivity.this,"777", Toast.LENGTH_LONG).show();
                    } else if (icon[i].contains("13d")) {
                        imgid[i] = R.drawable.pic_13d;
                        //Toast.makeText(MainActivity.this,"888", Toast.LENGTH_LONG).show();
                    } else if(icon[i].contains("50d")) {
                        imgid[i] = R.drawable.pic_50d;
                        //Toast.makeText(MainActivity.this,"999", Toast.LENGTH_LONG).show();
                    }if (icon[i].contains("01n")) {
                        imgid[i] = R.drawable.pic_01n;
                        //Toast.makeText(MainActivity.this,"111", Toast.LENGTH_LONG).show();
                    } else if (icon[i].contains("02n")) {
                        imgid[i] = R.drawable.pic_02n;
                        //Toast.makeText(MainActivity.this,"222", Toast.LENGTH_LONG).show();
                    } else if (icon[i].contains("03n")) {
                        imgid[i] = R.drawable.pic_03n;
                        //Toast.makeText(MainActivity.this,"333", Toast.LENGTH_LONG).show();
                    } else if (icon[i].contains("04n")) {
                        imgid[i] = R.drawable.pic_04n;
                        //Toast.makeText(MainActivity.this,"444", Toast.LENGTH_LONG).show();
                    } else if (icon[i].contains("09n")) {
                        imgid[i] = R.drawable.pic_09n;
                        //Toast.makeText(MainActivity.this,"555", Toast.LENGTH_LONG).show();
                    } else if (icon[i].contains("10n")) {
                        imgid[i] = R.drawable.pic_10n;
                        //Toast.makeText(MainActivity.this,"666", Toast.LENGTH_LONG).show();
                    } else if (icon[i].contains("11n")) {
                        imgid[i] = R.drawable.pic_11n;
                        //Toast.makeText(MainActivity.this,"777", Toast.LENGTH_LONG).show();
                    } else if (icon[i].contains("13n")) {
                        imgid[i] = R.drawable.pic_13n;
                        //Toast.makeText(MainActivity.this,"888", Toast.LENGTH_LONG).show();
                    } else if(icon[i].contains("50n")) {
                        imgid[i] = R.drawable.pic_50n;
                        //Toast.makeText(MainActivity.this,"999", Toast.LENGTH_LONG).show();
                    }

                }

            }catch (JSONException je){
                Log.d("json",je.toString());
            }

            //Toast.makeText(MainActivity.this,unit, Toast.LENGTH_SHORT).show();
            //Toast.makeText(MainActivity.this,unitdata, Toast.LENGTH_SHORT).show();


            for (int i=0;i<7;i++){
                String weekDay,weekDayShort, weekDate;

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
                SimpleDateFormat dayshortFormat = new SimpleDateFormat("EEE");
                calendar.add(Calendar.DAY_OF_WEEK,i);
                weekDay = dayFormat.format(calendar.getTime());
                weekDayShort = dayshortFormat.format(calendar.getTime());
                dayofweek[i] = weekDay;
                dayofweekshort[i] = weekDayShort;

                Calendar calendar1 = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM");
                calendar1.add(Calendar.DATE,i);
                weekDate = dateFormat.format(calendar1.getTime());
                dateofweek[i] = weekDate;
                //Toast.makeText(MainActivity.this,dateofweek[0], Toast.LENGTH_LONG).show();
            }

            Button btnGraph = (Button)findViewById(R.id.graphbtn);
            btnGraph.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(MainActivity.this,"++++", Toast.LENGTH_LONG).show();
                    graphintent = new Intent(MainActivity.this, GraphViewActivity.class);

                    graphintent.putExtra("date",dateofweek);
                    graphintent.putExtra("day",dayofweekshort);
                    graphintent.putExtra("country",country);
                    graphintent.putExtra("cityname",cityname);
                    graphintent.putExtra("temp_day",temp_day);
                    graphintent.putExtra("temp_min",temp_min);
                    graphintent.putExtra("temp_max",temp_max);
                    graphintent.putExtra("temp_night",temp_night);
                    graphintent.putExtra("temp_eve",temp_eve);
                    graphintent.putExtra("temp_morn",temp_morn);
                    graphintent.putExtra("pressure",pressure);
                    graphintent.putExtra("humidity",humidity);
                    graphintent.putExtra("speed",speed);
                    graphintent.putExtra("unittxt",unittxt);
                    graphintent.putExtra("unittxt2",unittxt2);
                    startActivity(graphintent);
                }
            });


            CustomList adapter = new CustomList(MainActivity.this,dayofweek,imgid,temp_max,temp_min,unittxt);
            list= (ListView)findViewById(R.id.mylist);
            list.setAdapter(adapter);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(getApplicationContext(),Main2Activity.class);
                    intent.putExtra("date",dateofweek[+position]);
                    intent.putExtra("country",country);
                    intent.putExtra("cityname",cityname);
                    intent.putExtra("temp_day",temp_day[+position]);
                    intent.putExtra("temp_min",temp_min[+position]);
                    intent.putExtra("temp_max",temp_max[+position]);
                    intent.putExtra("temp_night",temp_night[+position]);
                    intent.putExtra("temp_eve",temp_eve[+position]);
                    intent.putExtra("temp_morn",temp_morn[+position]);
                    intent.putExtra("tempaverage",tempaverage[+position]);
                    intent.putExtra("pressure",pressure[+position]);
                    intent.putExtra("humidity",humidity[+position]);
                    intent.putExtra("main",main[+position]);
                    intent.putExtra("description",description[+position]);
                    intent.putExtra("icon",icon[position]);
                    intent.putExtra("imgid",imgid[position]);
                    intent.putExtra("speed",speed[+position]);
                    intent.putExtra("unitid",unitid);
                    intent.putExtra("unittxt",unittxt);
                    intent.putExtra("unittxt2",unittxt2);
                    startActivity(intent);

                }
            });

            ProgressDialog.dismiss();

            //Toast.makeText(MainActivity.this,m,Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults){
        switch (requestCode){
            case RequestPermissionCode:
                if (grantResults.length> 0 &&(grantResults[0]== PackageManager.PERMISSION_GRANTED)){
                    Toast.makeText(MainActivity.this,"Internet Permission Granted",Toast.LENGTH_LONG).show();


                }else{
                    Toast.makeText(MainActivity.this,"Internet Permission Canceled",Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    public void intentdata(){
        Intent intentid = getIntent();
            String city = intentid.getStringExtra("citydata");
            unit = intentid.getStringExtra("unitdata");
            unittxt = intentid.getStringExtra("unittxt");
            unittxt2 = intentid.getStringExtra("unittxt2");

            if (city == null){
                citydata = "Colombo";
            }else {
                citydata = city;
            }

            if (unit == null){
                unitid = "metric";
            }else {
                unitid = unit;
            }

            if(unittxt == null){
                unittxt = "\u00B0C";
            }else {
                unittxt = unittxt;
            }
            if(unittxt2 == null){
                unittxt2 = "m/s";
            }else {
                unittxt2 = unittxt2;
            }
            //Toast.makeText(MainActivity.this,unit+" ** " + unit,Toast.LENGTH_LONG).show();
            //Toast.makeText(MainActivity.this,unitid,Toast.LENGTH_LONG).show();
    }

}

