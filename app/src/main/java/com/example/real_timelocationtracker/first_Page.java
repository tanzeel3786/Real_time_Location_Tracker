package com.example.real_timelocationtracker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import java.net.InetAddress;
import java.util.Objects;


public class first_Page extends AppCompatActivity {
  final   Handler handler=new Handler();
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

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(isInternetAvailable())
                Toast.makeText(getApplicationContext(),"on",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"off",Toast.LENGTH_SHORT).show();


                handler.postDelayed(this, 3000);
            }
        }, 1000);

        setContentView(R.layout.activity_first__page);



    }

    public boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        assert cm != null;
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }


}
