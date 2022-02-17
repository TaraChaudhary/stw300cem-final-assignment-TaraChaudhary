package com.example.traffic_rule_and_sign_quiz_app;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.traffic_rule_and_sign_quiz_app.Model.Place;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Button button;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        button=findViewById(R.id.back);
        textView=findViewById(R.id.toolbarhead);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MapActivity.this,DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MapActivity.this,DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        CameraUpdate zoom;
        zoom=CameraUpdateFactory.zoomTo(6);

        loadLocation();

        // Add a marker in Sydney and move the camera
//        LatLng softwarica = new LatLng(27.706451, 85.330044);
//        LatLng bigmovies = new LatLng(27.709904, 85.326714);
//        mMap.addMarker(new MarkerOptions().position(softwarica).title("Marker in Softwarica college"));
//        mMap.addMarker(new MarkerOptions().position(bigmovies).title("Big Movies"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(softwarica));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(bigmovies));
//        mMap.animateCamera(zoom);
    }

    public void loadLocation()
    {
        List<Place>placeList=new ArrayList<>();
        placeList.add(new Place(27.729571, 85.346791,"Sukedhara Transport Department"));
        placeList.add(new Place(27.666202, 85.311382,"Department of Transport Management"));
        placeList.add(new Place(27.718631, 85.285453,"Gandaki Transport Management"));
        // placeList.add(new Place(0,0,"Nepal"));

        CameraUpdate center,zoom;

        for (Place place:placeList){
            zoom=CameraUpdateFactory.zoomTo(11);
            center=CameraUpdateFactory.newLatLng(new LatLng(place.getLatitude(),place.getLongitude()));
            mMap.addMarker(new MarkerOptions().position(new LatLng(place.getLatitude(),place.getLongitude())).title(place.getName()));

            mMap.moveCamera(center);
            mMap.animateCamera(zoom);
            mMap.getUiSettings().setZoomControlsEnabled(true);

        }
    }
}
