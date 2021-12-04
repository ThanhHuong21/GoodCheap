package com.huon.goodcheap.View.Store;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.huon.goodcheap.Adapter.NewsAdapter;
import com.huon.goodcheap.Controller.NewsController;
import com.huon.goodcheap.Model.NewsModel;
import com.huon.goodcheap.R;

import java.util.ArrayList;

public class SearchActivity  extends AppCompatActivity {
    private  GridView gv;
    private NewsController newsController;
    private  NewsAdapter adapter;
    private  ArrayList<NewsModel> arrayList;
    private  TextView txtnodata;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        gv=findViewById(R.id.gv);
        txtnodata=findViewById(R.id.txtnodata);
        Intent intent= getIntent();;
        String search = intent.getStringExtra("KEY");
        arrayList=new ArrayList<>();
        newsController =new NewsController(this);
        newsController.SearchDataByName(search.trim(),arrayList);

        adapter=new NewsAdapter(this,arrayList);
        gv.setAdapter(adapter);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
                if(arrayList.size()>0){

                }else{
                    txtnodata.setVisibility(View.VISIBLE);
                }

            }
        },1500);
    }
}
