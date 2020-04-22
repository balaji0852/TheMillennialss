package com.tmv01.themillennial;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import javax.annotation.Nullable;

public class tib extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<firstpagedata> maindata= new ArrayList<>();
    public RecyclerView news;
    public int count=0;
    newsadapter newspage;
    String category="Politics";
    private String Sports,Politics,Technology,Autonews,Tib,Entertainment,Fashion,Education;
    String uno = "8151033423";

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
                Autonews = String.valueOf(prefered.getAutonews());
                Tib = String.valueOf(prefered.getTib());
                Entertainment = String.valueOf(prefered.getEntertainment());
                Fashion = String.valueOf(prefered.getFashion());
                Education = String.valueOf(prefered.getEducation());
            } });

        nextpage.setOnClickListener(new View.OnClickListener() {
            int count=0;
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                count++;
                            if(count==1){
                                if (Tib.equals("true")){
                                title.setText("Talks in Bangalore");
                                category="Talks in Bangalore";
                                onStart();
                                }
                                else {
                                    count++;
                                }

                            }
                            else if(count==2){
                                 if (Fashion.equals("true")){
                                     title.setText("Fashion");
                                     category="Fashion";
                                     onStart();
                                 }
                                 else {
                                     count++;
                                 }
                            }
                            else if(count==3 ){
                                if (Entertainment.equals("true")) {
                                    title.setText("Entertainment");
                                    category = "Entertainment";
                                    onStart();
                                }
                                else {
                                    count++;
                                }
                            }
                            else if(count==4 ){
                                if( Autonews.equals("true")) {
                                    title.setText("Auto News");
                                    category = "Auto News";
                                    onStart();
                                }
                                else {
                                    count++;
                                }
                                }
                            else if(count==5){
                                if( Technology.equals("true")) {
                                    title.setText("Technology");
                                    category = "Technology";
                                    onStart();
                                }
                                else {
                                    count++;
                                }
                            }
                            else if(count==6){
                                if( Sports.equals("true")) {
                                    title.setText("Sports");
                                    category = "Sports";
                                    onStart();
                                }
                                else {
                                    count++;
                                }
                            }

                            else if(count==7){
                                if(Education.equals("true")) {
                                    title.setText("Education");
                                    category = "Education";
                                    onStart();
                                }
                                else {
                                    count++;
                                }
                            }
                            else if(count==8){
                                if( Politics.equals("true")) {
                                    title.setText("Politics");
                                    category = "Politics";
                                    onStart();
                                }
                                else {
                                    count++;
                                }
                            }
                            else if(count==9){
                                title.setText("Novel Corona");
                                category="Novel Corona";
                                onStart();
                            }
                            else{
                                title.setText("Talks in Bangalore");
                                category="Talks in Bangalore";
                                onStart();
                                count=0;
                            }

                }

        });



    }


}
