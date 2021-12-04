package com.huon.goodcheap.Model;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import androidx.viewpager.widget.ViewPager;
import com.huon.goodcheap.Adapter.BannerAdapter;
import com.huon.goodcheap.Controller.BannerView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Banner {
    private  String hinhanh;
    private FirebaseFirestore db;
    private BannerView callback;
    private Handler handler;
    private ImageView imageView=null;
    private int k=0;
    private Context context;
    public  Banner(BannerView callback,Context context){
        this.context=context;
        this.callback=callback;
        db=FirebaseFirestore.getInstance();
    }
    public  Banner(){

    }
    public  Banner(String hinhanh){
        this.hinhanh=hinhanh;
    }
    public  void HandleReadBanner(final ViewPager viewFlipper){
        final ArrayList<String> arrayList =new ArrayList<>();
        db.collection("Banner").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                             for(DocumentSnapshot d : queryDocumentSnapshots) {
                                 Banner banner = d.toObject(Banner.class);

                                arrayList.add(banner.getHinhanh());
                                Log.d("CHECKED",arrayList.size()+" ");

                             }

                    }
                });
        handler=new Handler();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                BannerAdapter bannerAdapter=new BannerAdapter(context,arrayList);
                 viewFlipper.setAdapter(bannerAdapter);
                 handler.postDelayed(new Runnable() {
                     @Override
                     public void run() {

                          if(arrayList.size()>0){
                              if(k>arrayList.size()-1){
                                  k=0;

                              }else if(arrayList.size()>k){
                                  k++;
                              }
                              viewFlipper.setCurrentItem(k,true);
                              handler.postDelayed(this,3000);
                          }
                     }
                 },3000);


            }
        },3000);

    }

    public void HandleReadDataCategories( final ArrayList<String> arrayList){

        db.collection("Categories")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(DocumentSnapshot d : queryDocumentSnapshots){

                    arrayList.add(d.get("categories").toString());

                }
            }
        });

    }

    public String getHinhanh() {
        return hinhanh;
    }
}
