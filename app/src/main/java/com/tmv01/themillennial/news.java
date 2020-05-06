package com.tmv01.themillennial;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.squareup.picasso.Picasso;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class news extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onStart() {
        super.onStart();
        final CollectionReference database = db.collection(getIntent().getStringExtra("date")).document(getIntent().getStringExtra("category")).
                collection("news");
        database.whereEqualTo("headline",getIntent().getStringExtra("headline")).get().
                    addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Map<String, Object> viewed = new HashMap<>();
                                    viewed.put("views", getIntent().getIntExtra("views",1));
                                    database.document(document.getId()).set(viewed,SetOptions.merge());
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
        ImageView Imagaedata = findViewById(R.id.imagedata);
        category.setText(getIntent().getStringExtra("title"));
        Textualdata.setText(getIntent().getStringExtra("textualdata"));
        Headline.setText(getIntent().getStringExtra("headline"));
        Picasso.get().load(getIntent().getStringExtra("image")).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(Imagaedata);

    }
}
