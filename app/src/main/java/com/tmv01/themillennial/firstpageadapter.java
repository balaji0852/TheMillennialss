package com.tmv01.themillennial;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.firebase.firestore.FirebaseFirestore;
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
        myViewHolder.views.setText(  ":"+dataleft.getViews()+"views");
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
                data.putExtra("textualdata",dataleft.getTextualdata());
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
            TextView text,views;
            ImageView image;
            RelativeLayout block;


            public MyViewHolder(@NonNull final View itemView)
            {
                super(itemView);
                text = itemView.findViewById(R.id.tbtext);
                views = itemView.findViewById(R.id.viewcount);
                image= itemView.findViewById(R.id.timage);
                block = itemView.findViewById(R.id.block);
            }
        }


    }
