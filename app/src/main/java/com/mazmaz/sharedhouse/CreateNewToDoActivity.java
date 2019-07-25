package com.mazmaz.sharedhouse;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CreateNewToDoActivity extends FragmentActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    TextView mission_details_txt, enter_mission_title_txt;
    static TextView selected_date_textView;
    Button missions_date_btn, submit_new_mission_btn ;
    String sharedHouse_address, sharedHouse_city, sharedUserId, sharedHouseId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_new_to_do);
        final String sharedHouse_id = getIntent().getStringExtra("shared_HouseId");
        final String sharedHouse_address = getIntent().getStringExtra("house_address");
        final String sharedHouse_city = getIntent().getStringExtra("house_city");
        final String sharedUser_Id = getIntent().getStringExtra("shared_UserId");

        this.sharedHouse_address = sharedHouse_address;
        this.sharedHouse_city = sharedHouse_city;
        this.sharedUserId = sharedUser_Id;
        this.sharedHouseId = sharedHouse_id;
//        Log.d("Test",sharedHouse_id);
        Log.d("Test","Key token in create mission "+ sharedHouseId);
        Log.d("Test","User tokenin create mission "+ sharedUserId);
        selected_date_textView = findViewById(R.id.selected_date_textView);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(sharedHouse_id);
        databaseReference = firebaseDatabase.getReference("SharedHouseUsers/houses/"+sharedUser_Id+"/shared houses/"+sharedHouse_id);

        missions_date_btn = findViewById(R.id.missions_date_btn);
        mission_details_txt = findViewById(R.id.mission_details_txt);
        enter_mission_title_txt = findViewById(R.id.enter_mission_title_txt);



//                Log.d("Test",databaseReference.toString());
        submit_new_mission_btn = findViewById(R.id.submit_new_mission_btn);
        missions_date_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker(view);
            }
        });

        submit_new_mission_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d("Test", "1: "+mission_details_txt.length());
//                Log.d("Test", "2: "+selected_date_textView.getText().equals("Date Picker for mission"));
//                Log.d("Test", "3: "+enter_mission_title_txt.getText().equals(" "));

//                Log.d("Test", "4: "+enter_mission_title_txt.getText().toString());

                if(mission_details_txt.getText().length()==0 || enter_mission_title_txt.getText().length()==0
                        || selected_date_textView.getText().equals("Date Picker for mission")||
                mission_details_txt==null)
                {
                    Toast.makeText(CreateNewToDoActivity.this,"Make sure all fields are filed", Toast.LENGTH_SHORT).show();
                }else {
                    post_TodoMission();
//                    Toast.makeText(CreateNewToDoActivity.this,"Not working", Toast.LENGTH_SHORT).show();

                }
            }
        });



    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void post_TodoMission() {
        PostNewTodoMission postNewTodoMission = new PostNewTodoMission(enter_mission_title_txt.getText().toString(), selected_date_textView.getText().toString(),mission_details_txt.getText().toString());

        String key = databaseReference.child("mission").push().getKey();
//        Post post = new Post(userId, username, title, body);
//                    SharedHome sharedHome = new SharedHome(userMail);

        Map<String, Object> sharedHomeValues = postNewTodoMission.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
//                    childUpdates.put("/houses/" + key, sharedHomeValues);
        childUpdates.put("/mission/" + key, sharedHomeValues);

        databaseReference.updateChildren(childUpdates);

        Intent intent = new Intent(CreateNewToDoActivity.this, SharedHouse.class);
        intent.putExtra("sharedHouseAddress",sharedHouse_address);
        intent.putExtra("sharedHouseCity",sharedHouse_city);
        intent.putExtra("sharedHouseId",sharedHouseId);
        intent.putExtra("sharedUserId",sharedUserId);

        finish();
        startActivity(intent);
    }

    public void showDatePicker(View v) {
        DialogFragment newDateFragment = new MissionDatePickFragment();
        newDateFragment.show(getSupportFragmentManager(), "date picker");
    }




    public static class MissionDatePickFragment extends DialogFragment {

        public MissionDatePickFragment(){}
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), dateSetListener, year, month, day);
        }

        private DatePickerDialog.OnDateSetListener dateSetListener =
                new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int month, int day) {

                        Toast.makeText(getActivity(), "selected date is " + view.getYear() +
                                " / " + (view.getMonth()+1) +
                                " / " + view.getDayOfMonth(), Toast.LENGTH_SHORT).show();

                        selected_date_textView.setText( month+"/"+day+"/"+year);
                        selected_date_textView.setTextSize(30);
                    }


                };

//        @Override
//        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
//            showSetDate(year,monthOfYear,dayOfMonth);
//
//        }
//
//        private void showSetDate(int year, int monthOfYear, int dayOfMonth) {
//            selected_date_textView.setText( year+"/"+monthOfYear+"/"+dayOfMonth);
//        }
//
////        private String showSetDate(int year, int monthOfYear, int dayOfMonth) {
////            return year+"/+"+monthOfYear+"/"+dayOfMonth;
////        }
    }
}
