Android code to dump out all data from all contacts on the phone

The code here is written for the sole purpose of dumping out the contacts db on the phone. It shows the relation between the tables:
1. ContactsContract.Contact
2. ContactsContract.RawContacts
3. ContactsContract.Data

Contact table is a linked aggegation of the raw contacts on the phone. 
Each entry in RawContact table is a unique contact(not a unique person), as provided by a single AccountProvider.
Each entry in RawContact is made up of different subparts of different (unique or repeated) mimetypes. 
     - For ex: phone number, photo, GroupMembership, etc.
     
Each of these subparts are stored in the table 'Data'. Hence, each entry in table ContactsContract.Data has a reference to 
- the Contacts table row id (column name: CONTACT_ID)
- the RawContacts table row id (column name: RAW_CONTACT_ID).

In this app, the MainActivity only checks to get the permissions from the user for read/write contacts on the device.
Once it has the permissions, it will trigger an AsyncTask to load up the contacts and dump them out in logs. Hence, keep an eye out for the logs in your 'logcat' window.

