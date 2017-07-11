package com.example.prashant.contactapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prashant.contactapp.data.MessageContract;
import com.example.prashant.contactapp.data.MessageContract.MessageEntry;
import com.example.prashant.contactapp.data.MessageLoader;
import com.example.prashant.contactapp.objects.Contacts;
import com.example.prashant.contactapp.R;
import com.example.prashant.contactapp.ui.ContactDetailActivity;
import com.example.prashant.contactapp.ui.MessageDetailActivity;

import java.util.ArrayList;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder>{

    private final String TAG = getClass().getSimpleName();

    private Context mContext;
    private Cursor mCursor;

    public MessageListAdapter(Cursor cursor, Context context) {
        this.mCursor = cursor;
        this.mContext = context;
    }

    @Override
    public long getItemId(int position) {
        mCursor.moveToPosition(position);
        return mCursor.getLong(MessageLoader.Query.KEY_ID);
    }

    @Override
    public MessageListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_message, parent, false);

        final ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ID = String.valueOf(getItemId(vh.getAdapterPosition()));
                Toast.makeText(mContext, ID, Toast.LENGTH_SHORT).show();

                final Bundle args = new Bundle();
                args.putString("id", ID);
                Intent intent = new Intent(v.getContext(), MessageDetailActivity.class);
                intent.putExtras(args);
                v.getContext().startActivity(intent);
                Log.d(TAG, "Message id send & " + " ID is - " + ID);
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(MessageListAdapter.ViewHolder holder, int position) {
        holder.name.setText(mCursor.getString(MessageLoader.Query.KEY_NANME));

        holder.time.setText(mCursor.getString(MessageLoader.Query.KEY_DATE)  + " on " +
                mCursor.getString(MessageLoader.Query.KEY_TIME));
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView time;

        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.msg_name);
            time = (TextView) v.findViewById(R.id.msg_time);

        }
    }
}
