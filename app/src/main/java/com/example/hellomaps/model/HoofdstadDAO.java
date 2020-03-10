package com.example.hellomaps.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class HoofdstadDAO {

    public static final HoofdstadDAO INSTANCE = new HoofdstadDAO();
    private ArrayList<Hoofdstad> hoofdsteden;

    private HoofdstadDAO(){

    }

    public ArrayList<Hoofdstad> getHoofdsteden() {
        if(hoofdsteden == null){
            hoofdsteden = new ArrayList<>();
            hoofdsteden.add(new Hoofdstad(new LatLng(51.528308, -0.381789), "Londen", "dubbeldekkers in het rood"));
            hoofdsteden.add(new Hoofdstad(new LatLng(60.1098678, 24.738504), "Helsinki", "perkele vodka"));
            hoofdsteden.add(new Hoofdstad(new LatLng(-33.87365, 151.20689),"Sidney", "it is not the Capitol, cricket, toilet paper"));
        }
        return hoofdsteden;
    }
}
