package com.example.real_timelocationtracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EnterFinderPhoneNumber extends AppCompatActivity {
    private String type,phoneNumber,name;
    private HashMap<String,String> map;
    private CountryCodePicker ccp2;
    private EditText finderNumberEditText;
    private String finderNumber;
    private DatabaseReference mDatabase;
    List<String> finderList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_finder_phone_number);
        ccp2=findViewById(R.id.ccp2);
        mDatabase=FirebaseDatabase.getInstance().getReference();
        getList();
        finderNumberEditText=findViewById(R.id.finderNumberEDT);
        ccp2.registerCarrierNumberEditText(finderNumberEditText);
        Intent intent=getIntent();
        type=intent.getStringExtra("type");
        phoneNumber=intent.getStringExtra("mobile");
        name=intent.getStringExtra("name");



    }
    public void logout(View view)
    {   //if(!finderList.equals(ccp2.getFullNumberWithPlus().replace(" ",""))) {
      //  Toast.makeText(EnterFinderPhoneNumber.this,"Size is:"+finderList.get(0)+"",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(EnterFinderPhoneNumber.this, TargetFinalPage.class);
        ccp2.registerCarrierNumberEditText(finderNumberEditText);
        map = new HashMap<>();
        map.put("type", type);
        map.put("name", name);
        map.put("finderphoneNumber", ccp2.getFullNumberWithPlus().replace(" ", ""));
        if(finderList.contains(ccp2.getFullNumberWithPlus().replace(" ", ""))) {
            FirebaseDatabase.getInstance().getReference().child("target").child(phoneNumber).setValue(map);
            // Toast.makeText(EnterFinderPhoneNumber.this,ccp2.getFullNumberWithPlus(),Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }
        else
            Toast.makeText(EnterFinderPhoneNumber.this,"Finder Not found",Toast.LENGTH_SHORT).show();

   // }
    //else
      //  Toast.makeText(EnterFinderPhoneNumber.this,"Number already registred as target ",Toast.LENGTH_SHORT).show();
    }
    private void getList() {

        mDatabase.child("finder").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
               String typeinDB=snapshot.getKey();
                finderList.add(typeinDB);
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
    }
}