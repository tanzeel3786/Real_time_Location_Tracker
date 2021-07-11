package com.example.real_timelocationtracker;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.real_timelocationtracker.databinding.ActivityShowTargetLocationBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ShowTargetLocation extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityShowTargetLocationBinding binding;
    private String targetPhoneNumber;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private LatLng finderLocation;
    private Button show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityShowTargetLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        show=findViewById(R.id.finderBtn);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationManager=(LocationManager) getSystemService(LOCATION_SERVICE);
        Intent intent=getIntent();
        targetPhoneNumber=intent.getStringExtra("mobile");


         showFinderAndTarget();

       // Toast.makeText(ShowTargetLocation.this,targetPhoneNumber,Toast.LENGTH_SHORT).show();
    }

    private void showFinderAndTarget() {
        FirebaseDatabase.getInstance().getReference().child("target").child(targetPhoneNumber).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                double latitude=(Double) snapshot.child("latitude").getValue();
                double longitude=(Double) snapshot.child("longitude").getValue();
                LatLng targetLocation=new LatLng(latitude,longitude);
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                mMap.clear();
                try {
                    Marker targetMarker=mMap.addMarker(new MarkerOptions().position(targetLocation).title("Target").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                    Marker finderMarker=mMap.addMarker(new MarkerOptions().position(finderLocation).title("Finder").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                    ArrayList<Marker> myMarkers = new ArrayList();
                    myMarkers.add(targetMarker);
                    myMarkers.add(finderMarker);
                    for (Marker marker : myMarkers) {
                        builder.include(marker.getPosition());
                    }
                    LatLngBounds bounds = builder.build();
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds,10);
                    mMap.animateCamera(cameraUpdate);
                }
                catch (Exception e)
                {

                }
         //       Toast.makeText(ShowTargetLocation.this,finderLocation.latitude+"",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        show.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                locationManager=(LocationManager) getSystemService(LOCATION_SERVICE);
                locationListener=new LocationListener() {
                    @Override
                    public void onLocationChanged(@NonNull Location location) {
                        updateCameraPosition(location);
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
                    updateCameraPosition(currentPassengerLocation);

                }
                showFinderAndTarget();
            }
        });
        // Add a marker in Sydney and move the camera

    }

    private void updateCameraPosition(Location currentLocation) {
     finderLocation=new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
    }
}