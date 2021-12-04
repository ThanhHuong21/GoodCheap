package com.huon.goodcheap.Controller;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import com.huon.goodcheap.Model.UserModel;
import com.huon.goodcheap.View.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class UserController  implements  UserView{
    private UserModel userModel;
    private Context context;
    public  UserController(Context context){
        this.context=context;
        userModel=new UserModel(this,context);
    }
    public  void HandleDangKyUser(String email,String pass,String name,String sdt,String type,String tencuahang){
        userModel.HandleCreateUser(email,pass,name,sdt,type,tencuahang);
    }
    public  void HandleLoginUser(String email,String pass){
        userModel.HandleLoginUser(email,pass);
    }
    @Override
    public void OnFail() {
        Toast.makeText(context," thất bại",Toast.LENGTH_LONG).show();
    }

    @Override
    public void OnAuthEmail() {
        Toast.makeText(context, "Sucess", Toast.LENGTH_SHORT).show();

    }
    public  void HandleGetkeyID(){
        userModel.HandlegetKeyID();
    }
    public  void HandleUpdatePicture(String hinhanh){
        userModel.HandleUpdatePicture(hinhanh);
    }
    public  void HandleUpdateTimerOpen(String open){
     userModel.HandleUpdateTimerOpen(open);
    }
    public  void HandleUpdateTimerClose(String close){
        userModel.HandleUpdateTimerClose(close);
    }
    public  void HandleUpdateDiaChi(String diachi){
        userModel.HandleUpdateDiaChi(diachi);
    }
    public  void HandleUpdateSdt(String sdt){
        userModel.HandleUpdatesDT(sdt);
    }
    public  void HandleReadDataStore(TextView txtclose, TextView txtopen, TextView txtsdt, TextView txtdiachi,
                                     ImageView hinhanh, Toolbar toolbar){
        userModel.HandlegetDataUser(txtclose,txtopen,txtsdt,txtdiachi,hinhanh,toolbar);
    }
    public  void HandleReadDataStoreNews(TextView txtclose,TextView txtsdt, TextView txtdiachi,
                                     ImageView hinhanh, Toolbar toolbar,String email){
        userModel.HandlegetDataUserStorenews(txtclose,txtsdt,txtdiachi,hinhanh,toolbar, email);
    }

    @Override
    public void OnLoginSucess() {
        context.startActivity(new Intent(context, HomeActivity.class));
    }

    @Override
    public void OnSucessChange() {
        Toast.makeText(context, "Thao thác xong ", Toast.LENGTH_SHORT).show();
    }
}
