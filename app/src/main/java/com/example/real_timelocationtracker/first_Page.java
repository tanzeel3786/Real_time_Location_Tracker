package com.example.real_timelocationtracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class first_Page extends AppCompatActivity {
  final   Handler handler=new Handler();
  int REQUEST_LOCATION=1;
    Runnable my_runnable;
    boolean stop = false;
    private FirebaseAuth mAuth;
    String phoneNO="";
    String typeinDB="";


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            Objects.requireNonNull(this.getSupportActionBar()).hide();
        }
        catch (NullPointerException e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }




        //This is to run the page in every 3 seconds when internet is Off
        my_runnable = () -> {


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if(permissionLocation())
                {   if(isInternetAvailable())
                     {stop();

                     if(FirebaseAuth.getInstance().getCurrentUser()!=null) {
                      phoneNO= FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
                      final Query q=FirebaseDatabase.getInstance().getReference().orderByChild(phoneNO);
                      q.addChildEventListener(new ChildEventListener() {
                          @Override
                          public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                              typeinDB=snapshot.getKey();
                              Toast.makeText(first_Page.this,typeinDB,Toast.LENGTH_SHORT).show();

                          }

                          @Override
                          public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

                          }

                          @Override
                          public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {

                          }

                          @Override
                          public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

                          }

                          @Override
                          public void onCancelled(@NonNull @NotNull DatabaseError error) {

                          }
                      });
                      if(typeinDB.equals("finder"))
                         startActivity(new Intent(first_Page.this, FinderAndTargetCount.class));
                      else if(typeinDB.equals("target"))
                          startActivity(new Intent(first_Page.this, TargetFinalPage.class));

                     }
                     else
                    moveTonextActivity();
                     }
                     else
                     {Toast.makeText(getApplicationContext(),"Please turn On your Internet",Toast.LENGTH_SHORT).show();
                         start();
                      }
                }
                else
                {
                       start();
                }
            }
        };
        my_runnable.run();
        setContentView(R.layout.activity_first__page);
    }
   @RequiresApi(api = Build.VERSION_CODES.M)
   public boolean permissionLocation()
   {       String[] permissions={Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_BACKGROUND_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};

       if (ContextCompat.checkSelfPermission(
               first_Page.this, Manifest.permission.ACCESS_FINE_LOCATION) ==
               PackageManager.PERMISSION_GRANTED) {
           return true;
       }
       else
       {

           requestPermissions(permissions,0);

       }
       return  false;


   }

    private void moveTonextActivity() {
        Intent intent=new Intent(getApplicationContext(),Choose_Finder_Or_Target.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(first_Page.this,"Location permission on at begining",Toast.LENGTH_SHORT).show();


                }  else {
                    Toast.makeText(first_Page.this,"Location permission is needed to proceed further ",Toast.LENGTH_SHORT).show();
                }
        }
    }

    public void start() {
        handler.postDelayed(my_runnable, 3000);
    }


    public void stop() {
        handler.removeCallbacks(my_runnable);
    }

    public boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        assert cm != null;
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }


}



