package com.tmv01.themillennial;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.media.MediaDrm;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class preference extends AppCompatActivity  {
    ArrayList<preferencedata> prefer = new ArrayList<>();
    FirebaseFirestore db= FirebaseFirestore.getInstance();
    public  Boolean Sports,Politics,Technology,AutoNews,tib,Entertainment,Fashion,Education;

//    @Override
//    protected void onStart() {
//        super.onStart();
////        db.collection("8151033423").document("preference").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
////            @Override
////            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
////                if (task.isSuccessful()) {
////                    DocumentSnapshot document = task.getResult();
////                    if (document.exists()) {
////                        preferencedata datas = document.toObject(preferencedata.class);
////                        Toast.makeText(preference.this,"Success" , Toast.LENGTH_SHORT).show();
////                    }
////                    else{
////                        Toast.makeText(preference.this, "e", Toast.LENGTH_SHORT).show();
////                    }
////                }
////            }
////        });
//    }
                @Override
                protected void onCreate(Bundle savedInstanceState) {
                    super.onCreate(savedInstanceState);
                    setContentView(R.layout.activity_preference);
                    final Button sports = findViewById(R.id.Sports);
                    Button tib = findViewById(R.id.tib);
                    Button health = findViewById(R.id.Health);
                    Button technology = findViewById(R.id.Technology);
                    Button politics = findViewById(R.id.Politics);
                    Button education = findViewById(R.id.Education);
                    Button fashion = findViewById(R.id.Fashion);
                    Button autonews = findViewById(R.id.Autonews);
                    db.collection("8151033423").document("preference").addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                            if (e != null) {
                                Toast.makeText(preference.this, "Error while loading!", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            if (documentSnapshot.exists()) {
                                preferencedata note = documentSnapshot.toObject(preferencedata.class);
                                String title = String.valueOf(note.getSports());
                                Toast.makeText(preference.this,title, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(preference.this, "String.valueOf(title)", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    sports.setOnClickListener(new View.OnClickListener() {
                        int count=0;
                        @Override
                        public void onClick(View view) {
                            if(count%2==0){
                                Map<String, Object> data = new HashMap<>();
                                data.put("Sports", true);
                                db.collection("8151033423").document("preference")
                                        .set(data, SetOptions.merge());
                                sports.setBackgroundColor(getResources().getColor(R.color.black));
                                sports.setTextColor(getResources().getColor(R.color.white));
                                count++;

                            }
                            else {
                                Map<String, Object> data = new HashMap<>();
                                data.put("Sports", false);
                                db.collection("8151033423").document("preference")
                                        .set(data, SetOptions.merge());
                                count++;
                                sports.setBackgroundColor(getResources().getColor(R.color.white));
                                sports.setTextColor(getResources().getColor(R.color.black));
                            }
                        }
                    });




    }
}
