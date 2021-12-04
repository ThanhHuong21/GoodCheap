package com.huon.goodcheap.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.huon.goodcheap.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BannerAdapter  extends PagerAdapter {
    private List<String> stringList;
    private Context context;
    public BannerAdapter(Context context,List<String> list){
        this.stringList=list;
        this.context=context;

    }
    @Override
    public int getCount() {
        return stringList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }
    public  class ViewHodler{
        ImageView hinhanh;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view=null;
        ViewHodler viewHodler;
        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.dong_banner,null);
            viewHodler=new ViewHodler();
            viewHodler.hinhanh=view.findViewById(R.id.hinhanh);
            view.setTag(viewHodler);
        }else{
            viewHodler= (ViewHodler) view.getTag();
        }
        Picasso.with(context).load(stringList.get(position)).into(viewHodler.hinhanh);
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
