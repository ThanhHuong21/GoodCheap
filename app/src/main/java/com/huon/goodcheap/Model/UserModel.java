package com.huon.goodcheap.Model;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import com.huon.goodcheap.Config.PreferanceManager;
import com.huon.goodcheap.Controller.UserView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.HashMap;

public class UserModel implements Serializable {
    private FirebaseFirestore db;
    private String Email;
    private String pass;
    private UserView callback;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private PreferanceManager preferanceManager;
    private Context context;
    private HashMap<String,String> user;
    private String open,close;
    private String diachi,thongtin,hinhanh;
    private String name,sdt,tencuahang;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    public  UserModel(UserView callback,Context context){
        this.callback=callback;
        db=FirebaseFirestore.getInstance();
        this.context=context;
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        firebaseStorage=FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference();
        preferanceManager=new PreferanceManager(context);
        HandlegetKeyID();

    }
    public  UserModel(){

    }

    public UserModel(String email, String open, String close, String diachi, String thongtin, String hinhanh, String name, String sdt, String tencuahang) {
        this.Email = email;
        this.open = open;
        this.close = close;
        this.diachi = diachi;
        this.thongtin = thongtin;
        this.hinhanh = hinhanh;
        this.name = name;
        this.sdt = sdt;
        this.tencuahang = tencuahang;
    }

    public  void HandleCreateUser(final String Email, String pass , final String name, final String sdt, final String type,
                                  String tencuahang){
       preferanceManager=new PreferanceManager(context);
         user=new HashMap<>();
        user.put("email",Email);
        user.put("name",name);
        user.put("sdt",sdt);
        user.put("type",type);
        user.put("tencuahang",tencuahang);
        firebaseAuth.createUserWithEmailAndPassword(Email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    db.collection("User")
                            .document("Profile")
                            .collection("STORE")
                            .add(user).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {

                        }
                    });
                    callback.OnAuthEmail();

                }else{
                    callback.OnFail();
                    Log.d("CHECKED",task.getResult()+" ");
                }
            }
        });


    }
    public  void HandleLoginUser(String email,String pass){
        firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    callback.OnLoginSucess();

                }else{
                    callback.OnFail();
                }
            }
        });

    }
    public  void HandlegetKeyID(){

        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            preferanceManager=new PreferanceManager(context);
            db.collection("User")
                    .document("Profile")
                    .collection("STORE")
                    .whereEqualTo("email",firebaseAuth.getCurrentUser().getEmail())
                    .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    if(queryDocumentSnapshots.size() >  0 ){
                        DocumentSnapshot d =queryDocumentSnapshots.getDocuments().get(0);
                        preferanceManager.PutKeyIDUSer(d.getId());
                    }


                }
            });
        }

    }
    public  void HandleUpdatePicture(final String hinhanh){
        if(preferanceManager.getkeyIDUser().length()>0){
            preferanceManager=new PreferanceManager(context);
            db.collection("User")
                    .document("Profile")
                    .collection("STORE")
                    .document(preferanceManager.getkeyIDUser())
                    .update("hinhanh",hinhanh).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        preferanceManager.PutPicture(hinhanh);
                        callback.OnSucessChange();
                    }
                }
            });
        }

    }
    public  void HandleUpdateTimerOpen(String Open){


         if(preferanceManager.getkeyIDUser().length()> 0 ){
             preferanceManager=new PreferanceManager(context);
             db.collection("User")
                     .document("Profile")
                     .collection("STORE")
                     .document(preferanceManager.getkeyIDUser())
                     .update("open",Open).addOnCompleteListener(new OnCompleteListener<Void>() {
                 @Override
                 public void onComplete(@NonNull Task<Void> task) {

                 }
             });
         }



    }
    public  void HandleUpdateTimerClose(String Close){
        if(preferanceManager.getkeyIDUser().length()>0 ){

            db.collection("User")
                    .document("Profile")
                    .collection("STORE")
                    .document(preferanceManager.getkeyIDUser())
                    .update("close",Close).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                }
            }); }



    }
    public  void HandleUpdateDiaChi(String diachi){

        if(preferanceManager.getkeyIDUser().length()>0 ){
            db.collection("User")
                    .document("Profile")
                    .collection("STORE")
                    .document(preferanceManager.getkeyIDUser())
                    .update("diachi",diachi).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                }
            });
        }



    }
    public  void HandleUpdatesDT(String sdt){
        if(preferanceManager.getkeyIDUser().length()>0){
            db.collection("User")
                    .document("Profile")
                    .collection("STORE")
                    .document(preferanceManager.getkeyIDUser())
                    .update("sdt",sdt).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                }
            });
        }



    }
    public  void HandlegetDataUser(final TextView txtclose, final TextView txtopen, final TextView txtsdt,
                                   final TextView txtdiachi, final ImageView hinhanh, final Toolbar toolbar){
      if(FirebaseAuth.getInstance().getCurrentUser()!=null){
          db.collection("User")
                  .document("Profile")
                  .collection("STORE")
                  .whereEqualTo("email",firebaseAuth.getCurrentUser().getEmail())
                  .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
              @Override
              public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                  if(queryDocumentSnapshots.size()>0){
                      for(DocumentSnapshot d : queryDocumentSnapshots){
                          UserModel userModel=d.toObject(UserModel.class);
                          txtclose.setText("Đóng Lúc : "+userModel.getClose());
                          txtopen.setText("Mở Lúc : "+userModel.getOpen());
                          txtsdt.setText("Liên hệ : "+userModel.getSdt());
                          txtdiachi.setText("Địa Chỉ : "+userModel.getDiachi());
                          try{
                              storageReference.child("USERANDSTORE").child(userModel.getHinhanh())
                                      .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                  @Override
                                  public void onSuccess(Uri uri) {
                                      Picasso.with(context).load(uri).into(hinhanh);
                                  }
                              });
                          }catch ( Exception e){

                          }

                          toolbar.setTitle(userModel.getTencuahang());

                      }
                  }


              }
          });

      }


    }

    public  void HandlegetDataUserStorenews(final TextView txtclose,  final TextView txtsdt,
                                   final TextView txtdiachi, final ImageView hinhanh, final Toolbar toolbar,String email){
     preferanceManager=new PreferanceManager(context);
        db.collection("User")
                .document("Profile")
                .collection("STORE")
                .whereEqualTo("email",email)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(queryDocumentSnapshots.size()>0){
                    for(DocumentSnapshot d : queryDocumentSnapshots){
                        UserModel userModel=d.toObject(UserModel.class);
                        txtclose.setText("Thời gian  : "+userModel.getOpen()+" - "+userModel.getClose());
                        txtsdt.setText("Liên hệ : "+userModel.getSdt());
                        txtdiachi.setText("Địa Chỉ : "+userModel.getDiachi());

                       try{
                           storageReference.child("USERANDSTORE").child(userModel.getHinhanh())
                                   .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                               @Override
                               public void onSuccess(Uri uri) {
                                   if(uri.toString().length()>0){
                                       Picasso.with(context).load(uri).into(hinhanh);
                                   }
                               }
                           });
                       }catch ( Exception e){

                       }
                        toolbar.setTitle(userModel.getTencuahang());

                    }
                }


            }
        });


    }

    public String getEmail() {
        return Email;
    }

    public String getOpen() {
        return open;
    }

    public String getClose() {
        return close;
    }

    public String getDiachi() {
        return diachi;
    }

    public String getThongtin() {
        return thongtin;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public String getName() {
        return name;
    }

    public String getSdt() {
        return sdt;
    }

    public String getTencuahang() {
        return tencuahang;
    }

}
