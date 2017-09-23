package com.beerme.jpinc.beerme;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

/**
 * An activity that displays a Google map with a marker (pin) to indicate a particular location.
 */
public class BeerMapActivity extends AppCompatActivity {

    int PLACE_PICKER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.beer_map);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final BeerMapActivity activity = this;

        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                Intent intent;
                try{
                    intent = builder.build(activity);
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);
                }
                catch (GooglePlayServicesRepairableException e){
                    e.printStackTrace();
                }
                catch (GooglePlayServicesNotAvailableException e){
                    e.printStackTrace();
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String location = String.format("Place: %s", place.getAddress());

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Beer me! @ " + location);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        }
    }
}
