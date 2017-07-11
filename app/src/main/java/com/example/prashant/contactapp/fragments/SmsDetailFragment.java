package com.example.prashant.contactapp.fragments;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.prashant.contactapp.R;
import com.example.prashant.contactapp.data.MessageContract.MessageEntry;
import com.example.prashant.contactapp.objects.ContactsHelper;

public class SmsDetailFragment extends Fragment{

    private final String TAG = getClass().getSimpleName();

    private View mRootView;
    private Toolbar mToolbar;
    private EditText editText;
    private Button mButton;

    private static String id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_sms_detail, container, false);

        mToolbar = (Toolbar) mRootView.findViewById(R.id.toolbar);
        editText = (EditText) mRootView.findViewById(R.id.sms_field);
        mButton = (Button) mRootView.findViewById(R.id.sms_button);

        id = getActivity().getIntent().getStringExtra("id");

        setupToolbar();

        sendOtpTask(id);

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

    private void sendOtpTask(final String id) {

        editText.setText(getActivity().getString(R.string.otp_message));

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ContactsHelper.getContact(id).getName();
                String number = ContactsHelper.getContact(id).getNumber();

                String msg = editText.getText().toString();

                String date = "11/07/2017";
                String time = "12 pm";

                saveToDb(view, name, number, msg, date, time);
            }
        });
    }

//    private void sendSms(String name, String num, String msgStr, String id) {
//
//        String ACCOUNT_SID = getContext().getResources().getString(R.string.ACCOUNT_SID);
//        String AUTH_TOKEN = getContext().getResources().getString(R.string.AUTH_TOKEN);
//
//        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
//
//        Notification notification = Notification
//                .creator(SERVICE_SID)
//                .setBody(msgStr)
//                .setIdentity(num)
//                .create();
//        //System.out.println(message.getSid());
//
//
//    }

    private void saveToDb(View view, String name, String number, String msg, String date, String time) {
        ContentValues values = new ContentValues();
        values.put(MessageEntry.KEY_ID, id);
        values.put(MessageEntry.KEY_NAME, name);
        values.put(MessageEntry.KEY_NUMBER, number);
        values.put(MessageEntry.KEY_OPT, msg);
        values.put(MessageEntry.KEY_DATE, date);
        values.put(MessageEntry.KEY_TIME, time);

        getActivity().getContentResolver().insert(MessageEntry.CONTENT_URI, values);

        Snackbar.make(view, "Sending to " + name + " ...",
                Snackbar.LENGTH_LONG).setAction("Action", null).show();

        Log.d(TAG, "DB ENTRY - " + "id - " + id + " name - " + name + " number - " + number + "OPT - " + msg + " date " + date + " time - " + time);
    }

}
