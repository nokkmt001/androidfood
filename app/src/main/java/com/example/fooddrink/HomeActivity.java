package com.example.fooddrink;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.example.fooddrink.Model.Category;
import com.example.fooddrink.ViewHolder.MenuViewHolder;
import com.example.fooddrink.ViewHolder.ui.home.HomeFragment;
import com.example.fooddrink.databinding.ActivityHomeBinding;
import com.example.fooddrink.ui.base.BaseTestActivity;
import com.example.fooddrink.ui.food.FoodDetailFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class HomeActivity extends BaseTestActivity<ActivityHomeBinding> implements NavigationView.OnNavigationItemSelectedListener {
    FirebaseDatabase database;
    DatabaseReference category;
    Fragment fmActive, fragment1, fragment2,fragment3;
    FirebaseRecyclerAdapter<Category, MenuViewHolder> adapter;
    public HomeFragment homeFragment;
    public FoodDetailFragment galleryFragment;
    FragmentManager fmManager;

    public void gg() {
        fmManager = getSupportFragmentManager();

        homeFragment = new HomeFragment();
        galleryFragment = new FoodDetailFragment("");

        //init firebase
        database = FirebaseDatabase.getInstance();
        category = database.getReference("Category");

        binding.navView.setNavigationItemSelectedListener(this);
        binding.layoutHeader.imageDrawer.setOnClickListener(this);
        LoadMenu();
        setCheckDefault();
    }

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
        gg();
    }

    @Override
    protected void initData() {

    }

    private void LoadMenu() {
        adapter = new FirebaseRecyclerAdapter<Category,
                MenuViewHolder>(Category.class, R.layout.menu_item, MenuViewHolder.class, category) {
            @Override
            protected void populateViewHolder(MenuViewHolder menuViewHolder, Category category, int i) {
                menuViewHolder.txtMenuName.setText(category.getName());
                Picasso.get().load(category.getImage()).into(menuViewHolder.imageView);
                //Picasso.get().load().into(menuViewHolder.imageView);
                Category clickItem = category;
                menuViewHolder.setInternClickListener((view, position, isLongCick) -> {
                    //get categoryId and send to new Activity
                    Intent foodList = new Intent(HomeActivity.this, FoodList.class);
                    //Because CategoryId is key, so we just get key of this item
                    foodList.putExtra("CategoryId", adapter.getRef(position).getKey());
                    startActivity(foodList);
                });
            }
        };
//        recycler_menu.setAdapter(adapter);
    }

    protected void addFragment(@IdRes int containerViewId,
                               @NonNull Fragment fragment,
                               @NonNull String fragmentTag) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(containerViewId, fragment, fragmentTag)
                .disallowAddToBackStack()
                .commit();
    }

    protected void replaceFragment(@IdRes int containerViewId,
                                   @NonNull Fragment fragment,
                                   @NonNull String fragmentTag,
                                   @Nullable String backStackStateName) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerViewId, fragment, fragmentTag)
                .addToBackStack(backStackStateName)
                .commit();
    }

    public void setBottomNavigationView(Fragment fmMain, Fragment fragment, String tab) {
        try {
            if (fmMain == null) {
                fmMain = fragment;
                if (fmActive != null) {
                    fmManager.beginTransaction().hide(fmActive).commit();
                    if (fmActive != fragment) {
//                        fmManager.beginTransaction().add(R.id.frame_container, fragment, tab).hide(fmActive).commit();
                        fmManager.beginTransaction().add(R.id.frame_container, fragment, tab).commit();
                    }
                } else {
                    fmManager.beginTransaction().add(R.id.frame_container, fragment, tab).commit();
                }
            } else {
            fmManager.beginTransaction().hide(fmActive).show(fmMain).commit();
                //hide(fmActive).show(fmMain)
                fmMain = fragment;
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        switch (menuItem.getItemId()) {
            case R.id.nav_menu:
                setBottomNavigationView(fragment1, homeFragment, "1");
//                fragment = homeFragment;
                fragment1 = homeFragment;
                break;
            case R.id.nav_cart:
                setBottomNavigationView(fragment2, galleryFragment, "2");
                fragment1 = galleryFragment;
                break;
            case R.id.nav_orders:
//                Intent intent = new Intent(MainActivity.this, SendMailMultiServiceReportActivity.class);
//                startActivity(intent);
                break;
            case R.id.nav_log_out:
//                fragmentClass = PhieuYeuCauListFragment.class;
                break;
            default:
//                fragment = homeFragment;
                break;
        }
        return true;
    }
}