package com.tmv01.themillennial;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.annotation.Nullable;

import static java.lang.String.valueOf;

public class tib extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<firstpagedata> maindata= new ArrayList<>();
    public RecyclerView news;
    public int count=0;
    newsadapter newspage;
    String category="Talks in Bangalore";
    private String Sports,Politics,Technology,Autonews,Tib,Entertainment,Fashion,Education;
    String uno = "8151033423";
    ArrayList<String> data = new ArrayList<>();


    @Override
    protected void onStart() {
        super.onStart();

        db.collection(getIntent().getStringExtra("date")).document(category).collection("news").whereEqualTo("category",category)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                        maindata.clear();
                        for (QueryDocumentSnapshot retrievedDocSnap : queryDocumentSnapshots) {

                            firstpagedata main= retrievedDocSnap.toObject(firstpagedata.class);
                            maindata.add(main);
                        }
                        newspage.notifyDataSetChanged();
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tib);
        final Button nextpage = findViewById(R.id.nextcategory);
        final ImageButton setting = findViewById(R.id.settings);
        news =findViewById(R.id.tib);
        final TextView title = findViewById(R.id.newstitle);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(tib.this);
        ((LinearLayoutManager) layoutManager).setOrientation(RecyclerView.VERTICAL);
        news.setLayoutManager(layoutManager);
        newspage = new newsadapter(tib.this, maindata);
        news.setAdapter(newspage);

        db.collection(uno).document("preference").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                preferencedata prefered = documentSnapshot.toObject(preferencedata.class);
                Sports = String.valueOf(prefered.getSports());
                Politics = String.valueOf(prefered.getPolitics());
                Technology = String.valueOf(prefered.getTechnology());
                Autonews =String.valueOf(prefered.getAutonews());
                Tib = String.valueOf(prefered.getTib());
                Entertainment =String.valueOf(prefered.getEntertainment());
                Fashion =String.valueOf(prefered.getFashion());
                Education = String.valueOf(prefered.getEducation());
                if(Sports.equals("true")) {
                    data.add("Sports");
                }
                if(Technology.equals("true")) {
                    data.add("Technology");
                }
                if(Autonews.equals("true")) {
                    data.add("Autonews");
                }
                if(Tib.equals("true")) {
                    data.add("Tib");
                }
                if(Entertainment.equals("true")) {
                    data.add("Entertainment");
                }
                if(Fashion.equals("true")) {
                    data.add("Fashion");
                }
                if(Education.equals("true")) {
                    data.add("Education");
                }
                if(Politics.equals("true")) {
                    data.add("Politics");
                }
            } });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(tib.this,setting.class));
            }
        });


        nextpage.setOnClickListener(new View.OnClickListener() {
            int count=0;
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {

                           if(count<data.size()) {

                               if (data.get(count).equals("Tib")){
                                   title.setText("Talks in Bangalore");
                                   category ="Talks in Bangalore";
                                   count++;
                                   onStart();
                               }
                               else if (data.get(count).equals("Autonews")){
                                   title.setText("Auto News");
                                   category ="Auto News";
                                   count++;
                                   onStart();
                               }
                               else {
                                   title.setText(data.get(count));
                                   category = data.get(count);
                                   count++;
                                   onStart();
                               }

                           }
                           else{
                               Toast.makeText(tib.this, "Your preference list is done.", Toast.LENGTH_SHORT).show();
                               count=0;
                               title.setText(data.get(count));
                               category = data.get(count);;
                               onStart();
                           }


                }

        });



    }


}
