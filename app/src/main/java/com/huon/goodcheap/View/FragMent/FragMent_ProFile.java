package com.huon.goodcheap.View.FragMent;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.huon.goodcheap.Config.PreferanceManager;
import com.huon.goodcheap.R;
import com.huon.goodcheap.View.HomeStoreActivity;
import com.huon.goodcheap.View.LoginActivity;
import com.huon.goodcheap.View.MenuActivity;

public class FragMent_ProFile extends Fragment {
    View view;
    private PreferanceManager preferanceManager;
    TextView txtdangky;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_profile,container,false);
        IniWidget();
        Init();
        txtdangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(), MenuActivity.class));
            }
        });

        return  view;
    }

    private void Init() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        preferanceManager=new PreferanceManager(getContext());

        if(firebaseAuth!=null){

            if(firebaseAuth.getCurrentUser()!=null){
                     txtdangky.setVisibility(View.VISIBLE);

                     if(preferanceManager.getkeyIDUser().length()>5) {
                         startActivity(new Intent( getContext(), HomeStoreActivity.class));
                     }



            }else{
                txtdangky.setVisibility(View.GONE);
                startActivity(new Intent(getContext(), MenuActivity.class));

            }
        }else{
            startActivity(new Intent(getContext(), MenuActivity.class));
        }



    }

    private void IniWidget() {
        txtdangky=view.findViewById(R.id.txtdangxuat);
    }
}
