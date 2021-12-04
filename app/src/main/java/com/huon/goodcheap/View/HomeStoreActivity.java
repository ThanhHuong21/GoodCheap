package com.huon.goodcheap.View;

import android.Manifest;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.huon.goodcheap.Config.PreferanceManager;
import com.huon.goodcheap.Controller.UserController;
import com.huon.goodcheap.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HomeStoreActivity  extends AppCompatActivity
 implements View.OnClickListener {
    private UserController userController;
    private PreferanceManager preferanceManager;
    private ImageView hinhanh;
    private String link_hinhanh;
    private StorageReference storageReference;
    private FirebaseStorage firebaseStorage;
    private TextView txtopen,txtclose,txtdiachi,txtsdt;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_store);
        InitWidget();
        Init();
        preferanceManager =new PreferanceManager(this);
        Log.d("CHECKED", FirebaseAuth.getInstance().getCurrentUser().getEmail());
       hinhanh.setOnClickListener(this);
       txtclose.setOnClickListener(this);
       txtopen.setOnClickListener(this);
       txtsdt.setOnClickListener(this);
       txtdiachi.setOnClickListener(this);

    }

    private void InitWidget() {
        toolbar=findViewById(R.id.toolbar);
        txtopen=findViewById(R.id.txttimeopen);
        txtclose=findViewById(R.id.txttimerclose);
        hinhanh=findViewById(R.id.hinhanh);
        txtdiachi=findViewById(R.id.txtdiachi);
        txtsdt=findViewById(R.id.txtthongtinlienhe);
    }
    private  void Init(){
        firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference();
        preferanceManager=new PreferanceManager(this);
        userController=new UserController(this);
        userController.HandleGetkeyID();
       setSupportActionBar(toolbar);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       toolbar.setNavigationOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               startActivity(new Intent(HomeStoreActivity.this,
                       HomeActivity.class));
               finish();
           }
       });
         userController.HandleReadDataStore(txtclose,txtopen,txtsdt,txtdiachi,hinhanh,toolbar);


    }

    private void CheckPermisson() {
        if(Build.VERSION.SDK_INT>=23){
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},999);
            }else{
                PickGallary();
            }
        }else{
            PickGallary();
        }
    }

    private void PickGallary() {
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,999);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==999){
            if(resultCode==RESULT_OK){
                Uri uri=data.getData();
                try {
                    InputStream inputStream=getContentResolver().openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    hinhanh.setImageBitmap(bitmap);
                    bitmap=Bitmap.createScaledBitmap(bitmap,bitmap.getWidth()/4,bitmap.getHeight()/4,true);
                    ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG,80,byteArrayOutputStream);
                    byte[] dataimage=byteArrayOutputStream.toByteArray();
                    link_hinhanh=""+System.currentTimeMillis();
                    storageReference.child("USERANDSTORE")
                            .child(link_hinhanh).putBytes(dataimage)
                            .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                    if(task.isSuccessful()){
                                        userController.HandleUpdatePicture(link_hinhanh);

                                    }
                                }
                            });

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.hinhanh: CheckPermisson();break;
            case R.id.txttimerclose: DialogTimer(1);break;
            case R.id.txttimeopen: DialogTimer(2);break;
            case R.id.txtdiachi: DiaLogInput(1);break;
            case R.id.txtthongtinlienhe: DiaLogInput(2);break;
        }
    }

    private void DiaLogInput( final int i) {
        final Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.dialog_input);
        dialog.show();
        final EditText editnhap=dialog.findViewById(R.id.editnhap);
        Button btnxacnhan=dialog.findViewById(R.id.btnxacnhan);
        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noidung=editnhap.getText().toString().trim();
                switch (i){
                    case 1: userController.HandleUpdateDiaChi(noidung);
                    txtdiachi.setText("Địa Chỉ : "+noidung);break;

                    case 2: userController.HandleUpdateSdt(noidung);
                    txtsdt.setText("Liên hệ : "+noidung);break;
                }
                dialog.cancel();
            }
        });
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void DialogTimer( final int i) {

        final Calendar calendar=Calendar.getInstance();
        final SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
        TimePickerDialog timePickerDialog=new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(0,0,0,hourOfDay,minute);
                       String date= simpleDateFormat.format(calendar.getTime());
                        switch (i){
                            case 1: userController.HandleUpdateTimerClose(date);
                            txtclose.setText("Đóng Lúc : "+date);break;
                            case 2: userController.HandleUpdateTimerOpen(date);
                            txtopen.setText("Mở lúc : "+date);break;

                        }

                    }
                },7,0,true);
         timePickerDialog.show();
    }
}
