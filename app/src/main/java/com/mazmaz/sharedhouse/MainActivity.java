package com.mazmaz.sharedhouse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button btnLogout, btn_create_new_house, btn_existing_houses;
    private FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mDatabase;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        btnLogout = findViewById(R.id.btnLogout);
        firebaseAuth = FirebaseAuth.getInstance();
        btn_existing_houses = findViewById(R.id.btn_existing_houses);

        final FirebaseUser user = firebaseAuth.getInstance().getCurrentUser() ;
        userId = user.getUid();
//        Log.d("Test",user.getEmail());
//        Log.d("Test","in main ");
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });

        btn_create_new_house = findViewById(R.id.btn_create_new_house);
        btn_create_new_house.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                writeNewSharedHouse(user.getEmail());
                Intent i = new Intent(MainActivity.this, CreateNewSharedHouse.class);
                i.putExtra("UserToken", user.getUid());

//                finish();
                startActivity(i);

            }
        });

        btn_existing_houses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, CreateNewSharedHouse.class);
                i.putExtra("UserToken", user.getUid());

//                finish();
                startActivity(i);
            }
        });
        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = firebaseDatabase.getReference("SharedHouseUsers");


    }


//    @Exclude
//    public Map<String, Object> toMap() {
//        HashMap<String, Object> result = new HashMap<>();
//        result.put("admin mail", username);
//        result.put("shared houses", "");
////        result.put("title", title);
////        result.put("body", body);
////        result.put("starCount", starCount);
////        result.put("stars", stars);
//
//        return result;
//    }


    private void writeNewSharedHouse(String userMail) throws IllegalArgumentException{
        if(userMail==null || userMail.isEmpty()) throw new IllegalArgumentException();
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("houses").push().getKey();
//        Post post = new Post(userId, username, title, body);
        SharedHome sharedHome = new SharedHome(userMail);

        Map<String, Object> sharedHomeValues = sharedHome.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/houses/" + key, sharedHomeValues);
        childUpdates.put("/user-houses/" + userId + "/" + key, sharedHomeValues);

        mDatabase.updateChildren(childUpdates);
//        SharedHome sharedHome = new SharedHome(userMail);
//        mDatabase.updateChildrenAsync(sharedHome+"/gracehop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
