package com.huon.goodcheap.Controller;

import android.content.Context;

import androidx.viewpager.widget.ViewPager;
import com.huon.goodcheap.Model.Banner;

import java.util.ArrayList;

public class BannerController implements  BannerView{
    private Context context;
    private Banner banner;
    private ArrayList<Banner> arrayList=new ArrayList<>();
    private ViewPager viewFlipper;

    public  BannerController(Context context){
        this.context=context;
        banner=new Banner(this,context);
    }
    public void HandleBanner(ViewPager viewFlipper){
        this.viewFlipper=viewFlipper;

        banner.HandleReadBanner(viewFlipper);
    }
    public  void HandleReadCatgoreis(ArrayList<String> arrayList){
        banner.HandleReadDataCategories(arrayList);
    }

    @Override
    public void getDataBanner(String hinhanh) {


    }
}
