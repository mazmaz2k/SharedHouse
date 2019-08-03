package com.mazmaz.sharedhouse;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ShowAllMissionRecycleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

    IMissionItemClickListener iMissionItemClickListener;
    TextView text_mission_name, text_mission_date, text_mission_contact;
//    Button delete_mission_btn;


    public void setIMissionItemClickListener(IMissionItemClickListener iMissionItemClickListener) {
        this.iMissionItemClickListener = iMissionItemClickListener;


    }


    public ShowAllMissionRecycleViewHolder(@NonNull View itemView) {
        super(itemView);
        text_mission_name = itemView.findViewById(R.id.text_mission_name);
        text_mission_date = itemView.findViewById(R.id.text_mission_date);
        text_mission_contact = itemView.findViewById(R.id.text_mission_contact);
//        delete_mission_btn = itemView.findViewById(R.id.delete_mission_btn);
        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        iMissionItemClickListener.onClick(view, getAdapterPosition());
    }
}
