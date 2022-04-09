package com.example.fooddrink.ui.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.example.fooddrink.Model.Food;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("NotifyDataSetChanged")
public abstract class BaseLMAdapter<T, B extends ViewBinding> extends RecyclerView.Adapter<BindingViewHolder<B>> {
    protected B binding;
    ArrayList<T> listData = new ArrayList<>();
    Context context;
    public List<Food> listChose = new ArrayList<>();

    public abstract void setupViews(B b, T item, int position);

    public abstract B getViewBinding(ViewGroup parent, int viewType);

    public BaseLMAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public BindingViewHolder<B> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BindingViewHolder<B>(getViewBinding(parent, viewType));
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, int position) {
        binding = (B) holder.getBinding();
        setupViews((B) holder.getBinding(), listData.get(position), position);
    }

    @Override
    public int getItemCount() {
        return listData == null ? 0 : listData.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void initData(List<T> data) {
        listData.clear();
        listData.addAll(data);
        notifyDataSetChanged();
    }

    public T getItem(int position) {
        return listData.get(position);
    }

    public void clearData() {
        listData.clear();
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addData(List<T> data) {
        listData.addAll(data);
        notifyDataSetChanged();
    }

    public void remoteItem(int position) {
        listData.remove(position);
        notifyDataSetChanged();
    }

    public List<T> getListAllData() {
       return listData;
    }

    public void add(T item, int position) {
        listData.add(position, item);
        notifyDataSetChanged();
    }

    public interface OnClick {
        void onClick(View view, int position);
    }

    public interface OnLongClick {
        void onLongClick(View view, int position);
    }
}
