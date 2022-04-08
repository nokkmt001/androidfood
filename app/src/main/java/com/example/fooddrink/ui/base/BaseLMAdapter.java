package com.example.fooddrink.ui.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseLMAdapter<T, B extends ViewBinding> extends RecyclerView.Adapter<BindingViewHolder<B>> {
    private final int VIEW_TYPE_ITEM = 1;
    private final int VIEW_TYPE_LOAD_MORE = 2;
    protected B binding;
    ArrayList<T> listData = new ArrayList<>();
    private boolean isLoadMoreOrRefresh = false;
    Context context;

    public abstract void setupViews(B b, T item, int position);

    public abstract B getViewBinding(ViewGroup parent, int viewType);

    public BaseLMAdapter(Context context) {
        this.context = context;
    }

    @Override
    public BindingViewHolder<B> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BindingViewHolder<B>(getViewBinding(parent, viewType));
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_ITEM) {
            binding = (B) holder.getBinding();
            setupViews((B) holder.getBinding(), listData.get(position), position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == listData.size() - 1 &&
                isLoadMoreOrRefresh) ? VIEW_TYPE_LOAD_MORE : VIEW_TYPE_ITEM;
    }

    public void addLoadingFooter(T o) {
        isLoadMoreOrRefresh = true;
        listData.add(o);
        notifyItemInserted(listData.size() - 1);
    }

    public void removeLoadingFooter() {
        isLoadMoreOrRefresh = false;
        int position = listData.size() - 1;
        if (position == -1) return;
        T o = getItem(position);
        if (o != null) {
            listData.remove(position);
            notifyItemRemoved(position);
        }
    }

    @Override
    public int getItemCount() {
        return listData == null ? 0 : listData.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void initData(List<T> data) {
        isLoadMoreOrRefresh = false;
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
        isLoadMoreOrRefresh = false;
        listData.addAll(data);
        notifyDataSetChanged();
    }

    public void remoteItem(int position) {
        listData.remove(position);
        notifyDataSetChanged();
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

    public interface OnButtonClick {
        void onButtonClick(View view, int position);
    }

    public interface OnSwitchChangeListener {
        void onSwitchChange(View view, int position, boolean isChecked);
    }

    public <t extends View> t bind(View view, int id) {
        return view.findViewById(id);
    }
}
