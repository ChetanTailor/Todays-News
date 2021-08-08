package com.example.todaysnews;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.myviewholder>  {

    List<news> newsobject2 ;
    public Context context2;
    MainActivity mainActivity;

    public Adapter(List<news> data, Context c){
       newsobject2 = data;
       context2 = c;
    }



    @Override
    public myviewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View oneitem = layoutInflater.inflate(R.layout.newscard,parent,false);
        return new myviewholder(oneitem);

    }

    @Override
    public void onBindViewHolder(Adapter.myviewholder holder, int position) {
         holder.title.setText(newsobject2.get(position).title);
         holder.discription.setText(newsobject2.get(position).Discription);
         Glide.with(context2).load(newsobject2.get(position).urlimg).into(holder.imageView);

         holder.itemView.setOnClickListener(view -> {
             Intent intent = new Intent(mainActivity,web.class);
             intent.putExtra("URL",newsobject2.get(position).newsurl);
             mainActivity.startActivity(intent);
         });


    }


    @Override
    public int getItemCount() {
        return newsobject2.size();
    }

    public class myviewholder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView title,discription;
        public myviewholder(View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.imageView);
            this.title = itemView.findViewById(R.id.titleTV);
            this.discription = itemView.findViewById(R.id.DisTV);


        }
    }
}
