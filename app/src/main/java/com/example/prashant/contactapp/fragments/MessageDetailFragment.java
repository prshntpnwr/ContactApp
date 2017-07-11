package com.example.prashant.contactapp.fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.prashant.contactapp.R;
import com.example.prashant.contactapp.data.MessageLoader;
import com.example.prashant.contactapp.objects.MessageDetails;
import com.example.prashant.contactapp.ui.MessageProviderHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageDetailFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();

    private View mRootView;
    private Toolbar mToolbar;

    private String id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_message_detail, container, false);
        mToolbar = (Toolbar) mRootView.findViewById(R.id.otp_toolbar);

        setupToolbar();

        id = getActivity().getIntent().getStringExtra("id");
        Log.d(TAG, "intent receive from adapter is " + id);
        bindViewFromID(id);

        return mRootView;
    }

    private void setupToolbar() {
        if (mToolbar != null) {
            mToolbar.setNavigationIcon(ContextCompat
                    .getDrawable(getActivity(), R.drawable.ic_back));
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().supportFinishAfterTransition();
                }
            });
        }
    }

    private void bindViewFromID(String id) {
        if (mRootView == null) {
            return;
        }

        TextView optText = (TextView) mRootView.findViewById(R.id.otp_msg);
        TextView optUser = (TextView) mRootView.findViewById(R.id.otp_user);
        TextView optTime = (TextView) mRootView.findViewById(R.id.otp_time);

        try {
            MessageDetails messageDetails = MessageProviderHelper.getMovieFromDatabase(getActivity(), id);

            Log.d(TAG, "Message Detail - " + messageDetails.toString());

            optUser.setText(messageDetails.getName());
            optText.setText(messageDetails.getOpt());
            optTime.setText(messageDetails.getTime() + " on " + messageDetails.getDate());

        } catch (Exception e) {
            Log.d(TAG, "ERROR " + e);
            e.printStackTrace();
        }


    }

}