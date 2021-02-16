package com.example.sanaati.Customers.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

import com.example.sanaati.R;

import com.example.sanaati.UsersAuth.Class.Users;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    TextView data;
    ArrayList<Users> info;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MapsActivity.this,MainActivity.class);
        intent.putExtra("fromInq", "fromInq");
        startActivity(intent);
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        data=findViewById(R.id.data);

        info = new ArrayList<>();
        info.clear();

        FirebaseFirestore db1 = FirebaseFirestore.getInstance();
        db1.collection("Users").document("type").collection("Employees")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        info.add(new Users(document.getString("userid"),document.getString("name"),document.getString("email"),
                                document.getString("addressd"),document.getString("phone"),document.getString("job"),
                                document.getString("password"),document.getString("location"),document.getString("type")
                                , document.getString("rate"), document.getString("token"), document.getString("image")
                                , document.getString("aid"), document.getString("comission")));
                    }
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        googleMap.setMyLocationEnabled(true);

        for (int i=0; i<info.size();i++){
            double latitude = Double.parseDouble(info.get(i).location.substring(0,info.get(i).location.indexOf(",")));
            double longitude = Double.parseDouble(info.get(i).location.substring(info.get(i).location.indexOf(",")+1));

            mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(info.get(i).name+"-"+info.get(i).job).snippet(info.get(i).addressd));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude),12.0f));
        }
    }
}