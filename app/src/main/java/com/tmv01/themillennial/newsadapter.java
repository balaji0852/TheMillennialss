package com.tmv01.themillennial;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirestoreRegistrar;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class newsadapter extends RecyclerView.Adapter<newsadapter.MyViewHolder> {

    Context context;
    ArrayList<firstpagedata> maindata;




    public newsadapter(Context context, ArrayList<firstpagedata> maindata)
    {
        this.context = context;
        this.maindata = maindata ;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.tibres, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        final firstpagedata dataleft=maindata.get(i);
        myViewHolder.text.setText(dataleft.getHeadline());
        Picasso.get().load(dataleft.getImage()).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(myViewHolder.image);
        myViewHolder.views.setText(dataleft.getViews()+" Views");
        myViewHolder.nlikes.setText(dataleft.getPlikes()+ "%Liked");
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        myViewHolder.like.setOnClickListener(new View.OnClickListener() {
            final int[] count = {0};
            @Override
            public void onClick(View view) {
                count[0]++;
                Toast.makeText(context, String.valueOf(count[0]), Toast.LENGTH_SHORT).show();
                final CollectionReference newsdata =  db.collection(dataleft.getDate()).
                        document(dataleft.getCategory()).collection("news");
                final CollectionReference likeddata =db.collection("8151033423");
                db.collection("nusers").document("ucount").get().
                        addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                final Number nusers = (Number) documentSnapshot.get("users");
                                newsdata.whereEqualTo("headline", dataleft.getHeadline()).get().
                                        addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                                        Number templike = (Number) document.get("likes");
                                                        Integer likecount = templike.intValue();
                                                        Map<String, Object> liked = new HashMap<>();
                                                        likecount++;
                                                        Float temp = (Float.intBitsToFloat(likecount) / Float.intBitsToFloat(nusers.intValue()) * 100);
                                                        liked.put("likes",likecount);
                                                        liked.put("plikes",Math.round(temp));
                                                        newsdata.document(document.getId()).set(liked,SetOptions.merge());
                                                        db.collection("8151033423").document("views").get().
                                                                addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                    @Override
                                                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                        String Category =dataleft.getCategory();
                                                                        Number value = (Number) documentSnapshot.get(Category);
                                                                        Map<String, Object> viewsdata = new HashMap<>();
                                                                        viewsdata.put(Category, value.intValue()+1);
                                                                        db.collection("8151033423").document("views").set(viewsdata, SetOptions.merge());
                                                                    }});
                                                    }
                                                }
                                            }
                                        });
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
                                    db.collection("8151033423").document("views").get().
                                            addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                @Override
                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                    String Category =dataleft.getCategory();
                                                    Number value = (Number) documentSnapshot.get(Category);
                                                    Map<String, Object> viewsdata = new HashMap<>();
                                                    viewsdata.put(Category, value.intValue()+1);
                                                    db.collection("8151033423").document("views").set(viewsdata, SetOptions.merge());
                                                }});
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
        myViewHolder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Picasso.get().load(dataleft.getImage()).into(new Target() {
                        @Override public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                            Intent i = new Intent(Intent.ACTION_SEND);
                            i.setType("image/*");
                            i.putExtra(Intent.EXTRA_TEXT, "The Millennial(Beta)News app : News Headline :"+dataleft.getHeadline());
                            i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap));
                            view.getContext().startActivity(Intent.createChooser(i, "Share news"));
                        }
                        @Override
                        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                        }
                        @Override public void onPrepareLoad(Drawable placeHolderDrawable) { }
                    });
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
        return maindata.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView text,nlikes,views;
        ImageButton like,save,share;
        ImageView image;
        RelativeLayout block;


        public MyViewHolder(@NonNull final View itemView)
        {
            super(itemView);
            share=itemView.findViewById(R.id.share);
            nlikes = itemView.findViewById(R.id.nlikes);
            save = itemView.findViewById(R.id.saved);
            views =itemView.findViewById(R.id.viewcount);
            like= itemView.findViewById(R.id.like);
            text = itemView.findViewById(R.id.tibtext);
            image= itemView.findViewById(R.id.tibimage);



        }
    }

    public Uri getLocalBitmapUri(Bitmap bmp) {
        Uri bmpUri = null;
        try {
            File file =  new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Themillennial" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }





}
