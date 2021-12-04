package com.huon.goodcheap.Config;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferanceManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public  PreferanceManager(Context context){
        sharedPreferences=context.getSharedPreferences("INFO",Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }
    public  void PutType(String type){
        editor.putString("TYPE",type);
        editor.commit();
    }
    public  String getType(){
        String type=sharedPreferences.getString("TYPE","");
        return  type;
    }
    public  void PutLogin(int check){
        editor.putInt("LOGIN",check);
        editor.commit();
    }
    public  int getLogin(){
        int login=sharedPreferences.getInt("LOGIN",0);
        return  login;
    }
    public  void putInitalze(int k){
        editor.putInt("INIT",k);
        editor.commit();
    }
    public  int getInitalze(){
        int k=sharedPreferences.getInt("INIT",0);
        return  k;
    }
    public  void PutKeyIDUSer(String key){
        editor.putString("ID",key);
        editor.commit();
    }
    public  String getkeyIDUser(){
        String keyID=sharedPreferences.getString("ID","");
        return  keyID;
    }
    public  void PutPicture(String pic){
        editor.putString("PICTURE",pic);
        editor.commit();
    }
    public  String getPicture(){
        String pic=sharedPreferences.getString("PICTURE","");
        return  pic;
    }

}
