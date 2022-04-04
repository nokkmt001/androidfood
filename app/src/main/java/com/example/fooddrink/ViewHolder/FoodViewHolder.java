package com.example.fooddrink.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooddrink.Interface.InternClickListener;
import com.example.fooddrink.R;

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView food_name;
    public ImageView food_image;

    private InternClickListener internClickListener;

    public void setInternClickListener(InternClickListener internClickListener) {
        this.internClickListener = internClickListener;
    }

    public FoodViewHolder(@NonNull View itemView) {
        super(itemView);

        food_name = (TextView)itemView.findViewById(R.id.food_name);
        food_image   = (ImageView)itemView.findViewById(R.id.food_image);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        internClickListener.onClick(view,getAdapterPosition(),false);

    }
}
