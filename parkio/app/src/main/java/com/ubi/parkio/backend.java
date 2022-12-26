package com.ubi.parkio;

import android.graphics.Color;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class backend {

    private static final String TAG = MapsFragment.class.getSimpleName();

    public static Integer empty_stroke = 0xFF00FF00;
    public static Integer empty_fill = 0x7F00FF00;

    public static Integer medium_stroke = 0xFFFF7F00;
    public static Integer medium_fill = 0x7FFF7F00;

    public static Integer full_stroke = 0xFFFF0000;
    public static Integer full_fill = 0x7FFF0000;

    static FirebaseFirestore firestore;

    public static void setParkingLots(GoogleMap map){
        ArrayList<MarkerOptions> parkingLotsMarkers = getParkingLotsMarker();
        ArrayList<PolygonOptions> parkinfLotsPolys = getParkingLotsPolys();

        for (MarkerOptions marker: parkingLotsMarkers) {
            map.addMarker(marker);
        }

        for (PolygonOptions poly: parkinfLotsPolys) {
            map.addPolygon(poly);
        }

    }

    private static ArrayList<MarkerOptions> getParkingLotsMarker(){
        firestore = FirebaseFirestore.getInstance();
        ArrayList<MarkerOptions> markers = new ArrayList<MarkerOptions>();
        System.out.println("!!!!!!!!!!!!!!!!");
        firestore.collection("Parkinglot")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                try {
                                    MarkerOptions marker = new MarkerOptions()
                                            .position(new LatLng(Double.parseDouble(document.get("Latitude").toString()),
                                                    Double.parseDouble(document.get("Latitude").toString())))
                                            .title(document.get("Title").toString())
                                            .snippet("Lotation: "+document.get("OccupiedSpots").toString()+" / "+document.get("MaxLotation").toString());
                                    System.out.println("??????????");
                                    System.out.println(document.get("Latitude").toString());
                                    markers.add(marker);
                                } catch (Exception e){
                                    Log.e("Error getting markers", e.getMessage());
                                }
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        System.out.println(markers);
        return markers;
    }

    private static ArrayList<PolygonOptions> getParkingLotsPolys(){
        //TODO: CALL THE DB TO GET POLYLINES INFO!
        ArrayList<PolygonOptions> polys = new ArrayList<PolygonOptions>();

        // Check the parkinglot lotation
        Integer currlotation = 63;
        Integer maxlotation = 150;

        Integer stroke_color = 0;
        Integer fill_color = 0;
        if (currlotation < maxlotation/3){
            stroke_color = empty_stroke;
            fill_color = empty_fill;
        }
        if (currlotation >= maxlotation/3 && currlotation <= maxlotation){
            stroke_color = medium_stroke;
            fill_color = medium_fill;
        }
        if (currlotation > 2 * maxlotation/3){
            stroke_color = full_stroke;
            fill_color = full_fill;
        }

                PolygonOptions poly = new PolygonOptions()
                .add(new LatLng(40.270987093830335, -7.503207275608098))
                .add(new LatLng(40.27043330130643, -7.5021583910719585))
                .add(new LatLng(40.270100642913484, -7.502483985610732))
                .add(new LatLng(40.270561891625114, -7.503310414504393))
                .add(new LatLng(40.270987093830335, -7.503207275608098))
                .strokeColor(stroke_color)
                .fillColor(fill_color);

        PolygonOptions poly1 = new PolygonOptions()
                .add(new LatLng(40.27718690388179, -7.508872292066726))
                .add(new LatLng(40.27692346908259, -7.508691931906106))
                .add(new LatLng(40.27680841936814, -7.508117524319478))
                .add(new LatLng(40.27682120897833, -7.50804979854274))
                .add(new LatLng(40.27692096785686, -7.508074608976613))
                .add(new LatLng(40.276908178265664, -7.5081141715602735))
                .add(new LatLng(40.27695882503418, -7.508328748285649))
                .add(new LatLng(40.27700691384995, -7.508320701658447))
                .add(new LatLng(40.277138902128584, -7.508613732995873))
                .add(new LatLng(40.27718690388179, -7.508872292066726))
                .strokeColor(empty_stroke)
                .fillColor(empty_fill);

        polys.add(poly);
        polys.add(poly1);

        return polys;
    }
}
