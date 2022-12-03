package com.example.simulatepositioning;

import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.MapsInitializer;
import com.amap.api.maps.model.MyLocationStyle;

import java.text.SimpleDateFormat;
import java.util.Date;

public class test extends AppCompatActivity implements View.OnClickListener {
    private String city;

    public AMapLocationClientOption mLocationOption = null;

    public AMapLocationClient mLocationClient = null;

    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    amapLocation.getLocationType();
                    amapLocation.getLatitude();
                    amapLocation.getLongitude();
                    amapLocation.getAccuracy();
                    amapLocation.getAddress();
                    amapLocation.getCountry();
                    amapLocation.getProvince();
                    amapLocation.getCity();


                    dizhi.setText(amapLocation.getCity());
                    city=amapLocation.getCity();


                    amapLocation.getDistrict();
                    amapLocation.getStreet();
                    amapLocation.getStreetNum();
                    amapLocation.getCityCode();
                    amapLocation.getAdCode();
                    amapLocation.getAoiName();
                    amapLocation.getBuildingId();
                    amapLocation.getFloor();
                    amapLocation.getGpsAccuracyStatus();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(amapLocation.getTime());
                    df.format(date);

                } else {

                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }

        }
    };
    private MapView mMapView;
    private AMap aMap;
    private Button dizhi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        MapsInitializer.updatePrivacyShow(this,true,true);
        MapsInitializer.updatePrivacyAgree(this,true);

        dizhi = findViewById(R.id.dizhi);
        dizhi.setOnClickListener(this);

        mMapView = (MapView) findViewById(R.id.mapview);

        mMapView.onCreate(savedInstanceState);


        if (aMap == null) {
            aMap = mMapView.getMap();
        }

        try {
            dingwei();
        } catch (Exception e) {
            e.printStackTrace();
        }

    /*    routeSearch = new RouteSearch(this);

        routeSearch.setRouteSearchListener(this);*/

    }

    private void dingwei() throws Exception {
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();
        myLocationStyle.interval(2000);
        aMap.setMyLocationStyle(myLocationStyle);

        aMap.setMyLocationEnabled(true);



        mLocationClient = new AMapLocationClient(getApplicationContext());

        mLocationClient.setLocationListener(mLocationListener);



        mLocationOption = new AMapLocationClientOption();


        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);


        mLocationOption.setInterval(1000);


        mLocationOption.setNeedAddress(true);

        mLocationOption.setHttpTimeOut(20000);


        mLocationClient.setLocationOption(mLocationOption);

        mLocationClient.startLocation();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mMapView.onDestroy();
        mLocationClient.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLocationClient.stopLocation();
    }

    @Override
    public void onClick(View view) {
        Intent intent=new Intent(test.this,MainActivity.class);
        intent.putExtra("city",city);
        startActivityForResult(intent,1001);
    }
}