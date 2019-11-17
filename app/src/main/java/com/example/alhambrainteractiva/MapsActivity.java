package com.example.alhambrainteractiva;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.HashMap;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private Location currentLocation; // LOCALIZACIÓN ACTUAL

    private FusedLocationProviderClient fusedLocationProviderClient;

    private static final int LOCATION_REQUEST_CODE = 101; // CÓDIGO REQUERIDO EN LOS PERMISOS

    static final LatLng LEONES = new LatLng(37.177080, -3.589233);
    static final LatLng REYES = new LatLng(37.177065, -3.588986);
    static final LatLng COMARES = new LatLng(37.177574, -3.589640);
    static final LatLng NAZARIES = new LatLng(37.177333, -3.589758 );
    static final LatLng CARLOS = new LatLng(37.176807, -3.589948);


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        fetchLastLocation();
    }

    // BUSCAR ÚLTIMA UBICACIÓN
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void fetchLastLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

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

        near(latLng,googleMap);

    }

    public void near(LatLng latLng, GoogleMap googleMap) {
        if (Math.abs(latLng.latitude - LEONES.latitude) < 0.00045){
            Marker leones = googleMap.addMarker(new MarkerOptions()
                    .position(LEONES)
                    .title("http://www.alhambra-patronato.es/edificios-lugares/patio-de-los-leones")
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        }
        if (Math.abs(latLng.latitude - REYES.latitude) < 0.00045){
            Marker reyes = googleMap.addMarker(new MarkerOptions()
                    .position(REYES)
                    .title("http://www.alhambra-patronato.es/edificios-lugares/sala-de-los-reyes")
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        }
        if (Math.abs(latLng.latitude - COMARES.latitude) < 0.00045){
            Marker comares = googleMap.addMarker(new MarkerOptions()
                    .position(COMARES)
                    .title("https://www.alhambradegranada.org/es/info/palaciosnazaries/torredecomares.asp")
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        }
        if (Math.abs(latLng.latitude - NAZARIES.latitude) < 0.00045){
            Marker nazaries = googleMap.addMarker(new MarkerOptions()
                    .position(NAZARIES)
                    .title("https://www.alhambra.info/palacios-nazaries.html")
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        }
        if (Math.abs(latLng.latitude - CARLOS.latitude) < 0.00045){
            Marker carlos = googleMap.addMarker(new MarkerOptions()
                    .position(CARLOS)
                    .title("http://www.alhambra-patronato.es/edificios-lugares/palacio-de-carlos-v")
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        }
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
        {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String url = marker.getTitle();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                return true;
            }

        });
    }

    // REQUERIMIENTO DE PERMISOS
    @RequiresApi(api = Build.VERSION_CODES.M)
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