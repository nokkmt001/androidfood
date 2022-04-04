package com.example.fooddrink.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

public abstract class BaseFragment <T extends ViewBinding> extends Fragment implements View.OnClickListener {
    protected T binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = getViewBinding(inflater, container, savedInstanceState);
        View root = binding.getRoot();
        initView();
        initData();
        return root;
    }

    public abstract T getViewBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    protected abstract void initView();

    protected abstract void initData();
}
