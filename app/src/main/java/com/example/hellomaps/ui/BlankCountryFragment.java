package com.example.hellomaps.ui;

import android.icu.text.CaseMap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hellomaps.R;
import com.example.hellomaps.model.Hoofdstad;
import com.example.hellomaps.model.HoofdstadDAO;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.Tile;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlankCountryFragment extends Fragment {
        //fields
    private MapView mapView;
    private GoogleMap myMap;

        //listeners
    private OnMapReadyCallback onMapReady = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            //field maken van mijn map, om code in andere methoden te kunnen aanspreken
            myMap = googleMap;

            //kaart klaar
            // kaart centreren op een coordinaat.
            LatLng coordBrussel = new LatLng(50.858712, 4.347446);
            //hoe wil ik mijn camera updaten
            CameraUpdate moveToBrussel = CameraUpdateFactory.newLatLngZoom(coordBrussel, 16);

            myMap.animateCamera(moveToBrussel);
            //als je sataliet beeld wilt
            myMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

            //listener toevoegen voor meer info als je op het pinnetje klikt, soort about
            myMap.setOnInfoWindowClickListener(infoWindowClickListener);

            setMarkerAdapter();
            drawMarkers();
            drawLine();
        }
    };
    private GoogleMap.OnInfoWindowClickListener infoWindowClickListener = new GoogleMap.OnInfoWindowClickListener() {
        @Override
        public void onInfoWindowClick(Marker marker) {
            Hoofdstad h = (Hoofdstad) marker.getTag();
            Toast.makeText(getActivity(), h.getInfo(), Toast.LENGTH_SHORT).show();
        }
    };

    //map methods een lijn tekenen om de verschilende pinnen te verbinden
            private void drawLine(){
                //ARGB
                // alpha => doorzichtigheid , 0 =  transparant (eerste twee cijfers
                //RBG => waarde Rood , Groen, Blauw (3de en 4de cijfer)
                //waardes tussen 0 en 255 of in hexadecimaal tussen 00 en FF( de rest)
                myMap.addPolyline(new PolylineOptions()
                        .color(0xff990000)
                        .width(8)
                        .add(new LatLng(51.528308, -0.381789))
                        .add(new LatLng(60.1098678, 24.738504)));

    }


    //map methodes, markeerder zetten op kaart, een pin zetten dus
    // de map res/ rechtermuisknop new image asset en dan kan je u icoontje kieze
         //map methodes, de tekst die boven u pinnetje komt op u kaart
    private void setMarkerAdapter(){
        myMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View cardview = getActivity().getLayoutInflater().inflate(R.layout.marker_card, null, false);

                TextView tvTitle = cardview.findViewById(R.id.tv_card_title);
                TextView tvSnippet = cardview.findViewById(R.id.tv_card_snippet);

                tvTitle.setText(marker.getTitle());
                tvSnippet.setText(marker.getSnippet());

                return cardview;
            }
        });
    }
             private void drawMarkers() {
                    myMap.addMarker(new MarkerOptions()
                        .position(new LatLng(50.858712, 4.347446))
                        .title("Kaaitheater")
                        .snippet("dit is een theater")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_name))
                        .rotation(20));

                    // dit deelt zorgt ervoor dat de steden die je hebt toegevoegd worden gedisplayd, met de info die hebt toegevoegd
                    for (Hoofdstad hoofdstad : HoofdstadDAO.INSTANCE.getHoofdsteden()){
                        Marker m = myMap.addMarker(new MarkerOptions()
                                .position(hoofdstad.getCoordinate()));
                        m.setTitle(hoofdstad.getName());
                        m.setSnippet(hoofdstad.getInfo());
                        m.setTag(hoofdstad);
                    }
    }

    public BlankCountryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootview = inflater.inflate(R.layout.fragment_blank_country, container, false);

        mapView = rootview.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(onMapReady);

        return rootview;
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
