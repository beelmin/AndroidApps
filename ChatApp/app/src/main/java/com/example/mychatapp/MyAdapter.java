package com.example.mychatapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<ListItem> listItems;
    private Context context;

    public MyAdapter(List<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final ListItem listItem = listItems.get(i);

        viewHolder.user.setText(listItem.getUser());
        viewHolder.active.setText(listItem.getActive());

        viewHolder.user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(v.getContext(),"trenutni: " + listItem.getCurrentUser() + " kliknuti : " + listItem.getUser(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,Chat.class);
                intent.putExtra("current_user",listItem.getCurrentUser());
                intent.putExtra("other_user",listItem.getUser());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView user;
        public TextView active;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            user = (TextView) itemView.findViewById(R.id.user);
            active = (TextView) itemView.findViewById(R.id.active);

        }
    }
}

