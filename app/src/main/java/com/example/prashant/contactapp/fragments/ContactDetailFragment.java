package com.example.prashant.contactapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.prashant.contactapp.R;
import com.example.prashant.contactapp.objects.ContactsHelper;
import com.example.prashant.contactapp.ui.SmsActivity;

/**
 * A simple {@link Fragment} subclass.
 * use to show contact details(name, number and a floating button to send otp) when user click on a
 * particular contact,
 * FAB (floating action button) is used to send otp
 */
public class ContactDetailFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();

    private View mRootView;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private Toolbar mToolbar;
    private FloatingActionButton fab;
    private ImageView mBackdrop;

    public static String id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView = inflater.inflate(R.layout.fragment_contact_detail, container, false);

        mCollapsingToolbarLayout = (CollapsingToolbarLayout) mRootView.findViewById(R.id.collapsing_tool);
        mToolbar = (Toolbar) mRootView.findViewById(R.id.toolbar);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mBackdrop = (ImageView) mRootView.findViewById(R.id.backdrop);

        fab = (FloatingActionButton) mRootView.findViewById(R.id.fab);

        mCollapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(android.R.color.white));

        setupToolbar();

        id = getActivity().getIntent().getStringExtra("id");
        Log.d(TAG, "intent receive from adapter is " + id);

        getContactFromID(id);

        return mRootView;
    }

    private void getContactFromID(String id) {
        if (mRootView == null) {
            return;
        }

        TextView numView = (TextView) mRootView.findViewById(R.id.detail_number);
        TextView nameView = (TextView) mRootView.findViewById(R.id.detail_user_name);

        try {
            Glide.with(this.getContext())
                    .load(R.drawable.contact_bg)
                    .placeholder(R.color.colorAccent)
                    .error(R.color.colorPrimary)
                    .into(mBackdrop);

            nameView.setText(ContactsHelper.getContact(id).getName());
            numView.setText(ContactsHelper.getContact(id).getNumber());

            mCollapsingToolbarLayout.setTitle(ContactsHelper.getContact(id).getName());

        } catch (Exception e) {
            Log.e(TAG, "Error " + e);
            e.printStackTrace();
        }

        fabAction();
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

    private void fabAction() {

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Bundle args = new Bundle();
                args.putString("id", id);
                Intent intent = new Intent(view.getContext(), SmsActivity.class);
                intent.putExtras(args);
                view.getContext().startActivity(intent);
                Log.d(TAG, "detail intent send");

            }
        });
    }
}
