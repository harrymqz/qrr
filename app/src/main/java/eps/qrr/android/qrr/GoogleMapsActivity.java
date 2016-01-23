package eps.qrr.android.qrr;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;

/**
 * Created by ruben on 21/12/15.
 */
public class GoogleMapsActivity extends AppCompatActivity {

    GoogleMap map;
    MapView mapView;

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmaps);

        mapView = (MapView)findViewById(R.id.my_map);
        mapView.onCreate(savedInstanceState);

        map = mapView.getMap();

        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        // Appear the point of your location
        map.setMyLocationEnabled(true);
    }
}
