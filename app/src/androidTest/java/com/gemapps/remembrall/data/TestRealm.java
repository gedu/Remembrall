package com.gemapps.remembrall.data;

import android.support.test.runner.AndroidJUnit4;

import com.gemapps.remembrall.ui.model.Client;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.realm.Realm;
import io.realm.RealmResults;

import static android.support.test.InstrumentationRegistry.getContext;
import static org.junit.Assert.assertEquals;

/**
 * Created by edu on 1/11/17.
 */
@RunWith(AndroidJUnit4.class)
public class TestRealm {

    private static final String TAG = "TestRealm";

    @Before
    public void initRealm() {

        Realm.init(getContext());
        deleteAllClients();
    }

    @Test
    public void addClient(){

        saveClient();
        assertClientExist();
    }

    @Test
    public void removeAllClients(){
        saveClient();
        deleteAllClients();
        assertClientNotExist();
    }

    @Test
    public void removeSpecificClient(){
        Realm realm = Realm.getDefaultInstance();
        saveClient();

        assertClientExist();
        final RealmResults<Client> result = realm.where(Client.class).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Client client = result.where()
                        .equalTo(RemembrallContract.ClientEntry.COLUMN_FIRST_NAME, "Edu")
                        .findFirst();
                client.deleteFromRealm();
            }
        });
        assertClientNotExist();
    }

    private void deleteAllClients(){
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(Client.class);
            }
        });
    }

    private void saveClient(){
        Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                Client client = realm.createObject(Client.class);

                client.setFirstName("Edu");
                client.setLastName("Graciano");
                client.setAddress("Home");
                client.setEmail("email@edu.com");
                client.setHomePhone("321321312");
                client.setMobilePhone("321321313");
                client.setIdCard("111111");
            }
        });
    }

    private void assertClientExist(){
        Realm realm = Realm.getDefaultInstance();
        final Client client = realm.where(Client.class).findFirst();
        assertEquals("Edu", client.getFirstName());
        assertEquals("Graciano", client.getLastName());
    }

    private void assertClientNotExist(){
        Realm realm = Realm.getDefaultInstance();
        final Client client = realm.where(Client.class).findFirst();
        assertEquals(null, client);
    }
}
