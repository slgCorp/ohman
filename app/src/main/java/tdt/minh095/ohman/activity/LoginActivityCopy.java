package tdt.minh095.ohman.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;

import tdt.minh095.ohman.R;

public class LoginActivity extends AppCompatActivity {
    AppCompatButton button;
    EditText edPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edPass = (EditText) findViewById(R.id.edLoginPass);
        button = (AppCompatButton) findViewById(R.id.btn_Login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        edPass.setTypeface(Typeface.DEFAULT);
    }
	private int dangnhap(String username, String pass){
		return 1;
	}
	private int dangnhap1(String username, String pass){
		return 1;
	}
}
