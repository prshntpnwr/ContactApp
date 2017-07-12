package com.example.prashant.contactapp.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.prashant.contactapp.R;
import com.example.prashant.contactapp.adapters.ContactListAdapter;
import com.example.prashant.contactapp.objects.Contacts;
import com.example.prashant.contactapp.objects.GsonResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import static com.example.prashant.contactapp.objects.ContactsHelper.contactList;

/**
 * this contains the first tab of our main activity,
 * show a list of contact with name,
 */
public class ContactFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();

    public View mRootView;
    public RecyclerView mRecyclerView;
    public ContactListAdapter mAdapter;
    public LinearLayoutManager linearLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_contact, container, false);
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.recycler_view_contact);
        linearLayoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);

        mAdapter = new ContactListAdapter(contactList, getContext());

        new fetchContactTask().execute();

        return mRootView;
    }

    public class fetchContactTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            try {
                Gson gson = new Gson();
                contactList = gson.fromJson(GsonResponse.sampleJson, new TypeToken<ArrayList<Contacts>>() {
                }.getType());

                Log.d(TAG, " Contact length is - " + contactList.size());
            } catch (Exception e) {
                Log.e(TAG, "ERROR " + e);
                return null;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mRecyclerView.setLayoutManager(linearLayoutManager);
            mAdapter = new ContactListAdapter(contactList, getContext());
            mRecyclerView.setAdapter(mAdapter);
        }
    }
}
