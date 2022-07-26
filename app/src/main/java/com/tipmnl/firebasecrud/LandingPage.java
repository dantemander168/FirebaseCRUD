package com.tipmnl.firebasecrud;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LandingPage extends AppCompatActivity implements View.OnClickListener{

    Button button_login, button_register, button_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        button_login = (Button) findViewById(R.id.btn_login_page);
        button_register = (Button) findViewById(R.id.btn_register_page);
        button_exit = (Button) findViewById(R.id.btn_exit);

        button_login.setOnClickListener(this);
        button_register.setOnClickListener(this);
        button_exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == button_login){
            startActivity(new Intent(LandingPage.this, Login.class));
        }else if(view == button_register){
            startActivity(new Intent(LandingPage.this, Register.class));
        }else{
            finish();
        }
    }
}