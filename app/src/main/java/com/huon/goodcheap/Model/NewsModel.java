package com.huon.goodcheap.Model;

import android.content.Context;
import androidx.annotation.NonNull;
import com.huon.goodcheap.Controller.NewsView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class NewsModel  implements  Serializable {
    private  String ID;
    private String ten;
    private  String diachi;
    private String ngay;
    private String type;
    private String hinhanh;
    private String mota;
    private String email;
    private int soluong;
    private long giatien;
    private FirebaseFirestore db;
    private FirebaseAuth firebaseAuth;
    private Context context;

    private NewsView callback;
    public  NewsModel(Context context,NewsView callback){
        this.callback=callback;
        this.context=context;
        db=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
    }
    public NewsModel(){

    }

    public NewsModel(String ten, String diachi,String ngay, String type, String hinhanh,String mota,int soluong, long giatien,String email) {
        this.ten = ten;
        this.diachi = diachi;
        this.type = type;
        this.soluong = soluong;
        this.giatien = giatien;
        this.ngay=ngay;
        this.mota=mota;
        this.hinhanh=hinhanh;
        this.email=email;
    }
    public NewsModel(String ID,String ten, String diachi,String ngay, String type, String hinhanh,String mota,int soluong, long giatien,String email) {
        this.ID= ID;
        this.ten = ten;
        this.diachi = diachi;
        this.type = type;
        this.soluong = soluong;
        this.giatien = giatien;
        this.ngay=ngay;
        this.mota=mota;
        this.hinhanh=hinhanh;
        this.email=email;
    }
    public NewsModel(String ten, String diachi,String ngay,  String hinhanh,String mota,int soluong, long giatien,String email) {

        this.ten = ten;
        this.diachi = diachi;
        this.soluong = soluong;
        this.giatien = giatien;
        this.ngay=ngay;
        this.mota=mota;
        this.hinhanh=hinhanh;
        this.email=email;
    }
    public  void HandlegetDataNews(String type, final ArrayList<NewsModel> arrayList){
        db.collection("News")
                .document("Store")
                .collection("ALL")
                .whereEqualTo("type",type)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                   for(DocumentSnapshot d:queryDocumentSnapshots){
                       NewsModel newsModel=d.toObject(NewsModel.class);
                       arrayList.add(new NewsModel(newsModel.getTen(),
                               newsModel.getDiachi(),newsModel.getNgay(),newsModel.getType(),
                               newsModel.getHinhanh(),newsModel.getMota(),newsModel.getSoluong(),
                               newsModel.getGiatien(),newsModel.getEmail()));

                   }
            }
        });
    }
   public  void HandlegetDataSearch(String ten, final ArrayList<NewsModel> arrayList){
        db.collection("News")
                .document("Store")
                .collection("ALL")
                .whereEqualTo("ten",ten)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot d:queryDocumentSnapshots){
                    NewsModel newsModel=d.toObject(NewsModel.class);
                    arrayList.add(new NewsModel(d.getId(),newsModel.getTen(),
                            newsModel.getDiachi(),newsModel.getNgay(),newsModel.getType(),
                            newsModel.getHinhanh(),newsModel.getMota(),newsModel.getSoluong(),
                            newsModel.getGiatien(),newsModel.getEmail()));

                }
            }
        });
    }
    public  void HandlegetDataNewsALL( final ArrayList<NewsModel> arrayList){
        db.collection("News")
                .document("Store")
                .collection("ALL")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot d:queryDocumentSnapshots){
                    NewsModel newsModel=d.toObject(NewsModel.class);
                    arrayList.add(new NewsModel(d.getId(),newsModel.getTen(),
                            newsModel.getDiachi(),newsModel.getNgay(),newsModel.getType(),
                            newsModel.getHinhanh(),newsModel.getMota(),newsModel.getSoluong(),
                            newsModel.getGiatien(),newsModel.getEmail()));

                }
            }
        });
    }
    public  void HandlegetDataNewsStore(String email, final ArrayList<NewsModel> arrayList){
        db.collection("News")
                .document("Store")
                .collection("ALL")
                .whereEqualTo("email",email)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot d:queryDocumentSnapshots){
                    NewsModel newsModel=d.toObject(NewsModel.class);
                    arrayList.add(new NewsModel(d.getId(),newsModel.getTen(),
                            newsModel.getDiachi(),newsModel.getNgay(),newsModel.getType(),
                            newsModel.getHinhanh(),newsModel.getMota(),newsModel.getSoluong(),
                            newsModel.getGiatien(),newsModel.getEmail()));

                }
            }
        });
    }

    public  void HandleCreateNews(String ten, String diachi, String ngay, String type, String hinhanh,String mota,int soluong, long giatien){



        NewsModel newsModel=new NewsModel(ten,diachi,ngay,type,hinhanh,mota,soluong,giatien,firebaseAuth.getCurrentUser().getEmail());



        db.collection("News")
                .document("Store")
                .collection("ALL")
                .add(newsModel).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                   if(task.isSuccessful()){
                       callback.OnSucess();
                   }else{
                       callback.OnFail();
                   }
            }
        });

    }
    public  void HandleUpdateNews(String ID,String ten, String diachi, String ngay,String mota,int soluong, long giatien){







        db.collection("News")
                .document("Store")
                .collection("ALL")
                .document(ID).update("ten",ten,
                "diachi",diachi,
                "ngay",ngay,
                "mota",mota,
                "soluong",soluong,
                "giatien",giatien).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    callback.OnSucess();
                }else{
                    callback.OnFail();
                }

            }
        });


    }

    public String getTen() {
        return ten;
    }

    public String getDiachi() {
        return diachi;
    }

    public String getNgay() {
        return ngay;
    }

    public String getType() {
        return type;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public int getSoluong() {
        return soluong;
    }

    public long getGiatien() {
        return giatien;
    }

    public String getMota() {
        return mota;
    }

    public String getEmail() {
        return email;
    }

    public String getID() {
        return ID;
    }

    public void HandleDelete(String id) {
        db.collection("News")
                .document("Store")
                .collection("ALL")
                .document(id).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    callback.OnSucess();
                }else{
                    callback.OnFail();
                }
            }
        });
    }
}
