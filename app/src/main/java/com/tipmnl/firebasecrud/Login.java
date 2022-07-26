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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;

    private Button button_login, button_login_back;
    private EditText et_login_email, et_login_password;
    private ProgressBar pbLoading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        et_login_email = (EditText) findViewById(R.id.et_email_login);
        et_login_password = (EditText) findViewById(R.id.et_password_login);
        button_login = (Button) findViewById(R.id.btn_login);
        button_login_back = (Button) findViewById(R.id.btn_back_login);
        pbLoading = (ProgressBar) findViewById(R.id.pb_loading);

        button_login.setOnClickListener(this);
        button_login_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == button_login){
            pbLoading.setVisibility(View.VISIBLE);
            loginUser();
        }else{
            startActivity(new Intent(Login.this,LandingPage.class));
        }
    }

    public void loginUser(){
        String email,password;
        email = et_login_email.getText().toString().trim();
        password = et_login_password.getText().toString().trim();

        if (email.isEmpty()){
            et_login_email.setError("Email cannot be empty");
            et_login_email.requestFocus();
        }
        if(password.isEmpty()){
            et_login_password.setError("Password cannot be empty");
            et_login_password.requestFocus();
        }
        else{
            mAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                startActivity(new Intent(Login.this,MainActivity.class));
                                pbLoading.setVisibility(View.GONE);
                            }else{
                                Toast.makeText(Login.this,"Your inputs are invalid. Please try again",Toast.LENGTH_SHORT).show();
                                pbLoading.setVisibility(View.GONE);
                            }
                        }
                    });
        }
    }
}