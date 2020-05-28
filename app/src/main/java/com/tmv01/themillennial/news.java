package com.tmv01.themillennial;

import android.annotation.SuppressLint;
import android.media.Image;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class news extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<likenviews> saveddata = new ArrayList<>();

    @Override
    protected void onStart() {
        super.onStart();
        final CollectionReference database = db.collection(getIntent().getStringExtra("date")).document(getIntent().getStringExtra("category")).
                collection("news");
            database.whereEqualTo("headline", getIntent().getStringExtra("headline")).get().
                    addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Map<String, Object> viewed = new HashMap<>();
                                    viewed.put("views", getIntent().getIntExtra("views", 1));
                                    database.document(document.getId()).set(viewed, SetOptions.merge());
                                }
                            }
                        }
                    });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        TextView Textualdata = findViewById(R.id.textualdata);
        TextView Headline    = findViewById(R.id.headline);
        TextView category = findViewById(R.id.category);
        ImageButton save = findViewById(R.id.saved);
        ImageButton like = findViewById(R.id.like);
        ImageView Imagedata = findViewById(R.id.imagedata);
        Headline.setText(getIntent().getStringExtra("headline"));
        category.setText(getIntent().getStringExtra("title"));
        Picasso.get().load(getIntent().getStringExtra("image")).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(Imagedata);
        if (getIntent().getStringExtra("textualdata").isEmpty()){
            Toast.makeText(this, "to textualdata", Toast.LENGTH_SHORT).show();
        }
        else {
            Textualdata.setText(getIntent().getStringExtra("textualdata"));
        }
//
//        FirebaseFirestore.getInstance().collection(Objects.requireNonNull(getIntent().getStringExtra("date"))).
//                document(Objects.requireNonNull(getIntent().getStringExtra("category"))).collection("news").
//                whereEqualTo("headline",getIntent().getStringExtra("headline")).addSnapshotListener(new EventListener<QuerySnapshot>() {
//            TextView likes = findViewById(R.id.nlikes);
//            TextView views= findViewById(R.id.viewcount);
//                    @Override
//            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
//               QuerySnapshot datas=queryDocumentSnapshots;
//               likenviews data = (likenviews) datas.toObjects(likenviews.class);
//               views.setText(data.getViews()+"views");
//               views.setText(data.getLikes()+"likes");
//
//                    }
//        });
//        if(getIntent().getStringExtra("textualdata").isEmpty()){
//
//        }
//        else{
//            Textualdata.setText(getIntent().getStringExtra("textualdata"));
//
//        }



            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CollectionReference db =  FirebaseFirestore.getInstance().collection(getIntent().getStringExtra("date")).
                            document(getIntent().getStringExtra("category")).collection("news");


                    db.whereEqualTo("headline",getIntent().getStringExtra("headline")).get().
                            addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful()){
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Map<String, Object> liked = new HashMap<>();
                                            liked.put("likes", getIntent().getIntExtra("likes",1)+1);
                                            db.document(document.getId()).set(liked,SetOptions.merge());
                                        }
                                    }
                                }
                            });
                }
            });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CollectionReference database = (CollectionReference) db.collection(getIntent().getStringExtra("date")).
                        document(getIntent().getStringExtra("category")).
                        collection("news").whereEqualTo("headline",getIntent().getStringExtra("headline"));

                FirebaseFirestore.getInstance().collection("8151033423")
                        .document("saved").collection("news").whereEqualTo("headline",getIntent().getStringExtra("headline")).get().
                        addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.getResult().size()==0) {
                                    Map<String, Object> saved = new HashMap<>();
                                    saved.put("image", getIntent().getStringExtra("image"));
                                    saved.put("headline", getIntent().getStringExtra("headline"));
                                    saved.put("category",getIntent().getStringExtra("category"));
                                    saved.put("date",getIntent().getStringExtra("date"));
                                    FirebaseFirestore.getInstance().collection("8151033423").document("saved")
                                            .collection("news").document().set(saved);
                                    Toast.makeText(news.this, "News saved successfully.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(news.this, "News already exist in your archive.", Toast.LENGTH_SHORT).show();
                                }
                            }});
            }});
    }
}
