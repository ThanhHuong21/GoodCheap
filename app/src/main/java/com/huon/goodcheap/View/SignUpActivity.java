package com.huon.goodcheap.View;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.huon.goodcheap.Config.PreferanceManager;
import com.huon.goodcheap.Controller.UserController;
import com.huon.goodcheap.R;

public class SignUpActivity extends AppCompatActivity
 implements View.OnClickListener {
    private Button btndangky;
    private EditText editemail,editpass,editpass1,edithoten,edittencuahang,
    editsdt;
    private PreferanceManager preferanceManager;
    private int checked=0;
    private String Email_Vali="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private UserController userController;
    Toolbar toolbar;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        InitWidget();
        Init();
        btndangky.setOnClickListener(this);
    }

    private void Init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        userController=new UserController(this);
            preferanceManager=new PreferanceManager(this);
            if(preferanceManager.getType().equalsIgnoreCase("USERS")){
                edittencuahang.setVisibility(View.GONE);
            }else{
                edittencuahang.setVisibility(View.VISIBLE);
                checked=1;
            }

    }

    private void InitWidget() {
        toolbar=findViewById(R.id.toolbar);
        btndangky=findViewById(R.id.btnlogin);
       edithoten=findViewById(R.id.editten);
       edittencuahang=findViewById(R.id.edittencuahang);
        editsdt=findViewById(R.id.editsdt);
        editemail=findViewById(R.id.editemail);
        editpass=findViewById(R.id.editpass);
        editpass1=findViewById(R.id.editpass1);
    }

    @Override
    public void onClick(View v) {
           switch (v.getId()){
               case R.id.btnlogin:
                   String email=editemail.getText().toString().trim();
                   String ten=edithoten.getText().toString().trim();
                   String sdt=editsdt.getText().toString().trim();
                   String tencuahang=edittencuahang.getText().toString().trim();
                   String pass=editpass.getText().toString().trim();
                   String pass1=editpass1.getText().toString().trim();
                   if(email.length()>0 && !email.matches(Email_Vali)){
                       Toast.makeText(this,"Email không hợp lệ",Toast.LENGTH_LONG).show();
                   }
                   if(ten.length()<10){
                       Toast.makeText(this, "Tối thiểu 10 ký tự", Toast.LENGTH_SHORT).show();
                   }
                   if(pass.length()<6){
                       Toast.makeText(this, "Mật khẩu tối thiểu 6 ký tự", Toast.LENGTH_SHORT).show();
                   }
                   if(!pass.equals(pass1)){
                       Toast.makeText(this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                   }
                   if(sdt.length()<9 || sdt.length()>11){
                       Toast.makeText(this, "SDT >9 & SDT <12", Toast.LENGTH_SHORT).show();
                   }
                    if(checked==1){
                        if(tencuahang.length()<3){
                            Toast.makeText(this, "Tên cửa hàng ít nhất 3 ký tự", Toast.LENGTH_SHORT).show();
                        }
                        if(sdt.length()>9 && sdt.length()<11 && ten.length()>10
                                && pass.length()>6 && pass.equals(pass1) && email.length()>0 && email.matches(Email_Vali)
                                && tencuahang.length()>3){
                         userController.HandleDangKyUser(email,pass,ten,sdt,preferanceManager.getType(),tencuahang);
                        }

                    }

                    break;

                   
           }
    }
}
