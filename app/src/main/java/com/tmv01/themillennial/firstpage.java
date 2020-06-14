package com.tmv01.themillennial;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import javax.annotation.Nullable;

public class firstpage extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<firstpagedata> leftdata= new ArrayList<>();
    ArrayList<firstpagedata> lsdatas= new ArrayList<>();
    public RecyclerView firstleftdata,firstpagebottom;
    firstpageadapter pageadapter;
    cyclebottom bottomadapter;
    public static final String DATE_FORMAT ="yyyy-MM-dd";
    String udate;

    @Override
    public void onStart() {
        super.onStart();

        if (getIntent().getStringExtra("date") == null) {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date today = Calendar.getInstance().getTime();
            udate = dateFormat.format(today);
        } else {
            udate = getIntent().getStringExtra("date");
        }

        assert udate != null;
        db.collection(udate).document("First page").collection("news").
                whereEqualTo("category", "First page")
                 .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable final QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        leftdata.clear();
                        assert queryDocumentSnapshots != null;
                        for (QueryDocumentSnapshot retrievedDocSnap : queryDocumentSnapshots)
                        {
                            final firstpagedata left = retrievedDocSnap.toObject(firstpagedata.class);
                            db.collection("8151033423").document("saved").collection("news").
                                    whereEqualTo("headline",left.getHeadline()).get().
                                    addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                         if(task.isSuccessful())
                                         {
                                             for (final QueryDocumentSnapshot document : task.getResult())
                                             {
                                                 if ((Boolean)document.get("saved")){
                                                     left.setPoname("true");
                                                 }
                                                 else{
                                                     left.setPoname("false");
                                                 }
                                                 if ((Boolean) document.get("liked")){
                                                     left.setAid("true");
                                                 }
                                                 else{
                                                     left.setAid("false");
                                                 }
                                             }
                                             pageadapter.notifyDataSetChanged();
                                             bottomadapter.notifyDataSetChanged();
                                         }
                                         else {
                                             left.setPoname("false");
                                             left.setAid("false");
                                             }
                                        }
                                    });
                            leftdata.add(left);
                        }
                        pageadapter.notifyDataSetChanged();
                        bottomadapter.notifyDataSetChanged();

                    }
                });

    }

    public void like(ArrayList<firstpagedata> sample)
    {
        for (int i = 0; i <sample.size(); i++)
        {
            final firstpagedata datas = sample.get(i);
            final firstpagedata data = lsdatas.get(i);
            db.collection("8151033423").document("saved").collection("news").
                    whereEqualTo("headline",datas.getHeadline()).get().
                    addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful())
                            {
                                for (final QueryDocumentSnapshot document : task.getResult()) {
                                    data.setImage("null");
                                    data.setTextualdata("null");
                                    data.setAid("null");
                                    data.setPoname("null");
                                    data.setViews(0);
                                    data.setHeadline("null");
                                    data.setDate("null");
                                    data.setCategory("null");
                                    if((Boolean)document.get("liked"))
                                    {
                                        data.setLikes(1);
                                    }
                                    else{
                                        data.setLikes(0);
                                    }
                                    if((Boolean)document.get("saved"))
                                    {
                                        data.setPlikes(1);
                                    }
                                    else{
                                        data.setPlikes(0);
                                    }
                                }
                            }
                            else {
                                data.setImage("null");
                                data.setTextualdata("null");
                                data.setAid("null");
                                data.setPoname("null");
                                data.setViews(0);
                                data.setHeadline("null");
                                data.setDate("null");
                                data.setCategory("null");
                                data.setLikes(0);
                                data.setLikes(0);
                            }
                        }
                    });
        }
        leftdata.addAll(lsdatas);
    }




    @SuppressLint("RestrictedApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstpage);
        Toast.makeText(firstpage.this,udate,Toast.LENGTH_LONG).show();
        Button page2= findViewById(R.id.page3);
        firstleftdata = findViewById(R.id.cycleleft);
        firstpagebottom=findViewById(R.id.cyclebottom);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(firstpage.this);
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(firstpage.this);
        ((LinearLayoutManager) layoutManager).setOrientation(RecyclerView.HORIZONTAL);
        ((LinearLayoutManager) layoutManager1).setOrientation(RecyclerView.HORIZONTAL);
        firstleftdata.setLayoutManager(layoutManager);
        firstpagebottom.setLayoutManager(layoutManager1);
        pageadapter  = new firstpageadapter(firstpage.this,leftdata);
        firstleftdata.setAdapter(pageadapter);
        bottomadapter= new cyclebottom(firstpage.this,leftdata);
        firstpagebottom.setAdapter(bottomadapter);
        ImageButton date=findViewById(R.id.date);
        TextView views = findViewById(R.id.Headlinebackground);


        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(firstpage.this,Main2Activity.class));
                finish();
            }
        });

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
