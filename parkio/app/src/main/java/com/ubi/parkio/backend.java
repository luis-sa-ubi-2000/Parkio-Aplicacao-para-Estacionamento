package com.ubi.parkio;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class backend {

    public static void setParkingLots(GoogleMap map){
        ArrayList<MarkerOptions> parkingLotsMarkers = getParkingLotsMarker();
        ArrayList<PolylineOptions> parkinfLotsPolylines = getParkingLotsPolyLines();

        for (MarkerOptions marker: parkingLotsMarkers) {
            map.addMarker(marker);
        }

        for (PolylineOptions polyline: parkinfLotsPolylines) {
            map.addPolyline(polyline);
        }

    }

    private static ArrayList<MarkerOptions> getParkingLotsMarker(){
        //TODO: CALL THE DB TO GET MARKERS INFO!
        ArrayList<MarkerOptions> markers = new ArrayList<MarkerOptions>();

        LatLng position = new LatLng(40.270499, -7.502793);
        MarkerOptions marker = new MarkerOptions()
                .position(position)
                .title("Serra Shoping")
                .snippet("Lotation: 72 / 150");

        LatLng position1 = new LatLng(40.27705142155579, -7.508625802936676);
        MarkerOptions marker1 = new MarkerOptions()
                .position(position1)
                .title("Estacionamento UBI - 6ª Fase")
                .snippet("Lotation: 13 / 60");

        markers.add(marker);
        markers.add(marker1);

        return markers;
    }

    private static ArrayList<PolylineOptions> getParkingLotsPolyLines(){
        //TODO: CALL THE DB TO GET POLYLINES INFO!
        ArrayList<PolylineOptions> polylines = new ArrayList<PolylineOptions>();

        PolylineOptions polylineOptions = new PolylineOptions()
                .add(new LatLng(40.270987093830335, -7.503207275608098))
                .add(new LatLng(40.27043330130643, -7.5021583910719585))
                .add(new LatLng(40.270100642913484, -7.502483985610732))
                .add(new LatLng(40.270561891625114, -7.503310414504393))
                .add(new LatLng(40.270987093830335, -7.503207275608098));

        PolylineOptions polylineOptions1 = new PolylineOptions()
                .add(new LatLng(40.27718690388179, -7.508872292066726))
                .add(new LatLng(40.27692346908259, -7.508691931906106))
                .add(new LatLng(40.27680841936814, -7.508117524319478))
                .add(new LatLng(40.27682120897833, -7.50804979854274))
                .add(new LatLng(40.27692096785686, -7.508074608976613))
                .add(new LatLng(40.276908178265664, -7.5081141715602735))
                .add(new LatLng(40.27695882503418, -7.508328748285649))
                .add(new LatLng(40.27700691384995, -7.508320701658447))
                .add(new LatLng(40.277138902128584, -7.508613732995873))
                .add(new LatLng(40.27718690388179, -7.508872292066726));

        polylines.add(polylineOptions);
        polylines.add(polylineOptions1);

        return polylines;
    }
}
