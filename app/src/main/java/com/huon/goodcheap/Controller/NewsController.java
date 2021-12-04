package com.huon.goodcheap.Controller;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import com.huon.goodcheap.Model.NewsModel;
import com.huon.goodcheap.View.HomeActivity;

import java.util.ArrayList;

public class NewsController implements  NewsView{
    private NewsModel newsModel;
    private Context context;
    public  NewsController(Context context){
        this.context=context;
        newsModel=new NewsModel(context,this);
    }
    public  void HandleCreateNews(String ten,String diachi,String ngay,String type,String hinhanh,String mota,int soluong,long giatien){
        newsModel.HandleCreateNews(ten,diachi,ngay,type,hinhanh,mota,soluong,giatien);
    }
    public  void HandleCreateNewsUpdate(String ID,String ten,String diachi,String ngay,String mota,int soluong,long giatien){
        newsModel.HandleUpdateNews(ID,ten,diachi,ngay,mota,soluong,giatien);
    }

    public  void HandleReadDataNews(String type, ArrayList<NewsModel> arrayList){
        newsModel.HandlegetDataNews(type,arrayList);
    }
    public  void HandleReadDataNewsALL( ArrayList<NewsModel> arrayList){
        newsModel.HandlegetDataNewsALL(arrayList);
    }
    public  void HandleReadDataNewsStore(String email, ArrayList<NewsModel> arrayList){
        newsModel.HandlegetDataNewsStore(email,arrayList);
    }


    @Override
    public void OnSucess() {
        Toast.makeText(context," thành công",Toast.LENGTH_LONG).show();
        context.startActivity(new Intent(context, HomeActivity.class));
    }

    @Override
    public void OnFail() {
        Toast.makeText(context," thất bại",Toast.LENGTH_LONG).show();
    }

    public void HandleDelete(String id) {
        newsModel.HandleDelete(id);
    }

    public void SearchDataByName(String search, ArrayList<NewsModel> arrayList) {
        newsModel.HandlegetDataSearch(search,arrayList);
    }
}
