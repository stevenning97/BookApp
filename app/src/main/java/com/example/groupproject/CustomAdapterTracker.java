package com.example.groupproject;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapterTracker extends RecyclerView.Adapter<CustomAdapterTracker.MyViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList track_id, book_name, page_num, pulish_date;

    CustomAdapterTracker(Activity activity, Context context, ArrayList track_id, ArrayList book_name, ArrayList page_num,
                         ArrayList publish_date){
        this.activity = activity;
        this.context = context;
        this.track_id = track_id;
        this.book_name = book_name;
        this.page_num = page_num;
        this.pulish_date = publish_date;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.book_id_txt.setText(String.valueOf(track_id.get(position)));
        holder.book_title_txt.setText(String.valueOf(book_name.get(position)));
        holder.page_num_txt.setText(String.valueOf(page_num.get(position)));
        holder.public_time_txt.setText(String.valueOf(pulish_date.get(position)));
        //Recyclerview onClickListener
//        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, UpdateActivity.class);
//                intent.putExtra("id", String.valueOf(book_id.get(position)));
//                intent.putExtra("title", String.valueOf(book_title.get(position)));
//                intent.putExtra("author", String.valueOf(book_author.get(position)));
//                intent.putExtra("pages", String.valueOf(book_pages.get(position)));
//                activity.startActivityForResult(intent, 1);
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return track_id.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView book_id_txt, book_title_txt, page_num_txt, public_time_txt;
        LinearLayout mainLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            book_id_txt = itemView.findViewById(R.id.book_id_txt);
            book_title_txt = itemView.findViewById(R.id.book_title_txt);
            page_num_txt = itemView.findViewById(R.id.page_num_txt);
            public_time_txt = itemView.findViewById(R.id.public_time_txt);
//            mainLayout = itemView.findViewById(R.id.mainLayout);
//            //Animate Recyclerview
//            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
//            mainLayout.setAnimation(translate_anim);
        }

    }

}