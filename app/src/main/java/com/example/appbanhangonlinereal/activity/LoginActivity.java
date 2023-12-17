package com.example.appbanhangonlinereal.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanhangonlinereal.R;
import com.example.appbanhangonlinereal.Retrofit.ApiSale;
import com.example.appbanhangonlinereal.Retrofit.RetrofitClient;
import com.example.appbanhangonlinereal.model.UserModel;
import com.example.appbanhangonlinereal.utils.Utils;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {
    TextView txt_register, loginAdmin;
    EditText edtEmail, edtPassword;
    Button btnLogin;
    ApiSale apiSale;
    boolean isLogin;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initControl();
    }

    private void initControl() {
        txt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_email = edtEmail.getText().toString().trim();
                String str_pass = edtPassword.getText().toString().trim();
                if (TextUtils.isEmpty(str_email)) { //chua nhap email
                    Toast.makeText(getApplicationContext(), "Please enter your email", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(str_pass)) { //chua nhap pass
                    Toast.makeText(getApplicationContext(), "Please enter your password", Toast.LENGTH_SHORT).show();
                }else{
                    //luu email va pass vao paper
                    Paper.book().write("email", str_email);
                    Paper.book().write("pass", str_pass);
                    login(str_email, str_pass);
                }
            }
        });
        loginAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLoginAdmin = new Intent(getApplicationContext(), LoginAdminActivity.class);
                startActivity(intentLoginAdmin);
            }
        });
    }

    private void initView() {
//        Paper.init(this);
        apiSale = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiSale.class);
        txt_register = findViewById(R.id.txt_register);
        edtEmail = findViewById(R.id.login_email);
        edtPassword = findViewById(R.id.login_password);
        btnLogin = findViewById(R.id.btnLogin);
        loginAdmin = findViewById(R.id.txt_loginadmin);
    }

    private void login(String email, String pass) {
        compositeDisposable.add(apiSale.login(email, pass)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userModel -> {
                            if(userModel.isSuccess()){
                                isLogin = true;
                                Paper.book().write("islogin", isLogin);
                                Utils.user_current = userModel.getResult().get(0);
                                Paper.book().write("user", userModel.getResult().get(0)); //save user info
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                )
        );
    }
    //luu tai khoan mat khau, khong phai nhap lai khi login
    @Override
    protected void onResume() {
        super.onResume();
        if(Utils.user_current.getEmail() != null && Utils.user_current.getPass() != null){
            edtEmail.setText(Utils.user_current.getEmail());
            edtPassword.setText(Utils.user_current.getPass());
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}