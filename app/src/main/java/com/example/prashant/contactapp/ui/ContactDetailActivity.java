package com.example.prashant.contactapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.prashant.contactapp.R;
import com.example.prashant.contactapp.fragments.ContactDetailFragment;

public class ContactDetailActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contact_detail_container, new ContactDetailFragment())
                    .commit();

            Log.d(TAG, "Transition to ContactDetailFragment");
        }
    }
}
