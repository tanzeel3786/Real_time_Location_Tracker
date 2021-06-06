package com.example.real_timelocationtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class Choose_Finder_Or_Target extends AppCompatActivity  {
     private String type="";
     private RadioButton button1;
    private RadioButton button2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            Objects.requireNonNull(this.getSupportActionBar()).hide();
        }
        catch (NullPointerException e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        setContentView(R.layout.activity_choose__finder__or__target);
        button1=findViewById(R.id.cfot_finderRadioBtn);
        button2=findViewById(R.id.cfot_TargetRadioBtn);

    }

    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();


        switch(view.getId()) {
            case R.id.cfot_finderRadioBtn:
                if (checked)
                    type="finder";
                    break;
            case R.id.cfot_TargetRadioBtn:
                if (checked)
                    type="target";
                break;
        }
    }




    public void moveToNextActivity(View view) {
        if(button1.isChecked()||button2.isChecked()) {
            Intent intent = new Intent(getApplicationContext(), Signup.class);
            intent.putExtra("type", type);
            startActivity(intent);
        }
        else
            Toast.makeText(Choose_Finder_Or_Target.this,"Please choose an option",Toast.LENGTH_LONG).show();
    }
}
