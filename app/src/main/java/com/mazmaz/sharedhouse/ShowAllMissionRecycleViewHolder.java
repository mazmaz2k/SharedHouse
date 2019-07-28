package com.mazmaz.sharedhouse;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ShowAllMissionRecycleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
    private IHouseItemClickListener iHouseItemClickListener;

    public ShowAllMissionRecycleViewHolder(@NonNull View itemView) {
        super(itemView);
    }
    public void setiHouseItemClickListener(IHouseItemClickListener iHouseItemClickListener) {
        this.iHouseItemClickListener = iHouseItemClickListener;
    }
    @Override
    public void onClick(View view) {
        iHouseItemClickListener.onClick(view, getAdapterPosition());
    }
}
