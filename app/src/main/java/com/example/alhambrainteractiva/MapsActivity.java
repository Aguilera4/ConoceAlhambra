package com.example.alhambrainteractiva;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private Location currentLocation; // LOCALIZACIÓN ACTUAL

    private FusedLocationProviderClient fusedLocationProviderClient;

    private static final int LOCATION_REQUEST_CODE = 101; // CÓDIGO REQUERIDO EN LOS PERMISOS

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(MapsActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            return;
        }
        fetchLastLocation();
    }

    // BUSCAR ÚLTIMA UBICACIÓN
    private void fetchLastLocation(){
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    SupportMapFragment supportMapFragment= (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    supportMapFragment.getMapAsync(MapsActivity.this);
                }else{
                    Toast.makeText(MapsActivity.this,"Error: No Location recorded",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        // LATITUD Y LONGITUD ACTUAL
        LatLng latLng = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());

        // MARCADOR POSICIÓN ACTUAL
        MarkerOptions markerOptions = new MarkerOptions().position(latLng);

        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

        // ZOOM
        float zoomLevel = 16.5f;
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));

        // SE AÑADE EL MARCADOR
        googleMap.addMarker(markerOptions);

    }

    // REQUERIMIENTO DE PERMISOS
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResult) {
        switch (requestCode) {
            case LOCATION_REQUEST_CODE:
                if (grantResult.length > 0 && grantResult[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLastLocation();
                } else {
                    Toast.makeText(MapsActivity.this,"Error: Location permission missing",Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }
}
