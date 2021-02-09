package com.example.sanaati.UsersAuth.Activity;

import android.Manifest;
import android.app.ProgressDialog;
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
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.sanaati.MainActivity;
import com.example.sanaati.R;
import com.example.sanaati.UsersAuth.Class.Users;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoginActivity extends AppCompatActivity {

    private EditText name, inputPassword;
    private FirebaseAuth auth;

    CircleImageView img;
    private Button btnSignup, btnLogin, btnReset;
    ProgressDialog pb;
    FirebaseFirestore db;
    ArrayList<Users> users;
    Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        if (!getSharedPreferences("Info", Context.MODE_PRIVATE).getString("userid","").equals("")) {
//            GPSTracker gpsTracker = new GPSTracker(LoginActivity.this);
//            location = gpsTracker.getLocation();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish(); }

        db = FirebaseFirestore.getInstance();
        name = (EditText) findViewById(R.id.username);
        inputPassword = (EditText) findViewById(R.id.password);
        btnSignup = (Button) findViewById(R.id.btn_signup);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReset = (Button) findViewById(R.id.btn_reset_password);
        img=findViewById(R.id.img);

        pb = new ProgressDialog(this);
        pb.setMessage("يرجى الانتظار...");

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.show();
                String username = name.getText().toString();
                final String password = inputPassword.getText().toString();

                if (username.equals("")) {
                    Toast.makeText(getApplicationContext(), "ادخل اسمك", Toast.LENGTH_SHORT).show();
                    pb.dismiss();
                    return;
                }

                if (password.equals("")) {
                    Toast.makeText(getApplicationContext(), "ادخل كلمة السر", Toast.LENGTH_SHORT).show();
                    pb.dismiss();
                    return;
                }

                users = new ArrayList<>();

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Users").document("type").collection("Customers").whereEqualTo("name", username).whereEqualTo("password",password)
                        .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        List<DocumentSnapshot> gr = queryDocumentSnapshots.getDocuments();
                        if(gr==null){

                        }else{
                            if (gr.size() != 0) {
                                for (int i = 0; i < gr.size(); i++) {
                                    users.add(new Users(gr.get(i).getString("userid"), gr.get(i).getString("name"), gr.get(i).getString("email"),
                                            gr.get(i).getString("addressd"), gr.get(i).getString("phone"), gr.get(i).getString("job"),
                                            gr.get(i).getString("password"), gr.get(i).getString("location"), gr.get(i).getString("type")
                                            , gr.get(i).getString("rate"), gr.get(i).getString("token"), gr.get(i).getString("image")
                                            , gr.get(i).getString("aid"), gr.get(i).getString("comission")));

                                }
                        }}

                            FirebaseFirestore db2 = FirebaseFirestore.getInstance();
                            db2.collection("Users").document("type").collection("Employees").whereEqualTo("name", username).whereEqualTo("password",password)
                                    .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    List<DocumentSnapshot> gr = queryDocumentSnapshots.getDocuments();
                                    if(gr==null){

                                    }else{
                                        if (gr.size() != 0) {
                                            for (int i = 0; i < gr.size(); i++) {
                                                users.add(new Users(gr.get(i).getString("userid"), gr.get(i).getString("name"), gr.get(i).getString("email"),
                                                        gr.get(i).getString("addressd"), gr.get(i).getString("phone"), gr.get(i).getString("job"),
                                                        gr.get(i).getString("password"), gr.get(i).getString("location"), gr.get(i).getString("type")
                                                        , gr.get(i).getString("rate"), gr.get(i).getString("token"), gr.get(i).getString("image")
                                                        , gr.get(i).getString("aid"), gr.get(i).getString("comission")));
                                            }
                                        }

                                        if(users.size()>0){

                                            if(users.get(0).name.equals(username) && users.get(0).password.equals(password)) {
                                                pb.dismiss();
                                                SharedPreferences.Editor editor =
                                                        getSharedPreferences("Info", Context.MODE_PRIVATE).edit();
                                                editor.putString("name",username);
                                                editor.putString("userid",users.get(0).userid);
                                                editor.putString("location",users.get(0).location);
                                                editor.apply();
//                                                GPSTracker gpsTracker = new GPSTracker(LoginActivity.this);
//                                                location = gpsTracker.getLocation();
//
//                                                DocumentReference washingtonRef = db.collection("Users").whereEqualTo("name", username).whereEqualTo("password",password);
//                                                washingtonRef.update("Location", location.getLatitude()+","+location.getLongitude())
//                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                            @Override
//                                                            public void onSuccess(Void aVoid) {
//
//                                                            }
//                                                        })
//                                                        .addOnFailureListener(new OnFailureListener() {
//                                                            @Override
//                                                            public void onFailure(@NonNull Exception e) {
//
//                                                            }
//                                                        });

                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }else{
                                                pb.dismiss();
                                                Toast.makeText(LoginActivity.this, "المستخدم غير موجود1", Toast.LENGTH_SHORT).show();
                                            }
                                        }else{
                                            pb.dismiss();
                                            Toast.makeText(LoginActivity.this, "المستخدم غير موجو2د", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                }
                            });
                        }});
            }

        });
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

        /**
         * Function to get the user's current location
         *
         * @return
         */
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
                        if (ActivityCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

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

        /**
         * Stop using GPS listener Calling this function will stop using GPS in your
         * app
         * */
        public void stopUsingGPS() {
            if (locationManager != null) {
                locationManager.removeUpdates(GPSTracker.this);
            }
        }

        /**
         * Function to get latitude
         * */
        public double getLatitude() {
            if (location != null) {
                latitude = location.getLatitude();
            }

            // return latitude
            return latitude;
        }

        /**
         * Function to get longitude
         * */
        public double getLongitude() {
            if (location != null) {
                longitude = location.getLongitude();
            }

            // return longitude
            return longitude;
        }

        /**
         * Function to check GPS/wifi enabled
         *
         * @return boolean
         * */
        public boolean canGetLocation() {
            return this.canGetLocation;
        }

        /**
         * Function to show settings alert dialog On pressing Settings button will
         * lauch Settings Options
         * */
        public void showSettingsAlert() {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);

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

}
