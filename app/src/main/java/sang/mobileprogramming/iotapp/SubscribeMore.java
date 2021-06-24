package sang.mobileprogramming.iotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SubscribeMore extends AppCompatActivity implements OnMapReadyCallback {
    static boolean sub =  true;
    private GoogleMap mMap;
    SupportMapFragment mapFragment;
    SearchView searchView;
    List<LatLng> latLngList = new ArrayList<LatLng>();

    String place = null;
    String[] locationList = new String[] {
            "Bách Khoa Hà Nội",
            "Bạch Mai",
            "Minh Khai"
    };
    int locationListLength = locationList.length;

    public float getDistance(LatLng my_latlong, LatLng frnd_latlong) {
        Location l1 = new Location("One");
        l1.setLatitude(my_latlong.latitude);
        l1.setLongitude(my_latlong.longitude);

        Location l2 = new Location("Two");
        l2.setLatitude(frnd_latlong.latitude);
        l2.setLongitude(frnd_latlong.longitude);

        return l1.distanceTo(l2);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subscribe_more);
        searchView = findViewById(R.id.search_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView.getQuery().toString();
                if(location != null || !location.equals("")){
                    List<Address> addressList = null;
                    Geocoder geocoder = new Geocoder((SubscribeMore.this));
                    try{
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));

                    float minDistance = getDistance(latLng,latLngList.get(0));
                    int iDistance = 0;

                    for(int i = 1; i < locationListLength ; i++) {
                        if(minDistance > getDistance(latLng,latLngList.get(i))){
                            minDistance = getDistance(latLng,latLngList.get(i));
                            iDistance = i;
                        }
                    }
                    TextView textView = findViewById(R.id.nearest_place);
                    if(minDistance < 1000) {
                        String text = "The place nearest this location is " + locationList[iDistance] + ", you can subscribe it";
                        textView.setText(text);
                        TextView textView1 = findViewById(R.id.subscribe);
                        place = locationList[iDistance];
                        if(SubscribedList.subcribedList.contains(place)){
                            textView1.setText("Unsubscribe");
                        }
                        else textView1.setText("Subscribe");
                    }
                    else {
                        String text = "Sorry! There are no places near the location you've chosen.";
                        textView.setText(text);
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        for(int i = 0; i < locationListLength ; i++){
            List<Address> addressList = null;
            Geocoder geocoder = new Geocoder((SubscribeMore.this));
            try {
                addressList = geocoder.getFromLocationName(locationList[i], 1);

            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            latLngList.add(latLng);
            mMap.addMarker(new MarkerOptions().position(latLng).title(locationList[i]));
        }
        // Add a marker in Sydney and move the camera
        LatLng thanhnhan = new LatLng(21.002931, 105.857719);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(thanhnhan));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(thanhnhan, 15));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                //mMap.addMarker(new MarkerOptions().position(latLng).title("Your Location"));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));

                float minDistance = getDistance(latLng,latLngList.get(0));
                int iDistance = 0;

                for(int i = 1; i < locationListLength ; i++) {
                    if(minDistance > getDistance(latLng,latLngList.get(i))){
                        minDistance = getDistance(latLng,latLngList.get(i));
                        iDistance = i;
                    }
                }
                TextView textView = findViewById(R.id.nearest_place);
                if(minDistance < 1000) {
                    String text = "The place nearest this location is " + locationList[iDistance] + ", you can subscribe it";
                    textView.setText(text);
                    TextView textView1 = findViewById(R.id.subscribe);
                    place = locationList[iDistance];
                    if(SubscribedList.subcribedList.contains(place)){
                        textView1.setText("Unsubscribe");;
                    }
                    else textView1.setText("Subscribe");
                }
                else {
                    String text = "Sorry! There are no places near the location you've chosen.";
                    textView.setText(text);
                }
            }
        });
    }

    public void subscribeClick(View v)
    {
        Toast t = null;
        TextView textView = findViewById(R.id.subscribe);
        int len = SubscribedList.subcribedList.size();
        if(place != null && SubscribedList.subcribedList.contains(place)){
            sub = true;
            textView.setText("Subscribe");
            t = Toast.makeText(this, "Unsubscribed", Toast.LENGTH_SHORT);
            List<String> tempList = new ArrayList<String>();
            for(int i = 0; i < len; i++){
                String location = SubscribedList.subcribedList.get(i);
                if(place != location){
                    tempList.add(location);
                }
            }
            len--;
            SubscribedList.subcribedList.clear();
            for(int i = 0; i < len; i++){
                SubscribedList.subcribedList.add(tempList.get(i));
            }
        }
        else if(place != null && !SubscribedList.subcribedList.contains(place)){
            sub = false;
            textView.setText("Unsubscribe");
            t = Toast.makeText(this, "Subscribed", Toast.LENGTH_SHORT);
            SubscribedList.subcribedList.add(place);
        }
        else{
            t = Toast.makeText(this, "None", Toast.LENGTH_SHORT);
        }
        t.show();
    }
}
