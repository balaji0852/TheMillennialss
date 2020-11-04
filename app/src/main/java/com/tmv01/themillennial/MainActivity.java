package com.tmv01.themillennial;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    FirebaseFirestore db= FirebaseFirestore.getInstance();
    Boolean flag,flag1= false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button gotoNewspaper = findViewById(R.id.newspaper);
        final Button createAcc = findViewById(R.id.createAccount);
        final EditText phoneNumber = findViewById(R.id.phoneNumber);
        final EditText phoneNumber1 = findViewById(R.id.phoneNumber1);
        final EditText email = findViewById(R.id.email);
        final EditText password = findViewById(R.id.password);
        final EditText password1 = findViewById(R.id.password1);
        final EditText userName = findViewById(R.id.userName);






        gotoNewspaper.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View view) {
                                                 checkPresence(phoneNumber.getText().toString(),password.getText().toString());
                                             }
                                         });


        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount(phoneNumber1.getText().toString(),email.getText().toString(),password1.getText().toString(),userName.getText().toString());
            }
        });




    }
    public void createAccount(String phoneNumber,String email,String password,String userName){
        CollectionReference account = db.collection(phoneNumber);
        Map<String,Object> preference = new HashMap<>();
        Map<String,Object> Account = new HashMap<>();
        preference.put("Autonews",false);
        preference.put("Education",false);
        preference.put("Entertainment",false);
        preference.put("Fashion",false);
        preference.put("Politics",false);
        preference.put("Sports",false);
        preference.put("Technology",false);
        preference.put("Tib",false);
        Account.put("emailId",email);
        Account.put("password",password);
        Account.put("phoneNumber",phoneNumber);
        Account.put("UserName",userName);
        account.document("preference").set(preference);
        account.document("Account").set(Account);
        startActivity(new Intent(MainActivity.this,firstpage.class));

    }

     public void checkPresence(final String phoneNumber,final String password) {
         db.collection(phoneNumber).document("Account").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
             @Override
             public void onSuccess(DocumentSnapshot documentSnapshot) {
                 if(documentSnapshot.get("phoneNumber").equals(phoneNumber)){
                     checkPassword(password,phoneNumber);
                 }
                 else{
                     Toast.makeText(MainActivity.this,"Please try again later.", Toast.LENGTH_LONG).show();
                 }
             }
         }).addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {
                 Toast.makeText(MainActivity.this,"No account exist on this phone Number.", Toast.LENGTH_LONG).show();
             }
         });
     }

    public void checkPassword(final String pwd, final String phoneNumber) {
        db.collection(phoneNumber).document("Account").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.get("password").equals(pwd)){
                    Toast.makeText(MainActivity.this,"Successfully logged in.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this,firstpage.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(MainActivity.this,"Incorrect password", Toast.LENGTH_LONG).show();
                }
            }
        });
    }





//    Boolean checkPwd(final String phoneNumber){
//        db.collection(phoneNumber).document("preference").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if(task.isSuccessful()){
//                  if(data.phoneNumber.e)
//                }
//            }
//        });

}
