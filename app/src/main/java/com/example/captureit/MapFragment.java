package com.example.captureit;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends SupportMapFragment implements
        GetNearby.getNearbyListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, OnMapReadyCallback {


//    GoogleMap gMap;

    ////////////////////////////////
    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    ArrayList<LocationData> locationList = new ArrayList<>();

    SupportMapFragment mapFragment;

    public MapFragment() {
        // Required empty public constructor
    }


//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View v = inflater.inflate(R.layout.fragment_map, container, false);
//        mapFragment = (SupportMapFragment)getFragmentManager().findFragmentById(R.id.mapFragment);
////        mapFragment.getMapAsync(this);
//
//        return v;
//    }

    @Override
    public void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }



    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;

        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }


        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

//        Double vanLat = 49.2827;
//        Double vanLng = -123.1207;
//
//        ReceiveData rd = new ReceiveData();
//        ArrayList<Data> hotelData =  new ArrayList<>();
//        ArrayList<Data> restData = new ArrayList<>();
//
//
//
//        String hotels = "hotels";
//        String restaurants = "restaurant";
//
//        ArrayList<String> hlatitudesList = new ArrayList<>();
//        ArrayList<String> hlongitudesList = new ArrayList<>();
//        ArrayList<String> hnamesList = new ArrayList<>();
//
//        ArrayList<String> rlatitudesList = new ArrayList<>();
//        ArrayList<String> rlongitudesList = new ArrayList<>();
//        ArrayList<String> rnamesList = new ArrayList<>();
//        //////////////////////////////////////////
//        hotelData = rd.receiveData(location.getLatitude()+"", location.getLongitude()+"", hotels);
//        restData = rd.receiveData(location.getLatitude()+"", location.getLongitude()+"", restaurants);
////        d = rd.receiveData(vanLat.toString(), vanLng.toString(), hotels);
//
//        for(int a=0; a<hotelData.size(); a++){
//            hlatitudesList.add(hotelData.get(a).getLat());
//            hlongitudesList.add(hotelData.get(a).getLng());
//            hnamesList.add(hotelData.get(a).getName());
//        }
//
//        for(int b = 0; b<restData.size(); b++){
//            rlatitudesList.add(restData.get(b).getLat());
//            rlongitudesList.add(restData.get(b).getLng());
//            rnamesList.add(restData.get(b).getName());
//        }



        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));

        mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);

//        for(int i=0; i<hotelData.size(); i++){
//            LatLng l = new LatLng(Double.parseDouble(hlatitudesList.get(i)) , Double.parseDouble(hlongitudesList.get(i)));
//            MarkerOptions markerOption = new MarkerOptions();
//            markerOption.position(l);
//            markerOption.title(hnamesList.get(i));
//            markerOption.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
//            mCurrLocationMarker = mGoogleMap.addMarker(markerOption);
//        }
//
//        for(int i=0; i<restData.size(); i++){
//            LatLng l = new LatLng(Double.parseDouble(rlatitudesList.get(i)) , Double.parseDouble(rlongitudesList.get(i)));
//            MarkerOptions markerOption1 = new MarkerOptions();
//            markerOption1.position(l);
//            markerOption1.title(rnamesList.get(i));
//            markerOption1.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
//            mCurrLocationMarker = mGoogleMap.addMarker(markerOption1);
//        }
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,14));

    }


    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onPause() {
        super.onPause();

        if(mGoogleApiClient!=null){
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;


    public void onProviderEnabled(String s) {

    }

    public void onProviderDisabled(String s) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        mLocationRequest = new LocationRequest();
        //mLocationRequest.setInterval(1000);
        //mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void setUpMapIfNeeded() {

        if (mGoogleMap == null) {
            getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

//        LatLng pp = new LatLng(11,104);
//        MarkerOptions option = new MarkerOptions();
//        option.position(pp).title("Phone");
//        gMap.addMarker(option);
//        gMap.moveCamera(CameraUpdateFactory.newLatLng(pp));

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                buildGoogleApiClient();
                mGoogleMap.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        }
        else {
            buildGoogleApiClient();
            mGoogleMap.setMyLocationEnabled(true);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }
    @Override
    public ArrayList getResult(String jsonData) {
        return null;
    }


    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(getActivity())
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION );
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }
    }



    @Override

        public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
            switch (requestCode) {
                case MY_PERMISSIONS_REQUEST_LOCATION: {
                    if (grantResults.length > 0
                            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                        if (ContextCompat.checkSelfPermission(getActivity(),
                                Manifest.permission.ACCESS_FINE_LOCATION)
                                == PackageManager.PERMISSION_GRANTED) {

                            if (mGoogleApiClient == null) {
                                buildGoogleApiClient();
                            }
                            mGoogleMap.setMyLocationEnabled(true);
                        }

                    } else {

                        Toast.makeText(getActivity(), "permission denied", Toast.LENGTH_LONG).show();
                    }
                    return;
                }

            }

    }
}
