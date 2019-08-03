package com.mazmaz.sharedhouse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SharedHouse extends AppCompatActivity {

    TextView address_txt, city_txt;
    Button create_new_todo_item_btn, show_all_todo_items_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_house);

        address_txt = findViewById(R.id.house_address_txt);
        city_txt = findViewById(R.id.house_city_txt);
        final String address_t = getIntent().getStringExtra("sharedHouseAddress");
        final String city_t = getIntent().getStringExtra("sharedHouseCity");
        final String houseId = getIntent().getStringExtra("sharedHouseId");
        final String sharedUserId = getIntent().getStringExtra("sharedUserId");
        show_all_todo_items_btn = findViewById(R.id.show_all_todo_items_btn);
        create_new_todo_item_btn = findViewById(R.id.create_new_todo_item_btn);
//        Log.d("Testing222",address_t);

//        Log.d("Test","Key token in shared house "+ houseId);
//        Log.d("Test","User token in shared house "+ sharedUserId);
        address_txt.append(address_t);
        city_txt.append(city_t);

        create_new_todo_item_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d("Test",""+ sharedUserId+"/"+houseId);
                Intent intent = new Intent(SharedHouse.this, CreateNewToDoActivity.class);
                intent.putExtra("shared_HouseId", houseId);
                intent.putExtra("shared_UserId", sharedUserId);

                intent.putExtra("house_address",address_t );
                intent.putExtra("house_city", city_t );
                finish();
                startActivity(intent);

            }
        });

        show_all_todo_items_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SharedHouse.this, ShowAllMissionsActivity.class);
                intent.putExtra("shared_HouseId", houseId);
                intent.putExtra("shared_UserId", sharedUserId);

                intent.putExtra("house_address",address_t );
                intent.putExtra("house_city", city_t );
                finish();
                startActivity(intent);

            }
        });

    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        try {
//            final String address_t = getIntent().getStringExtra("shared_House_address");
//            final String city_t = getIntent().getStringExtra("shared_House_city");
//
//            address_txt.append(address_t);
//            city_txt.append(city_t);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//
//    }
}
