package com.example.real_timelocationtracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Signup extends AppCompatActivity {
    String type;
  private EditText signupNameEdittext;
  private EditText signupNumberEdittext;
  private CountryCodePicker ccp;
  String typeinDB="";
  boolean flag=true;
  String number="";
    private DatabaseReference mDatabase;
    List<String> targetList=new ArrayList<>();
   public List<String> finderList=new ArrayList<>();
    long findercount=0,targetcount=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        try
        {
            Objects.requireNonNull(this.getSupportActionBar()).hide();
        }
        catch (NullPointerException e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }

        mDatabase=FirebaseDatabase.getInstance().getReference();
        getList();
        ccp=findViewById(R.id.ccp);
        signupNameEdittext=findViewById(R.id.signupNameEdittext);
        signupNumberEdittext=findViewById(R.id.signupNumberEdittext);
        ccp.registerCarrierNumberEditText(signupNumberEdittext);
        number= ccp.getFullNumberWithPlus().replace(" ", "");
        Intent intent=getIntent();
        type=intent.getStringExtra("type");





    }

    private void getList() {

        mDatabase.child("finder").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                typeinDB=snapshot.getKey();
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
        mDatabase.child("target").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                typeinDB=snapshot.getKey();
                targetList.add(typeinDB);
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

    public void moveToOTPActivity(View view) {
        if(signupNumberEdittext.getText().toString().length()>0&&signupNameEdittext.getText().toString().length()>0) {
            if(checkInDB()) {
                Intent intent = new Intent(getApplicationContext(), verifyOTP.class);
                intent.putExtra("type", type);
                intent.putExtra("name", signupNameEdittext.getText().toString());
                intent.putExtra("mobile", ccp.getFullNumberWithPlus().replace(" ", ""));
                intent.putStringArrayListExtra("targetlist", (ArrayList<String>) targetList);
                startActivity(intent);
            }
            else
                Toast.makeText(Signup.this,"Finder and target cant be same",Toast.LENGTH_SHORT).show();

            
        }
        else
            Toast.makeText(Signup.this,"Please fill Completely",Toast.LENGTH_LONG).show();
    }

    private boolean checkInDB() {
        if(type.equals("target"))
        {
         if(finderList.contains(ccp.getFullNumberWithPlus().replace(" ", ""))) {
          //   Toast.makeText(Signup.this, targetList.get(2), Toast.LENGTH_SHORT).show();
             return false;
         }
        }

        else if(type.equals("finder"))
        {
            if(targetList.contains(ccp.getFullNumberWithPlus().replace(" ", "")))
                return false;
        }

        return flag;
    }
}
