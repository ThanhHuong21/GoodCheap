package com.huon.goodcheap.View.Store;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;
import com.huon.goodcheap.Controller.BannerController;
import com.huon.goodcheap.Controller.NewsController;
import com.huon.goodcheap.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import de.hdodenhof.circleimageview.CircleImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CreateNewsActivity  extends AppCompatActivity
 implements View.OnClickListener {
    private EditText editten,editsoluong,editdiachi,editgiatien,editmota;
    private Button btnthem;
    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat;
    private CircleImageView hinhanh;
    private String link_hinhanh="";
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private NewsController newsController;
    private BannerController bannerController;
    private String type;
    private AppCompatSpinner appCompatSpinner;
    private ArrayList<String> arrayList;
    private Toolbar toolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_create);
        Initwidget();
        Init();
        HandleEvents();

    }

    private void HandleEvents() {
        btnthem.setOnClickListener(this);
        hinhanh.setOnClickListener(this);
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
        arrayList=new ArrayList<>();
        newsController=new NewsController(this);
        bannerController=new BannerController(this);
        bannerController.HandleReadCatgoreis(arrayList);
        firebaseStorage=FirebaseStorage.getInstance("gs://goodcheap-4b69a.appspot.com/");
        storageReference=firebaseStorage.getReference();
        calendar=Calendar.getInstance();
        simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
        final ArrayAdapter arrayAdapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayList);
        appCompatSpinner.setAdapter(arrayAdapter);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                arrayAdapter.notifyDataSetChanged();
            }
        },2000);
        appCompatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type=arrayList.get(position);
                Toast.makeText(CreateNewsActivity.this,""+type,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void Initwidget() {
        toolbar=findViewById(R.id.toolbar);
        appCompatSpinner=findViewById(R.id.spinerfolder);
        hinhanh=findViewById(R.id.hinhanh);
        btnthem=findViewById(R.id.btnthem);
        editsoluong=findViewById(R.id.editsoluong);
        editdiachi=findViewById(R.id.editdiachi);
        editten=findViewById(R.id.editten);
        editgiatien=findViewById(R.id.editgiatien);
        editmota=findViewById(R.id.editmota);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnthem:
                String ten=editten.getText().toString().trim();
                String diachi=editdiachi.getText().toString().trim();
                String giatien=editgiatien.getText().toString().trim();
                String soluong=editsoluong.getText().toString().trim();
                String mota=editmota.getText().toString().trim();
                if(ten.length()<3){
                    Toast.makeText(this,"Tên món hàng ít nhất 3 ký tự",Toast.LENGTH_LONG).show();
                }
                if(diachi.length()<0){
                    Toast.makeText(this, "Địa chỉ không để trống", Toast.LENGTH_SHORT).show();
                }
                if(giatien.length()<0){
                    Toast.makeText(this, "Giá tiền không để trống", Toast.LENGTH_SHORT).show();
                }
                if(soluong.length()<0){
                    Toast.makeText(this, "Số lượng không để trống", Toast.LENGTH_SHORT).show();
                }
                if(link_hinhanh.length()<0){
                    Toast.makeText(this, "Chọn 1 tấm ảnh", Toast.LENGTH_SHORT).show();
                }
                if(mota.length()<0){
                    Toast.makeText(this, "Hãy mô tả ", Toast.LENGTH_SHORT).show();
                }
                if(ten.length()>3 && giatien.length()>0 &&diachi.length()>0
                && soluong.length()>0 && link_hinhanh.length()>5 && mota.length()>0){
                    int soluongs  = Integer.parseInt(soluong);
                    if(soluongs<0){
                        Toast.makeText(this, "Số lượng >0", Toast.LENGTH_SHORT).show();
                    }
                    long giatiens=Long.parseLong(giatien);
                    if(giatiens<0){
                        Toast.makeText(this, "Giá tiền > 0", Toast.LENGTH_SHORT).show();
                    }
                    if(soluongs>0 && giatiens>0){
                 newsController.HandleCreateNews(ten,diachi,simpleDateFormat.format(calendar.getTime()),type,link_hinhanh,mota,soluongs,giatiens);
                    }
                }
                break;
            case R.id.hinhanh:
                CheckPermisson();
        }
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
                    storageReference.child("Store")
                            .child(link_hinhanh).putBytes(dataimage)
                            .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                    if(task.isSuccessful()){

                                    }else{
                                        Toast.makeText(CreateNewsActivity.this, "Fail", Toast.LENGTH_SHORT).show();
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         if(item.getItemId() == R.id.edit){
             startActivity(new Intent( CreateNewsActivity.this,StoreEditActivity.class
             ));
         }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_store,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
