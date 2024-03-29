package com.tmv01.themillennial;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class cyclebottom extends RecyclerView.Adapter<cyclebottom.MyViewHolder> {

    Context context;
    ArrayList<firstpagedata> firstpagebottomdata;




    public cyclebottom(Context context, ArrayList<firstpagedata> firstpagebottomdata)
    {
        this.context = context;
        this.firstpagebottomdata = firstpagebottomdata ;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.cyclebottom, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        final firstpagedata dataLeft=firstpagebottomdata.get(i);
        myViewHolder.text.setText(dataLeft.getHeadline());
        myViewHolder.viewCount.setText(dataLeft.getViews()+" views");
//        Picasso.get().load(dataLeft.getImage()).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(myViewHolder.image);

//        myViewHolder.image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v ) {
//                Intent data = new Intent(v.getContext(), news.class);
//                data.putExtra("date",dataLeft.getDate());
//                data.putExtra("category",dataLeft.getCategory());
//                data.putExtra("headline",dataLeft.getHeadline());
//                data.putExtra("image",dataLeft.getImage());
//                data.putExtra("textualdata",dataLeft.getTextualdata());
//                data.putExtra("savednews","false");
//                data.putExtra("likes",dataLeft.getLikes());
//                data.putExtra("title","The Millennial");
//                Integer views  = dataLeft.getViews()+1;
//                data.putExtra("views", views);
//                v.getContext().startActivity(data);
//            }
//        });
        myViewHolder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v ) {
                Intent data = new Intent(v.getContext(), news.class);
                data.putExtra("date",dataLeft.getDate());
                data.putExtra("category",dataLeft.getCategory());
                data.putExtra("headline",dataLeft.getHeadline());
                data.putExtra("savednews","false");
                data.putExtra("image",dataLeft.getImage());
                data.putExtra("textualdata",dataLeft.getTextualdata());
                data.putExtra("likes",dataLeft.getLikes());
                data.putExtra("title","The Millennial");
                Integer views  = dataLeft.getViews()+1;
                data.putExtra("views", views);
                v.getContext().startActivity(data);
            }
        });


    }

    @Override
    public int getItemCount()
    {
        return firstpagebottomdata.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView text,viewCount;

        public MyViewHolder(@NonNull final View itemView)
        {
            super(itemView);

            text = itemView.findViewById(R.id.tbtext);
            viewCount = itemView.findViewById(R.id.viewcount);




        }
    }


}
