package com.example.real_timelocationtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class Signup extends AppCompatActivity {
  private TextInputLayout signup_targetTIL;
  String type;
  private EditText signupNameEdittext;
  private EditText signupNumberEdittext;
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
        signupNameEdittext=findViewById(R.id.signupNameEdittext);
        signupNumberEdittext=findViewById(R.id.signupNumberEdittext);
        signup_targetTIL= findViewById(R.id.signup_targetTIL);
        Intent intent=getIntent();
          type=intent.getStringExtra("type");
         if(type.equals("target"))
             signup_targetTIL.setHint("Enter Your Finder Phone Number");
         else
             signup_targetTIL.setHint("Enter Your Phone Number");







    }
    public void moveToOTPActivity(View view) {
        if(signupNumberEdittext.getText().toString().length()>0&&signupNameEdittext.getText().toString().length()>0) {
            Intent intent = new Intent(getApplicationContext(), verifyOTP.class);

            intent.putExtra("type", type);
            intent.putExtra("mobile","+91"+signupNumberEdittext.getText().toString());
            startActivity(intent);
        }
        else
            Toast.makeText(Signup.this,"Please fill Completely",Toast.LENGTH_LONG).show();
    }
}
