package com.example.simulatepositioning;

import java.util.HashMap;
import java.util.Map;

public class Util {
    static Map<String,String> city;
    static {
        city = new HashMap<>();
        city.put("杭州","330100");
        city.put("宁波","330200");
        city.put("温州","330300");
        city.put("嘉兴","330400");
        city.put("湖州","330500");
        city.put("绍兴","330400");
    }
    public static String getCodeByName(String name){
        return city.get(name);
    }
    static Map<String,Integer> pics;
    static {
        pics=new HashMap<>();
        pics.put("晴",R.drawable.a0);
        pics.put("多云",R.drawable.a4);
        pics.put("阴",R.drawable.a9);
        pics.put("大雪",R.drawable.a17);
        pics.put("雨",R.drawable.a14);
        pics.put("小雨",R.drawable.a13);
        pics.put("中雨",R.drawable.a14);
        pics.put("大雨",R.drawable.a15);
        pics.put("雨夹雪",R.drawable.a20);
    }

    public static Integer getPicByWeather(String weather){
        return pics.get(weather);
    }
}
