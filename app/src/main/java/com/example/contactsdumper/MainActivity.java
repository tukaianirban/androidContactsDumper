package com.example.contactsdumper;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


public class MainActivity extends AppCompatActivity
{
    private static final String LOGTAG = MainActivity.class.getSimpleName();

    private ContactsReaderAsyncTask contactsReader;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 10);
            return;
        }else
        {
            Log.d(LOGTAG, "have contacts read permissions");

            if (contactsReader == null) contactsReader = new ContactsReaderAsyncTask(this);

            contactsReader.execute("dummyfile");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode != 10) return;

        if (resultCode== Activity.RESULT_OK)
        {
            Log.d(LOGTAG, "have contacts read permissions");

            if (contactsReader == null) contactsReader = new ContactsReaderAsyncTask(this);

            contactsReader.execute("dummyfile");

        }else
        {
            Log.d(LOGTAG, "dont have contacts read permissions. Abort !");

            finish();
        }
    }


}
