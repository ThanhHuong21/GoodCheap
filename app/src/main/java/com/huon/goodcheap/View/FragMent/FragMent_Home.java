package com.huon.goodcheap.View.FragMent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.huon.goodcheap.Adapter.NewsAdapter;
import com.huon.goodcheap.Controller.BannerController;
import com.huon.goodcheap.Controller.NewsController;
import com.huon.goodcheap.Model.NewsModel;
import com.huon.goodcheap.R;
import com.huon.goodcheap.View.Store.SearchActivity;
import com.huon.goodcheap.View.Store.StoreNewsActivity;

import java.util.ArrayList;

public class FragMent_Home  extends Fragment {
    View view;
    private ViewPager viewFlipper;
    private BannerController bannerController;
    private NewsController newsController;
    private AppCompatSpinner appCompatSpinner;
    private ArrayList<String> arrayList;
    private ArrayList<NewsModel> newsModelArrayList;
    private GridView gv;
    private NewsAdapter adapter;
    private EditText editSearch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_home,container,false);
        InitWidget();
        Init();
        return  view;
    }

    private void Init() {
        arrayList=new ArrayList<>();
        newsModelArrayList=new ArrayList<>();
        bannerController=new BannerController(getContext());
        newsController=new NewsController(getContext());
        bannerController.HandleBanner(viewFlipper);
        bannerController.HandleReadCatgoreis(arrayList);


        final ArrayAdapter arrayAdapter=new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1,arrayList);
        appCompatSpinner.setAdapter(arrayAdapter);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                arrayAdapter.notifyDataSetChanged();
            }
        },2000);
        appCompatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(newsModelArrayList.size()>0){
                    newsModelArrayList.clear();
                }
                 if(position == 0){
                     newsController.HandleReadDataNewsALL(newsModelArrayList);
                 }else{
                     newsController.HandleReadDataNews(arrayList.get(position),newsModelArrayList);
                 }

                adapter=new NewsAdapter(getContext(),newsModelArrayList);
                gv.setAdapter(adapter);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent=new Intent(getContext(), StoreNewsActivity.class);
                                intent.putExtra("User",newsModelArrayList.get(position));
                                startActivity(intent);
                            }
                        });
                    }
                },1500);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_SEARCH){
                    String key = editSearch.getText().toString().trim();
                    if(key.length() >0 ){
                        startActivity(new Intent(getContext(), SearchActivity.class).putExtra("KEY",editSearch.getText().
                                toString().trim()));


                    }else{
                        Toast.makeText(getContext(), "Key not null", Toast.LENGTH_SHORT).show();
                    }

                }
                return true;
            }
        });


    }

    private void InitWidget() {
        gv=view.findViewById(R.id.gv);
        appCompatSpinner=view.findViewById(R.id.spiner);
        viewFlipper=view.findViewById(R.id.viewfilip);
        editSearch=view.findViewById(R.id.editSearch);
    }
}
