package com.mazmaz.sharedhouse;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ImageButton btn_create_new_house, btn_existing_houses;
    private FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mDatabase;
    String userId;
    private ImageButton btnLogout;
    String key_token;
    boolean doubleBackToExitPressedOnce = false;
    private static final int TIME_INTERVAL = 3000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mDatabase = FirebaseDatabase.getInstance().getReference();

        btnLogout = findViewById(R.id.btnLogout);
        firebaseAuth = FirebaseAuth.getInstance();
        btn_existing_houses = findViewById(R.id.btn_existing_houses);

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
        final FirebaseUser user = firebaseAuth.getInstance().getCurrentUser() ;

        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = firebaseDatabase.getReference("SharedHouseUsers");

        btn_create_new_house.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                writeNewSharedHouse(user.getEmail());
                Intent i = new Intent(MainActivity.this, CreateNewSharedHouse.class);
                i.putExtra("UserToken", userId);
                i.putExtra("show_only_houses_list",1);
                i.putExtra("token_key",key_token);

//                Log.d("Test","111111 "+key_token);

//                finish();
                startActivity(i);

            }
        });

        if(key_token==null){
            btn_existing_houses.setEnabled(false);
        }else{
            btn_existing_houses.setEnabled(true);

        }
        btn_existing_houses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d("Test", "hiiiiiiiii");


                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        setToken();

                    }
                };
//        if(key_token==null){
                runnable.run();
                Intent i = new Intent(MainActivity.this, CreateNewSharedHouse.class);
                i.putExtra("UserToken", userId);
                i.putExtra("token_key",key_token);
                i.putExtra("show_only_houses_list",0);
//                Log.d("Test", "sending token : "+ key_token);

//                finish();
                startActivity(i);
            }
        });

//        query.addListenerForSingleValueEvent();

//        Log.d("Test", "Query: "+ query.getRef());

//        query.getRef();


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


    @Override
    protected void onResume() {
        super.onResume();
        final FirebaseUser user = firebaseAuth.getInstance().getCurrentUser() ;
        if(user==null){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
        }
        userId = user.getUid();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
               setToken();

            }
        };
//        if(key_token==null){
            runnable.run();
////            setToken();;
//            Log.d("Test", "1 -- key_token is null in onResume" + key_token);
//            if(key_token == null){
//                btn_existing_houses.setEnabled(false);
//                Log.d("Test", "2 -- key_token is null in onResume" + key_token);
//
//            }
//            else{
//                btn_existing_houses.setEnabled(true);
//
//            }
//        }
    }

    private void setToken(){
        final FirebaseUser user = firebaseAuth.getInstance().getCurrentUser() ;
        mDatabase = FirebaseDatabase.getInstance().getReference();

        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = firebaseDatabase.getReference("SharedHouseUsers");

        mDatabase.child("houses").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean fl = false;

                for (DataSnapshot children: dataSnapshot.getChildren()){
                    if(fl==true){
                        break;
                    }
                    for (DataSnapshot child : children.getChildren()) {

                        if(child.getKey().equals("admin mail")){
                            if(child.getValue().equals(user.getEmail())){
//                                Log.d("Test", "setToken token: "+ child.getValue());
                                key_token=children.getKey();
                                break;
                            }
                            continue;
                        }
                    }

                }
                if(fl){
                    btn_existing_houses.setEnabled(false);
                }else{
                    btn_existing_houses.setEnabled(true);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }
    private void writeNewSharedHouse(String userMail) throws IllegalArgumentException{
        if(userMail==null || userMail.isEmpty()) throw new IllegalArgumentException();
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("houses").push().getKey();
//        Post post = new Post(userId, username, title, body);
        SharedHome sharedHome = new SharedHome(userMail);

        Map<String, Object> sharedHomeValues = sharedHome.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        if(key_token==null){
//            key_token=key;
//            Log.d("Test","NULL in key");
            childUpdates.put("/houses/" + key, sharedHomeValues);
            childUpdates.put("/user-houses/" + userId + "/" + key, sharedHomeValues);
            key_token=key;

        }else{
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    setToken();

                }
            };
//        if(key_token==null){
            runnable.run();
        }


        mDatabase.updateChildren(childUpdates);
//        SharedHome sharedHome = new SharedHome(userMail);
//        mDatabase.updateChildrenAsync(sharedHome+"/gracehop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        firebaseAuth.signOut();

//        Auth.GoogleSignInApi.signOut(new GoogleApiClient.Builder(getApplicationContext()) //Use app context to prevent leaks using activity
//                //.enableAutoManage(this /* FragmentActivity */, connectionFailedListener)
//                .addApi(Auth.GOOGLE_SIGN_IN_API)
//                .build());

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user==null){
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
            }
            };
        });
    }

    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            super.onBackPressed();
            return;
        }
        else { Toast.makeText(getBaseContext(), "Tap back button in order to exit", Toast.LENGTH_SHORT).show(); }

        mBackPressed = System.currentTimeMillis();

    }


//    private class searchinDBTssk extends AsyncTask<Void, Void, Void>{
//
//
//
//
//        @Override
//        protected Void doInBackground(Void... voids) {
////            setToken();
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result) {
//        }
//    }
}
