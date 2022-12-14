package com.ubi.parkio;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.StyleSpan;

public class MapsFragment extends Fragment {

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            // Set the map coordinates.
            LatLng SerraShoping = new LatLng(40.270499, -7.502793);
            // Set the map type to Hybrid.
            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            // Add a marker on the map coordinates.
            googleMap.addMarker(new MarkerOptions()
                    .position(SerraShoping)
                    .title("Serra Shoping")
                    .snippet("Lotation: 72 / 150"));
            // Move the camera to the map coordinates and zoom in closer.
            googleMap.moveCamera(CameraUpdateFactory.zoomTo(18));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(SerraShoping));
            // Display traffic.
            googleMap.setTrafficEnabled(true);

            // Instantiates a new Polyline object and adds points to define a rectangle

            PolylineOptions polylineOptions = new PolylineOptions()
                    .add(new LatLng(40.270987093830335, -7.503207275608098))
                    .add(new LatLng(40.27043330130643, -7.5021583910719585))
                    .add(new LatLng(40.270100642913484, -7.502483985610732))
                    .add(new LatLng(40.270561891625114, -7.503310414504393))
                    .add(new LatLng(40.270987093830335, -7.503207275608098));

            // Get back the mutable Polyline
            Polyline polyline = googleMap.addPolyline(polylineOptions);
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}