package com.tipmnl.firebasecrud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private TextView tv_user, tv_email;
    private Button button_summoner;
    private ListView lv_summoners;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private DatabaseReference userReference;
    Summoner summoners;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        tv_user = (TextView) findViewById(R.id.tv_user);
        tv_email = (TextView) findViewById(R.id.tv_email);
        button_summoner = (Button) findViewById(R.id.btn_summoner);
        lv_summoners = (ListView) findViewById(R.id.lv_summoners);

        getData();

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Summoners");

        arrayList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<String>(this,R.layout.summoner_info, R.id.tv_key,arrayList);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){
                    summoners = ds.getValue(Summoner.class);
                    String key = ds.getKey();
                    arrayList.add(key + "\n"+ summoners.getSummoner_name().toString());
                }
                lv_summoners.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        lv_summoners.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent updateDelete = new Intent(MainActivity.this, UpdateDeleteSummoner.class);
                String str[] = arrayList.get(i).split("\n");
                updateDelete.putExtra("key",str[0]);
                updateDelete.putExtra("summoner_name",str[1]);
                startActivity(updateDelete);
            }
        });

        button_summoner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Add_Summoners.class));
            }
        });


    }

    private void getData(){
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = null;
        if (firebaseUser != null){
            uid = firebaseUser.getUid();
        }else{
            startActivity(new Intent(MainActivity.this,Register.class));
        }
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        userReference = databaseReference.child(uid);
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                tv_user.setText(user.username);
                tv_email.setText(user.email);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
    }


}