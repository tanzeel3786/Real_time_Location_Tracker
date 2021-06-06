package com.example.real_timelocationtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class verifyOTP extends AppCompatActivity {
    private EditText verifyOTPEdittext1,verifyOTPEdittext2,verifyOTPEdittext3,verifyOTPEdittext4,verifyOTPEdittext5,verifyOTPEdittext6;
    private String type,phoneNumber;
    private FirebaseAuth mAuth;
    private String otp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        try
        { Objects.requireNonNull(this.getSupportActionBar()).hide();
        }
        catch (NullPointerException e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        mAuth=FirebaseAuth.getInstance();

        Intent intent=getIntent();
        type=intent.getStringExtra("type");
        phoneNumber=intent.getStringExtra("mobile");

        verifyOTPEdittext1=findViewById(R.id.verifyOTPEdittext1);
        verifyOTPEdittext2=findViewById(R.id.verifyOTPEdittext2);
        verifyOTPEdittext3=findViewById(R.id.verifyOTPEdittext3);
        verifyOTPEdittext4=findViewById(R.id.verifyOTPEdittext4);
        verifyOTPEdittext5=findViewById(R.id.verifyOTPEdittext5);
        verifyOTPEdittext6=findViewById(R.id.verifyOTPEdittext6);

        moveToNextEditText(verifyOTPEdittext1,verifyOTPEdittext2);
        moveToNextEditText(verifyOTPEdittext2,verifyOTPEdittext3);
        moveToNextEditText(verifyOTPEdittext3,verifyOTPEdittext4);
        moveToNextEditText(verifyOTPEdittext4,verifyOTPEdittext5);
        moveToNextEditText(verifyOTPEdittext5,verifyOTPEdittext6);

        initiateOTP();




    }
    public void checkOTPManually(View viewo)
    {
        String editTextOTP=verifyOTPEdittext1.getText().toString()+verifyOTPEdittext2.getText().toString()+
                verifyOTPEdittext3.getText().toString()+verifyOTPEdittext4.getText().toString()+
                verifyOTPEdittext5.getText().toString()+verifyOTPEdittext6.getText().toString();

        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(otp,editTextOTP);
        signInWithPhoneAuthCredential(credential);
    }

    private void initiateOTP() {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onCodeSent(@NonNull @NotNull String s, @NonNull @NotNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                otp=s;

                            }

                            @Override
                            public void onVerificationCompleted(@NonNull @NotNull PhoneAuthCredential phoneAuthCredential) {
                              signInWithPhoneAuthCredential(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull @NotNull FirebaseException e) {

                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information


                        FirebaseUser user = Objects.requireNonNull(task.getResult()).getUser();
                        startActivity(new Intent(verifyOTP.this,FinderAndTargetCount.class));
                        // Update UI
                    } else {
                        // Sign in failed, display a message and update the UI
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            // The verification code entered was invalid
                            Toast.makeText(verifyOTP.this,"Invalid OTP",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void moveToNextEditText(EditText et1, EditText et2) {
        et1.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(et1.getText().toString().length()==1)     //size as per your requirement
                {
                    et2.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });
    }
}