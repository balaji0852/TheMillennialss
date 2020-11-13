package com.tmv01.themillennial;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class preference extends AppCompatActivity  {
    FirebaseFirestore db= FirebaseFirestore.getInstance();
    private String Sports,Politics,Technology,Autonews,Tib,Entertainment,Fashion,Education;
    String uno;

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
                    final Button entertainment = findViewById(R.id.Entertainment);
                    final Button technology = findViewById(R.id.Technology);
                    final Button politics = findViewById(R.id.Politics);
                    final Button education = findViewById(R.id.Education);
                    final Button fashion = findViewById(R.id.Fashion);
                    final Button autonews = findViewById(R.id.AutoNews);
                    ImageButton Back = findViewById(R.id.backButton);
                    FeedReaderDbHelper database = new FeedReaderDbHelper(this);
                    uno = database.getUser(this);

                    Back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent Back = new Intent(preference.this,setting.class);
                            Back.putExtra("date",getIntent().getStringExtra("date"));
                            finish();
                            startActivity(Back);
                        }
                    });

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
                    @Override
                        public void onClick(View view) {
                                if(Sports.equals("true")) {
                                    Map<String, Object> data = new HashMap<>();
                                    data.put("Sports", false);
                                    db.collection(uno).document("preference")
                                            .set(data, SetOptions.merge());
                                    sports.setBackgroundColor(getResources().getColor(R.color.white));
                                    sports.setTextColor(getResources().getColor(R.color.black));
                                    Sports= "false";
                                }
                                else
                                    {
                                    Map<String, Object> data = new HashMap<>();
                                    data.put("Sports", true);
                                    db.collection(uno).document("preference")
                                            .set(data, SetOptions.merge());
                                    sports.setBackgroundColor(getResources().getColor(R.color.black));
                                    sports.setTextColor(getResources().getColor(R.color.white));
                                    Sports= "true";
                                }

                        }
                    });
                        technology.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(Technology.equals("true")){
                                    Map<String, Object> data = new HashMap<>();
                                    data.put("Technology", false);
                                    db.collection(uno).document("preference")
                                            .set(data, SetOptions.merge());
                                    technology.setBackgroundColor(getResources().getColor(R.color.white));
                                    technology.setTextColor(getResources().getColor(R.color.black));
                                    Technology="false";

                                }
                                else {
                                    Map<String, Object> data = new HashMap<>();
                                    data.put("Technology", true);
                                    db.collection(uno).document("preference")
                                            .set(data, SetOptions.merge());
                                    technology.setBackgroundColor(getResources().getColor(R.color.black));
                                    technology.setTextColor(getResources().getColor(R.color.white));
                                    Technology="true";
                                }
                            }
                        });
                        politics.setOnClickListener(new View.OnClickListener() {
                            int count=0;
                            @Override
                            public void onClick(View view) {
                                if(Politics.equals("true")){
                                    Map<String, Object> data = new HashMap<>();
                                    data.put("Politics", false);
                                    db.collection(uno).document("preference")
                                            .set(data, SetOptions.merge());
                                    politics.setBackgroundColor(getResources().getColor(R.color.white));
                                    politics.setTextColor(getResources().getColor(R.color.black));
                                    Politics="false";

                                }
                                else {
                                    Map<String, Object> data = new HashMap<>();
                                    data.put("Politics", true);
                                    db.collection(uno).document("preference")
                                            .set(data, SetOptions.merge());
                                    politics.setBackgroundColor(getResources().getColor(R.color.black));
                                    politics.setTextColor(getResources().getColor(R.color.white));
                                    Politics="true";
                                }
                            }
                        });
                        entertainment.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(Entertainment.equals("true")){
                                    Map<String, Object> data = new HashMap<>();
                                    data.put("Entertainment", false);
                                    db.collection(uno).document("preference")
                                            .set(data, SetOptions.merge());
                                    entertainment.setBackgroundColor(getResources().getColor(R.color.white));
                                    entertainment.setTextColor(getResources().getColor(R.color.black));
                                    Entertainment="false";

                                }
                                else {
                                    Map<String, Object> data = new HashMap<>();
                                    data.put("Entertainment", true);
                                    db.collection(uno).document("preference")
                                            .set(data, SetOptions.merge());
                                    entertainment.setBackgroundColor(getResources().getColor(R.color.black));
                                    entertainment.setTextColor(getResources().getColor(R.color.white));
                                    Entertainment="true";
                                }
                            }
                        });
                        education.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(Education.equals("true")){
                                    Map<String, Object> data = new HashMap<>();
                                    data.put("Education", false);
                                    db.collection(uno).document("preference")
                                            .set(data, SetOptions.merge());
                                    education.setBackgroundColor(getResources().getColor(R.color.white));
                                    education.setTextColor(getResources().getColor(R.color.black));
                                    Education="false";

                                }
                                else {
                                    Map<String, Object> data = new HashMap<>();
                                    data.put("Education", true);
                                    db.collection(uno).document("preference")
                                            .set(data, SetOptions.merge());
                                    education.setBackgroundColor(getResources().getColor(R.color.black));
                                    education.setTextColor(getResources().getColor(R.color.white));
                                    Education="true";
                                }
                            }
                        });
                        autonews.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(Autonews.equals("true")){
                                    Map<String, Object> data = new HashMap<>();
                                    data.put("Autonews", false);
                                    db.collection(uno).document("preference")
                                            .set(data, SetOptions.merge());
                                    autonews.setBackgroundColor(getResources().getColor(R.color.white));
                                    autonews.setTextColor(getResources().getColor(R.color.black));
                                    Autonews ="false";
                                }
                                else {
                                    Map<String, Object> data = new HashMap<>();
                                    data.put("Autonews", true);
                                    db.collection(uno).document("preference")
                                            .set(data, SetOptions.merge());
                                    autonews.setBackgroundColor(getResources().getColor(R.color.black));
                                    autonews.setTextColor(getResources().getColor(R.color.white));
                                    Autonews="true";
                                }
                            }
                        });
                        tib.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(Tib.equals("true")){
                                    Map<String, Object> data = new HashMap<>();
                                    data.put("Tib", false);
                                    db.collection(uno).document("preference")
                                            .set(data, SetOptions.merge());
                                    tib.setBackgroundColor(getResources().getColor(R.color.white));
                                    tib.setTextColor(getResources().getColor(R.color.black));
                                    Tib ="false";
                                }
                                else {
                                    Map<String, Object> data = new HashMap<>();
                                    data.put("Tib", true);
                                    db.collection(uno).document("preference")
                                            .set(data, SetOptions.merge());
                                    tib.setBackgroundColor(getResources().getColor(R.color.black));
                                    tib.setTextColor(getResources().getColor(R.color.white));
                                    Tib="true";
                                }
                            }
                        });
                        fashion.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(Fashion.equals("true")){
                                    Map<String, Object> data = new HashMap<>();
                                    data.put("Fashion", false);
                                    db.collection(uno).document("preference")
                                            .set(data, SetOptions.merge());
                                    fashion.setBackgroundColor(getResources().getColor(R.color.white));
                                    fashion.setTextColor(getResources().getColor(R.color.black));
                                    Fashion="false";

                                }
                                else {
                                    Map<String, Object> data = new HashMap<>();
                                    data.put("Fashion", true);
                                    db.collection(uno).document("preference")
                                            .set(data, SetOptions.merge());
                                    fashion.setBackgroundColor(getResources().getColor(R.color.black));
                                    fashion.setTextColor(getResources().getColor(R.color.white));
                                    Fashion="true";

                                }
                            }
                        });



    }
}
