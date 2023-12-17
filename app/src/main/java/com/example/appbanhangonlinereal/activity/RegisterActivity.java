package com.example.appbanhangonlinereal.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanhangonlinereal.R;
import com.example.appbanhangonlinereal.Retrofit.ApiSale;
import com.example.appbanhangonlinereal.Retrofit.RetrofitClient;
import com.example.appbanhangonlinereal.utils.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RegisterActivity extends AppCompatActivity {
    TextView txt_login;
    EditText email, username ,password, repass, mobile;
    Button btnRegister;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiSale apiSale;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initControl();
    }
    private void initControl() {
        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_login = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent_login);
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    private void register() {
        String str_email = email.getText().toString().trim();
        String str_pass = password.getText().toString().trim();
        String str_repass = repass.getText().toString().trim();
        String str_mobile = mobile.getText().toString().trim();
        String str_username = username.getText().toString().trim();
        if (TextUtils.isEmpty(str_email)) {
            Toast.makeText(this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(str_username)) {
            Toast.makeText(this, "Please enter your username", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(str_pass)) {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(str_repass)) {
            Toast.makeText(this, "Please enter your repassword", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(str_mobile)) {
            Toast.makeText(this, "Please enter your phone num", Toast.LENGTH_SHORT).show();
        }
        // check password
        else {
            if (str_pass.equals(str_repass)) {
                compositeDisposable.add(apiSale.register(str_email, str_pass, str_username, str_mobile)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            userModel -> {
                                if(userModel.isSuccess()){
                                    Utils.user_current.setEmail(str_email);
                                    Utils.user_current.setPass(str_pass);
                                    Intent intent_register = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(intent_register);
                                    finish();
                                }else {
                                    Toast.makeText(this, userModel.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        ));
            } else{
            Toast.makeText(this, "Password re-entered does not match", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initView() {
        apiSale = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiSale.class);
        txt_login = findViewById(R.id.txt_login);
        email = findViewById(R.id.register_email);
        password = findViewById(R.id.register_pass);
        repass = findViewById(R.id.register_repass);
        mobile = findViewById(R.id.register_mobile);
        username = findViewById(R.id.register_username);
        btnRegister = findViewById(R.id.btnRegister);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();

    }
}