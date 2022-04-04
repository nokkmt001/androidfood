package com.example.fooddrink;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooddrink.Common.Common;
import com.example.fooddrink.Model.Category;
import com.example.fooddrink.ViewHolder.MenuViewHolder;
import com.example.fooddrink.ViewHolder.ui.gallery.GalleryFragment;
import com.example.fooddrink.ViewHolder.ui.home.HomeFragment;
import com.example.fooddrink.databinding.ActivityHomeBinding;
import com.example.fooddrink.ui.base.BaseTestActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

//import android.widget.Toolbar;

public class Home extends BaseTestActivity<ActivityHomeBinding> {

    FirebaseDatabase database;
    DatabaseReference category;
    TextView txtFullName;

    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Category, MenuViewHolder> adapter;

    private AppBarConfiguration mAppBarConfiguration;
    public HomeFragment homeFragment;
    public GalleryFragment galleryFragment;
    ActivityHomeBinding binding;


    public void gg() {
        homeFragment = new HomeFragment();
        galleryFragment = new GalleryFragment();
        setContentView(R.layout.activity_home);

        setSupportActionBar(binding.appBarHome.toolbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");

        //init firebase
        database = FirebaseDatabase.getInstance();
        category = database.getReference("Category");


        binding.appBarHome.fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        //set name for user
        binding.navView.setNavigationItemSelectedListener(item -> {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
            Fragment fragmentClass = null;

            switch (item.getItemId()) {
                case R.id.nav_home:
                    fragmentClass = homeFragment;
                    break;
                case R.id.nav_gallery:
                    replaceFragment(R.id.nav_host_fragment_content_home, galleryFragment,
                            "gg","hometwo");
                    fragmentClass = galleryFragment;
                    break;
                default:
                    break;

            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            assert fragmentClass != null;
            fragmentManager.beginTransaction().replace(R.id.nav_host_fragment_content_home, fragmentClass).commit();
//            setTitle(menuItem.getTitle());
            return true;
        });
        View headerView = navigationView.getHeaderView(0);
        txtFullName = (TextView) headerView.findViewById(R.id.txtFullName);
        txtFullName.setText(Common.currentUser.getName());

        //load menu
        recycler_menu = (RecyclerView) findViewById(R.id.recycler_menu);
        recycler_menu.setHasFixedSize(true);
        recycler_menu.setLayoutManager(new GridLayoutManager(this, 2));
//        binding.navView.setO(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                switch (view.getId()){
//                    case R.id.
//                }
//            }
//        });
        LoadMenu();

    }

    @Override
    public ActivityHomeBinding getViewBinding() {
        return ActivityHomeBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {

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
                    Intent foodList = new Intent(Home.this, FoodList.class);
                    //Because CategoryId is key, so we just get key of this item
                    foodList.putExtra("CategoryId", adapter.getRef(position).getKey());
                    startActivity(foodList);
                });
            }
        };
        recycler_menu.setAdapter(adapter);
    }

    //này là các món ăn
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
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

    @Override
    public void onClick(View view) {

    }
}