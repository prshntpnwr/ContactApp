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

import com.example.prashant.contactapp.adapters.ContactListAdapter;
import com.example.prashant.contactapp.objects.Contacts;
import com.example.prashant.contactapp.objects.ContactsHelper;
import com.example.prashant.contactapp.objects.GsonResponse;
import com.example.prashant.contactapp.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import static com.example.prashant.contactapp.objects.ContactsHelper.contactList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ContactFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ContactFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private final String TAG = getClass().getSimpleName();

    public View mRootView;
    public RecyclerView mRecyclerView;
    public ContactListAdapter mAdapter;
    public LinearLayoutManager linearLayoutManager;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContactFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContactFragment newInstance(String param1, String param2) {
        ContactFragment fragment = new ContactFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

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
