package com.tmv01.themillennial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class saved extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<savedpagedata> saveddata= new ArrayList<>();
    public RecyclerView savedlist;
    savedadapter saves;


    @Override
    protected void onStart() {
        super.onStart();

        db.collection("8151033423").document("saved")
                .collection("news").whereEqualTo("saved","true")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                        saveddata.clear();
                        for (QueryDocumentSnapshot retrievedDocSnap : queryDocumentSnapshots) {

                            savedpagedata save= retrievedDocSnap.toObject(savedpagedata.class);
                            saveddata.add(save);
                        }
                        saves.notifyDataSetChanged();
                    }
                });

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);
        savedlist = findViewById(R.id.save);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(saved.this);
        ((LinearLayoutManager) layoutManager).setOrientation(RecyclerView.VERTICAL);
        savedlist.setLayoutManager(layoutManager);
        saves= new savedadapter(saved.this, saveddata);
        savedlist.setAdapter(saves);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                saves.deleteItem(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(savedlist);
    }
}
