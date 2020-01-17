package com.example.simpletodo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class itemsAdapter extends RecyclerView.Adapter<itemsAdapter.ViewHolder> {

    public interface  onLongClickListener{
        void onLongClickListener(int position);
    }

    public interface  onClickListener{
        void onClickListener(int position);
    }


    List<String> items;
    onLongClickListener longClickListener;
    onClickListener ClickListener;

    //Constructor for the class

    public itemsAdapter(List<String> items, onLongClickListener longClickListener,onClickListener ClickListener){
        this.items=items;
        this.longClickListener=longClickListener;
        this.ClickListener=ClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Use Layout inflator to inflate the view
        //Wrap it inside the view holder and return it
        View todoView=LayoutInflater.from((parent.getContext())).inflate(android.R.layout.simple_expandable_list_item_1,parent,false);
        return new ViewHolder(todoView);
    }

    //responsible for binding data to a particular view holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Grab an item at a position
        String item=items.get(position);
        holder.bind(item);
     //Bind the item at that position
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //Container to provide easy accessto viewlistem of items
    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvItems;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItems=itemView.findViewById((android.R.id.text1));

        }

        //Update the view inside the view holder
        public void bind(String item) {
            tvItems.setText(item);
            tvItems.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClickListener.onClickListener(getAdapterPosition());
                }
            });


            tvItems.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //notify the position that long clikced
                    longClickListener.onLongClickListener(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}
