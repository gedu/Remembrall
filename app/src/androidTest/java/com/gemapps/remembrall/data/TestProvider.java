package com.gemapps.remembrall.data;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.test.AndroidTestCase;

/**
 * Created by edu on 8/26/16.
 */

public class TestProvider extends AndroidTestCase {
    // TODO: 2/1/17 update so it can use REALM (?)
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    public void testProviderInManifest(){
        PackageManager pm = getContext().getPackageManager();

        ComponentName providerComponent = new ComponentName(getContext().getPackageName(),
                RemembrallProvider.class.getName());

        try {

            ProviderInfo providerInfo = pm.getProviderInfo(providerComponent, 0);

            assertEquals(providerInfo.authority, RemembrallContract.CONTENT_AUTHORITY);

        } catch (PackageManager.NameNotFoundException e) {
            assertTrue("Error: no registered at "+getContext().getPackageName(), false);
        }
    }

    public void testClientQuery(){
//
//        String CLIENT_NAME = "Provider";
//        long clientId = TestUtil.insertClient(
//                new RemembrallSqlHelper(getContext()).getWritableDatabase(), CLIENT_NAME);
//
//
//        Cursor providerCursor = getContext().getContentResolver().query(
//                RemembrallContract.ClientEntry.CONTENT_URI,
//                null, null,
//                null, null
//        );
//
//        assertNotNull(providerCursor);
//
//        providerCursor.moveToPosition(0);
//        long id = providerCursor.getLong(providerCursor.getColumnIndex(RemembrallContract.ClientEntry._ID));
//        String name = providerCursor.getString(providerCursor.getColumnIndex(RemembrallContract.ClientEntry.COLUMN_FIRST_NAME));
//        assertEquals(clientId, id);
//        assertEquals(CLIENT_NAME, name);
//
//        providerCursor.close();
    }
}
