package com.tmv01.themillennial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Typeface;
import android.os.Bundle;
import android.service.autofill.TextValueSanitizer;
import android.widget.TextView;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class firstpage extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<firstpagedata> leftdata= new ArrayList<>();
    private RecyclerView firstleftdata,firstpagebottom;
    firstpageadapter pageadapter;
    cyclebottom bottomadapter;

    @Override
    protected void onStart() {
        super.onStart();
        db.collection("2020-04-06").document("First page").collection("news").whereEqualTo("category","First page")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                        leftdata.clear();
                        for (QueryDocumentSnapshot retrievedDocSnap : queryDocumentSnapshots) {

                            firstpagedata left = retrievedDocSnap.toObject(firstpagedata.class);
                            leftdata.add(left);
                        }
                        pageadapter.notifyDataSetChanged();
                        bottomadapter.notifyDataSetChanged();
                    }
                });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstpage);

        firstleftdata = findViewById(R.id.cycleleft);
        firstpagebottom=findViewById(R.id.cyclebottom);
        TextView title= (TextView)findViewById(R.id.title);
        title.setTypeface(Typeface.DEFAULT);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(firstpage.this);
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(firstpage.this);
        ((LinearLayoutManager) layoutManager).setOrientation(RecyclerView.HORIZONTAL);
        ((LinearLayoutManager) layoutManager1).setOrientation(RecyclerView.VERTICAL);
        firstleftdata.setLayoutManager(layoutManager);
        firstpagebottom.setLayoutManager(layoutManager1);
        pageadapter  = new firstpageadapter(firstpage.this, leftdata);
        firstleftdata.setAdapter(pageadapter);
        bottomadapter= new cyclebottom(firstpage.this,leftdata);
        firstpagebottom.setAdapter(bottomadapter);

    }
}
