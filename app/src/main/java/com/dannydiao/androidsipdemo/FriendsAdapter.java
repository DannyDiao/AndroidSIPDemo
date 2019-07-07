package com.dannydiao.androidsipdemo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.VH> {

    public static class VH extends RecyclerView.ViewHolder {
        public final TextView name;
        public final TextView ID;
        public final ImageView icon;

        public VH(View view) {
            super(view);
            name = view.findViewById(R.id.single_name);
            ID = view.findViewById(R.id.single_ID);
            icon = view.findViewById(R.id.single_icon);

        }
    }

    private ArrayList<String> nameArray;
    private ArrayList<String> IDArray;

    //FriendsAdapter的默认构造器
    public FriendsAdapter(ArrayList<String> data1, ArrayList<String> data2) {
        this.nameArray = data1;
        this.IDArray = data2;

    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int i) {
        vh.name.setText(nameArray.get(i));
        vh.ID.setText(IDArray.get(i));

    }

    @Override
    public int getItemCount() {
        return nameArray.size();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hall_single_line_xml, viewGroup, false);
        return new VH(v);
    }
}
