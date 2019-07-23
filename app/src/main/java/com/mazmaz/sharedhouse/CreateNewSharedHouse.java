package com.mazmaz.sharedhouse;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class CreateNewSharedHouse extends AppCompatActivity {

    Button btn_post_house, btn_update_house, btn_delete_house;
    EditText enter_home_address;
    EditText enter_home_city;
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseRecyclerOptions<postNewHouse> options;
    FirebaseRecyclerAdapter<postNewHouse, SharedHouseRecycleViewHolder> adapter;
    postNewHouse selectPost;
    String selectedKey;
    private String userToken,keyToken;
    LinearLayout create_housed_layout;
    TextView empty_house_list;
    ValueEventListener valueEventListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_shared_house);
        userToken = getIntent().getStringExtra("UserToken");
        final int show_only_houses_list = getIntent().getIntExtra("show_only_houses_list",1);
        final String keyToken= getIntent().getStringExtra("token_key");
        this.keyToken=keyToken;
        empty_house_list = findViewById(R.id.empty_house_list);
        create_housed_layout= findViewById(R.id.create_house_layout);
        if(show_only_houses_list==0) {
            create_housed_layout.setVisibility(View.GONE);
        }else{
            create_housed_layout.setVisibility(View.VISIBLE);

        }
        if(userToken==null){
            Log.d("Test", "user Is Empty");
        }
        Log.d("Test",userToken);
        btn_post_house = findViewById(R.id.btn_post_house);
        btn_update_house = findViewById(R.id.btn_update_house);
        btn_delete_house = findViewById(R.id.btn_delete_house);
        enter_home_address = findViewById(R.id.enter_home_address);
        enter_home_city = findViewById(R.id.enter_home_city);
        recyclerView = findViewById(R.id.new_sharedHouse_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("SharedHouseUsers").child("houses");

//        databaseReference = firebaseDatabase.getReference("sharedhouseusers/SharedHouseUsers/houses/"+keyToken+"/shared houses");

        displayHouse();

        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                displayHouse();
                if(adapter!=null){
                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        btn_post_house.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d("Test","in new house btn " );
                postHouse();

            }
        });

        btn_update_house.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedKey==null){
                    throw new NullPointerException();
                }
                databaseReference.
                        child(keyToken).child("shared houses").child(selectedKey)
                        .setValue(
                                new postNewHouse(enter_home_address.getText().toString(),enter_home_city.getText().toString(),userToken
                                )).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(CreateNewSharedHouse.this, "updated", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CreateNewSharedHouse.this, "Error on updating: "+ e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
                empty_house_list.setVisibility(View.GONE);

            }
        });

        btn_delete_house.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(selectedKey==null){
                    throw new NullPointerException();
                }
                databaseReference.
                        child(keyToken).child("shared houses").child(selectedKey)
                        .removeValue()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(CreateNewSharedHouse.this, "Deleted", Toast.LENGTH_SHORT).show();
//                        Log.d("Test", databaseReference.getParent().getKey());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CreateNewSharedHouse.this, "Error on delete: "+ e.getMessage(), Toast.LENGTH_SHORT).show();


                    }
                });
                Log.d("Test", "ddddddddddd "+ databaseReference.child(keyToken).toString());

                if(databaseReference.child(userToken).equals("")){
                    Log.d("Test", "TIS EMPTY!!!!!!!!!!!!!!!!!!!!!! #3");

                }
            }
        });
//        DatabaseReference DB_r = databaseReference.child(userToken);
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int count = (int) dataSnapshot.getChildrenCount(); //Cast long to int
                if (count ==0){
                    Log.d("Test", "TIS EMPTY!!!!!!!!!!!!!!!!!!!!!! #1");
                    empty_house_list.setVisibility(View.VISIBLE);


                }else{
                    Log.d("Test", "TIS EMPTY!!!!!!!!!!!!!!!!!!!!!! #2:  "+ count);
                    empty_house_list.setVisibility(View.GONE);


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        databaseReference.addListenerForSingleValueEvent(valueEventListener);
//        if(recyclerView.getChildCount()==0){
//            empty_house_list.setVisibility(View.VISIBLE);
//            Log.d("Test", "TIS EMPTY!!!!!!!!!!!!!!!!!!!!!! "+ recyclerView.getChildCount());
//            Log.d("Test", "TIS EMPTY!!!!!!!!!!!!!!!!!!!!!! "+ recyclerView.getItemDecorationCount());
//
//        }
//        else{
//            empty_house_list.setVisibility(View.INVISIBLE);
//        }

    }

    @Override
    protected void onStop() {
        if(adapter!=null){
            adapter.stopListening();
        }
        super.onStop();
    }

    private void postHouse(){
//        Log.d("Test",userToken);
//        if(recyclerView.getChildCount()==0){
//            empty_house_list.setVisibility(View.VISIBLE);
//            Log.d("Test", "This EMPTY!!!!!!!!!!!!!!!!!!!!!! ");
//        }
//        else{
//            empty_house_list.setVisibility(View.INVISIBLE);
//        }
        String address = enter_home_address.getText().toString();
        String city = enter_home_city.getText().toString();
        postNewHouse postNewHouse = new postNewHouse(address, city, keyToken);
        if(keyToken==null)
            throw new ExceptionInInitializerError();
        databaseReference.child(keyToken).child("shared houses").push().setValue(postNewHouse);
        Log.d("Test", "key token :  "+ keyToken);
//        Log.d("Test", "ddddddddd    "+databaseReference.child(keyToken).child("shared houses"));

        if(keyToken==null){
            throw new ExceptionInInitializerError();
        }else{
            displayHouse();

        }

        adapter.notifyDataSetChanged();

    }

    @Override
    protected void onResume() {
        super.onResume();
//        if(adapter.getItemCount()>0){
            displayHouse();

        databaseReference.addListenerForSingleValueEvent(valueEventListener);
    }

    private void displayHouse(){
//        Log.d("Test", "ddddddddd    "+databaseReference.child(keyToken).child("shared houses"));
        options =
                new FirebaseRecyclerOptions.Builder<postNewHouse>()
                    .setQuery(databaseReference.child(keyToken).child("shared houses"), postNewHouse.class).build();


        adapter =
                new FirebaseRecyclerAdapter<postNewHouse, SharedHouseRecycleViewHolder>(options) {


                    @Override
                    protected void onBindViewHolder(@NonNull SharedHouseRecycleViewHolder holder, final int position, @NonNull final postNewHouse model) {

                        holder.text_house_address.setText(model.getAddress());
                        holder.text_house_city.setText(model.getCity());
                        holder.sharedHouseId =getSnapshots().getSnapshot(position).getKey();
                        holder.sharedUserId = selectedKey;

                        holder.setiHouseItemClickListener(new IHouseItemClickListener() {
                            @Override
                            public void onClick(View view, int postion) {
                                selectPost = model;
                                selectedKey= getSnapshots().getSnapshot(position).getKey();
                                Log.d("Test", ""+selectedKey);


                                //bind data

                                enter_home_address.setText(model.getAddress());
                                enter_home_city.setText(model.getCity());



                            }
                        });
                    }

                    @NonNull
                    @Override
                    public SharedHouseRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_shared_houses_item, viewGroup, false);
                        return new SharedHouseRecycleViewHolder(itemView);
                    }
                };

        adapter.startListening();
        recyclerView.setAdapter(adapter);
//        if(recyclerView.getChildCount()==0){
//            empty_house_list.setVisibility(View.VISIBLE);
//            Log.d("Test", "TIS EMPTY!!!!!!!!!!!!!!!!!!!!!! ");
//        }
//        else{
//            empty_house_list.setVisibility(View.INVISIBLE);
//        }

    }
}
