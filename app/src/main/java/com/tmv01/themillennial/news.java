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
     Number templike;
     Integer likecount;
     FirebaseFirestore db =  FirebaseFirestore.getInstance();


    @Override
    protected void onStart() {
        super.onStart();



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        TextView Headline    = findViewById(R.id.headline);
        final TextView Textualdata = findViewById(R.id.textualdata);
        TextView category = findViewById(R.id.category);
        final TextView likes = findViewById(R.id.nlikes);
        final TextView views = findViewById(R.id.viewcount);
        ImageButton save = findViewById(R.id.saved);
        final ImageButton like = findViewById(R.id.like);
        ImageView Imagedata = findViewById(R.id.imagedata);

        Headline.setText(getIntent().getStringExtra("headline"));
        category.setText(getIntent().getStringExtra("title"));
        Picasso.get().load(getIntent().getStringExtra("image")).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(Imagedata);



              db.collection(getIntent().getStringExtra("date")).
                document(getIntent().getStringExtra("category")).
                collection("news").whereEqualTo("headline",getIntent().getStringExtra("headline")).get().
                    addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    if(getIntent().getStringExtra("savednews").equals("true")){
                                        Textualdata.setText(String.valueOf(document.getString("textualdata")));
                                        Number tempview=(Number)document.get("views");
                                        templike=(Number)document.get("likes");
                                        likes.setText(String.format("%s likes", templike.intValue()));
                                        Integer value  = tempview.intValue() +1;
                                        views.setText(String.format("%s views", value));
                                        Map<String, Object> viewed = new HashMap<>();
                                        viewed.put("views",value );
                                        db.collection(getIntent().getStringExtra("date")).
                                                document(getIntent().getStringExtra("category")).
                                                collection("news") .document(document.getId()).set(viewed, SetOptions.merge());
                                    }
                                    else {
                                        db.collection(getIntent().getStringExtra("date")).
                                                document(getIntent().getStringExtra("category")).
                                                collection("news").whereEqualTo("headline",getIntent().getStringExtra("headline")).get().
                                                addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                        if(task.isSuccessful()){
                                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                                Number view=(Number)document.get("views");
                                                                Integer value  = view.intValue() +1;
                                                                Map<String, Object> viewed = new HashMap<>();
                                                                viewed.put("views",value );
                                                                db.collection(getIntent().getStringExtra("date")).
                                                                        document(getIntent().getStringExtra("category")).
                                                                        collection("news")
                                                                        .document(document.getId()).set(viewed, SetOptions.merge());
                                                            }
                                                        }
                                                    }
                                                });
                                        Textualdata.setText(getIntent().getStringExtra("textualdata"));
                                        likes.setText(getIntent().getIntExtra("likes",0)+"likes");
                                        views.setText(getIntent().getIntExtra("views",0)+ "views");
                                    }

                                }
                            }
                        }
                    });


           like.setOnClickListener(new View.OnClickListener() {
               Integer count=1;
                @Override
                public void onClick(View view) {
                    if(count==1){
                        db.collection(getIntent().getStringExtra("date")).
                                document(getIntent().getStringExtra("category")).
                                collection("news").whereEqualTo("headline", getIntent().getStringExtra("headline")).get().
                                addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                if (getIntent().getStringExtra("savednews").equals("true")) {
                                                    likecount = Integer.valueOf(templike.intValue());
                                                } else {
                                                    likecount = Integer.valueOf(getIntent().getIntExtra("likes", 0));
                                                }

                                                Map<String, Object> liked = new HashMap<>();
                                                likecount++;
                                                likes.setText(likecount + "likes");
                                                liked.put("likes", likecount);
                                                FirebaseFirestore.getInstance().collection(getIntent().getStringExtra("date")).
                                                        document(getIntent().getStringExtra("category")).
                                                        collection("news").document(document.getId()).set(liked, SetOptions.merge());
                                                Toast.makeText(news.this, "Liked", Toast.LENGTH_SHORT).show();
                                                count++;
                                            }
                                        }
                                    }
                                });
                    }
                    else if (count%2==0 ) {
                        db.collection(getIntent().getStringExtra("date")).
                                document(getIntent().getStringExtra("category")).
                                collection("news").whereEqualTo("headline", getIntent().getStringExtra("headline")).get().
                                addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                Number dislike=(Number)document.get("likes");
                                                Integer tempdislike  = dislike.intValue()-1;
                                                Map<String, Object> viewed = new HashMap<>();
                                                viewed.put("views",tempdislike );
                                                Map<String, Object> liked = new HashMap<>();
                                                likes.setText(tempdislike+"likes");
                                                liked.put("likes", tempdislike);
                                                FirebaseFirestore.getInstance().collection(getIntent().getStringExtra("date")).
                                                        document(getIntent().getStringExtra("category")).
                                                        collection("news").document(document.getId()).set(liked, SetOptions.merge());
                                                Toast.makeText(news.this, "disliked", Toast.LENGTH_SHORT).show();
                                                count++;

                                            }
                                        }
                                    }
                                });
                    }
                    else{
                        db.collection(getIntent().getStringExtra("date")).
                                document(getIntent().getStringExtra("category")).
                                collection("news").whereEqualTo("headline", getIntent().getStringExtra("headline")).get().
                                addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                Number dislike=(Number)document.get("likes");
                                                Integer templike  = dislike.intValue()+1;
                                                Map<String, Object> viewed = new HashMap<>();
                                                viewed.put("views",templike );
                                                Map<String, Object> liked = new HashMap<>();
                                                likes.setText(templike+"likes");
                                                liked.put("likes", templike);
                                                FirebaseFirestore.getInstance().collection(getIntent().getStringExtra("date")).
                                                        document(getIntent().getStringExtra("category")).
                                                        collection("news").document(document.getId()).set(liked, SetOptions.merge());
                                                Toast.makeText(news.this, "Liked", Toast.LENGTH_SHORT).show();
                                                count++;
                                            }
                                        }
                                    }
                                });

                    }

                }
            });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            final  CollectionReference db =
                     FirebaseFirestore.getInstance().collection("8151033423")
                             .document("saved").collection("news");
                db.whereEqualTo("headline",getIntent().getStringExtra("headline")).get().
                        addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.getResult().size()==0) {
                                    Map<String, Object> saved = new HashMap<>();
                                    saved.put("image", getIntent().getStringExtra("image"));
                                    saved.put("headline", getIntent().getStringExtra("headline"));
                                    saved.put("category",getIntent().getStringExtra("category"));
                                    saved.put("date",getIntent().getStringExtra("date"));
                                     db.document().set(saved);
                                    Toast.makeText(news.this, "News saved successfully.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(news.this, "News already exist in your archive.", Toast.LENGTH_SHORT).show();
                                }
                            }});
            }});
    }
}
