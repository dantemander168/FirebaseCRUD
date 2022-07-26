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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateDeleteSummoner extends AppCompatActivity implements View.OnClickListener{

    private EditText et_key, et_new_summoner;
    private Button btn_update, btn_delete, btn_cancel;
    private ProgressBar pbloading;

    private FirebaseDatabase database;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_summoner);

        et_key = (EditText) findViewById(R.id.et_key);
        et_new_summoner = (EditText) findViewById(R.id.et_new_summoner);
        pbloading = (ProgressBar) findViewById(R.id.pb_loading);
        btn_update = (Button) findViewById(R.id.btn_update_summoner);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        et_key.setText(getIntent().getStringExtra("key"));
        et_new_summoner.setText(getIntent().getStringExtra("summoner_name"));

        database = FirebaseDatabase.getInstance();
        String key = getIntent().getStringExtra("key");
        reference = database.getReference("Summoners").child(key);

        btn_update.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view == btn_update){
            updateSummoner();
            pbloading.setVisibility(View.VISIBLE);
        }else if (view == btn_delete){
            deleteSummoner();
            pbloading.setVisibility(View.VISIBLE);
        }else{
            startActivity(new Intent(UpdateDeleteSummoner.this, MainActivity.class));
        }
    }

    public void updateSummoner(){
        String new_summoner = et_new_summoner.getText().toString().trim();
        if(new_summoner.isEmpty()){
            Toast.makeText(this, "Please input a Summoner name", Toast.LENGTH_SHORT).show();
            pbloading.setVisibility(View.GONE);
        }else{
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    snapshot.getRef().child("summoner_name").setValue(new_summoner);
                    Toast.makeText(UpdateDeleteSummoner.this, "Summoner info updated!", Toast.LENGTH_SHORT).show();
                    pbloading.setVisibility(View.GONE);

                    startActivity(new Intent(UpdateDeleteSummoner.this, MainActivity.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    public void deleteSummoner(){
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot.getRef().child("summoner_name").removeValue();
                Toast.makeText(UpdateDeleteSummoner.this, "Summoner Deleted!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UpdateDeleteSummoner.this, MainActivity.class));
                pbloading.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}