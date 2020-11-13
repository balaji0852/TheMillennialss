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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
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
        String uno;
//        ArrayList<lsdata> likeddata;
//        ,ArrayList<lsdata> likeddata



    public firstpageadapter(Context context, ArrayList<firstpagedata> firstpageleftdata,String uno)

    {
        this.context = context;
        this.firstpageleftdata = firstpageleftdata ;
        this.uno = uno;
//        this.likeddata = likeddata;

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

        if (dataleft.getAid().equals("true")) {
            myViewHolder.like.setImageDrawable(context.getDrawable(R.drawable.black_heart));
        }
//        else{
//            myViewHolder.like.setImageDrawable(context.getDrawable(R.drawable.ic_favorite_black_24dp));
//        }
        if (dataleft.getPoname().equals("true")) {
            myViewHolder.save.setImageDrawable(context.getDrawable(R.drawable.black_star));
        }
//        else{
//            myViewHolder.save.setImageDrawable(context.getDrawable(R.drawable.ic_grade_black_24dp));
            myViewHolder.text.setText(dataleft.getHeadline());
            myViewHolder.views.setText(dataleft.getViews()+" views");
//            myViewHolder.likes.setText(dataleft.getPlikes()+"% users liked");
            Picasso.get().load(dataleft.getImage()).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(myViewHolder.image);
            final FirebaseFirestore db = FirebaseFirestore.getInstance();

            myViewHolder.like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CollectionReference newsdata =  db.collection(dataleft.getDate()).
                            document(dataleft.getCategory()).collection("news");
                    final CollectionReference likeddata =db.collection(uno);
                    final CollectionReference database = db.collection(uno).
                            document("saved").collection("news");
                    db.collection("nusers").document("ucount").get().
                            addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    final Number nusers = (Number) documentSnapshot.get("users");

                                    database.whereEqualTo("headline", dataleft.getHeadline()).get().
                                            addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                Map<String, Object> liked = new HashMap<>();

                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.getResult().size() == 0) {
                                                        myViewHolder.like.setImageDrawable(context.getDrawable(R.drawable.black_heart));
                                                        liked.put("saved", false);
                                                        liked.put("headline", dataleft.getHeadline());
                                                        liked.put("liked", true);
                                                        liked.put("date", dataleft.getDate());
                                                        liked.put("category",dataleft.getCategory());
                                                        liked.put("image","no image");
                                                        FirebaseFirestore.getInstance().collection(uno).document("saved")
                                                                .collection("news").document().set(liked);
                                                        Toast.makeText(context, "liked", Toast.LENGTH_SHORT).show();
                                                        newsdata.whereEqualTo("headline", dataleft.getHeadline()).get().
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

                                                        db.collection(uno).document("views").get().
                                                                addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                    @Override
                                                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                        String Category =dataleft.getCategory();
                                                                        Number value = (Number) documentSnapshot.get(Category);
                                                                        Map<String, Object> viewsdata = new HashMap<>();
                                                                        viewsdata.put(Category, value.intValue()+1);
                                                                        db.collection(uno).document("views").set(viewsdata, SetOptions.merge());
                                                                    }});
                                                    } else {
                                                        for (QueryDocumentSnapshot docs : task.getResult()) {

                                                            if ((Boolean)docs.get("liked")) {
                                                                myViewHolder.like.setImageDrawable(context.getDrawable(R.drawable.ic_favorite_black_24dp));
                                                                newsdata.whereEqualTo("headline", dataleft.getHeadline()).get().
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
                                                                    db.collection(uno).document("saved")
                                                                            .collection("news").document(docs.getId()).update(liked);
                                                                    Toast.makeText(context, "disliked.", Toast.LENGTH_SHORT).show();
                                                                } else {
                                                                    db.collection(uno).document("saved")
                                                                            .collection("news").document(docs.getId()).delete();
                                                                    Toast.makeText(context, "disliked.", Toast.LENGTH_SHORT).show();
                                                                }

                                                            } else {
                                                                myViewHolder.like.setImageDrawable(context.getDrawable(R.drawable.black_heart));
                                                                if ((Boolean)docs.get("saved")) {
                                                                    liked.put("liked", true);
                                                                    FirebaseFirestore.getInstance().collection(uno).document("saved")
                                                                            .collection("news").document(docs.getId()).update(liked);
                                                                    Toast.makeText(context, "liked", Toast.LENGTH_SHORT).show();
                                                                } else {
                                                                    liked.put("liked", true);
                                                                    FirebaseFirestore.getInstance().collection(uno).document("saved")
                                                                            .collection("news").document(docs.getId()).update(liked);
                                                                    Toast.makeText(context, "liked.", Toast.LENGTH_SHORT).show();
                                                                }
                                                                db.collection(uno).document("views").get().
                                                                        addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                            @Override
                                                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                                String Category =dataleft.getCategory();
                                                                                Number value = (Number) documentSnapshot.get(Category);
                                                                                Map<String, Object> viewsdata = new HashMap<>();
                                                                                assert value != null;
                                                                                viewsdata.put(Category, value.intValue()+1);
                                                                                db.collection(uno).document("views").set(viewsdata, SetOptions.merge());
                                                                            }});
                                                                newsdata.whereEqualTo("headline", dataleft.getHeadline()).get().
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

                                                            }
                                                        }
                                                    }

                                                }
                                            });


                                }});

                } });


            myViewHolder.save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final CollectionReference database = db.collection(uno).
                            document("saved").collection("news");
                    database.whereEqualTo("headline", dataleft.getHeadline()).get().
                            addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
                            {
                                Map<String, Object> saved = new HashMap<>();
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task)
                                {
                                    if (task.getResult().size()==0) {
                                        myViewHolder.save.setImageDrawable(context.getDrawable(R.drawable.black_star));
                                        db.collection(uno).document("views").get().
                                                addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                        String Category =dataleft.getCategory();
                                                        Number value = (Number) documentSnapshot.get(Category);
                                                        Map<String, Object> viewsdata = new HashMap<>();
                                                        viewsdata.put(Category, value.intValue()+1);
                                                        db.collection(uno).document("views").set(viewsdata, SetOptions.merge());
                                                    }});
                                        saved.put("category", dataleft.getCategory());
                                        saved.put("headline", dataleft.getHeadline());
                                        saved.put("image",dataleft.getImage());
                                        saved.put("date",dataleft.getDate());
                                        saved.put("saved", true);
                                        saved.put("liked",false);
                                        FirebaseFirestore.getInstance().collection(uno).document("saved")
                                                .collection("news").document().set(saved);
                                        Toast.makeText(context, "News saved successfully.", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        for (QueryDocumentSnapshot docs : task.getResult()) {
                                            if ((Boolean)docs.get("saved")) {
                                                myViewHolder.save.setImageDrawable(context.getDrawable(R.drawable.ic_grade_black_24dp));
                                                if ((Boolean)docs.get("liked")) {
                                                    saved.put("saved", false);
                                                    saved.put("image","no image");
                                                    FirebaseFirestore.getInstance().collection(uno).document("saved")
                                                            .collection("news").document(docs.getId()).update(saved);
                                                    Toast.makeText(context, "News was removed from saved successfully."+uno, Toast.LENGTH_SHORT).show();
                                                } else {
                                                    FirebaseFirestore.getInstance().collection(uno).document("saved")
                                                            .collection("news").document(docs.getId()).delete();
                                                    Toast.makeText(context, "News was removed from saved successfully.", Toast.LENGTH_SHORT).show();
                                                }

                                            } else {
                                                myViewHolder.save.setImageDrawable(context.getDrawable(R.drawable.black_star));
                                                db.collection(uno).document("views").get().
                                                        addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                            @Override
                                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                String Category =dataleft.getCategory();
                                                                Number value = (Number) documentSnapshot.get(Category);
                                                                Map<String, Object> viewsdata = new HashMap<>();
                                                                viewsdata.put(Category, value.intValue()+1);
                                                                db.collection(uno).document("views").set(viewsdata, SetOptions.merge());
                                                            }});
                                                if ((Boolean)docs.get("liked")) {
                                                    saved.put("image", dataleft.getImage());
                                                    saved.put("saved", true);
                                                    FirebaseFirestore.getInstance().collection(uno).document("saved")
                                                            .collection("news").document(docs.getId()).update(saved);
                                                    Toast.makeText(context, "News saved successfully.", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    saved.put("image", dataleft.getImage());
                                                    saved.put("saved", true);
                                                    FirebaseFirestore.getInstance().collection(uno).document("saved")
                                                            .collection("news").document(docs.getId()).update(saved);
                                                    Toast.makeText(context, "News saved successfully.", Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        }
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
            ImageButton like,save;
            RelativeLayout block;



            public MyViewHolder(@NonNull final View itemView)
            {
                super(itemView);
                likes = itemView.findViewById(R.id.nlikes);
                like= itemView.findViewById(R.id.like);
                text = itemView.findViewById(R.id.tbtext);
                views = itemView.findViewById(R.id.viewcount);
                image= itemView.findViewById(R.id.timage);
                save =itemView.findViewById(R.id.saved);
                block = itemView.findViewById(R.id.block);
            }
        }


    }
