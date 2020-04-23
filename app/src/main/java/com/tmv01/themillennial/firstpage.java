package com.tmv01.themillennial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.service.autofill.TextValueSanitizer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.annotation.Nullable;

public class firstpage extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<firstpagedata> leftdata= new ArrayList<>();
    public RecyclerView firstleftdata,firstpagebottom;
    firstpageadapter pageadapter;
    cyclebottom bottomadapter;
    public static final String DATE_FORMAT ="yyyy-MM-dd";
    int zero =0;
    String udate;
    @Override
    public void onStart() {
        super.onStart();

        if(getIntent().getStringExtra("date")==null)
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date today = Calendar.getInstance().getTime();
            udate = dateFormat.format(today);
        }
        else{
            udate = getIntent().getStringExtra("date");
        }

        db.collection(udate).document("First page").collection("news").whereEqualTo("category","First page")
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstpage);
        TextView title= findViewById(R.id.title);
        Toast.makeText(firstpage.this,udate,Toast.LENGTH_LONG).show();
        Button page2= findViewById(R.id.page3);
        firstleftdata = findViewById(R.id.cycleleft);
        firstpagebottom=findViewById(R.id.cyclebottom);
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

        page2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(firstpage.this, tib.class);
                intent.putExtra("date",udate);
                startActivity(intent);
            }
        });


    }




}
