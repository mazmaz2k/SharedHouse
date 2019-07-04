package com.mazmaz.sharedhouse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SharedHouse extends AppCompatActivity {

    TextView address_txt, city_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_house);

        address_txt = findViewById(R.id.house_address_txt);
        city_txt = findViewById(R.id.house_city_txt);
        final String address_t = getIntent().getStringExtra("sharedHouseAddress");
        final String city_t = getIntent().getStringExtra("sharedHouseCity");

        address_txt.setText(address_t);
        city_txt.setText(city_t);


    }
}
