package com.example.fooddrink;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.fooddrink.Model.Category;
import com.example.fooddrink.ViewHolder.MenuViewHolder;
import com.example.fooddrink.database.AppApplication;
import com.example.fooddrink.database.AppPreference;
import com.example.fooddrink.database.PublicData;
import com.example.fooddrink.databinding.ActivityHomeBinding;
import com.example.fooddrink.ui.base.BaseTestActivity;
import com.example.fooddrink.ui.food.FoodDetailFragment;
import com.example.fooddrink.ui.food.FoodMainFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class HomeActivity extends BaseTestActivity<ActivityHomeBinding> implements NavigationView.OnNavigationItemSelectedListener {
    FirebaseDatabase database;
    DatabaseReference category;
    Fragment fmActive, fragment1, fragment2, fragment3;
    FirebaseRecyclerAdapter<Category, MenuViewHolder> adapter;
    public FoodMainFragment homeFragment;
    public FoodDetailFragment galleryFragment;
    FragmentManager fmManager;

    private void setCheckDefault() {
        onNavigationItemSelected(binding.navView.getMenu().findItem(R.id.nav_menu));
        binding.navView.setCheckedItem(R.id.nav_menu);
    }

    @Override
    public ActivityHomeBinding getViewBinding() {
        return ActivityHomeBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        fmManager = getSupportFragmentManager();
        homeFragment = new FoodMainFragment();
        galleryFragment = new FoodDetailFragment("");
        //init firebase
        database = PublicData.database;
        category = database.getReference("Category");
        binding.navView.setNavigationItemSelectedListener(this);
        binding.layoutHeader.imageDrawer.setOnClickListener(this);
        setCheckDefault();
    }

    @Override
    protected void initData() {

    }

    public void setBottomNavigationView(Fragment fmMain, Fragment fragment, String tab) {
        try {
            if (fmMain == null) {
                if (fmActive != null) {
                    fmManager.beginTransaction().hide(fmActive).commit();
                    if (fmActive != fragment) {
                        fmManager.beginTransaction().add(R.id.frame_container, fragment, tab).commit();
                    }
                } else {
                    fmManager.beginTransaction().add(R.id.frame_container, fragment, tab).commit();
                }
            } else {
                fmManager.beginTransaction().hide(fmActive).show(fmMain).commit();
            }
            fmActive = fragment;
        } catch (Exception ignored) {

        }

    }

    @Override
    public void onClick(View viewMain) {
        switch (viewMain.getId()) {
            case R.id.imageDrawer:
                binding.drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        switch (menuItem.getItemId()) {
            case R.id.nav_menu:
                setBottomNavigationView(fragment1, homeFragment, "1");
                fragment1 = homeFragment;
                break;
            case R.id.nav_cart:
                setBottomNavigationView(fragment2, galleryFragment, "2");
                fragment2 = galleryFragment;
                break;
            case R.id.nav_orders:
                break;
            case R.id.nav_log_out:
                alertDialog("ĐĂNG XUẤT", "Bạn có chắc muốn đăng xuất ứng dụng?",
                        "ĐĂNG XUẤT", null, (dialogInterface, i) -> {
                            PublicData.clear();
                            Intent intent = new Intent(this, SignInActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            PublicData.clear();
                            finish();
                        });
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            alertDialog(getString(R.string.title_exits), getString(R.string.title_detail_exits),
                    getString(R.string.dialog_btn_ok), null,
                    (dialogInterface, i) -> {
                        moveTaskToBack(true);
                        finishAffinity();
                        PublicData.clear();
                    });
        }
    }
}