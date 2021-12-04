package com.huon.goodcheap.View;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.huon.goodcheap.Model.NewsModel;
import com.huon.goodcheap.R;
import com.squareup.picasso.Picasso;

public class ContentActivity  extends AppCompatActivity
{
    ImageView hinhanh;
    TextView txttensp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                setContentView(R.layout.acitivty_content);
                hinhanh=findViewById(R.id.hinhanh);
              txttensp=findViewById(R.id.txttensp);
                Intent intent= getIntent();;
        NewsModel newsModel = (NewsModel) intent.getSerializableExtra("ND");
             Picasso.with(this).load(newsModel.getHinhanh()).into(hinhanh);
     FirebaseStorage   firebaseStorage= FirebaseStorage.getInstance("gs://goodcheap-4b69a.appspot.com/");
        StorageReference storageReference= firebaseStorage.getReference();
        storageReference.child("Store").child(newsModel.getHinhanh())
                .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Picasso.with(ContentActivity.this).load(uri).into(hinhanh);
            }
        });
             txttensp.setText(newsModel.getTen());

    }
}
