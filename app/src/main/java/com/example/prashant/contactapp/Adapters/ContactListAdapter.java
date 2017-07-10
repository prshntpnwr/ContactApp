package com.example.prashant.contactapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prashant.contactapp.Objects.Contacts;
import com.example.prashant.contactapp.R;

import java.util.ArrayList;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder>{

    private final String TAG = getClass().getSimpleName();

    private ArrayList<Contacts> mContactList = new ArrayList<>();
    private Context mContext;

    public ContactListAdapter(ArrayList<Contacts> ContactList, Context context) {
        this.mContactList = ContactList;
        this.mContext = context;
        //Log.d(TAG, " Movie adapter MovieList " + ContactList.size() + " " + mContactList.size());
    }

    private String getItem(int position) {
        return mContactList.get(position).getId();
    }

    @Override
    public ContactListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_contact, parent, false);

        final ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Position " + getItem(vh.getAdapterPosition()), Toast.LENGTH_SHORT).show();
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(ContactListAdapter.ViewHolder holder, int position) {
        holder.titleView.setText(mContactList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mContactList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleView;

        public ViewHolder(View v) {
            super(v);
            titleView = (TextView) v.findViewById(R.id.user_name);
        }
    }
}
