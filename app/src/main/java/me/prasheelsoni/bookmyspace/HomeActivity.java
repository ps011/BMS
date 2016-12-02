package me.prasheelsoni.bookmyspace;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yayandroid.locationmanager.LocationBaseActivity;
import com.yayandroid.locationmanager.LocationConfiguration;
import com.yayandroid.locationmanager.LocationManager;
import com.yayandroid.locationmanager.constants.FailType;
import com.yayandroid.locationmanager.constants.LogType;
import com.yayandroid.locationmanager.constants.ProviderType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.prasheelsoni.bookmyspace.adapters.DrawerItemAdapter;
import me.prasheelsoni.bookmyspace.pojo.ParkingSlot;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends LocationBaseActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    public Location currentLocation;
    AutoCompleteTextView locationSearch;
    DrawerLayout mDrawerLayout;
    RecyclerView mDrawerRecyclerView;
    DrawerItemAdapter mDrawerItemAdapter;
    ActionBarDrawerToggle mDrawerToggle;
    String[] locationList = {"Sapna Sangeeta ", "Vijay Nagar", "Palasia", "Rajwada", "Bhawarkua", "Annapurna"};
    ArrayList<String> options = new ArrayList<>();
    Toolbar mToolBar;
    ArrayList<Integer> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getLocation();
        //TODO : Add Loader Here (Getting Location).


        options.add("Profile");
        options.add("History");
        options.add("Help");
        options.add("Contact");
        options.add("Logout");

        images.add(R.drawable.profile);
        images.add(R.drawable.history);
        images.add(R.drawable.help);
        images.add(R.drawable.contact);
        images.add(R.drawable.logout);



        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer);
        mDrawerRecyclerView = (RecyclerView)findViewById(R.id.left_drawer);
        mDrawerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDrawerItemAdapter = new DrawerItemAdapter(options,images);
        mDrawerRecyclerView.setAdapter(mDrawerItemAdapter);
        mToolBar = (Toolbar)findViewById(R.id.toolbar);

        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                mToolBar,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
         locationSearch = (AutoCompleteTextView) findViewById(R.id.editText);
        locationSearch.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, locationList));
        mapFragment.getMapAsync(this);

        LocationManager.setLogType(LogType.GENERAL);
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
        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
        LatLng sydney;
        // Add a marker in Sydney and move the camera
        if(currentLocation == null) {
             sydney = new LatLng(-34, 151);
        }
        else{
            sydney = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        }

       // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


    }

    @Override
    public LocationConfiguration getLocationConfiguration() {
        return new LocationConfiguration()
                .keepTracking(true)
                .askForGooglePlayServices(true)
                .setMinAccuracy(200.0f)
                .setWaitPeriod(ProviderType.GOOGLE_PLAY_SERVICES, 5 * 1000)
                .setWaitPeriod(ProviderType.GPS, 10 * 1000)
                .setWaitPeriod(ProviderType.NETWORK, 5 * 1000)
                .setGPSMessage("Please Turn On GPS in Settings.")
                .setRationalMessage("Allow application to access GPS settings ?");
    }

    @Override
    public void onLocationFailed(int failType) {
        switch (failType) {
            case FailType.PERMISSION_DENIED: {
                Toast.makeText(HomeActivity.this, "Couldn't get location, because user didn't give permission!", Toast.LENGTH_LONG).show();
                //  locationText.setText("Couldn't get location, because user didn't give permission!");
                break;
            }
            case FailType.GP_SERVICES_NOT_AVAILABLE:
            case FailType.GP_SERVICES_CONNECTION_FAIL: {
                Toast.makeText(HomeActivity.this, "Couldn't get location, because Google Play Services not available!", Toast.LENGTH_LONG).show();
                //locationText.setText("Couldn't get location, because Google Play Services not available!");
                break;
            }
            case FailType.NETWORK_NOT_AVAILABLE: {
                Toast.makeText(HomeActivity.this, "Couldn't get location, because network is not accessible!", Toast.LENGTH_LONG).show();
                //locationText.setText("Couldn't get location, because network is not accessible!");
                break;
            }
            case FailType.TIMEOUT: {
                Toast.makeText(HomeActivity.this, "Couldn't get location, and timeout!", Toast.LENGTH_LONG).show();
                //locationText.setText("Couldn't get location, and timeout!");
                break;
            }
            case FailType.GP_SERVICES_SETTINGS_DENIED: {
                Toast.makeText(HomeActivity.this, "Couldn't get location, because user didn't activate providers via settingsApi!", Toast.LENGTH_LONG).show();
                //locationText.setText("Couldn't get location, because user didn't activate providers via settingsApi!");
                break;
            }
            case FailType.GP_SERVICES_SETTINGS_DIALOG: {
                Toast.makeText(HomeActivity.this, "Couldn't display settingsApi dialog!", Toast.LENGTH_LONG).show();
                //locationText.setText("Couldn't display settingsApi dialog!");
                break;
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        //TODO : Dismiss onCreate Loader Here.

        currentLocation = location;
        LatLng sydney;
        sydney = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        mMap.addMarker(new MarkerOptions().position(sydney).title("Your Location"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,14.0f));
       // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public void onMapSearch(View view) {

        //TODO : Get parking slots from server and add them to the auto complete edit text adapter.


        String location = locationSearch.getText().toString();
        List<Address> addressList = null;

        if (location != null || !location.equals("")) {
            Geocoder geocoder = new Geocoder(this);
            location = location.concat(",Indore");
            try {
                addressList = geocoder.getFromLocationName(location, 1);

            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            findParkings(latLng);

//            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
//            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }

    public void findParkings(LatLng latLng){

        Retrofit adapter = new Retrofit.Builder()
                .baseUrl("http://bms-ps11.rhcloud.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NetworkCalls service  = adapter.create(NetworkCalls.class);

        Call<List<ParkingSlot>> response = service.getAvailableParkings((String.valueOf(latLng.latitude)),String.valueOf(latLng.longitude));

        response.enqueue(new Callback<List<ParkingSlot>>() {
            @Override
            public void onResponse(Call<List<ParkingSlot>> call, Response<List<ParkingSlot>> response) {
                if(response.isSuccessful()){
                    for (ParkingSlot p : response.body()) {
                        if(p.getFull().equals("0")){
                            String gps = p.getGps();
                            String[] coords = gps.split(",");
                            if(p.getType().equals("car"))
                            {  mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(Double.parseDouble(coords[0]), Double.parseDouble(coords[1])))
                                    .title(p.getName())
                                    .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.cars)))
                                    .snippet("Rate : Rs."+p.getRate()+"/hr")

                            );}
                            else
                            { mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(Double.parseDouble(coords[0]), Double.parseDouble(coords[1])))
                                    .title(p.getName())
                                    .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bikes)))
                                    .snippet("Rate : Rs."+p.getRate()+"/hr")
                            );}
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ParkingSlot>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Failed to fetch Parking Lots", Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
            return true;
        } else
            return false;
    }


    @Override
    public void onInfoWindowClick(Marker marker) {
        String selectedPosition = marker.getTitle();
        Intent i = new Intent(HomeActivity.this, LotDetailsActivity.class);
        i.putExtra("POSITION", selectedPosition);
        startActivity(i);
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
      marker.showInfoWindow();
    return true;
    }
}
