package toq.toqlocation.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;
    static double CurrentLatittude, CurrentLongitude;
    TextView user_location, textView;
    private FusedLocationProviderClient mFusedLocationClient;
    double lat1, lon1;
    List<LocationsObj> locations = new ArrayList<>();
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        locations.add(new LocationsObj(77.627106, 12.927923, "Kormangla"));
        locations.add(new LocationsObj(77.627747, 12.923830, "Madiwala"));
        locations.add(new LocationsObj(77.677002, 12.839939, "Electronic City"));
        locations.add(new LocationsObj(77.604523, 12.975880, "MG Road"));
        user_location = findViewById(R.id.user_location);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLocation();
    }

    private void fetchLocation() {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {


                new AlertDialog.Builder(this)
                        .setTitle("Required Location Permission")
                        .setMessage("You have to give this permission to acess this feature")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);

            }
        } else {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                CurrentLatittude = location.getLatitude();
                                CurrentLongitude = location.getLongitude();
                                user_location.setText("User's Current Location" + "\nLatitude = " + CurrentLatittude + "\nLongitude = " + CurrentLongitude);
                                for (int j = 0; j < locations.size(); j++) {
                                    lat1 = locations.get(j).getLatitude();
                                    lon1 = locations.get(j).getLongitude();
                                    name = locations.get(j).getName();
                                    getDistance();
                                }
                            }
                        }
                    });
        }
    }

    public void getDistance() {
        double theta = lon1 - CurrentLongitude;
        double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(CurrentLatittude)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(CurrentLatittude)) * Math.cos(Math.toRadians(theta));
        dist = Math.acos(dist);
        dist = Math.toDegrees(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;

        List<Double> x = new ArrayList<Double>();
        x.add(dist);

       Collections.reverse(x);
        textView.setText(textView.getText().toString() + "\nTo " + name + " = " + x);




    }

}