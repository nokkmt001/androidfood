package com.example.fooddrink.ui.address;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.example.fooddrink.Model.User;
import com.example.fooddrink.R;
import com.example.fooddrink.database.AppPreference;
import com.example.fooddrink.databinding.ActivityPlaceBinding;
import com.example.fooddrink.ui.base.BaseTestActivity;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class PlaceActivity extends BaseTestActivity<ActivityPlaceBinding> implements OnMapReadyCallback {
    private GoogleMap mMap;
    User info;
    @Override
    public ActivityPlaceBinding getViewBinding() {
        return ActivityPlaceBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView() {
        info = AppPreference.getUserMain();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        binding.inputSearch.setText(info.getAddress());
        binding.inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    binding.imageClear.setVisibility(View.GONE);
                } else {
                    binding.imageClear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.layoutHeader.imageBack.setOnClickListener(v -> finish());
        binding.layoutHeader.textTitle.setText("Sửa địa chỉ");

        binding.imageClear.setOnClickListener(v -> binding.inputSearch.setText(""));

        binding.inputSearch.setOnClickListener(v -> showConfirm());

        binding.layoutHeader.textOK.setOnClickListener(v -> {
            if (TextUtils.isEmpty(binding.inputSearch.getText())) {
                showToast("Bạn chưa nhập địa chỉ");
            } else {
                Intent intent = new Intent();
                intent.putExtra("TITLE", binding.inputSearch.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {

    }

    private void checkLocation(String address) {
        LatLng locationMain = getLocationFromAddress(address);
        MarkerOptions markerOptions = new MarkerOptions();
        if (locationMain == null) return;
        markerOptions.position(locationMain);
        markerOptions.title(address);
        mMap.clear();
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                locationMain, 16f);
        mMap.animateCamera(location);
        mMap.addMarker(markerOptions);
    }

    private void checkLocationByLatLng(LatLng latLng) {
        mMap.clear();
        CameraUpdate location1 = CameraUpdateFactory.newLatLngZoom(
                latLng, 16f);
        mMap.animateCamera(location1);
        MarkerOptions markerOptions1 = new MarkerOptions();
        markerOptions1.position(latLng);
        String gg = getAddress(latLng);
        binding.inputSearch.setText(gg);
        markerOptions1.title(gg);
        mMap.addMarker(markerOptions1);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        String address = info.getAddress();
        LatLng locationMain = getLocationFromAddress(address);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.setOnMapClickListener(this::checkLocationByLatLng);
        mMap.setOnMarkerClickListener(marker -> {
            marker.showInfoWindow();
            LatLng latLng = marker.getPosition();
            binding.inputSearch.setText(getAddress(latLng));
            return true;

        });

        MarkerOptions markerOptions = new MarkerOptions();
        if (locationMain == null) return;
        markerOptions.position(locationMain);

        markerOptions.title(address);
        mMap.clear();
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                locationMain, 16f);
        mMap.animateCamera(location);
        mMap.addMarker(markerOptions);
        Log.d("status", "success");


    }

    public LatLng getLocationFromAddress(String strAddress) {
        Geocoder coder = new Geocoder(this);
        List<Address> address;
        LatLng p1 = null;
        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = null;
            if (address.size() > 0) {
                location = address.get(0);
            }
            if (location == null) return null;
            p1 = new LatLng(location.getLatitude(), location.getLongitude());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return p1;
    }

    private void showConfirm() {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_input_address, null);
        EditText editText = view.findViewById(R.id.inputAddress);
        editText.setHint("Nhận địa chỉ");
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false)
                .setTitle("Địa chỉ")
                .setView(view)
                .setPositiveButton("Xác Nhận", (dialog, id) -> {
                    String gg = editText.getText().toString();
                    binding.inputSearch.setText(gg);
                    checkLocation(gg);
                })
                .setNegativeButton("HỦY", (dialog, id) -> dialog.cancel());
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private String getAddress(LatLng latLng) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            if (addresses.size() == 0) {
                return "No Address Found";
            }
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();
            return address;
        } catch (IOException e) {
            e.printStackTrace();
            return "No Address Found";
        }
    }

}
