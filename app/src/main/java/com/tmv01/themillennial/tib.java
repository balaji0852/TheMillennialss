package com.tmv01.themillennial;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
        Button nextpage = findViewById(R.id.nextcategory);
        news =findViewById(R.id.tib);
        final TextView title = findViewById(R.id.newstitle);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(tib.this);
        ((LinearLayoutManager) layoutManager).setOrientation(RecyclerView.VERTICAL);
        news.setLayoutManager(layoutManager);
        newspage = new newsadapter(tib.this, maindata);
        news.setAdapter(newspage);

        nextpage.setOnClickListener(new View.OnClickListener() {
            int count=0;
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                count++;
                if(count==1){
                    title.setText("Talks in Bangalore");
                    category="Talks in Bangalore";
                    onStart();
                }
                else if(count==2){
                    title.setText("Fashion");
                    category="Fashion";
                    onStart();
                }
                else if(count==3){
                    title.setText("Entertainment");
                    category="Entertainment";
                    onStart();
                }
                else if(count==4){
                    title.setText("Auto News");
                    category="Auto News";
                    onStart();
                }
                else if(count==5){
                    title.setText("Technology");
                    category="Technology";
                    onStart();
                }
                else if(count==8){
                    title.setText("Novel Corona");
                    category="Novel Corona";
                    onStart();
                }
                else if(count==7){
                    title.setText("Education");
                    category="Education";
                    onStart();
                }
                else if(count==6){
                    title.setText("Sports");
                    category="Sports";
                    onStart();
                }
                else if(count==6){
                    title.setText("Health");
                    category="Health";
                    onStart();
                }
                else{
                    title.setText("Politics");
                    category="Politics";
                    onStart();
                    count=0;
                }

            }
        });



    }


}
