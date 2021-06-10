package com.example.real_timelocationtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class FinderAndTargetCount extends AppCompatActivity {

    private ListView listView;
    String[] maintitle ={
            "Tanzeel","Tannu king",
            "Krishu","Mannu",
            "Patel","Jhanvi Nonwa","Prakash","Jadverkar naag","Smarty mukesh",
    };
    private TextView findertargetcounttextview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finder_and_target_count);

         findertargetcounttextview=findViewById(R.id.findertargetcounttextview);
         findertargetcounttextview.setText("Found "+maintitle.length+" targets");
        CustomListAdapter1 adapter=new CustomListAdapter1(this, maintitle);
        listView=(ListView)findViewById(R.id.findertargetcountlist);
        listView.setAdapter(adapter);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.finderandtargetcountmenu,menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.findertargetcountlogout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(FinderAndTargetCount.this,Choose_Finder_Or_Target.class));
                break;
        }
        return true;
    }
}