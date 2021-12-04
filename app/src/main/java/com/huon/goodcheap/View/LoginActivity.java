package com.huon.goodcheap.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.huon.goodcheap.Controller.UserController;
import com.huon.goodcheap.R;

public class LoginActivity  extends AppCompatActivity
 implements View.OnClickListener {
    private Button btnlogin;
    private EditText editemail,editpass;
    private TextView txtdangky;
    private UserController userController;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        InitWidget();
        Init();
        HandleEvents();
    }

    private void HandleEvents() {
        txtdangky.setOnClickListener(this);
        btnlogin.setOnClickListener(this);
    }

    private void Init() {
        userController=new UserController(this);
    }

    private void InitWidget() {
        btnlogin=findViewById(R.id.btnlogin);
        txtdangky=findViewById(R.id.txtsignup);
        editemail=findViewById(R.id.editemail);
        editpass=findViewById(R.id.editpass);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txtsignup: startActivity(new Intent(this,SignUpActivity.class));break;
            case R.id.btnlogin:
                String email=editemail.getText().toString().trim();
                String pass=editpass.getText().toString().trim();
                userController.HandleLoginUser(email,pass);break;
        }
    }
}
