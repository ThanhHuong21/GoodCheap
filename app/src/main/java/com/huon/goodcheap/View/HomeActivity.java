package com.huon.goodcheap.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.huon.goodcheap.Config.PreferanceManager;
import com.huon.goodcheap.R;
import com.huon.goodcheap.View.FragMent.FragMent_Home;
import com.huon.goodcheap.View.FragMent.FragMent_ProFile;
import com.huon.goodcheap.View.Store.CreateNewsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity  extends AppCompatActivity
 implements BottomNavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView bottomNavigationView;
    private Fragment fm;
    private PreferanceManager preferanceManager;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        InitWidget();
        Init();

    }

    private void Init() {
        preferanceManager=new PreferanceManager(this);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        fm=new FragMent_Home();
        getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,fm).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    private void InitWidget() {
       bottomNavigationView=findViewById(R.id.bottomnavigation);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.trangchu: fm=new FragMent_Home();break;
            case R.id.bantin:
                if(firebaseUser!=null){
                    if(preferanceManager.getType().equalsIgnoreCase("STORE")){
                              startActivity(new Intent(this, CreateNewsActivity.class));
                              finish();
                    }else{
                        Toast.makeText(this,"Chỉ dành cho cửa hàng",Toast.LENGTH_LONG).show();
                    }

                }else{

                    startActivity(new Intent(this,MenuActivity.class));

                }

                break;
            case R.id.thongtin:
                fm=new FragMent_ProFile();break;
        }
        if(fm!=null){
            getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,fm).commit();
        }
        return true;
    }


}
