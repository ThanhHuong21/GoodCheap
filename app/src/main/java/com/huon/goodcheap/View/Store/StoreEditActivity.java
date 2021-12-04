package com.huon.goodcheap.View.Store;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import com.huon.goodcheap.Adapter.NewsAdapter;
import com.huon.goodcheap.Controller.BannerController;
import com.huon.goodcheap.Controller.NewsController;
import com.huon.goodcheap.Model.NewsModel;
import com.huon.goodcheap.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class StoreEditActivity  extends AppCompatActivity {
    private  GridView gv;
    private  ArrayList<NewsModel> newsModelArrayList;
    private  NewsController newsController;
    private NewsAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_edit);
        gv=findViewById(R.id.gv);

        newsModelArrayList=new ArrayList<>();

       Hienthi();
        gv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                DialogUpdate(newsModelArrayList.get(i));
                return true;
            }
        });






    }

    private void Hienthi() {
        newsController=new NewsController(StoreEditActivity.this);
        newsController.HandleReadDataNewsALL(newsModelArrayList);
        adapter=new NewsAdapter(StoreEditActivity.this,newsModelArrayList);
        gv.setAdapter(adapter);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();

            }
        },1500);
    }

    private void DialogUpdate(final NewsModel newsModel) {
        final Dialog dialog  =new Dialog(StoreEditActivity.this);
        dialog.setContentView(R.layout.dialog_update_delete);
        dialog.show();


       Button btnthem=dialog.findViewById(R.id.btnthem);
       Button btnxoa=dialog.findViewById(R.id.btndelete);
      final EditText  editsoluong=dialog.findViewById(R.id.editsoluong);
        final EditText editdiachi=dialog.findViewById(R.id.editdiachi);
        final EditText  editten=dialog.findViewById(R.id.editten);
        final EditText  editgiatien=dialog.findViewById(R.id.editgiatien);
        final EditText  editmota=dialog.findViewById(R.id.editmota);
        editmota.setText(newsModel.getMota());
        editdiachi.setText(newsModel.getDiachi());
        editgiatien.setText(newsModel.getGiatien()+"");
        editsoluong.setText(newsModel.getSoluong()+"");
        editten.setText(newsModel.getTen());
        Log.d("CHECKED",newsModel.getID());
        btnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                newsController.HandleDelete(newsModel.getID());
                dialog.cancel();

            }
        });
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ten=editten.getText().toString().trim();
                String diachi=editdiachi.getText().toString().trim();
                String giatien=editgiatien.getText().toString().trim();
                String soluong=editsoluong.getText().toString().trim();
                String mota=editmota.getText().toString().trim();
                if(ten.length()<3){
                    Toast.makeText(StoreEditActivity.this,"Tên món hàng ít nhất 3 ký tự",Toast.LENGTH_LONG).show();
                }
                if(diachi.length()<0){
                    Toast.makeText(StoreEditActivity.this, "Địa chỉ không để trống", Toast.LENGTH_SHORT).show();
                }
                if(giatien.length()<0){
                    Toast.makeText(StoreEditActivity.this, "Giá tiền không để trống", Toast.LENGTH_SHORT).show();
                }
                if(soluong.length()<0){
                    Toast.makeText(StoreEditActivity.this, "Số lượng không  để trống", Toast.LENGTH_SHORT).show();
                }

                if(mota.length()<0){
                    Toast.makeText(StoreEditActivity.this, "Hãy  mô tả ", Toast.LENGTH_SHORT).show();
                }
                if(ten.length()>3 && giatien.length()>0 &&diachi.length()>0
                        && soluong.length()>0 && mota.length()>0){
                    int soluongs  = Integer.parseInt(soluong);
                    if(soluongs<0){
                        Toast.makeText(StoreEditActivity.this, "Số lượng >0", Toast.LENGTH_SHORT).show();
                    }
                    long giatiens=Long.parseLong(giatien);
                    if(giatiens<0){
                        Toast.makeText(StoreEditActivity.this, "Giá tiền > 0", Toast.LENGTH_SHORT).show();
                    }
                    if(soluongs>0 && giatiens>0){
                        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd");
                        Calendar calendar = Calendar.getInstance();
                        newsController.HandleCreateNewsUpdate(newsModel.getID(),ten,diachi,simpleDateFormat.format(calendar.getTime()),mota,soluongs,giatiens);
                  Hienthi();
                    }
                }

            }
        });

    }
}
