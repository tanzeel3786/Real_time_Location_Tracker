package com.example.real_timelocationtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;

public class EnterFinderPhoneNumber extends AppCompatActivity {
    private String type,phoneNumber,name;
    private HashMap<String,String> map;
    private CountryCodePicker ccp2;
    private EditText finderNumberEditText;
    private String finderNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_finder_phone_number);
        ccp2=findViewById(R.id.ccp2);
        finderNumberEditText=findViewById(R.id.finderNumberEDT);

        Intent intent=getIntent();
        type=intent.getStringExtra("type");
        phoneNumber=intent.getStringExtra("mobile");
        name=intent.getStringExtra("name");



    }
    public void logout(View view)
    {
        Intent intent=new Intent(EnterFinderPhoneNumber.this,TargetFinalPage.class);
        ccp2.registerCarrierNumberEditText(finderNumberEditText);
        map=new HashMap<>();
        map.put("type",type);
        map.put("name",name);
        map.put("finderphoneNumber",ccp2.getFullNumberWithPlus().replace(" ",""));
        FirebaseDatabase.getInstance().getReference().child("target").child(ccp2.getFullNumberWithPlus()).setValue(map);
       // Toast.makeText(EnterFinderPhoneNumber.this,ccp2.getFullNumberWithPlus(),Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
}