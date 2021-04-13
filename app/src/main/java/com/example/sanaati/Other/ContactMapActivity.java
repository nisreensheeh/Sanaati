package com.example.sanaati.Other;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import com.example.sanaati.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ContactMapActivity extends AppCompatActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    TextView data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_map);

        data=findViewById(R.id.data);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        googleMap.setMyLocationEnabled(true);

//        for (int i=0; i<info.size();i++){
//            double latitude = Double.parseDouble(info.get(i).location.substring(0,info.get(i).location.indexOf(",")));
//            double longitude = Double.parseDouble(info.get(i).location.substring(info.get(i).location.indexOf(",")+1));
//
//            mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(info.get(i).name+"-"+info.get(i).job).snippet(info.get(i).addressd));
//            mMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f));
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude),12.0f));
//        }
    }
}