package com.example.fooddrink.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooddrink.Interface.InternClickListener;
import com.example.fooddrink.R;

public class HistoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtMenuName;
    public ImageView imageView;

    private InternClickListener internClickListener;

    public HistoryViewHolder(@NonNull View itemView) {
        super(itemView);

        txtMenuName = (TextView)itemView.findViewById(R.id.menu_name);
        imageView   = (ImageView)itemView.findViewById(R.id.menu_image);

        itemView.setOnClickListener(this);
    }

    public InternClickListener getInternClickListener() {
        return internClickListener;
    }

    public void setInternClickListener(InternClickListener internClickListener) {
        this.internClickListener = internClickListener;
    }

    @Override
    public void onClick(View view) {

        internClickListener.onClick(view,getAdapterPosition(),false);
    }
}