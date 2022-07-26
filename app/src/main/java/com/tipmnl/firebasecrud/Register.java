package com.tipmnl.firebasecrud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;

    private EditText etUsername, etEmail, etPassword;
    private Button btnRegister, btnRegisterBackToLanding;
    private ProgressBar pbLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        etUsername = (EditText) findViewById(R.id.et_username);
        etEmail = (EditText) findViewById(R.id.et_email);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnRegister = (Button) findViewById(R.id.btn_register);
        btnRegisterBackToLanding = (Button) findViewById(R.id.btn_back_register);
        pbLoading = (ProgressBar) findViewById(R.id.pb_loading);

        btnRegister.setOnClickListener(this);
        btnRegisterBackToLanding.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == btnRegister ){
            register();
            pbLoading.setVisibility(View.VISIBLE);
        }else{
            startActivity(new Intent(Register.this, LandingPage.class));
        }
    }

    public void register(){
       String username, email, password;
       username = etUsername.getText().toString().trim();
       email = etEmail.getText().toString().trim();
       password = etPassword.getText().toString().trim();

       if (username.isEmpty()){
           etUsername.setError("Please check your username");
           etUsername.requestFocus();
           return;
       }
        if (email.isEmpty()){
            etEmail.setError("Please check your email");
            etEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.setError("Please input a valid email address");
            etEmail.requestFocus();
            return;
        }
        if (password.isEmpty()){
            etPassword.setError("Please check your password");
            etPassword.requestFocus();
            return;
        }
        if (password.length()<6){
            etPassword.setError("Minimum password length is 6 characters");
            etPassword.requestFocus();
            return;
        }
            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                User user = new User(username,email);
                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(Register.this,"User has been registered",Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(Register.this,Login.class));
                                                    pbLoading.setVisibility(View.GONE);

                                                }else{
                                                    Toast.makeText(Register.this,"There was an error registering the user",Toast.LENGTH_SHORT).show();
                                                    pbLoading.setVisibility(View.GONE);

                                                }
                                            }
                                        });

                            }else{
                                Toast.makeText(Register.this,"There was an error registering the user",Toast.LENGTH_SHORT).show();
                                pbLoading.setVisibility(View.GONE);
                            }
                        }
                    });
    }
}