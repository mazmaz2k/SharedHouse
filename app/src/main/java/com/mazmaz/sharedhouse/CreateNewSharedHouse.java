package com.mazmaz.sharedhouse;

import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
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

import java.util.HashMap;
import java.util.LinkedList;


public class CreateNewSharedHouse extends AppCompatActivity {

    ImageButton btn_post_house, btn_update_house, btn_delete_house;
    EditText enter_home_address;
    EditText enter_home_city;
    RecyclerView recyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseRecyclerOptions<PostNewHouse> options;
    FirebaseRecyclerAdapter<PostNewHouse, SharedHouseRecycleViewHolder> adapter;
    PostNewHouse selectPost;
    String selectedKey;
    private String userToken,keyToken;
    LinearLayout create_housed_layout;
    TextView empty_house_list;
    ValueEventListener valueEventListener;
    private int show_only_houses_list;
    private int houses_count= 0;
    private GestureDetector gd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_shared_house);
        final String userToken = getIntent().getStringExtra("UserToken");
        this.userToken = userToken;

        final int show_only_houses_list = getIntent().getIntExtra("show_only_houses_list",1);
        final String keyToken= getIntent().getStringExtra("token_key");
        Log.d("Test","Key token(houses) "+ keyToken);
        Log.d("Test","User token "+ userToken);
        this.show_only_houses_list = show_only_houses_list;
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
        try{
            displayHouse();

        }catch (Exception e){
//            empty_house_list.setVisibility(View.VISIBLE);
            e.printStackTrace();
        }
//        if(selectedKey==null){
//            Log.d("Test","Delete");
//            btn_update_house.setEnabled(false);
//            btn_delete_house.setEnabled(false);
////            throw new NullPointerException();
//        }
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

//                final LinkedList<PostNewTodoMission> postNewTodoMissionList = new LinkedList<>();
                final LinkedList<HashMap<String, Object>> postNewTodoMissionList_2 = new LinkedList<>();
                if(selectedKey==null){
//                    throw new NullPointerException();
                    Toast.makeText(CreateNewSharedHouse.this, "not updated please select !!", Toast.LENGTH_SHORT).show();

                }else{

                     databaseReference.
                             child(keyToken).child("shared houses").child(selectedKey).child("mission").addValueEventListener(new ValueEventListener() {
                         @Override
                         public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

//                             boolean fl = false;
//                             Log.d("Test", "1) in on update data changed ---- "+ keyToken+"/shared houses/"+selectedKey);

//                             for(PostNewTodoMission p : postNewTodoMissionList){
//                                 Log.d("Test","1) postNewTodoMission "+ p.getMission() +
//                                         " "+ p.getMissionDate()+" "+ p.getMissionContent());

//                                 HashMap<String, Object> result = new HashMap<>();
//                                 result.put("Mission Name", p.getMission());
//                                 result.put("Mission Date", p.getMissionDate());
//                                 result.put("Mission Content", p.getMissionContent());
//                                 postNewTodoMissionList_2.add(result);

//                             }

                             final Handler handler = new Handler();

                             final Runnable r = new Runnable() {
                                 @Override
                                 public void run() {

                                     for (DataSnapshot children: dataSnapshot.getChildren()){
//                                 missions.add(String.valueOf(children.getValue()) + "1");
//                                 if(fl==true){
//                                     break;
//                                 }

//                                 postNewTodoMissionList.add( new PostNewTodoMission(children.child("Mission Name").getValue().toString()
//                                         ,children.child("Mission Date").getValue().toString(),
//                                         children.child("Mission Content").getValue().toString()));

                                         if(children.child("Mission Name")==null){
                                             break;
                                         }
                                         try {
                                             HashMap<String, Object> result = new HashMap<>();
                                             result.put("content", children.child("content").getValue().toString());
                                             result.put("date", children.child("date").getValue().toString());
                                             result.put("name", children.child("name").getValue().toString());

//                                             Log.d("Test","1) postNewTodoMission "+ result.values().toString());

                                             postNewTodoMissionList_2.add(result);

                                         }catch (Exception e ){
                                             e.printStackTrace();
                                         }

//                                 Log.d("Test","2) postNewTodoMission "+ children.child("Mission Name").getValue().toString() + " "+
//                                         children.child("Mission Date").getValue().toString()+
//                                         " "+ children.child("Mission Content").getValue().toString());

//                                 children.child("Mission Content").getValue();
//                                 for (DataSnapshot child : children.getChildren()) {
//
////                                     missions.add(String.valueOf(child.getValue())+ "2");
//
////                                     if(child.getKey().equals("mission")){
////                                         if(child.getValue().equals(user.getEmail())){
////                                             Log.d("Test", "setToken token: "+ child.getValue());
////                                             key_token=children.getKey();
////                                             break;
////                                         }
////                                         continue;
//                                     }
//                                 }

                                     }
                                     Log.d("Test","1) handler ");

//                                     handler.postDelayed(this, 1000);

                                 }
                             };
                             Log.d("Test","2) handler ");

                             handler.postDelayed(r, 1000);


                         }

                         @Override
                         public void onCancelled(@NonNull DatabaseError databaseError) {

                         }
                     });

//                     Log.d("Test","postNewTodoMissionList"+ postNewTodoMissionList.toString());



//                     databaseReference.
//                             child(keyToken).child("shared houses").child(selectedKey).child("mission").addValueEventListener(new ValueEventListener() {
//                         @Override
//                         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                             missions[0] = dataSnapshot.getValue();
//                         }
//
//                         @Override
//                         public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                         }
//                     })
//                    for(String s : missions){
//                        Log.d("Test","ssssss: "+ s);
//                    }
////                    Log.d("Test", "Missions ---- "+ missions.toString());
//
//                    Log.d("Test", "3) in on update data changed ---- "+ missions.toString());

                    databaseReference.
                            child(keyToken).child("shared houses").child(selectedKey)
                            .setValue(
                                    new PostNewHouse(
                                            enter_home_address.getText().toString(),enter_home_city.getText().toString(),
                                            keyToken

                                    )).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            for(HashMap r : postNewTodoMissionList_2){
//                                Log.d("Test","4) postNewTodoMission "+ r.values().toString());

                                databaseReference.
                                        child(keyToken).child("shared houses").child(selectedKey).child("mission").push().updateChildren(r);
                            }
                            Toast.makeText(CreateNewSharedHouse.this, "updated", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CreateNewSharedHouse.this, "Error on updating: "+ e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

//                    databaseReference.
//                            child(keyToken).child("shared houses").child(selectedKey).child("mission")
//                            .setValue(postNewTodoMissionList_2)
//                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            Toast.makeText(CreateNewSharedHouse.this, "Success in adding mission: ", Toast.LENGTH_SHORT).show();
//
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(CreateNewSharedHouse.this, "Error on updating: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
//
//                        }
//                    });

//                    HashMap<String, Object> result = new HashMap<>();
//                    result.put("Mission Name", mission);
//                    result.put("Mission Date", missionDate);
//                    result.put("Mission Content", missionContent);


                    empty_house_list.setVisibility(View.GONE);

                }
            }
        });

        btn_delete_house.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (selectedKey == null) {
//                    Log.d("Test", "Delete");
                    Toast.makeText(CreateNewSharedHouse.this, "Can't delete please select house!!!", Toast.LENGTH_SHORT).show();

//            throw new NullPointerException();
                } else {

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
                            Toast.makeText(CreateNewSharedHouse.this, "Error on delete: " + e.getMessage(), Toast.LENGTH_SHORT).show();


                        }
                    });
//                    Log.d("Test", "ddddddddddd " + databaseReference.child(keyToken).toString());
                    selectedKey = null;

                    if (databaseReference.child(userToken).equals("")) {
                        Log.d("Test", "TIS EMPTY!!!!!!!!!!!!!!!!!!!!!! #3");

                    }
                }

                if(houses_count==0){
                    empty_house_list.setVisibility(View.VISIBLE);
                }
            }
        });
//        DatabaseReference DB_r = databaseReference.child(userToken);

        //updating the number of houses to each user
        valueEventListener = new ValueEventListener() {



            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(keyToken==null){
                    empty_house_list.setVisibility(View.VISIBLE);
                    return;
                }
                int count = (int) dataSnapshot.child(keyToken).child("shared houses").getChildrenCount(); //Cast long to int
                Log.d("Test", "House counter!!!!!!!!!!!!!!!!!!!!!!  "+ houses_count);

                if (count ==0){
//                    Log.d("Test", "TIS EMPTY!!!!!!!!!!!!!!!!!!!!!! #1");
                    empty_house_list.setVisibility(View.VISIBLE);


                }else{
//                    Log.d("Test", "TIS EMPTY!!!!!!!!!!!!!!!!!!!!!! #2:  "+ count);
                    empty_house_list.setVisibility(View.GONE);


                }
                houses_count = count;
//                if(selectedKey!=null) {
//                    Log.d("Test", "no Delete");
//                    btn_update_house.setEnabled(true);
//                    btn_delete_house.setEnabled(true);
//                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        databaseReference.addValueEventListener(valueEventListener);
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

        String address = enter_home_address.getText().toString();
        String city = enter_home_city.getText().toString();
        PostNewHouse postNewHouse = new PostNewHouse(address, city, keyToken);
        if(keyToken==null)
            throw new ExceptionInInitializerError();
        databaseReference.child(keyToken).child("shared houses").push().setValue(postNewHouse);
        if(keyToken==null){
            throw new ExceptionInInitializerError();
        }else{
            displayHouse();

        }

        adapter.notifyDataSetChanged();
        empty_house_list.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if(adapter.getItemCount()>0){
        if(keyToken!=null){
            displayHouse();

        }
//        Log.d("Test","Key token"+ keyToken);
//        Log.d("Test","User token"+ userToken);
        databaseReference.addValueEventListener(valueEventListener);
    }

    private void displayHouse(){

        options =
                new FirebaseRecyclerOptions.Builder<PostNewHouse>()
                    .setQuery(databaseReference.child(keyToken).child("shared houses"), PostNewHouse.class).build();

        if(selectedKey!=null) {
//            Log.d("Test", "no Delete");
            btn_update_house.setEnabled(true);
            btn_delete_house.setEnabled(true);
        }
        adapter =
                new FirebaseRecyclerAdapter<PostNewHouse, SharedHouseRecycleViewHolder>(options) {




                    @Override
                    protected void onBindViewHolder(@NonNull SharedHouseRecycleViewHolder holder, final int position, @NonNull final PostNewHouse model) {

                        Log.d("Test","house address model - "+model.getAddress());
                        Log.d("Test","house city model- "+ model.getCity());
                        holder.text_house_address.setText(model.getAddress());
                        holder.text_house_city.setText(model.getCity());
                        holder.sharedHouseId =getSnapshots().getSnapshot(position).getKey();
                        holder.sharedUserId = keyToken;


//                        Log.d("Test", "sharedUserId in create new shared house: "+ holder.sharedUserId  );
//                        Log.d("Test", "sharedHouseId in create new shared house: "+ holder.sharedHouseId );
                        holder.setiHouseItemClickListener(new IHouseItemClickListener() {
                            @Override
                            public void onClick(View view, int postion) {
                                selectPost = model;
                                selectedKey= getSnapshots().getSnapshot(position).getKey();
//                                Log.d("Test", ""+selectedKey);


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

    }



}
