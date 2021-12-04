package com.huon.goodcheap.Adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.huon.goodcheap.Model.NewsModel;
import com.huon.goodcheap.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter  extends BaseAdapter {
    private ArrayList<NewsModel> arrayList;
    private Context context;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    public  NewsAdapter(Context context,ArrayList<NewsModel> arrayList){
        this.context=context;
        this.arrayList=arrayList;
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public  class  ViewHodler{
        TextView txttencuahang,txttensp,
        txtngay,txtgiatien;
        ImageView hinhanh;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHodler viewHodler;
        firebaseStorage=FirebaseStorage.getInstance("gs://goodcheap-4b69a.appspot.com/");
        storageReference=firebaseStorage.getReference();
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.dong_news_food,null);
            viewHodler=new ViewHodler();
            viewHodler.hinhanh=convertView.findViewById(R.id.hinhanh);
            viewHodler.txtngay=convertView.findViewById(R.id.txtngay);
            viewHodler.txtgiatien=convertView.findViewById(R.id.txtgiatien);
            viewHodler.txttensp=convertView.findViewById(R.id.txtten);
            convertView.setTag(viewHodler);
        }else{
            viewHodler= (ViewHodler) convertView.getTag();
        }
        Log.d("CHECKEDHINHANH",arrayList.get(position).getHinhanh()+" ");
         storageReference.child("Store").child(""+arrayList.get(position).getHinhanh())
                 .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
             @Override
             public void onSuccess(Uri uri) {

                 Picasso.with(context).load(uri).into(viewHodler.hinhanh);
             }
         });
        viewHodler.txttensp.setText(arrayList.get(position).getTen());
        viewHodler.txtngay.setText(arrayList.get(position).getNgay());
        viewHodler.txtgiatien.setText(arrayList.get(position).getGiatien()+"đ");


        return convertView;
    }
}
