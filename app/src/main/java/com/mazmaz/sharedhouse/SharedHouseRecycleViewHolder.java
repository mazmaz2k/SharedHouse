package com.mazmaz.sharedhouse;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SharedHouseRecycleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

//    TextView text_mission, text_mission_date,text_mission_contact;
     TextView text_house_address, text_house_city;
     Button enter_shared_house_btn;
     String sharedHouseId, sharedUserId;
    IHouseItemClickListener iHouseItemClickListener;

    public void setiHouseItemClickListener(IHouseItemClickListener iHouseItemClickListener) {
        this.iHouseItemClickListener = iHouseItemClickListener;
    }

    public SharedHouseRecycleViewHolder(@NonNull View itemView) {
        super(itemView);
//        text_mission = itemView.findViewById(R.id.text_mission);
//        text_mission_contact = itemView.findViewById(R.id.text_mission_contact);
//        text_mission_date = itemView.findViewById(R.id.text_mission_date);

        text_house_address = itemView.findViewById(R.id.text_house_address);
        text_house_city = itemView.findViewById(R.id.text_house_city);

        enter_shared_house_btn = itemView.findViewById(R.id.enter_shared_house_btn);
        itemView.setOnClickListener(this);

        enter_shared_house_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), SharedHouse.class);
                intent.putExtra("sharedHouseAddress",text_house_address.getText().toString());
                intent.putExtra("sharedHouseCity",text_house_city.getText().toString());
                intent.putExtra("sharedHouseId",sharedHouseId);
                intent.putExtra("sharedUserId",sharedUserId);

                view.getContext().startActivity(intent);


            }
        });
    }

    @Override
    public void onClick(View view) {
        iHouseItemClickListener.onClick(view, getAdapterPosition());
    }
}
