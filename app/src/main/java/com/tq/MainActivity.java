package com.tq;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tq.view.OwlView;

public class MainActivity extends AppCompatActivity {

    private OwlView mOwlView;
    private EditText email,password;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mOwlView= (OwlView) findViewById(R.id.owl_view);
        email= (EditText) findViewById(R.id.email);
        password= (EditText) findViewById(R.id.password);
        login= (Button) findViewById(R.id.btn_login);
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    mOwlView.open();

                }else{
                    mOwlView.close();
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"我要登录了",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
