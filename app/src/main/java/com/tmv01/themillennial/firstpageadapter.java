package com.tmv01.themillennial;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.squareup.picasso.Picasso;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class firstpageadapter extends RecyclerView.Adapter<firstpageadapter.MyViewHolder> {

        Context context;
        ArrayList<firstpagedata> firstpageleftdata;




    public firstpageadapter(Context context, ArrayList<firstpagedata> firstpageleftdata)
    {
        this.context = context;
        this.firstpageleftdata = firstpageleftdata ;
    }



        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.cycleleftcontent, viewGroup, false);
        return new MyViewHolder(view);
    }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        final firstpagedata dataleft=firstpageleftdata.get(i);
        myViewHolder.text.setText(dataleft.getHeadline());
        myViewHolder.views.setText(  dataleft.getViews()+" views");
        myViewHolder.likes.setText(dataleft.getLikes()+ " Likes");
        Picasso.get().load(dataleft.getImage()).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(myViewHolder.image);

            myViewHolder.liked.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    final CollectionReference db =  FirebaseFirestore.getInstance().collection(dataleft.getDate()).
                            document("First page").collection("news");

                        db.whereEqualTo("headline",dataleft.getHeadline()).get().
                                addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if(task.isSuccessful()){
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                Map<String, Object> liked = new HashMap<>();
                                                liked.put("likes", dataleft.getLikes()+1);
                                                db.document(document.getId()).set(liked,SetOptions.merge());
                                            }
                                        }
                                    }
                                });
                }
            });

            myViewHolder.save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseFirestore.getInstance().collection("8151033423").document("saved").collection("news").whereEqualTo("headline",dataleft.getHeadline()).get().
                            addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.getResult().size()==0) {
                                        Map<String, Object> saved = new HashMap<>();
                                        saved.put("image", dataleft.getImage());
                                        saved.put("headline", dataleft.getHeadline());
                                        saved.put("category",dataleft.getCategory());
                                        saved.put("date",dataleft.getDate());
                                        FirebaseFirestore.getInstance().collection("8151033423").document("saved")
                                                .collection("news").document().set(saved);
                                        Toast.makeText(context, "News saved successfully.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(context, "News already exist in your archive.", Toast.LENGTH_SHORT).show();
                                    }
                                }});
                }});

        myViewHolder.image.setOnClickListener(new View.OnClickListener() {
            Intent data;
            @Override
            public void onClick(View v ) {
                data = new Intent(v.getContext(), news.class);
                data.putExtra("date",dataleft.getDate());
                data.putExtra("category",dataleft.getCategory());
                data.putExtra("headline",dataleft.getHeadline());
                data.putExtra("image",dataleft.getImage());
                data.putExtra("textualdata",dataleft.getTextualdata());
                data.putExtra("likes",dataleft.getLikes());
                data.putExtra("savednews","false");
                data.putExtra("title","The Millennial");
                Integer views  = dataleft.getViews()+1;
                data.putExtra("views", views);
                v.getContext().startActivity(data);

            }
        });
            myViewHolder.text.setOnClickListener(new View.OnClickListener() {
                Intent data;
                @Override
                 public void onClick(View v ) {
                    data=new Intent(v.getContext(),news.class);
                    data.putExtra("date",dataleft.getDate());
                    data.putExtra("category",dataleft.getCategory());
                    data.putExtra("headline",dataleft.getHeadline());
                    data.putExtra("image",dataleft.getImage());
                    data.putExtra("textualdata",dataleft.getTextualdata());
                    data.putExtra("likes",dataleft.getLikes());
                    data.putExtra("savednews","false");
                    data.putExtra("title","The Millennial");
                    Integer views  = dataleft.getViews()+1;
                    data.putExtra("views", views);
                    v.getContext().startActivity(data);

                }
            });


    }

        @Override
        public int getItemCount()
    {
        return firstpageleftdata.size();
    }



        public class MyViewHolder extends RecyclerView.ViewHolder
        {
            TextView text,views,likes;
            ImageView image;
            ImageButton liked,save;
            RelativeLayout block;



            public MyViewHolder(@NonNull final View itemView)
            {
                super(itemView);
                likes = itemView.findViewById(R.id.nlikes);
                liked= itemView.findViewById(R.id.like);
                text = itemView.findViewById(R.id.tbtext);
                views = itemView.findViewById(R.id.viewcount);
                image= itemView.findViewById(R.id.timage);
                save =itemView.findViewById(R.id.saved);
                block = itemView.findViewById(R.id.block);
            }
        }


    }
