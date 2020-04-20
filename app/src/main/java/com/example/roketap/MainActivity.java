package com.example.roketap;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView cityName;
    Button searchButton;

    class Weather extends AsyncTask<String,Void,String>{
        public void search(View view){
            cityName = findViewById(R.id.cityName);
            searchButton = findViewById(R.id.searchButton);

            String cName = cityName.getText().toString();
            String content;
            Weather weather = new Weather();
            try {
                content = weather.execute("http://api.openweathermap.org/data/2.5/weather?q="+cName+"&appid=479e37fc608889d0a24816c5934edad5").get();
                Log.i("content",content);
                //JSON
                JSONObject jsonObject = new JSONObject(content);
                String weatherData = jsonObject.getString("weather");
                Log.i("weatherData",weatherData);
                //weatherDAte
                JSONArray arrey = new JSONArray(weatherData);

                String main = "";
                String description = "";

                for (int i=0; i<arrey.length(); i++){
                    JSONObject weatherPart = arrey.getJSONObject(i);
                    main = weatherPart.getString("main");
                    description = weatherPart.getString("description");
                    Log.i("main",main);
                    Log.i("description",description);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        @Override
        protected String doInBackground(String... address) {
            try {
                URL url = new URL(address[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);

                int data = isr.read();
                String content = "";
                char ch;
                while (data !=-1){
                    ch = (char) data;
                    content = content + ch;
                    data = isr.read();
                }
                return content;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




    }

}
