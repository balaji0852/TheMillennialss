package com.tmv01.themillennial;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

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
        firstpagedata dataleft=firstpageleftdata.get(i);
        myViewHolder.text.setText(dataleft.getHeadline());
        Picasso.get().load(dataleft.getImage()).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(myViewHolder.image);

    }

        @Override
        public int getItemCount()
    {
        return firstpageleftdata.size();
    }

        public class MyViewHolder extends RecyclerView.ViewHolder
        {
            TextView text;
            ImageView image;


            public MyViewHolder(@NonNull final View itemView)
            {
                super(itemView);
                text = itemView.findViewById(R.id.tbtext);
                image= itemView.findViewById(R.id.timage);
            }
        }


    }
