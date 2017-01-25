package me.prasheelsoni.bookmyspace;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class RecieptActivity extends AppCompatActivity {


    TextView name,rate,time,car1,car2,car3,car4,car5,contact;
    String nameValue, rateValue, timeValue, contactValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reciept);

        name=(TextView) findViewById(R.id.name);
        rate = (TextView)findViewById(R.id.rate);
        time = (TextView) findViewById(R.id.booking_time);
        car1 = (TextView) findViewById(R.id.car_1);
        car2 = (TextView) findViewById(R.id.car_2);
        car3 = (TextView) findViewById(R.id.car_3);
        car4 = (TextView) findViewById(R.id.car_4);
        car5 = (TextView) findViewById(R.id.car_5);
        contact = (TextView) findViewById(R.id.contact);

       nameValue = getIntent().getStringExtra("NAME");
        rateValue = getIntent().getStringExtra("RATE");
        timeValue = getIntent().getStringExtra("TIME");
        contactValue = getIntent().getStringExtra("POC");


        int bookedSlots = getIntent().getIntExtra("bookedSlots", 0);

        switch(bookedSlots){

            case 1 : {
                car1.setVisibility(View.VISIBLE);
                car1.setText("CAR 1 : "+ getIntent().getStringExtra("CAR1"));
                break;
            }
            case 2 : {
                car1.setVisibility(View.VISIBLE);
                car2.setVisibility(View.VISIBLE);
                car1.setText("CAR 1 : "+ getIntent().getStringExtra("CAR1"));
                car2.setText("CAR 2 : "+ getIntent().getStringExtra("CAR2"));
                break;
            }
            case 3 : {
                car1.setVisibility(View.VISIBLE);
                car2.setVisibility(View.VISIBLE);
                car3.setVisibility(View.VISIBLE);

                car1.setText("CAR 1 : "+ getIntent().getStringExtra("CAR1"));
                car2.setText("CAR 2 : "+ getIntent().getStringExtra("CAR2"));
                car3.setText("CAR 3 : "+ getIntent().getStringExtra("CAR3"));
                break;
            }
            case 4 : {
                car1.setVisibility(View.VISIBLE);
                car2.setVisibility(View.VISIBLE);
                car3.setVisibility(View.VISIBLE);
                car4.setVisibility(View.VISIBLE);

                car1.setText("CAR 1 : "+ getIntent().getStringExtra("CAR1"));
                car2.setText("CAR 2 : "+ getIntent().getStringExtra("CAR2"));
                car3.setText("CAR 3 : "+ getIntent().getStringExtra("CAR3"));
                car4.setText("CAR 4 : "+ getIntent().getStringExtra("CAR4"));
                break;
            }
            case 5 : {
                car1.setVisibility(View.VISIBLE);
                car2.setVisibility(View.VISIBLE);
                car3.setVisibility(View.VISIBLE);
                car4.setVisibility(View.VISIBLE);
                car5.setVisibility(View.VISIBLE);

                car1.setText("CAR 1 : "+ getIntent().getStringExtra("CAR1"));
                car2.setText("CAR 2 : "+ getIntent().getStringExtra("CAR2"));
                car3.setText("CAR 3 : "+ getIntent().getStringExtra("CAR3"));
                car4.setText("CAR 4 : "+ getIntent().getStringExtra("CAR4"));
                car5.setText("CAR 5 : "+ getIntent().getStringExtra("CAR5"));
                break;
            }
            default: {
                car1.setText("No Slots Booked");
            }





        }




        name.setText("Parking Lot : "+ nameValue);
        rate.setText("Parking Charges : "+rateValue+"/hr");
        time.setText("Booking Time : "+timeValue);
        contact.setText("Parking Lot Contact : "+contactValue);












    }
}
