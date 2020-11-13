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
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    FirebaseFirestore db= FirebaseFirestore.getInstance();
    Boolean flag,flag1= false;
    final FeedReaderDbHelper database = new FeedReaderDbHelper(this);
    String data;

    @Override
    protected void onStart() {
        super.onStart();

        if(database.getUser(this)!="false"){
            startActivity(new Intent(MainActivity.this,firstpage.class));
            finish();
        }
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        data = database.getUser(this);
        TextView createAcc = findViewById(R.id.signUp);
        final EditText phoneNumber = findViewById(R.id.phoneNumber);
        final EditText password = findViewById(R.id.password);
        TextView forgotPassword = findViewById(R.id.forgotPassword);
        Button login = findViewById(R.id.loginBtn);
        //Toast.makeText(MainActivity.this,String.valueOf(database.getAuth(this)), Toast.LENGTH_LONG).show();







                                         login.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View view) {
                                                 checkPresence(phoneNumber.getText().toString(),password.getText().toString(),database,MainActivity.this);
                                             }
                                         });

        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this,SignUp.class));
            }
        });




    }

//    public void createAccount(String phoneNumber,String email,String password,String userName){
//        CollectionReference account = db.collection(phoneNumber);
//        Map<String,Object> preference = new HashMap<>();
//        Map<String,Object> Account = new HashMap<>();
//        preference.put("Autonews",false);
//        preference.put("Education",false);
//        preference.put("Entertainment",false);
//        preference.put("Fashion",false);
//        preference.put("Politics",false);
//        preference.put("Sports",false);
//        preference.put("Technology",false);
//        preference.put("Tib",false);
//        Account.put("emailId",email);
//        Account.put("password",password);
//        Account.put("phoneNumber",phoneNumber);
//        Account.put("UserName",userName);
//        account.document("preference").set(preference);
//        account.document("Account").set(Account);
//        startActivity(new Intent(MainActivity.this,firstpage.class));
//
//    }

     public void checkPresence(final String phoneNumber, final String password, final FeedReaderDbHelper database, final Context context) {

         db.collection(phoneNumber).document("Account").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
             @Override
             public void onSuccess(DocumentSnapshot documentSnapshot) {
                 if(documentSnapshot.get("phoneNumber").equals(phoneNumber)){

                     checkPassword(password,phoneNumber,database,context);

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

    public void checkPassword(final String pwd, final String phoneNumber, final FeedReaderDbHelper database, final Context context) {
        db.collection(phoneNumber).document("Account").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(Objects.equals(documentSnapshot.get("password"), pwd) &&  database.insertUSER(phoneNumber,context)){
                    String number = String.valueOf(database.getUser(context));
                    Toast.makeText(MainActivity.this,"Successfully logged in" , Toast.LENGTH_LONG).show();
                    startActivity(new Intent(context,firstpage.class));
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

class FeedReaderDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "AUTHENTICATION";

    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS AUTHENTICATION(phoneNumber NUMBER PRIMARY KEY, user_in NUMBER)");
        System.out.println("table created.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS AUTHENTICATION");
        System.out.println("New table was created ");
        onCreate(db);
    }

    public boolean insertUSER(String phoneNumber,Context context) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("phoneNumber",phoneNumber);
        contentValues.put("user_in",1);
        db.insert("AUTHENTICATION", null, contentValues);
        Toast.makeText(context,"Successfully logged in.", Toast.LENGTH_SHORT).show();
        return true;
    }

    public String getUser(Context context) {
            SQLiteDatabase db = this.getReadableDatabase();
            ArrayList<String> array_list = new ArrayList<String>();
            Cursor res = db.rawQuery("SELECT phoneNumber FROM AUTHENTICATION", null);
            res.moveToFirst();
            while (!res.isAfterLast()) {
                array_list.add(res.getString(0));
                res.moveToNext();
            }
            res.close();

            return array_list.size()>=1?array_list.get(0):"false";
    }



    public void logout(Context context){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM AUTHENTICATION" );
        db.close();
    }


}


