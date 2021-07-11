package com.example.real_timelocationtracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FinderAndTargetCount extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private TextView findertargetcounttextview;
    private DatabaseReference mDatabase;
    String number;
    String[] maintitle=new String[5];
    CustomListAdapter1 adapter;
    static  int i=0;
    private ProgressDialog progressDialog;
    List<String> targets=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finder_and_target_count);
        mDatabase= FirebaseDatabase.getInstance().getReference();
          Intent intent=getIntent();
          number=intent.getStringExtra("mobile");
          try {
              getList();
              showProgress();

          }
          catch (Exception e)
          {
            e.printStackTrace();
          }


         findertargetcounttextview=findViewById(R.id.findertargetcounttextview);

//         for(int i=0;i<maintitle.length;i++)
//             maintitle[i]=targets.get(i);

      //  Toast.makeText(FinderAndTargetCount.this,number+"",Toast.LENGTH_SHORT).show();





    }

    private void showProgress() throws InterruptedException {
        progressDialog = new ProgressDialog(FinderAndTargetCount.this);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("ProgressDialog"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }).start();
        try {
            setAdpater();
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
        listView.setOnItemClickListener(FinderAndTargetCount.this);


    }



    private void setAdpater() throws InterruptedException {


        adapter=new CustomListAdapter1(this, maintitle);
        listView=(ListView)findViewById(R.id.findertargetcountlist);
        listView.setAdapter(adapter);

    }

    private void getList() {


        mDatabase.child("target").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                String target=(String) snapshot.child("finderphoneNumber").getValue();
                if(target.equals(number));
               maintitle[i]=(String) snapshot.getKey();

              // Toast.makeText(FinderAndTargetCount.this,maintitle[i]+"",Toast.LENGTH_SHORT).show();
               i++;
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String value =adapter.getItem(position);
        if(value!=null)
        {
            Intent intent=new Intent(FinderAndTargetCount.this,ShowTargetLocation.class);
            intent.putExtra("mobile",value);
            startActivity(intent);
        }
     //   Toast.makeText(FinderAndTargetCount.this, value, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}