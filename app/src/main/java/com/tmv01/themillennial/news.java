package com.tmv01.themillennial;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.Loader;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class news extends AppCompatActivity {
     Number templike,nusers,Count;
     Integer likecount;
     FirebaseFirestore db =  FirebaseFirestore.getInstance();
    Bitmap bmp ;
    Context context;
    private Object Context;


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
        final ImageButton save = findViewById(R.id.saved);
        final ImageButton like = findViewById(R.id.like);
        ImageView Imagedata = findViewById(R.id.imagedata);
        ImageButton share = findViewById(R.id.share);

        Headline.setText(getIntent().getStringExtra("headline"));
        category.setText(getIntent().getStringExtra("title"));
        Picasso.get().load(getIntent().getStringExtra("image")).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(Imagedata);
        db.collection("8151033423").document("saved").collection("news").
                whereEqualTo("headline",getIntent().getStringExtra("headline")).get().
                addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (final QueryDocumentSnapshot document : task.getResult()) {
                               if ((Boolean)document.get("saved"))
                               {
                                   save.setImageDrawable(getDrawable(R.drawable.black_star));
                               }
                                if ((Boolean)document.get("liked"))
                                {
                                    like.setImageDrawable(getDrawable(R.drawable.black_heart));
                                }
                            }
                        }
                    }});

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
                                                    likes.setText( templike.intValue()+"% users liked");
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
                                                                            likes.setText(plikes + "% users liked");
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



           share.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   shareItem(getIntent().getStringExtra("image"));
               }
           });

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CollectionReference newsdata =  db.collection(getIntent().getStringExtra("date")).
                        document(getIntent().getStringExtra("category")).collection("news");
                final CollectionReference database = db.collection("8151033423").
                        document("saved").collection("news");
                db.collection("nusers").document("ucount").get().
                        addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                final Number nusers = (Number) documentSnapshot.get("users");
                                newsdata.whereEqualTo("headline", getIntent().getStringExtra("headline")).get().
                                        addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    for (final QueryDocumentSnapshot document : task.getResult()) {
                                                        Number templike = (Number) document.get("likes");
                                                        final Integer[] likecount = {templike.intValue()};
                                                        Map<String, Object> liked = new HashMap<>();
                                                        database.whereEqualTo("headline", getIntent().getStringExtra("headline")).get().
                                                                addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                    Map<String, Object> liked = new HashMap<>();

                                                                    @Override
                                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                        if (task.getResult().size() == 0) {
                                                                            like.setImageDrawable(getDrawable(R.drawable.black_heart));
                                                                            Toast.makeText(news.this, "liked", Toast.LENGTH_SHORT).show();
                                                                            liked.put("saved", false);
                                                                            liked.put("headline", getIntent().getStringExtra("headline"));
                                                                            liked.put("liked", true);
                                                                            liked.put("image","no image");
                                                                            liked.put("date", getIntent().getStringExtra("date"));
                                                                            liked.put("category", getIntent().getStringExtra("category"));
                                                                            newsdata.whereEqualTo("headline", getIntent().getStringExtra("headline")).get().
                                                                                    addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                                        @Override
                                                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                                            if (task.isSuccessful()) {
                                                                                                for (final QueryDocumentSnapshot document : task.getResult()) {
                                                                                                    Number templike = (Number) document.get("likes");
                                                                                                    Integer likecount = templike.intValue();
                                                                                                    Map<String, Object> liked = new HashMap<>();
                                                                                                    likecount++;
                                                                                                    Float temp = (Float.intBitsToFloat(likecount) / Float.intBitsToFloat(nusers.intValue()) * 100);
                                                                                                    liked.put("likes", likecount);
                                                                                                    liked.put("plikes", Math.round(temp));
                                                                                                    newsdata.document(document.getId()).set(liked, SetOptions.merge());
                                                                                                }}}});
                                                                            FirebaseFirestore.getInstance().collection("8151033423").document("saved")
                                                                                    .collection("news").document().set(liked);
                                                                            Toast.makeText(news.this, "liked", Toast.LENGTH_SHORT).show();
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
                                                                            for (QueryDocumentSnapshot docs : task.getResult()) {
                                                                                if ((Boolean)docs.get("liked")) {
                                                                                    like.setImageDrawable(getDrawable(R.drawable.ic_favorite_black_24dp));
                                                                                    newsdata.whereEqualTo("headline", getIntent().getStringExtra("headline")).get().
                                                                                            addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                                                @Override
                                                                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                                                    if (task.isSuccessful()) {
                                                                                                        for (final QueryDocumentSnapshot document : task.getResult()) {
                                                                                                            Number templike = (Number) document.get("likes");
                                                                                                            Integer likecount = templike.intValue();
                                                                                                            Map<String, Object> liked = new HashMap<>();
                                                                                                            likecount--;
                                                                                                            Float temp = (Float.intBitsToFloat(likecount) / Float.intBitsToFloat(nusers.intValue()) * 100);
                                                                                                            liked.put("likes", likecount);
                                                                                                            liked.put("plikes", Math.round(temp));
                                                                                                            newsdata.document(document.getId()).set(liked, SetOptions.merge());
                                                                                                        }}}});

                                                                                    if ((Boolean)docs.get("saved")) {
                                                                                        liked.put("liked", false);
                                                                                        db.collection("8151033423").document("saved")
                                                                                                .collection("news").document(docs.getId()).update(liked);
                                                                                        Toast.makeText(news.this, "disliked.", Toast.LENGTH_SHORT).show();
                                                                                    } else {
                                                                                        db.collection("8151033423").document("saved")
                                                                                                .collection("news").document(docs.getId()).delete();
                                                                                        Toast.makeText(news.this, "disliked.", Toast.LENGTH_SHORT).show();
                                                                                    }

                                                                                } else {
                                                                                    like.setImageDrawable(getDrawable(R.drawable.black_heart));
                                                                                    newsdata.whereEqualTo("headline", getIntent().getStringExtra("headline")).get().
                                                                                            addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                                                @Override
                                                                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                                                    if (task.isSuccessful()) {
                                                                                                        for (final QueryDocumentSnapshot document : task.getResult()) {
                                                                                                            Number templike = (Number) document.get("likes");
                                                                                                            Integer likecount = templike.intValue();
                                                                                                            Map<String, Object> liked = new HashMap<>();
                                                                                                            likecount--;
                                                                                                            Float temp = (Float.intBitsToFloat(likecount) / Float.intBitsToFloat(nusers.intValue()) * 100);
                                                                                                            liked.put("likes", likecount);
                                                                                                            liked.put("plikes", Math.round(temp));
                                                                                                            newsdata.document(document.getId()).set(liked, SetOptions.merge());
                                                                                                        }}}});
                                                                                    if ((Boolean)docs.get("saved")) {
                                                                                        liked.put("liked", true);
                                                                                        FirebaseFirestore.getInstance().collection("8151033423").document("saved")
                                                                                                .collection("news").document(docs.getId()).update(liked);
                                                                                        Toast.makeText(news.this, "liked", Toast.LENGTH_SHORT).show();
                                                                                    } else {
                                                                                        liked.put("liked", true);
                                                                                        FirebaseFirestore.getInstance().collection("8151033423").document("saved")
                                                                                                .collection("news").document(docs.getId()).update(liked);
                                                                                        Toast.makeText(news.this, "liked.", Toast.LENGTH_SHORT).show();
                                                                                    }
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

                                                                                }
                                                                            }
                                                                        }

                                                                    }
                                                                });

                                                    }}  }});
                            }});

            } });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("8151033423").
                        document("saved").collection("news")
                        .whereEqualTo("headline", getIntent().getStringExtra("headline")).get().
                        addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
                        {
                            Map<String, Object> saved = new HashMap<>();
                            @RequiresApi(api = Build.VERSION_CODES.M)
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task)
                            {
                                if (task.getResult().size()==0) {
                                    save.setImageDrawable(getDrawable(R.drawable.black_star));
                                    saved.put("category", getIntent().getStringExtra("category"));
                                    saved.put("headline", getIntent().getStringExtra("headline"));
                                    saved.put("image",getIntent().getStringExtra("image"));
                                    saved.put("date",getIntent().getStringExtra("date"));
                                    saved.put("saved",true);
                                    saved.put("liked",false);
                                    db.collection("8151033423").
                                            document("saved").collection("news").document().set(saved);
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
                                }
                                else {
                                    for (QueryDocumentSnapshot docs : task.getResult()) {
                                        if ((Boolean)docs.get("saved")) {
                                            save.setImageDrawable(getDrawable(R.drawable.ic_grade_black_24dp));
                                            if ((Boolean)docs.get("liked")) {
                                                saved.put("image","no image");
                                                saved.put("saved", false);
                                                FirebaseFirestore.getInstance().collection("8151033423").document("saved")
                                                        .collection("news").document(docs.getId()).update(saved);
                                                Toast.makeText(news.this, "News was removed from saved successfully.", Toast.LENGTH_SHORT).show();
                                            } else {
                                                FirebaseFirestore.getInstance().collection("8151033423").document("saved")
                                                        .collection("news").document(docs.getId()).delete();
                                                Toast.makeText(news.this, "News was removed from saved successfully.", Toast.LENGTH_SHORT).show();
                                            }

                                        } else {
                                            save.setImageDrawable(getDrawable(R.drawable.black_star));
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
                                            if ((Boolean)docs.get("liked")) {
                                                saved.put("image",getIntent().getStringExtra("image"));
                                                saved.put("saved", true);
                                                FirebaseFirestore.getInstance().collection("8151033423").document("saved")
                                                        .collection("news").document(docs.getId()).update(saved);
                                                Toast.makeText(news.this, "News saved successfully.", Toast.LENGTH_SHORT).show();
                                            } else {
                                                saved.put("image",getIntent().getStringExtra("image"));
                                                saved.put("saved", true);
                                                FirebaseFirestore.getInstance().collection("8151033423").document("saved")
                                                        .collection("news").document(docs.getId()).update(saved);
                                                Toast.makeText(news.this, "News saved successfully.", Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    }
                                }



                            }});
            }});
    }
    public Uri getLocalBitmapUri(Bitmap bmp) {
        Uri bmpUri = null;
        try {
            File file =  new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Themillennial" + System.currentTimeMillis() + ".jpg" +
                    "");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }
    public void shareItem( String url) {
        Picasso.get().load(url).into(new Target() {
            @Override public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("image/jpg");
                i.putExtra(Intent.EXTRA_TEXT, "The Millennial(Beta)News app : News Headline"+getIntent().getStringExtra("headline"));
                i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap));
                startActivity(Intent.createChooser(i, "Share news"));
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override public void onPrepareLoad(Drawable placeHolderDrawable) { }
        });
    }

}
