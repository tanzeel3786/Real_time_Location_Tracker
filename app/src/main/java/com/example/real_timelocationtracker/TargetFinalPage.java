package com.example.real_timelocationtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.skyfishjy.library.RippleBackground;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class TargetFinalPage extends AppCompatActivity {
    private LocationListener locationListener;
    private LocationManager locationManager;
    boolean flag=true;
    private FirebaseAuth mAuth;
    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target_final_page);
        mAuth=FirebaseAuth.getInstance();
        locationManager=(LocationManager) getSystemService(LOCATION_SERVICE);
        final RippleBackground rippleBackground=(RippleBackground)findViewById(R.id.content);
        ImageView imageView=(ImageView)findViewById(R.id.centerImage);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag) {
                    changeFlag();
                    locationManager=(LocationManager) getSystemService(LOCATION_SERVICE);
                    Toast.makeText(TargetFinalPage.this,"Sending live location",Toast.LENGTH_LONG).show();
                    rippleBackground.startRippleAnimation();
                    locationListener=new LocationListener() {
                        @Override
                        public void onLocationChanged(@NonNull Location location) {
                            updateLocation(location);
                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {

                        }
                    };
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                    Location currentPassengerLocation;

                    currentPassengerLocation=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if(currentPassengerLocation!=null) {
                        //  locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                        updateLocation(currentPassengerLocation);

                    }

                }
                else
                { changeFlag();
                    Toast.makeText(TargetFinalPage.this,"Stopping Sending Location",Toast.LENGTH_LONG).show();
                locationManager.removeUpdates(locationListener);
                locationManager=null;
                    rippleBackground.stopRippleAnimation();

                }
            }
        });



    }

    private void changeFlag() {
        if(flag)
            flag=false;
        else
            flag=true;
    }

    private void updateLocation(Location location) {

        LatLng passengerLocation = new LatLng(location.getLatitude(), location.getLongitude()); //getting potion of passenger

        Map<String,Object> hashMap=new HashMap<>();
        hashMap.put("latitude",passengerLocation.latitude );
        hashMap.put("longitude",passengerLocation.longitude);
        FirebaseDatabase.getInstance().getReference().child("target").child(mAuth.getCurrentUser().getPhoneNumber()+"").updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.finderandtargetcountmenu,menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.findertargetcountlogout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(TargetFinalPage.this,Choose_Finder_Or_Target.class));
                break;
        }
        return true;
    }
}