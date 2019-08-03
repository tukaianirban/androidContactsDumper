package com.example.contactsdumper;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.util.Log;

public class ContactsReaderAsyncTask extends AsyncTask<String, Void, Void>
{
    private final static String LOGTAG = ContactsReaderAsyncTask.class.getSimpleName();

    private ContentResolver contactsContentResolver;
    private Context context;

    public ContactsReaderAsyncTask(@NonNull Context context)
    {
        super();
        this.context = context;
    }

    @Override
    protected void onPreExecute()
    {
        contactsContentResolver = context.getContentResolver();
    }

    @Override
    protected void onPostExecute(Void aVoid)
    {
        super.onPostExecute(aVoid);
    }

    @Override
    protected Void doInBackground(String... strings)
    {

        Cursor mainContactsCursor = contactsContentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if (mainContactsCursor==null)
        {
            Log.d(LOGTAG, "Main contacts cursor is null. Abort");
            return null;
        }

        final int ciId = mainContactsCursor.getColumnIndex(ContactsContract.Contacts._ID);
        final int ciLookupKey = mainContactsCursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY);
        final int ciDisplayNameRawContactId = mainContactsCursor.getColumnIndex(ContactsContract.Contacts.NAME_RAW_CONTACT_ID);
        final int ciDisplayNamePrimary = mainContactsCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY);
        final int ciPhotoDataTableRef = mainContactsCursor.getColumnIndex(ContactsContract.Contacts.PHOTO_ID);
        final int ciPhotoFullURI = mainContactsCursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI);
        final int ciPhotoThumbnailURI = mainContactsCursor.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI);
        final int ciIsContactVisible = mainContactsCursor.getColumnIndex(ContactsContract.Contacts.IN_VISIBLE_GROUP);
        final int ciHasPhoneNumber = mainContactsCursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
        final int ciStarred = mainContactsCursor.getColumnIndex(ContactsContract.Contacts.STARRED);
        final int ciRingtone = mainContactsCursor.getColumnIndex(ContactsContract.Contacts.CUSTOM_RINGTONE);
        final int ciSend2Voicemail = mainContactsCursor.getColumnIndex(ContactsContract.Contacts.SEND_TO_VOICEMAIL);
        final int ciIMPresenceStatus = mainContactsCursor.getColumnIndex(ContactsContract.Contacts.CONTACT_PRESENCE);
        final int ciStatusUpdateText = mainContactsCursor.getColumnIndex(ContactsContract.Contacts.CONTACT_STATUS);
        final int ciStatusUpdateTimestamp = mainContactsCursor.getColumnIndex(ContactsContract.Contacts.CONTACT_STATUS_TIMESTAMP);
        final int ciStatusUpdateResources = mainContactsCursor.getColumnIndex(ContactsContract.Contacts.CONTACT_STATUS_RES_PACKAGE);
        final int ciStatusUpdateLabel = mainContactsCursor.getColumnIndex(ContactsContract.Contacts.CONTACT_STATUS_LABEL);
        final int ciStatusUpdateIcon = mainContactsCursor.getColumnIndex(ContactsContract.Contacts.CONTACT_STATUS_ICON);




        // look through the items in the main contacts table
        while (mainContactsCursor.moveToNext())
        {

            String displayName = mainContactsCursor.getString(ciDisplayNamePrimary);
            if (!displayName.startsWith("Hamed")) continue;

            Log.d(LOGTAG, "Main Contact: Id: " + mainContactsCursor.getLong(ciId) +
                            "    Lookup key: " + mainContactsCursor.getString(ciLookupKey) +
                            "    Displayname's RawContactId: " + mainContactsCursor.getLong(ciDisplayNameRawContactId) +
                            "    Primary display name: " + mainContactsCursor.getString(ciDisplayNamePrimary) +
                            "    Photo Data-table ref: " + mainContactsCursor.getLong(ciPhotoDataTableRef) +
                            "    Photo (full) URI: " + mainContactsCursor.getLong(ciPhotoFullURI) +
                            "    Photo (thumbnail) URI: " + mainContactsCursor.getLong(ciPhotoThumbnailURI) +
                            "    Visible? " + mainContactsCursor.getInt(ciIsContactVisible) +
                            "    has phonenumber? " + mainContactsCursor.getInt(ciHasPhoneNumber) +
                            "    Starrted? " + mainContactsCursor.getInt(ciStarred) +
                            "    Ringtone: " + mainContactsCursor.getString(ciRingtone) +
                            "    Send2Voicemail: " + mainContactsCursor.getInt(ciSend2Voicemail) +
                            "    IM presence status: " + mainContactsCursor.getInt(ciIMPresenceStatus) +
                            "    Status update: " + mainContactsCursor.getString(ciStatusUpdateText) +
                            "    Status update timestamp: " + mainContactsCursor.getLong(ciStatusUpdateTimestamp) +
                            "    Status update resource: " + mainContactsCursor.getString(ciStatusUpdateResources) +
                            "    Status update label: " + mainContactsCursor.getLong(ciStatusUpdateLabel) +
                            "    Status update source icon: " + mainContactsCursor.getLong(ciStatusUpdateIcon));


            long mainContactId = mainContactsCursor.getLong(ciId);

            //cursor for ContactsContract.RawContacts table
            Cursor rawContactsCursor = contactsContentResolver.query(
                    ContactsContract.RawContacts.CONTENT_URI,
                    null,
                    ContactsContract.RawContacts.CONTACT_ID + "=?", new String[]{String.valueOf(mainContactId)},
                    null);

            if (rawContactsCursor == null)
            {
                Log.d(LOGTAG, "RawContacts cursor is null !");
                continue;
            }

            final int cirId = rawContactsCursor.getColumnIndex(ContactsContract.RawContacts._ID);
            final int cirContactId = rawContactsCursor.getColumnIndex(ContactsContract.RawContacts.CONTACT_ID);
            final int cirAggregationMode = rawContactsCursor.getColumnIndex(ContactsContract.RawContacts.AGGREGATION_MODE);
            final int cirDeleted = rawContactsCursor.getColumnIndex(ContactsContract.RawContacts.DELETED);
            final int cirRingtone = rawContactsCursor.getColumnIndex(ContactsContract.RawContacts.CUSTOM_RINGTONE);
            final int cirStarred = rawContactsCursor.getColumnIndex(ContactsContract.RawContacts.STARRED);
            final int cirSendVoicemail = rawContactsCursor.getColumnIndex(ContactsContract.RawContacts.SEND_TO_VOICEMAIL);
            final int cirAccountName = rawContactsCursor.getColumnIndex(ContactsContract.RawContacts.ACCOUNT_NAME);
            final int cirAccountType = rawContactsCursor.getColumnIndex(ContactsContract.RawContacts.ACCOUNT_TYPE);
            final int cirSourceId = rawContactsCursor.getColumnIndex(ContactsContract.RawContacts.SOURCE_ID);
            final int cirDataset = rawContactsCursor.getColumnIndex(ContactsContract.RawContacts.DATA_SET);
            final int cirVersion = rawContactsCursor.getColumnIndex(ContactsContract.RawContacts.VERSION);
            final int cirDirty = rawContactsCursor.getColumnIndex(ContactsContract.RawContacts.DIRTY);
            final int cirSync1 = rawContactsCursor.getColumnIndex(ContactsContract.RawContacts.SYNC1);


            // for the specific contact in main Contacts table, look for the contacts in the raw contacts table
            while (rawContactsCursor.moveToNext())
            {
                Log.d(LOGTAG, "Raw Contact Id: " + rawContactsCursor.getLong(cirId) +
                        "   Contact id: " + rawContactsCursor.getLong(cirContactId) +
                        "   Aggr. mode: " + rawContactsCursor.getInt(cirAggregationMode) +
                        "   Deleted: " + rawContactsCursor.getInt(cirDeleted) +
                        "   Starred: " + rawContactsCursor.getInt(cirStarred) +
                        "   Ringtone: " + rawContactsCursor.getString(cirRingtone) +
                        "   Send2Voicemail: " + rawContactsCursor.getInt(cirSendVoicemail) +
                        "   Account name: " + rawContactsCursor.getString(cirAccountName) +
                        "   Account type: " + rawContactsCursor.getString(cirAccountType) +
                        "   Data set: " + rawContactsCursor.getString(cirDataset) +
                        "   Source id: " + rawContactsCursor.getString(cirSourceId) +
                        "   Version: " + rawContactsCursor.getInt(cirVersion) +
                        "   Dirty: " + rawContactsCursor.getInt(cirDirty) +
                        "   SYNC1: " + rawContactsCursor.getString(cirSync1));

                long rawContactId = rawContactsCursor.getLong(cirId);

                // get all ContactsContract.Data table entries for this RawContact
                Cursor dataCursor = contactsContentResolver.query(
                        ContactsContract.Data.CONTENT_URI,
                        null,
                        ContactsContract.Data.RAW_CONTACT_ID + "=?",
                        new String[]{String.valueOf(rawContactId)},
                        null);

                if (dataCursor == null)
                {
                    Log.d(LOGTAG, "Data cursor is a null. Bunk ");
                    continue;
                }

                final int cidContactId = dataCursor.getColumnIndex(ContactsContract.Data.CONTACT_ID);
                final int cidRawContactId = dataCursor.getColumnIndex(ContactsContract.Data.RAW_CONTACT_ID);
                final int cidMimeType = dataCursor.getColumnIndex(ContactsContract.Data.MIMETYPE);
                final int cidAccountType = dataCursor.getColumnIndex(ContactsContract.Data.ACCOUNT_TYPE_AND_DATA_SET);
                final int cidData1 = dataCursor.getColumnIndex(ContactsContract.Data.DATA1);
                final int cidData2 = dataCursor.getColumnIndex(ContactsContract.Data.DATA2);
                final int cidData3 = dataCursor.getColumnIndex(ContactsContract.Data.DATA3);
                final int cidData4 = dataCursor.getColumnIndex(ContactsContract.Data.DATA4);
                final int cidData5 = dataCursor.getColumnIndex(ContactsContract.Data.DATA5);
                final int cidData6 = dataCursor.getColumnIndex(ContactsContract.Data.DATA6);
                final int cidData7 = dataCursor.getColumnIndex(ContactsContract.Data.DATA7);
                final int cidData8 = dataCursor.getColumnIndex(ContactsContract.Data.DATA8);
                final int cidData9 = dataCursor.getColumnIndex(ContactsContract.Data.DATA9);
                final int cidData10 = dataCursor.getColumnIndex(ContactsContract.Data.DATA10);
                final int cidData11 = dataCursor.getColumnIndex(ContactsContract.Data.DATA11);
                final int cidData12 = dataCursor.getColumnIndex(ContactsContract.Data.DATA12);
                final int cidData13 = dataCursor.getColumnIndex(ContactsContract.Data.DATA13);
                final int cidData14 = dataCursor.getColumnIndex(ContactsContract.Data.DATA14);
                final int cidData15 = dataCursor.getColumnIndex(ContactsContract.Data.DATA15);

                Log.d(LOGTAG, "For RawContact id: " + rawContactId + ", count of raw data items = " + dataCursor.getCount() + "     Raw contact data dumps: ");

                // for the given raw contact item, look for the entries in the ContactsContact.Data table.
                while (dataCursor.moveToNext())
                {
                    Log.d(LOGTAG, "Data row id: " + dataCursor.getLong(dataCursor.getColumnIndex(ContactsContract.Data._ID)) +
                            "   ContactId : " + dataCursor.getInt(cidContactId) +
                            "   RawContactId : " + dataCursor.getInt(cidRawContactId) +
                            "   MimeType: " + dataCursor.getString(cidMimeType) +
                            "   Account type: " + dataCursor.getString(cidAccountType) +
                            "   Data1: " + dataCursor.getString(cidData1) +
                            "   Data2: " + dataCursor.getString(cidData2) +
                            "   Data3: " + dataCursor.getString(cidData3) +
                            "   Data4: " + dataCursor.getString(cidData4) +
                            "   Data5: " + dataCursor.getString(cidData5) +
                            "   Data6: " + dataCursor.getString(cidData6) +
                            "   Data7: " + dataCursor.getString(cidData7) +
                            "   Data8: " + dataCursor.getString(cidData8) +
                            "   Data9: " + dataCursor.getString(cidData9) +
                            "   Data10: " + dataCursor.getString(cidData10) +
                            "   Data11: " + dataCursor.getString(cidData11) +
                            "   Data12: " + dataCursor.getString(cidData12) +
                            "   Data13: " + dataCursor.getString(cidData13) +
                            "   Data14: " + dataCursor.getString(cidData14) +
                            "   Data15 (BLOB): " + dataCursor.getBlob(cidData15));

                    Log.d(LOGTAG, "\n\n");
                }

                dataCursor.close();

            }

            rawContactsCursor.close();

        }

        mainContactsCursor.close();

        return null;
    }
}
