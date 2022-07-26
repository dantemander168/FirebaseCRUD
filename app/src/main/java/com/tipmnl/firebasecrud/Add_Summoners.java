package com.tipmnl.firebasecrud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Add_Summoners extends AppCompatActivity implements View.OnClickListener{

    ProgressBar pbloading;
    EditText et_add;
    Button btn_add, btn_cancel;
    FirebaseDatabase database;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_summoners);

        pbloading = (ProgressBar) findViewById(R.id.pb_loading);
        et_add = (EditText) findViewById(R.id.et_add_summoner);
        btn_add = (Button) findViewById(R.id.btn_add_summoner);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);

        btn_add.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Summoners");

    }

    @Override
    public void onClick(View view) {
        if(view == btn_add){
            addSummoner();
            pbloading.setVisibility(View.VISIBLE);
        }else{
            startActivity(new Intent(Add_Summoners.this, MainActivity.class));
        }
    }

    public void addSummoner(){
        String summoner_name = et_add.getText().toString().trim();
        if (summoner_name.isEmpty()){
            et_add.setError("Please check your username");
            et_add.requestFocus();
            pbloading.setVisibility(View.GONE);
            return;
        }else{
            Summoner summoner = new Summoner();
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String uid = reference.push().getKey();
                    summoner.setSummoner_name(summoner_name);
                    reference.child(uid).setValue(summoner);
                    Toast.makeText(getApplicationContext(),"Summoner successfully registered!",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Add_Summoners.this, MainActivity.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}