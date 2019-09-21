package com.mazmaz.sharedhouse;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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

public class ShowAllMissionsActivity extends AppCompatActivity {

    FirebaseRecyclerOptions<PostNewTodoMission> options;
    FirebaseRecyclerAdapter<PostNewTodoMission, ShowAllMissionRecycleViewHolder> adapter;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    PostNewTodoMission selectPost;
    private String selectedKey;
    private String userToken,keyToken;
    RecyclerView recyclerView;
    ValueEventListener valueEventListener;
    ImageButton btn_post_mission, btn_delete_mission, btn_update_mission;
    String m_name, m_date, m_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_missions);

        final String userToken = getIntent().getStringExtra("shared_UserId");
        this.userToken = userToken;

//        final int show_only_houses_list = getIntent().getIntExtra("show_only_houses_list",1);

        final String keyToken= getIntent().getStringExtra("shared_HouseId");
        Log.d("Test","User token in show all missions "+ keyToken);
        Log.d("Test","User token in show all missions "+ userToken);

        final String address_t = getIntent().getStringExtra("house_address");
        final String city_t = getIntent().getStringExtra("house_city");

        this.keyToken=keyToken;

        recyclerView = findViewById(R.id.show_all_missions_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("SharedHouseUsers").child("houses").child(userToken);

//        databaseReference = firebaseDatabase.getReference("sharedhouseusers/SharedHouseUsers/houses/"+keyToken+"/shared houses");
//        Log.d("Test", "1) ref:  "+ databaseReference.child("shared houses").
//                child(keyToken).child("mission"));



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



//        valueEventListener = new ValueEventListener() {
//
//
//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                int count = (int) dataSnapshot.child(keyToken).child("shared houses").getChildrenCount(); //Cast long to int
////                Log.d("Test", "House counter!!!!!!!!!!!!!!!!!!!!!!  "+ houses_count);
////
////                if (count ==0){
//////                    Log.d("Test", "TIS EMPTY!!!!!!!!!!!!!!!!!!!!!! #1");
//////                    empty_house_list.setVisibility(View.VISIBLE);
////
////
////                }else{
//////                    Log.d("Test", "TIS EMPTY!!!!!!!!!!!!!!!!!!!!!! #2:  "+ count);
//////                    empty_house_list.setVisibility(View.GONE);
////
////
////                }
////                houses_count = count;
////                if(selectedKey!=null) {
////                    Log.d("Test", "no Delete");
////                    btn_update_house.setEnabled(true);
////                    btn_delete_house.setEnabled(true);
////                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        };
//        databaseReference.addValueEventListener(valueEventListener);

        btn_post_mission = findViewById(R.id.btn_post_mission);
        btn_delete_mission = findViewById(R.id.btn_delete_mission);
        btn_update_mission = findViewById(R.id.btn_update_mission);

        btn_update_mission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedKey == null) {
                    Toast.makeText(ShowAllMissionsActivity.this, "Error - please select house for updating !", Toast.LENGTH_SHORT).show();

                } else {
                    Intent intent = new Intent(ShowAllMissionsActivity.this, CreateNewToDoActivity.class);
                    intent.putExtra("shared_HouseId", keyToken);
                    intent.putExtra("shared_UserId", userToken);

                    intent.putExtra("house_address",address_t );
                    intent.putExtra("house_city", city_t );

                    intent.putExtra("mission_name", m_name);
                    intent.putExtra("mission_date", m_date);
                    intent.putExtra("mission_content",m_content );

                    intent.putExtra("selectedKey", selectedKey);
                    intent.putExtra("updatingOrPostingKey","UP");
                    if(adapter!=null){
                        adapter.notifyDataSetChanged();

                    }

                    finish();
                    startActivity(intent);
                }
            }
        });
        btn_post_mission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ShowAllMissionsActivity.this, CreateNewToDoActivity.class);
                intent.putExtra("shared_HouseId", keyToken);
                intent.putExtra("shared_UserId", userToken);
                intent.putExtra("updatingOrPostingKey","POS");
                intent.putExtra("house_address",address_t );
                intent.putExtra("house_city", city_t );
                if(adapter!=null){
                    adapter.notifyDataSetChanged();

                }

                finish();
                startActivity(intent);

            }
        });

        btn_delete_mission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedKey == null) {
//                    Log.d("Test", "Delete");
                    Toast.makeText(ShowAllMissionsActivity.this, "Can't delete please select house!!!", Toast.LENGTH_SHORT).show();

//            throw new NullPointerException();
                } else {

                    databaseReference.child("shared houses").
                            child(keyToken).child("mission").child(selectedKey)
                            .removeValue()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(ShowAllMissionsActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
//                        Log.d("Test", databaseReference.getParent().getKey());
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ShowAllMissionsActivity.this, "Error on delete: " + e.getMessage(), Toast.LENGTH_SHORT).show();


                        }
                    });
//                    Log.d("Test", "ddddddddddd " + databaseReference.child(keyToken).toString());
                    selectedKey = null;

//                    if (databaseReference.child(userToken).equals("")) {
//                        Log.d("Test", "TIS EMPTY!!!!!!!!!!!!!!!!!!!!!! #3");
//
//                    }
                }

            }
        });
        if(adapter!=null){
            adapter.notifyDataSetChanged();

        }

    }


    @Override
    protected void onStop() {
        if(adapter!=null){
            adapter.stopListening();
        }
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        adapter.startListening();
//        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try{
            displayMissions();

        }catch (Exception e){
//            empty_house_list.setVisibility(View.VISIBLE);
            e.printStackTrace();
        }
    }

    private void displayMissions(){

        options =
                new FirebaseRecyclerOptions.Builder<PostNewTodoMission>()
                        .setQuery(databaseReference.child("shared houses").
                                child(keyToken).child("mission"), PostNewTodoMission.class).build();

        adapter = new FirebaseRecyclerAdapter<PostNewTodoMission, ShowAllMissionRecycleViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ShowAllMissionRecycleViewHolder holder, final int position, @NonNull final PostNewTodoMission model) {
//                Log.d("Test", "model "+ model.getContent());
                holder.text_mission_name.setText(model.getName());
                holder.text_mission_date.setText(model.getDate());
                holder.text_mission_contact.setText(model.getContent());


                holder.setIMissionItemClickListener(new IMissionItemClickListener() {
                    @Override
                    public void onClick(View view, int postion) {
                        selectPost = model;
                        selectedKey= getSnapshots().getSnapshot(position).getKey();
                        m_name = model.getName();
                        m_content = model.getContent();
                        m_date = model.getDate();


                    }
                });
            }

            @NonNull
            @Override
            public ShowAllMissionRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_to_do_missions_item, viewGroup, false);
//                Log.d("Test", "itemView---  "+itemView.getContext());
                return new ShowAllMissionRecycleViewHolder(itemView);
            }
        };


        adapter.startListening();
        recyclerView.setAdapter(adapter);


    }

}
