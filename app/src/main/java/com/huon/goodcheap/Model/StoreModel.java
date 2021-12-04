package com.huon.goodcheap.Model;

import com.huon.goodcheap.Controller.StoreView;
import com.google.firebase.firestore.FirebaseFirestore;

public class StoreModel {
    private FirebaseFirestore db;
    private String hinhanh;
    private String ten;
    private String thoigian;
    private String thongtin;
    private String sdt;
    private StoreView callback;
    public  StoreModel(StoreView callback){
        this.callback=callback;
        db=FirebaseFirestore.getInstance();

    }
    public  void HandleSaveTimer(){

    }
}
