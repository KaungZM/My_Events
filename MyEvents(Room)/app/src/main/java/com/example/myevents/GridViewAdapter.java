package com.example.myevents;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GridViewAdapter extends RecyclerView.Adapter<GridViewAdapter.ViewHolder> {

    private List<Event> list;

    public List<Event> getList() {
        return list;
    }

    public void setList(List<Event> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    Context context;

    public GridViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = null;
        if(viewType == R.layout.event) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event, null);
        }
        if(viewType == R.layout.addbutton) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.addbutton, null);
        }
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("adapter debug", String.valueOf(position));
        if(position != list.size()) {
            Event event = list.get(position);
            holder.id = list.get(position).id;
            holder.position = position;
            holder.name.setText(event.name);
            holder.date.setText(event.date);
            holder.location.setText(event.location);
            holder.description.setText(event.description);
        }
    }

    @Override
    public int getItemCount() {
        return (list != null) ? list.size() + 1 : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == list.size()) ? R.layout.addbutton : R.layout.event;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public int position;
        public int id;
        protected TextView name;
        protected TextView date;
        protected TextView location;
        protected TextView description;
        protected ImageView add;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            context = view.getContext();
            this.name = (TextView) view.findViewById(R.id.name);
            this.date = (TextView) view.findViewById(R.id.date);
            this.location = (TextView) view.findViewById(R.id.location);
            this.description = (TextView) view.findViewById(R.id.description);
            this.add = (ImageView) view.findViewById(R.id.addButton);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, EditActivity.class);
            if(getItemViewType() == R.layout.event) {
                intent.putExtra("position", position);
                intent.putExtra("id", id);
            } else {
                intent.putExtra("position", -1);
            }
            context.startActivity(intent);
        }
    }
}
