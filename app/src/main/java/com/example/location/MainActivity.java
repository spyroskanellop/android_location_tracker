package com.example.location;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private TextView longView, latView, countryView, localView, addressView;

    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assign variable
        button = (Button) findViewById(R.id.button);
        longView = (TextView) findViewById(R.id.textView);
        latView = (TextView) findViewById(R.id.textView2);
        countryView = (TextView) findViewById(R.id.textView3);
        localView = (TextView) findViewById(R.id.textView4);
        addressView = (TextView) findViewById(R.id.textView5);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //Check permission
//                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                    getLocation();
//                } else {
//                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
//                }
//            }
//        });
    }

    public void onClick(View view) {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLocation();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) { return; }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                    try {
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1);
                        longView.setText(Html.fromHtml("<font color='#6200EE'><b>Longitude :</b><br></font>" + addresses.get(0).getLongitude()));

                        latView.setText(Html.fromHtml("<font color='#6200EE'><b>Latitude :</b><br></font>" + addresses.get(0).getLatitude()));
                        countryView.setText(Html.fromHtml("<font color='#6200EE'><b>Country :</b><br></font>" + addresses.get(0).getCountryName()));
                        localView.setText(Html.fromHtml("<font color='#6200EE'><b>Locality :</b><br></font>" + addresses.get(0).getLocality()));
                        addressView.setText(Html.fromHtml("<font color='#6200EE'><b>Address Line :</b><br></font>" + addresses.get(0).getAddressLine(0)));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

}