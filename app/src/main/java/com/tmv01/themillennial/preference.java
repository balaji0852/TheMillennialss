package com.tmv01.themillennial;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class preference extends AppCompatActivity  {
    FirebaseFirestore db= FirebaseFirestore.getInstance();
    private String Sports,Politics,Technology,Autonews,Tib,Entertainment,Fashion,Education;
    String uno = "8151033423";

    @Override
    protected void onStart() {
        super.onStart();

            }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
                    super.onCreate(savedInstanceState);
                    setContentView(R.layout.activity_preference);
                    final Button sports = findViewById(R.id.Sports);
                    final Button tib = findViewById(R.id.tib);
                    final Button entertainment = findViewById(R.id.entertainment);
                    final Button technology = findViewById(R.id.Technology);
                    final Button politics = findViewById(R.id.Politics);
                    final Button education = findViewById(R.id.Education);
                    final Button fashion = findViewById(R.id.Fashion);
                    final Button autonews = findViewById(R.id.Autonews);
        db.collection(uno).document("preference").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                preferencedata prefered = documentSnapshot.toObject(preferencedata.class);
                Sports = String.valueOf(prefered.getSports());
                Politics = String.valueOf(prefered.getPolitics());
                Technology = String.valueOf(prefered.getTechnology());
                Autonews = String.valueOf(prefered.getAutonews());
                Tib = String.valueOf(prefered.getTib());
                Entertainment = String.valueOf(prefered.getEntertainment());
                Fashion = String.valueOf(prefered.getFashion());
                Education = String.valueOf(prefered.getEducation());
                if (Sports.equals("true")) {
                    sports.setBackgroundColor(getResources().getColor(R.color.black));
                    sports.setTextColor(getResources().getColor(R.color.white));
                } else {
                    sports.setBackgroundColor(getResources().getColor(R.color.white));
                    sports.setTextColor(getResources().getColor(R.color.black));
                }
                if (Politics.equals("true")) {
                    politics.setBackgroundColor(getResources().getColor(R.color.black));
                    politics.setTextColor(getResources().getColor(R.color.white));
                } else {
                    politics.setBackgroundColor(getResources().getColor(R.color.white));
                    politics.setTextColor(getResources().getColor(R.color.black));
                }
                if (Technology.equals("true")) {
                    technology.setBackgroundColor(getResources().getColor(R.color.black));
                    technology.setTextColor(getResources().getColor(R.color.white));
                } else {
                    technology.setBackgroundColor(getResources().getColor(R.color.white));
                    technology.setTextColor(getResources().getColor(R.color.black));
                }
                if (Autonews.equals("true")) {
                    autonews.setBackgroundColor(getResources().getColor(R.color.black));
                    autonews.setTextColor(getResources().getColor(R.color.white));
                } else {

                    fashion.setBackgroundColor(getResources().getColor(R.color.white));
                    fashion.setTextColor(getResources().getColor(R.color.black));
                }
                if (Fashion.equals("true")) {
                    fashion.setBackgroundColor(getResources().getColor(R.color.black));
                    fashion.setTextColor(getResources().getColor(R.color.white));

                } else {
                    fashion.setBackgroundColor(getResources().getColor(R.color.white));
                    fashion.setTextColor(getResources().getColor(R.color.black));
                }
                if (Education.equals("true")) {
                    education.setBackgroundColor(getResources().getColor(R.color.black));
                    education.setTextColor(getResources().getColor(R.color.white));
                } else {
                    education.setBackgroundColor(getResources().getColor(R.color.white));
                    education.setTextColor(getResources().getColor(R.color.black));
                }
                if (Entertainment.equals("true")) {
                    entertainment.setBackgroundColor(getResources().getColor(R.color.black));
                    entertainment.setTextColor(getResources().getColor(R.color.white));
                } else {
                    entertainment.setBackgroundColor(getResources().getColor(R.color.white));
                    entertainment.setTextColor(getResources().getColor(R.color.black));
                }
                if (Tib.equals("true")) {
                    tib.setBackgroundColor(getResources().getColor(R.color.black));
                    tib.setTextColor(getResources().getColor(R.color.white));
                } else {
                    tib.setBackgroundColor(getResources().getColor(R.color.white));
                    tib.setTextColor(getResources().getColor(R.color.black));
                }
            }});





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
                        technology.setOnClickListener(new View.OnClickListener() {
                            int count=0;
                            @Override
                            public void onClick(View view) {
                                if(count%2==0){
                                    Map<String, Object> data = new HashMap<>();
                                    data.put("Technology", true);
                                    db.collection("8151033423").document("preference")
                                            .set(data, SetOptions.merge());
                                    technology.setBackgroundColor(getResources().getColor(R.color.black));
                                    technology.setTextColor(getResources().getColor(R.color.white));
                                    count++;

                                }
                                else {
                                    Map<String, Object> data = new HashMap<>();
                                    data.put("Technology", false);
                                    db.collection("8151033423").document("preference")
                                            .set(data, SetOptions.merge());
                                    count++;
                                    technology.setBackgroundColor(getResources().getColor(R.color.white));
                                    technology.setTextColor(getResources().getColor(R.color.black));
                                }
                            }
                        });
                        politics.setOnClickListener(new View.OnClickListener() {
                            int count=0;
                            @Override
                            public void onClick(View view) {
                                if(count%2==0){
                                    Map<String, Object> data = new HashMap<>();
                                    data.put("Politics", true);
                                    db.collection("8151033423").document("preference")
                                            .set(data, SetOptions.merge());
                                    politics.setBackgroundColor(getResources().getColor(R.color.black));
                                    politics.setTextColor(getResources().getColor(R.color.white));
                                    count++;

                                }
                                else {
                                    Map<String, Object> data = new HashMap<>();
                                    data.put("Politics", false);
                                    db.collection("8151033423").document("preference")
                                            .set(data, SetOptions.merge());
                                    count++;
                                    politics.setBackgroundColor(getResources().getColor(R.color.white));
                                    politics.setTextColor(getResources().getColor(R.color.black));
                                }
                            }
                        });
                        entertainment.setOnClickListener(new View.OnClickListener() {
                            int count=0;
                            @Override
                            public void onClick(View view) {
                                if(count%2==0){
                                    Map<String, Object> data = new HashMap<>();
                                    data.put("Entertainment", true);
                                    db.collection("8151033423").document("preference")
                                            .set(data, SetOptions.merge());
                                    entertainment.setBackgroundColor(getResources().getColor(R.color.black));
                                    entertainment.setTextColor(getResources().getColor(R.color.white));
                                    count++;

                                }
                                else {
                                    Map<String, Object> data = new HashMap<>();
                                    data.put("Entertainment", false);
                                    db.collection("8151033423").document("preference")
                                            .set(data, SetOptions.merge());
                                    count++;
                                    entertainment.setBackgroundColor(getResources().getColor(R.color.white));
                                    entertainment.setTextColor(getResources().getColor(R.color.black));
                                }
                            }
                        });
                        education.setOnClickListener(new View.OnClickListener() {
                            int count=0;
                            @Override
                            public void onClick(View view) {
                                if(count%2==0){
                                    Map<String, Object> data = new HashMap<>();
                                    data.put("Education", true);
                                    db.collection("8151033423").document("preference")
                                            .set(data, SetOptions.merge());
                                    education.setBackgroundColor(getResources().getColor(R.color.black));
                                    education.setTextColor(getResources().getColor(R.color.white));
                                    count++;

                                }
                                else {
                                    Map<String, Object> data = new HashMap<>();
                                    data.put("Education", false);
                                    db.collection("8151033423").document("preference")
                                            .set(data, SetOptions.merge());
                                    count++;
                                    education.setBackgroundColor(getResources().getColor(R.color.white));
                                    education.setTextColor(getResources().getColor(R.color.black));
                                }
                            }
                        });
                        autonews.setOnClickListener(new View.OnClickListener() {
                            int count=0;
                            @Override
                            public void onClick(View view) {
                                if(count%2==0){
                                    Map<String, Object> data = new HashMap<>();
                                    data.put("Autonews", true);
                                    db.collection("8151033423").document("preference")
                                            .set(data, SetOptions.merge());
                                    autonews.setBackgroundColor(getResources().getColor(R.color.black));
                                    autonews.setTextColor(getResources().getColor(R.color.white));
                                    count++;

                                }
                                else {
                                    Map<String, Object> data = new HashMap<>();
                                    data.put("Autonews", false);
                                    db.collection("8151033423").document("preference")
                                            .set(data, SetOptions.merge());
                                    count++;
                                    autonews.setBackgroundColor(getResources().getColor(R.color.white));
                                    autonews.setTextColor(getResources().getColor(R.color.black));
                                }
                            }
                        });
                        tib.setOnClickListener(new View.OnClickListener() {
                            int count=0;
                            @Override
                            public void onClick(View view) {
                                if(count%2==0){
                                    Map<String, Object> data = new HashMap<>();
                                    data.put("Tib", true);
                                    db.collection("8151033423").document("preference")
                                            .set(data, SetOptions.merge());
                                    tib.setBackgroundColor(getResources().getColor(R.color.black));
                                    tib.setTextColor(getResources().getColor(R.color.white));
                                    count++;

                                }
                                else {
                                    Map<String, Object> data = new HashMap<>();
                                    data.put("Tib", false);
                                    db.collection("8151033423").document("preference")
                                            .set(data, SetOptions.merge());
                                    count++;
                                    tib.setBackgroundColor(getResources().getColor(R.color.white));
                                    tib.setTextColor(getResources().getColor(R.color.black));
                                }
                            }
                        });
                        fashion.setOnClickListener(new View.OnClickListener() {
                            int count=0;
                            @Override
                            public void onClick(View view) {
                                if(count%2==0){
                                    Map<String, Object> data = new HashMap<>();
                                    data.put("Fashion", true);
                                    db.collection("8151033423").document("preference")
                                            .set(data, SetOptions.merge());
                                    fashion.setBackgroundColor(getResources().getColor(R.color.black));
                                    fashion.setTextColor(getResources().getColor(R.color.white));
                                    count++;

                                }
                                else {
                                    Map<String, Object> data = new HashMap<>();
                                    data.put("Fashion", false);
                                    db.collection("8151033423").document("preference")
                                            .set(data, SetOptions.merge());
                                    count++;
                                    fashion.setBackgroundColor(getResources().getColor(R.color.white));
                                    fashion.setTextColor(getResources().getColor(R.color.black));
                                }
                            }
                        });



    }





}
