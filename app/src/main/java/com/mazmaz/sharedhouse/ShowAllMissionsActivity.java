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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ShowAllMissionsActivity extends AppCompatActivity {

    FirebaseRecyclerAdapter<PostNewTodoMission, ShowAllMissionRecycleViewHolder> adapter;
    FirebaseRecyclerOptions<PostNewTodoMission> options;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    postNewHouse selectPost;
    String selectedKey;
    private String userToken,keyToken;
    RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_missions);

        final String userToken = getIntent().getStringExtra("shared_UserId");
        this.userToken = userToken;

//        final int show_only_houses_list = getIntent().getIntExtra("show_only_houses_list",1);

        final String keyToken= getIntent().getStringExtra("shared_HouseId");
        Log.d("Test","User token in show all missions "+ keyToken);
        Log.d("Test","User tokenin show all missions "+ userToken);
        this.keyToken=keyToken;

        recyclerView = findViewById(R.id.show_all_missions_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("SharedHouseUsers").child("houses");

//        databaseReference = firebaseDatabase.getReference("sharedhouseusers/SharedHouseUsers/houses/"+keyToken+"/shared houses");

        displayHouse();

    }


    @Override
    protected void onStop() {
        if(adapter!=null){
            adapter.stopListening();
        }
        super.onStop();
    }



    private void displayHouse(){
        options =
                new FirebaseRecyclerOptions.Builder<PostNewTodoMission>()
                        .setQuery(databaseReference.child(userToken).child("shared houses").child(keyToken).child("mission"), PostNewTodoMission.class).build();

        if(selectedKey!=null) {
//            Log.d("Test", "no Delete");
//            btn_update_house.setEnabled(true);
//            btn_delete_house.setEnabled(true);
        }
        adapter =
                new FirebaseRecyclerAdapter<PostNewTodoMission, ShowAllMissionRecycleViewHolder>(options) {




                    @Override
                    protected void onBindViewHolder(@NonNull ShowAllMissionRecycleViewHolder holder, final int position, @NonNull final PostNewTodoMission model) {

//                        holder.text_house_address.setText(model.getAddress());
//                        holder.text_house_city.setText(model.getCity());
//                        holder.sharedHouseId =getSnapshots().getSnapshot(position).getKey();
//                        holder.sharedUserId = keyToken;


//                        Log.d("Test", "sharedUserId in create new shared house: "+ holder.sharedUserId  );
//                        Log.d("Test", "sharedHouseId in create new shared house: "+ holder.sharedHouseId );
                        holder.setiHouseItemClickListener(new IHouseItemClickListener() {
                            @Override
                            public void onClick(View view, int postion) {
//                                selectPost = model;
//                                selectedKey= getSnapshots().getSnapshot(position).getKey();
//                                Log.d("Test", ""+selectedKey);
//
//
//                                //bind data
//
//                                enter_home_address.setText(model.getAddress());
//                                enter_home_city.setText(model.getCity());



                            }
                        });
                    }

                    @NonNull
                    @Override
                    public ShowAllMissionRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_to_do_missions_item, viewGroup, false);
                        return new ShowAllMissionRecycleViewHolder(itemView);
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
