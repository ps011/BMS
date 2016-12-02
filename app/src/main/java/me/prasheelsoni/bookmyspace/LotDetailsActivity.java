package me.prasheelsoni.bookmyspace;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import me.prasheelsoni.bookmyspace.pojo.ParkingSlot;
import me.prasheelsoni.bookmyspace.pojo.Slot;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LotDetailsActivity extends AppCompatActivity {

    TextView lotName,counter,availableSlots, mRate;
    EditText mSlot1, mSlot2, mSlot3, mSlot4, mSlot5;
    FloatingActionButton plus, minus, book;
    Integer slotsAvailable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lot_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        lotName = (TextView) findViewById(R.id.lot_name);
        counter = (TextView) findViewById(R.id.counter);
        availableSlots = (TextView) findViewById(R.id.slots_available);
        mRate = (TextView)findViewById(R.id.rate);

        mSlot1 = (EditText)findViewById(R.id.n_one);
        mSlot2 = (EditText)findViewById(R.id.n_two);
        mSlot3 = (EditText)findViewById(R.id.n_three);
        mSlot4 = (EditText)findViewById(R.id.n_four);
        mSlot5 = (EditText)findViewById(R.id.n_five);

        plus = (FloatingActionButton) findViewById(R.id.plus);
        minus = (FloatingActionButton) findViewById(R.id.minus);
        book = (FloatingActionButton) findViewById(R.id.book);

        String data = getIntent().getStringExtra("POSITION");
        getLotDetails(data);
        //TODO : Add Loader Here.




        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plus(v);
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minus(v);
            }
        });

       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }




    public void getLotDetails(String name){

        Retrofit adapter = new Retrofit.Builder()
                .baseUrl("http://bms-ps11.rhcloud.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NetworkCalls service = adapter.create(NetworkCalls.class);

        Call<ParkingSlot> response =  service.getSealectedSlotDetails(name);

        response.enqueue(new Callback<ParkingSlot>() {
            @Override
            public void onResponse(Call<ParkingSlot> call, Response<ParkingSlot> response) {
                if (response.isSuccessful()) {
                    lotName.setText(response.body().getName());
                    int count = 0;
                    ArrayList<Slot> freeSlots = (ArrayList<Slot>) response.body().getSlots();
                    for (Slot s : freeSlots) {
                        if (s.getBooked().equals("0")){
                            count++;
                        }
                    }
                    slotsAvailable = count;
                    availableSlots.setText("Available Slots : "+count);
                    mRate.setText("Parking Charges/hour : Rs."+response.body().getRate());
                }
            }

            @Override
            public void onFailure(Call<ParkingSlot> call, Throwable t) {

            }
            //TODO : Dismiss onCreate Loader Here.
        });




    }


    public void plus(View v){

        if(Integer.parseInt(counter.getText().toString()) == 5){
            Toast.makeText(this, "You cannot book more than 5 slots.", Toast.LENGTH_LONG).show();
        }
        else {

                if(Integer.parseInt(counter.getText().toString())==slotsAvailable){
                    Toast.makeText(this, "You cannot book more than "+slotsAvailable+" slots at this lot.", Toast.LENGTH_LONG).show();
                }
            else {
                    counter.setText(String.valueOf(Integer.parseInt(counter.getText().toString()) + 1));

                    switch (Integer.parseInt(counter.getText().toString())) {

                        case 1: {
                            mSlot1.setVisibility(View.VISIBLE);
                            break;
                        }
                        case 2: {
                            mSlot2.setVisibility(View.VISIBLE);
                            break;
                        }
                        case 3: {
                            mSlot3.setVisibility(View.VISIBLE);
                            break;
                        }
                        case 4: {
                            mSlot4.setVisibility(View.VISIBLE);
                            break;
                        }
                        case 5: {
                            mSlot5.setVisibility(View.VISIBLE);
                            break;
                        }
                    }
                }
        }

    }

    public void minus(View v){
        if(Integer.parseInt(counter.getText().toString())>0)
        counter.setText(String.valueOf(Integer.parseInt(counter.getText().toString())-1));
        else
            Toast.makeText(this, "You can't take parked cars from the lot!!", Toast.LENGTH_SHORT).show();


        switch(Integer.parseInt(counter.getText().toString())){

            case 0 : {
                mSlot1.setVisibility(View.INVISIBLE);
                break;}
            case 1 : { mSlot2.setVisibility(View.INVISIBLE);
                break;}
            case 2 : { mSlot3.setVisibility(View.INVISIBLE);
                break;}
            case 3 : { mSlot4.setVisibility(View.INVISIBLE);
                break;}
            case 4 : { mSlot5.setVisibility(View.INVISIBLE);
                break;}

        }

    }



}
