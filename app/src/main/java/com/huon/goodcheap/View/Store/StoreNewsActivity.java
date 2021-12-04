package com.huon.goodcheap.View.Store;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.huon.goodcheap.Adapter.NewsAdapter;
import com.huon.goodcheap.Controller.NewsController;
import com.huon.goodcheap.Controller.UserController;
import com.huon.goodcheap.Model.NewsModel;
import com.huon.goodcheap.R;
import com.huon.goodcheap.View.ContentActivity;
import com.huon.goodcheap.View.HomeStoreActivity;

import java.util.ArrayList;

public class StoreNewsActivity  extends AppCompatActivity {
    private UserController userController;
    private ImageView hinhanh;
    private TextView txtthoigian,txtdiachi,txtthongtin;
    private Toolbar toolbar;
    private GridView gv;
    private NewsAdapter newsAdapter;
    private ArrayList<NewsModel> arrayList;
    private NewsController newsController;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_news);
        InitWidget();
        Init();
    }

    private void Init() {
        arrayList=new ArrayList<>();
        Intent intent=getIntent();
        NewsModel userModel= (NewsModel) intent.getSerializableExtra("User");
       newsController=new NewsController(this);
        userController=new UserController(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        userController.HandleReadDataStoreNews(txtthoigian,txtthongtin,txtdiachi,
                hinhanh,toolbar,userModel.getEmail());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StoreNewsActivity.this,
                        HomeStoreActivity.class));
                finish();
            }
        });
        newsController.HandleReadDataNewsStore(userModel.getEmail(),arrayList);
        newsAdapter=new NewsAdapter(this,arrayList);
        gv.setAdapter(newsAdapter);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                newsAdapter.notifyDataSetChanged();
            }
        },2500);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent =new Intent(StoreNewsActivity.this, ContentActivity.class);
                intent.putExtra("ND",arrayList.get(i));
                startActivity(intent);
            }
        });
    }

    private void InitWidget() {
        txtthoigian=findViewById(R.id.txtthoigian);
        txtdiachi=findViewById(R.id.txtdiachi);
        txtthongtin=findViewById(R.id.txtthongtin);
        toolbar=findViewById(R.id.toolbar);
        hinhanh=findViewById(R.id.hinhanh);
        gv=findViewById(R.id.gv);
    }
}
