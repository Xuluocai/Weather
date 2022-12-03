package com.example.simulatepositioning;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class likeCity extends AppCompatActivity {

    UserDao dao;
    List<Map<String, Object>> list;
    List<String> city=new ArrayList<>();

    private ListView listView;
    Map<String, Object> map;
    private WeatherInfo weatherInfo;
    private TextView hint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like_city);

        listView = findViewById(R.id.listView);
        hint=findViewById(R.id.hint);

        dao = new UserDao(this);
        reFreshData();

        listView.setAdapter(new MyBaseAdapter());


    }


    class MyBaseAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return city.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {//Assembly data
            View view=View.inflate(likeCity.this,R.layout.item,null);//get id from the List_item
            TextView likeCity=(TextView) view.findViewById(R.id.likeCity);
            final TextView hum=(TextView) view.findViewById(R.id.hum);
            final TextView qw=(TextView) view.findViewById(R.id.qw);
            final ImageView tqImage=(ImageView)view.findViewById(R.id.tqImage);
            final LinearLayout bg=view.findViewById(R.id.bg);

            likeCity.setText(city.get(position));

            String realUrl = "https://restapi.amap.com/v3/weather/weatherInfo?city=CITY&key=c1cc8b7afea3b4d8c364c66e208a717c&extensions=base";
            realUrl = realUrl.replace("CITY", city.get(position));
            OkHttpClient client = new OkHttpClient();
            final Request request = new Request.Builder().url(realUrl).build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println(e);
                            Toast.makeText(likeCity.this, "request failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String json = response.body().string();
                    try {
                        JSONObject object = new JSONObject(json);
                        String status = object.getString("status");
                        if (status.equals("1")) {
                            JSONArray array = object.getJSONArray("lives");
                            JSONObject real_obj = (JSONObject) array.get(0);
                            final String city = real_obj.getString("city");
                            final String weather = real_obj.getString("weather");
                            final int temp = real_obj.getInt("temperature");
                            final String winddirection = real_obj.getString("winddirection");
                            final String windpower = real_obj.getString("windpower");
                            final int hunidity = real_obj.getInt("humidity");
                            final String reporttime = real_obj.getString("reporttime");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    weatherInfo = new WeatherInfo(city, weather, temp, winddirection, windpower, hunidity, reporttime);
                                    qw.setText(weatherInfo.getTemperature()+"°");
                                    hum.setText("Humidity:"+weatherInfo.getHumidity());
                                    bg.setBackgroundResource(getBgImage(weatherInfo.getWeather()));
                                    tqImage.setImageResource(Util.getPicByWeather(weatherInfo.getWeather()));
                                }
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });


            //组装玩开始返回
            return view;
        }
    }

    private Integer getBgImage(String we) {
        if (we.indexOf("晴")!=-1){
            return R.drawable.qingmp;
        }else if (we.indexOf("多云")!=-1){
            return R.drawable.yump;
        }else if (we.indexOf("阴")!=-1){
            return R.drawable.yingmp;
        }else if (we.indexOf("雨")!=-1){
            return R.drawable.yump;
        }
        return R.drawable.qingmp;
    }

    private void reFreshData() { //load all the data,用于初始化，添加，删除，修改后的数据
        List<String> cityList = dao.getAll();
        city=new ArrayList<>();
        list = new ArrayList<>();
        if (cityList.size()==0){
            hint.setText("No data found");
        }else{
            hint.setText("Favorites in total："+cityList.size()+"cities");
        }
        if (cityList != null && cityList.size() != 0) {
            for (String c : cityList) {
                city.add(c);

            }

        }
    }

}
