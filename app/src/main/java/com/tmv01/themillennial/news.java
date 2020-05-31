package com.tmv01.themillennial;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.Loader;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.squareup.picasso.Picasso;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class news extends AppCompatActivity {
     Number templike,nusers,Count;
     Integer likecount;
     FirebaseFirestore db =  FirebaseFirestore.getInstance();



    @Override
    protected void onStart() {
        super.onStart();

        db.collection("8151033423").document("views").get().
                addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String Category =getIntent().getStringExtra("category");
                        Number val = (Number) documentSnapshot.get(Category);
                        Map<String, Object> viewsdata = new HashMap<>();
                        viewsdata.put(Category, val.intValue()+1);
                        db.collection("8151033423").document("views").set(viewsdata, SetOptions.merge());
                    }});

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



              db.collection(Objects.requireNonNull(getIntent().getStringExtra("date"))).
                document(Objects.requireNonNull(getIntent().getStringExtra("category"))).
                collection("news").whereEqualTo("headline",getIntent().getStringExtra("headline")).get().
                    addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (final QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                    if (Objects.equals(getIntent().getStringExtra("savednews"), "true")) {
                                                    Textualdata.setText(String.valueOf(document.getString("textualdata")));
                                                    Number tempview = (Number) document.get("views");
                                                    templike = (Number) document.get("plikes");
                                                    assert templike != null;
                                                    likes.setText(String.format("%s liked", templike.intValue()));
                                                    Integer value = tempview.intValue() + 1;
                                                    views.setText(String.format("%s views", value));
                                                    Map<String, Object> viewed = new HashMap<>();
                                                    viewed.put("views", value);
                                                    db.collection(Objects.requireNonNull(getIntent().getStringExtra("date"))).
                                                            document(Objects.requireNonNull(getIntent().getStringExtra("category"))).
                                                            collection("news").document(document.getId()).set(viewed, SetOptions.merge());

                                                } else {
                                                    db.collection(Objects.requireNonNull(getIntent().getStringExtra("date"))).
                                                            document(Objects.requireNonNull(getIntent().getStringExtra("category"))).
                                                            collection("news").whereEqualTo("headline", getIntent().getStringExtra("headline")).get().
                                                            addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                    if (task.isSuccessful()) {
                                                                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                                                            Number view = (Number) document.get("views");
                                                                            Number plikes = (Number) document.get("plikes");
                                                                            assert view != null;
                                                                            Integer value = view.intValue() + 1;
                                                                            Map<String, Object> viewed = new HashMap<>();
                                                                            viewed.put("views", value);
                                                                            db.collection(Objects.requireNonNull(getIntent().getStringExtra("date"))).
                                                                                    document(Objects.requireNonNull(getIntent().getStringExtra("category"))).
                                                                                    collection("news")
                                                                                    .document(document.getId()).set(viewed, SetOptions.merge());
                                                                            Textualdata.setText(getIntent().getStringExtra("textualdata"));
                                                                            likes.setText(plikes + "% liked");
                                                                            views.setText(getIntent().getIntExtra("views", 0) + "views");
                                                                        }
                                                                    }
                                                                }
                                                            });
                                                }
                                }
                            }
                        }
                    });




           like.setOnClickListener(new View.OnClickListener() {
               Integer count=1;

                @Override
                public void onClick(View view) {

                    db.collection("nusers").document("ucount").get().
                            addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    nusers = (Number) documentSnapshot.get("users");
                                    db.collection(getIntent().getStringExtra("date")).
                                            document(getIntent().getStringExtra("category")).
                                            collection("news").whereEqualTo("headline", getIntent().getStringExtra("headline")).get().
                                            addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        for (final QueryDocumentSnapshot document : task.getResult()) {
                                                            templike = (Number) document.get("likes");
                                                            likecount = templike.intValue();
                                                            db.collection("8151033423").document("views").get().
                                                                    addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                        @Override
                                                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                            String Category =getIntent().getStringExtra("category");
                                                                            Count = (Number) documentSnapshot.get(Category);
                                                                            Map<String, Object> viewsdata = new HashMap<>();


                                                            if (count == 1) {
                                                                Map<String, Object> liked = new HashMap<>();
                                                                likecount++;
                                                                Float temp = (Float.intBitsToFloat(likecount) / Float.intBitsToFloat(nusers.intValue()) * 100);
                                                                liked.put("likes",likecount);
                                                                liked.put("plikes",Math.round(temp));
                                                                likes.setText(Math.round(temp)+ "%" + "liked");
                                                                FirebaseFirestore.getInstance().collection(getIntent().getStringExtra("date")).
                                                                        document(getIntent().getStringExtra("category")).
                                                                        collection("news").document(document.getId()).set(liked, SetOptions.merge());
                                                                viewsdata.put(Category, Count.intValue()+1);
                                                                db.collection("8151033423").document("views").
                                                                        set(viewsdata, SetOptions.merge());
                                                                count++;
                                                            } else if (count % 2 == 0) {
                                                                likecount--;
                                                                Map<String, Object> liked = new HashMap<>();
                                                                Float temp = (Float.intBitsToFloat(likecount) / Float.intBitsToFloat(nusers.intValue()) * 100);
                                                                liked.put("likes",likecount);
                                                                liked.put("plikes",Math.round(temp));
                                                                likes.setText(Math.round(temp)+ "%" + "liked");
                                                                FirebaseFirestore.getInstance().collection(getIntent().getStringExtra("date")).
                                                                        document(getIntent().getStringExtra("category")).
                                                                        collection("news").document(document.getId()).set(liked, SetOptions.merge());
                                                                viewsdata.put(Category, Count.intValue()-1);
                                                                db.collection("8151033423").document("views").
                                                                        set(viewsdata, SetOptions.merge());
                                                                count++;
                                                            } else {
                                                                likecount++;
                                                                Map<String, Object> liked = new HashMap<>();
                                                                Float temp = (Float.intBitsToFloat(likecount) / Float.intBitsToFloat(nusers.intValue()) * 100);
                                                                liked.put("likes",likecount);
                                                                liked.put("plikes",Math.round(temp));
                                                                likes.setText(Math.round(temp)+ "%" + "liked");
                                                                FirebaseFirestore.getInstance().collection(getIntent().getStringExtra("date")).
                                                                        document(getIntent().getStringExtra("category")).
                                                                        collection("news").document(document.getId()).set(liked, SetOptions.merge());
                                                                viewsdata.put(Category, Count.intValue()+1);
                                                                db.collection("8151033423").document("views").
                                                                        set(viewsdata, SetOptions.merge());
                                                                count++;
                                                            }
                                                                        }});
                                                        }
                                                    }
                                                }
                                            });
                                }
                            });
                }
            });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            final  CollectionReference database =db.collection("8151033423")
                             .document("saved").collection("news");
                database.whereEqualTo("headline",getIntent().getStringExtra("headline")).get().
                        addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                if (task.getResult().size()==0) {
                                    Map<String, Object> saved = new HashMap<>();
                                    saved.put("image", getIntent().getStringExtra("image"));
                                    saved.put("headline", getIntent().getStringExtra("headline"));
                                    saved.put("category",getIntent().getStringExtra("category"));
                                    saved.put("date",getIntent().getStringExtra("date"));
                                    database.document().set(saved);
                                    Toast.makeText(news.this, "News saved successfully.", Toast.LENGTH_SHORT).show();
                                    db.collection("8151033423").document("views").get().
                                            addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                @Override
                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                    String Category =getIntent().getStringExtra("category");
                                                    Number value = (Number) documentSnapshot.get(Category);
                                                    Map<String, Object> viewsdata = new HashMap<>();
                                                    viewsdata.put(Category, value.intValue()+1);
                                                    db.collection("8151033423").document("views").set(viewsdata, SetOptions.merge());
                                                }});
                                } else {
                                    Toast.makeText(news.this, "News already exist in your archive.", Toast.LENGTH_SHORT).show();
                                }
                            }});
            }});
    }
}
