package com.example.prashant.contactapp.fragments;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.prashant.contactapp.R;
import com.example.prashant.contactapp.data.MessageContract.MessageEntry;
import com.example.prashant.contactapp.objects.ContactsHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public class SmsDetailFragment extends Fragment {

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

        Random rnd = new Random();
        int n = 100000 + rnd.nextInt(900000);

        editText.setText(getActivity().getString(R.string.otp_message) + " " + n);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sendSms(view, id);
                saveToDb("Tony Stark", "9012168161", editText.getText().toString());
            }
        });
    }

    private void sendSms(final View view, String id) {

        final String name = ContactsHelper.getContact(id).getName();
        final String numTo = ContactsHelper.getContact(id).getNumber();
        final String numFrom = getActivity().getResources().getString(R.string.twilio_number);

        final String body = editText.getText().toString();

        String ACCOUNT_SID = getContext().getResources().getString(R.string.ACCOUNT_SID);
        String AUTH_TOKEN = getContext().getResources().getString(R.string.AUTH_TOKEN);

        String base64EncodedCredentials = "Basic " + Base64.encodeToString(
                (ACCOUNT_SID + ":" + AUTH_TOKEN).getBytes(), Base64.NO_WRAP
        );

        Map<String, String> data = new HashMap<>();
        data.put("From", numFrom);
        data.put("To", numTo);
        data.put("Body", body);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.twilio.com/2010-04-01/")
                .build();

        TwilioApi api = retrofit.create(TwilioApi.class);

        api.sendMessage(ACCOUNT_SID, base64EncodedCredentials, data).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d("TAG", "onResponse->success");

                    snackBar(view, "OTP Send to " + name + " ... ");

                    //saveToDb(name, numTo, body);
                } else {
                    Log.d("TAG", "onResponse->failure " + response.toString());
                    snackBar(view, "Error Sending Message");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("TAG", " onFailure " + t);
            }
        });

    }

    private void saveToDb(String name, String number, String msg) {
        @SuppressLint("SimpleDateFormat")
        String date = new SimpleDateFormat("dd / MM / yyyy")
                .format(Calendar.getInstance().getTime());

        @SuppressLint("SimpleDateFormat")
        String time = new SimpleDateFormat("HH:mm a")
                .format(Calendar.getInstance().getTime());

        ContentValues values = new ContentValues();
        values.put(MessageEntry.KEY_ID, id);
        values.put(MessageEntry.KEY_NAME, name);
        values.put(MessageEntry.KEY_NUMBER, number);
        values.put(MessageEntry.KEY_OPT, msg);
        values.put(MessageEntry.KEY_DATE, date);
        values.put(MessageEntry.KEY_TIME, time);

        getActivity().getContentResolver().insert(MessageEntry.CONTENT_URI, values);

        Log.d(TAG, "DB ENTRY - " + "id - " + id + " name - " + name + " number - " + number + "OPT - " + msg + " date " + date + " time - " + time);
    }

    private void snackBar(View view, String message) {
        Snackbar.make(view, message,
                Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    interface TwilioApi {
        @FormUrlEncoded
        @POST("Accounts/{ACCOUNT_SID}/Messages")
        Call<ResponseBody> sendMessage(
                @Path("ACCOUNT_SID") String accountSId,
                @Header("Authorization") String signature,
                @FieldMap Map<String, String> metadata
        );
    }
}
