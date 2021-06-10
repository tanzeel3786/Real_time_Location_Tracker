package com.example.real_timelocationtracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

import java.util.Objects;

public class Signup extends AppCompatActivity {
    String type;
  private EditText signupNameEdittext;
  private EditText signupNumberEdittext;
  private CountryCodePicker ccp;

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
        ccp=findViewById(R.id.ccp);
        signupNameEdittext=findViewById(R.id.signupNameEdittext);
        signupNumberEdittext=findViewById(R.id.signupNumberEdittext);
        ccp.registerCarrierNumberEditText(signupNumberEdittext);
        Intent intent=getIntent();
          type=intent.getStringExtra("type");

    }
    public void moveToOTPActivity(View view) {
        if(signupNumberEdittext.getText().toString().length()>0&&signupNameEdittext.getText().toString().length()>0) {
            Intent intent = new Intent(getApplicationContext(), verifyOTP.class);

            intent.putExtra("type", type);
            intent.putExtra("name",signupNameEdittext.getText().toString());
            intent.putExtra("mobile",ccp.getFullNumberWithPlus().replace(" ",""));
            startActivity(intent);
            
        }
        else
            Toast.makeText(Signup.this,"Please fill Completely",Toast.LENGTH_LONG).show();
    }
}
