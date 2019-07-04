package com.mazmaz.sharedhouse;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_shared_house);
        btn_post_house = findViewById(R.id.btn_post_house);
        btn_update_house = findViewById(R.id.btn_update_house);
        btn_delete_house = findViewById(R.id.btn_delete_house);
        enter_home_address = findViewById(R.id.enter_home_address);
        enter_home_city = findViewById(R.id.enter_home_city);
        recyclerView = findViewById(R.id.new_sharedHouse_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("SharedHouseUsers");


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
                Log.d("Test","in new house btn " );
                postHouse();

            }
        });

        btn_update_house.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.
                        child(selectedKey).setValue(
                                new postNewHouse(enter_home_address.getText().toString(),enter_home_city.getText().toString()
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
            }
        });

        btn_delete_house.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.
                        child(selectedKey)
                        .removeValue()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(CreateNewSharedHouse.this, "Deleted", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CreateNewSharedHouse.this, "Error on delete: "+ e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });



    }

    @Override
    protected void onStop() {
        if(adapter!=null){
            adapter.stopListening();
        }
        super.onStop();
    }

    private void postHouse(){
        String address = enter_home_address.getText().toString();
        String city = enter_home_city.getText().toString();
        postNewHouse postNewHouse = new postNewHouse(address, city);
        databaseReference.push().setValue(postNewHouse);
        displayHouse();

        adapter.notifyDataSetChanged();

    }

    @Override
    protected void onResume() {
        super.onResume();
//        if(adapter.getItemCount()>0){
            displayHouse();


    }

    private void displayHouse(){
        options =
                new FirebaseRecyclerOptions.Builder<postNewHouse>()
                    .setQuery(databaseReference, postNewHouse.class).build();

        adapter =
                new FirebaseRecyclerAdapter<postNewHouse, SharedHouseRecycleViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull SharedHouseRecycleViewHolder holder, final int position, @NonNull final postNewHouse model) {
                        holder.text_house_address.setText(model.getAddress());
                        holder.text_house_city.setText(model.getCity());


                        holder.setiHouseItemClickListener(new IHouseItemClickListener() {
                            @Override
                            public void onClick(View view, int postion) {
                                selectPost = model;
                                selectedKey= getSnapshots().getSnapshot(position).getKey();
                                Log.d("Key house item", ""+selectedKey);

                                //bind data

                                enter_home_address.setText(model.getAddress());
                                enter_home_city.setText(model.getCity());


                            }
                        });
                    }

                    @NonNull
                    @Override
                    public SharedHouseRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View itemView = LayoutInflater.from(getBaseContext()).inflate(R.layout.post_shared_houses_item, viewGroup, false);
                        return new SharedHouseRecycleViewHolder(itemView);
                    }
                };

        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }
}
