package com.example.sanaati.Employees.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.sanaati.Customers.Activity.ChangePasswordActivity;
import com.example.sanaati.Customers.Activity.MainActivity;
import com.example.sanaati.Customers.Fragment.FabFragment;
import com.example.sanaati.Customers.Fragment.MainFragment;
import com.example.sanaati.Customers.Fragment.ProfileFragment;
import com.example.sanaati.Employees.Fragment.EmpMainFrag;
import com.example.sanaati.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

public class EmpMainActivity extends AppCompatActivity {
    FrameLayout frameLayout;
    Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_main);
        Space(savedInstanceState);

        frameLayout = findViewById(R.id.frameLayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool);
        setSupportActionBar(toolbar);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (task.isSuccessful()) {
                            String token = task.getResult();
                            SharedPreferences.Editor editor = getSharedPreferences("Info",MODE_PRIVATE).edit();
                            editor.putString("token", token);
                            editor.putString("new_token", "yes");
                            editor.apply();
                            UploadToken();
                        }
                    }
                });

       GPSTracker gpsTracker = new GPSTracker(EmpMainActivity.this);
        location = gpsTracker.getLocation();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Users").document("type").collection("Employees").document(getSharedPreferences("Info",MODE_PRIVATE).getString("userid",""))
                .update("location", location.getLatitude()+","+location.getLongitude());

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.down_to_top,R.anim.top_to_down,R.anim.down_to_top,R.anim.top_to_down);
        ft.addToBackStack(null);
        ft.replace(R.id.frameLayout, new EmpMainFrag());
        ft.commit();
    }

    public final class GPSTracker implements LocationListener {

        private final Context mContext;

        // flag for GPS status
        public boolean isGPSEnabled = false;

        // flag for network status
        boolean isNetworkEnabled = false;

        // flag for GPS status
        boolean canGetLocation = false;

        Location location; // location
        double latitude; // latitude
        double longitude; // longitude

        // The minimum distance to change Updates in meters
        private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1; // 10 meters

        // The minimum time between updates in milliseconds
        private static final long MIN_TIME_BW_UPDATES = 1; // 1 minute

        // Declaring a Location Manager
        protected LocationManager locationManager;

        public GPSTracker(Context context) {
            this.mContext = context;
            getLocation();
        }

        public Location getLocation() {
            try {
                locationManager = (LocationManager) mContext
                        .getSystemService(Context.LOCATION_SERVICE);

                // getting GPS status
                isGPSEnabled = locationManager
                        .isProviderEnabled(LocationManager.GPS_PROVIDER);

                Log.v("isGPSEnabled", "=" + isGPSEnabled);

                // getting network status
                isNetworkEnabled = locationManager
                        .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                Log.v("isNetworkEnabled", "=" + isNetworkEnabled);

                if (isGPSEnabled == false && isNetworkEnabled == false) {
                    // no network provider is enabled
                } else {
                    this.canGetLocation = true;
                    if (isNetworkEnabled) {
                        location = null;
                        if (ActivityCompat.checkSelfPermission(EmpMainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        }
                        locationManager.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("Network", "Network");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                    // if GPS Enabled get lat/long using GPS Services
                    if (isGPSEnabled) {
                        location=null;
                        if (location == null) {
                            locationManager.requestLocationUpdates(
                                    LocationManager.GPS_PROVIDER,
                                    MIN_TIME_BW_UPDATES,
                                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                            Log.d("GPS Enabled", "GPS Enabled");
                            if (locationManager != null) {
                                location = locationManager
                                        .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                if (location != null) {
                                    latitude = location.getLatitude();
                                    longitude = location.getLongitude();
                                }
                            }
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return location;
        }

        public void stopUsingGPS() {
            if (locationManager != null) {
                locationManager.removeUpdates(GPSTracker.this);
            }
        }

        public double getLatitude() {
            if (location != null) {
                latitude = location.getLatitude();
            }
            return latitude;
        }

        public double getLongitude() {
            if (location != null) {
                longitude = location.getLongitude();
            }

            // return longitude
            return longitude;
        }

        public boolean canGetLocation() {
            return this.canGetLocation;
        }

        public void showSettingsAlert() {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(EmpMainActivity.this);

            // Setting Dialog Title
            alertDialog.setTitle("GPS is settings");

            // Setting Dialog Message
            alertDialog
                    .setMessage("GPS is not enabled. Do you want to go to settings menu?");

            // On pressing Settings button
            alertDialog.setPositiveButton("Settings",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(
                                    Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    });

            // on pressing cancel button
            alertDialog.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

            // Showing Alert Message
            alertDialog.show();
        }

        @Override
        public void onLocationChanged(Location location) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_example, menu);
//
        // return true so that the menu pop up is opened
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.signout:
                // do something
                return true;
            case R.id.changpass:
                startActivity(new Intent(EmpMainActivity.this, ChangePasswordActivity.class));
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


    private void UploadToken() {

        if(getSharedPreferences("Info",MODE_PRIVATE).getString("new_token", "no").equals("yes")){
            SharedPreferences.Editor editor = getSharedPreferences("Info",MODE_PRIVATE).edit();
            editor.putString("new_token", "no");
            editor.apply();

            FirebaseFirestore db = FirebaseFirestore.getInstance();

            if((getSharedPreferences("Info",MODE_PRIVATE).getString("type","").equals("موظف"))){
                db.collection("Users").document("type").collection("Employees").document(getSharedPreferences("Info",MODE_PRIVATE).getString("userid",""))
                        .update("token", getSharedPreferences("Info",MODE_PRIVATE).getString("token", "-"));
            }else {
                db.collection("Users").document("type").collection("Customers").document(getSharedPreferences("Info",MODE_PRIVATE).getString("userid",""))
                        .update("token", getSharedPreferences("Info",MODE_PRIVATE).getString("token", "-"));
            }
        }
    }

    private void Space(Bundle savedInstanceState){

        SpaceNavigationView spaceNavigationView = (SpaceNavigationView) findViewById(R.id.space);
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);

        spaceNavigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_help));
        spaceNavigationView.addSpaceItem(new SpaceItem("",R.drawable.ic_baseline_person_pin_24));
        spaceNavigationView.showIconOnly();
        spaceNavigationView.setSpaceItemIconSize(100);
        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.down_to_top,R.anim.top_to_down,R.anim.down_to_top,R.anim.top_to_down);
                ft.addToBackStack(null);
                ft.replace(R.id.frameLayout, new EmpMainFrag());
                ft.commit();
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                switch (itemIndex){
                    case 0:
                        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                        ft1.setCustomAnimations(R.anim.down_to_top,R.anim.top_to_down,R.anim.down_to_top,R.anim.top_to_down);
                        ft1.addToBackStack(null);
                        ft1.replace(R.id.frameLayout, new FabFragment());
                        ft1.commit();
                        break;
                    case 1:
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.setCustomAnimations(R.anim.down_to_top,R.anim.top_to_down,R.anim.down_to_top,R.anim.top_to_down);
                        ft.addToBackStack(null);
                        ft.replace(R.id.frameLayout, new ProfileFragment());
                        ft.commit();
                        break;

                }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                switch (itemIndex){
                    case 0:
                        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                        ft1.setCustomAnimations(R.anim.down_to_top,R.anim.top_to_down,R.anim.down_to_top,R.anim.top_to_down);
                        ft1.addToBackStack(null);
                        ft1.replace(R.id.frameLayout, new FabFragment());
                        ft1.commit();
                        break;

                    case 1:
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.setCustomAnimations(R.anim.down_to_top,R.anim.top_to_down,R.anim.down_to_top,R.anim.top_to_down);
                        ft.addToBackStack(null);
                        ft.replace(R.id.frameLayout, new ProfileFragment());
                        ft.commit();
                        break;
                }
            }
        });
        spaceNavigationView.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
    }

}