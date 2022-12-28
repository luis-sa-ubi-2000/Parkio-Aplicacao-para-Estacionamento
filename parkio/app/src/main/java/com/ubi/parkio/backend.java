package com.ubi.parkio;

import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class backend {

    private static final String TAG = MapsFragment.class.getSimpleName();

    public static ArrayList<Map<String, String>> capacity = new ArrayList<Map<String, String>>();

    public static Integer empty_stroke = 0xFF00FF00;
    public static Integer empty_fill = 0x7F00FF00;

    public static Integer medium_stroke = 0xFFFF7F00;
    public static Integer medium_fill = 0x7FFF7F00;

    public static Integer full_stroke = 0xFFFF0000;
    public static Integer full_fill = 0x7FFF0000;

    static FirebaseFirestore firestore;

    public static void setParkingLots(GoogleMap map){
        setParkingLotsMarkers(map);
        setParkingLotsPolys(map);
    }

    private static void setParkingLotsMarkers(GoogleMap map){
        firestore = FirebaseFirestore.getInstance();

        firestore.collection("Parkinglot")
                .orderBy("Id")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                try {
                                    Map<String, String> capacity = new HashMap<String, String>();
                                    capacity.put("OccupiedSpots", document.get("OccupiedSpots").toString());
                                    capacity.put("MaxLotation", document.get("MaxLotation").toString());

                                    backend.addCapacity(capacity);

                                    MarkerOptions marker = new MarkerOptions()
                                            .position(new LatLng(Double.parseDouble(document.get("Latitude").toString()),
                                                    Double.parseDouble(document.get("Longitude").toString())))
                                            .title(document.get("Title").toString())
                                            .snippet("Lotation: "+capacity.get("OccupiedSpots")+" / "+capacity.get("MaxLotation"));

                                    map.addMarker(marker);
                                } catch (Exception e){
                                    Log.e("Error getting markers", e.getMessage());
                                }
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private static void setParkingLotsPolys(GoogleMap map){
        firestore = FirebaseFirestore.getInstance();
        ArrayList<PolygonOptions> polys = new ArrayList<PolygonOptions>();


        for (int i = 0; i < backend.getCapacity().size(); i++) {
            System.out.println(i);
            System.out.println(backend.getCapacity().get(i));

            // Check the parkinglot lotation
            Integer currlotation = Integer.valueOf(backend.getCapacity().get(i).get("OccupiedSpots"));
            Integer maxlotation = Integer.valueOf(backend.getCapacity().get(i).get("MaxLotation"));

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

            Integer finalStroke_color = stroke_color;
            Integer finalFill_color = fill_color;

            firestore.collection("Parkinglot_Area")
                    .whereEqualTo("Parkinglot_Id", i)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                try {
                                    PolygonOptions poly = new PolygonOptions();
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        poly.add(new LatLng(Float.valueOf(document.get("Latitude").toString()), Float.valueOf(document.get("Longitude").toString())));
                                        System.out.println(document);
                                    }
                                    poly.strokeColor(finalStroke_color);
                                    poly.fillColor(finalFill_color);

                                    System.out.println(poly.getPoints());

                                    map.addPolygon(poly);
                                } catch (Exception e){
                                    Log.e("Error adding polys", e.getMessage());
                                }

                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });

        }

        backend.clearCapacity();
    }

    public static void updateParkinglots(Integer seconds, GoogleMap map) {
        firestore = FirebaseFirestore.getInstance();

        firestore.collection("Parkinglot")
                .orderBy("Id")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }

                        List<String> cities = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : value) {
                            if (doc.get("Title") != null) {
                                cities.add(doc.getString("Title"));
                            }
                        }
                        Log.d(TAG, "Current cites in CA: " + cities);
                    }
                });

    }

    public static ArrayList<Map<String, String>> getCapacity() {
        return capacity;
    }

    public static void addCapacity(Map<String, String> capacity) {
        backend.capacity.add(capacity);
    }

    public static void clearCapacity(){capacity.clear();}
}
