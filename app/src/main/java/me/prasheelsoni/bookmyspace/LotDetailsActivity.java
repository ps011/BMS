package me.prasheelsoni.bookmyspace;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import me.prasheelsoni.bookmyspace.pojo.Booking;
import me.prasheelsoni.bookmyspace.pojo.ParkingSlot;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.lang.Integer.parseInt;

public class LotDetailsActivity extends AppCompatActivity {

    TextView lotName,counter,availableSlots, mRate;
    EditText mSlot1, mSlot2, mSlot3, mSlot4, mSlot5;
    FloatingActionButton plus, minus, book;
    Integer slotsAvailable;
    String lot, parkingCharges,contact;
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
        book.setEnabled(false);

        String data = getIntent().getStringExtra("POSITION");
        lot = data;
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

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+1:00"));
                Date currentLocalTime = cal.getTime();
                DateFormat date = new SimpleDateFormat("HH:mm a");
                date.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
              final  String localTime = date.format(currentLocalTime);


            Retrofit adapter = new Retrofit.Builder()
                    .baseUrl("http://bms-ps11.rhcloud.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

                NetworkCalls service = adapter.create(NetworkCalls.class);

                Call<Booking> response = service.bookSlot(lot,counter.getText().toString(), parkingCharges, localTime);

                response.enqueue(new Callback<Booking>() {
                    @Override
                    public void onResponse(Call<Booking> call, Response<Booking> response) {
                        if(response.isSuccessful()){


                            Intent i = new Intent(LotDetailsActivity.this, RecieptActivity.class);
                            i.putExtra("NAME", lot);
                            i.putExtra("RATE", parkingCharges);
                            i.putExtra("POC", contact);
                            i.putExtra("TIME", localTime);
                            int bookedSlots = Integer.parseInt(counter.getText().toString());
                            i.putExtra("bookedSlots", bookedSlots);
                            switch(bookedSlots) {

                                case 1: {
                                    i.putExtra("CAR1", mSlot1.getText().toString());
                                    break;
                                }
                                case 2: {
                                    i.putExtra("CAR1", mSlot1.getText().toString());
                                    i.putExtra("CAR2", mSlot2.getText().toString());
                                    break;
                                }
                                case 3: {
                                    i.putExtra("CAR1", mSlot1.getText().toString());
                                    i.putExtra("CAR2", mSlot2.getText().toString());
                                    i.putExtra("CAR3", mSlot3.getText().toString());
                                    break;
                                }
                                case 4: {
                                    i.putExtra("CAR1", mSlot1.getText().toString());
                                    i.putExtra("CAR2", mSlot2.getText().toString());
                                    i.putExtra("CAR3", mSlot3.getText().toString());
                                    i.putExtra("CAR4", mSlot4.getText().toString());
                                    break;
                                }
                                case 5: {
                                    i.putExtra("CAR1", mSlot1.getText().toString());
                                    i.putExtra("CAR2", mSlot2.getText().toString());
                                    i.putExtra("CAR3", mSlot3.getText().toString());
                                    i.putExtra("CAR4", mSlot4.getText().toString());
                                    i.putExtra("CAR5", mSlot5.getText().toString());
                                    break;
                                }

                            }

                            startActivity(i);

                        }
                    }

                    @Override
                    public void onFailure(Call<Booking> call, Throwable t) {
                        Toast.makeText(LotDetailsActivity.this, "Slot Booking Failed", Toast.LENGTH_SHORT).show();
                    }
                });
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
                    int count = 0, total=0;
                    total = Integer.parseInt(response.body().getTotalSlots());
                    count = Integer.parseInt(response.body().getBookedSlots());
                    slotsAvailable = total-count;
                    if(slotsAvailable>0)
                        book.setEnabled(true);

                    availableSlots.setText("Available Slots : "+slotsAvailable);
                    mRate.setText("Parking Charges/hour : Rs."+response.body().getRate());
                    parkingCharges = response.body().getRate();
                    contact = response.body().getPoc();
                }
            }

            @Override
            public void onFailure(Call<ParkingSlot> call, Throwable t) {

            }
            //TODO : Dismiss onCreate Loader Here.
        });




    }


    public void plus(View v){

        if(parseInt(counter.getText().toString()) == 5){
            Toast.makeText(this, "You cannot book more than 5 slots.", Toast.LENGTH_LONG).show();
        }
        else {

                if(parseInt(counter.getText().toString())==slotsAvailable){
                    Toast.makeText(this, "You cannot book more than "+slotsAvailable+" slots at this lot.", Toast.LENGTH_LONG).show();
                }
            else {
                    counter.setText(String.valueOf(parseInt(counter.getText().toString()) + 1));

                    switch (parseInt(counter.getText().toString())) {

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
        if(parseInt(counter.getText().toString())>0)
        counter.setText(String.valueOf(parseInt(counter.getText().toString())-1));
        else
            Toast.makeText(this, "You can't take parked cars from the lot!!", Toast.LENGTH_SHORT).show();


        switch(parseInt(counter.getText().toString())){

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
