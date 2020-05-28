package com.tmv01.themillennial;

import android.content.Context;
import android.content.Intent;
import android.media.MediaDrm;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class savedadapter extends RecyclerView.Adapter<savedadapter.MyViewHolder> {

    Context context;
    ArrayList<savedpagedata> savedpagedatas;


    public savedadapter(Context context, ArrayList<savedpagedata> savedpagedatas) {
        this.context = context;
        this.savedpagedatas = savedpagedatas;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.savedlist, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        final savedpagedata dataleft=savedpagedatas.get(i);
        myViewHolder.newsdate.setText("News date:"+dataleft.getDate());
        myViewHolder.text.setText(dataleft.getHeadline());
        Picasso.get().load(dataleft.getImage()).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(myViewHolder.image);

        myViewHolder.image.setOnClickListener(new View.OnClickListener() {
            Intent data;
            @Override
            public void onClick(View v ) {
                data = new Intent(v.getContext(), news.class);
                data.putExtra("date",dataleft.getDate());
                data.putExtra("category",dataleft.getCategory());
                data.putExtra("headline",dataleft.getHeadline());
                data.putExtra("image",dataleft.getImage());
                data.putExtra("title","The Millennial");
                v.getContext().startActivity(data);
            }
        });
        myViewHolder.text.setOnClickListener(new View.OnClickListener() {
            Intent data;
            @Override
            public void onClick(View v ) {
                data = new Intent(v.getContext(), news.class);
                data.putExtra("date",dataleft.getDate());
                data.putExtra("category",dataleft.getCategory());
                data.putExtra("headline",dataleft.getHeadline());
                data.putExtra("image",dataleft.getImage());
                data.putExtra("title","The Millennial");
                v.getContext().startActivity(data);
            }
        });
        myViewHolder.newsdate.setOnClickListener(new View.OnClickListener() {
            Intent data;
            @Override
            public void onClick(View v ) {
                data = new Intent(v.getContext(), news.class);
                data.putExtra("date",dataleft.getDate());
                data.putExtra("category",dataleft.getCategory());
                data.putExtra("headline",dataleft.getHeadline());
                data.putExtra("image",dataleft.getImage());
                data.putExtra("title","The Millennial");
                v.getContext().startActivity(data);
            }
        });


    }

    @Override
    public int getItemCount()
    {
        return savedpagedatas.size();
    }

    public void deleteItem(int position) {
        final savedpagedata dataleft=savedpagedatas.get(position);
        CollectionReference delete = FirebaseFirestore.getInstance().collection("8151033423").document("saved").
                collection("news");
        delete.whereEqualTo("headline",dataleft.getHeadline()).get().
                addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                FirebaseFirestore.getInstance().collection("8151033423").
                                        document("saved").
                                        collection("news").document(document.getId()).delete();
                                Toast.makeText(context, "News was removed from your starred list.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView text,newsdate;
        ImageView image;

        public MyViewHolder(@NonNull final View itemView)
        {
            super(itemView);
            newsdate=itemView.findViewById(R.id.newsdate);
            text = itemView.findViewById(R.id.headline);
            image= itemView.findViewById(R.id.image);



        }
    }


}
