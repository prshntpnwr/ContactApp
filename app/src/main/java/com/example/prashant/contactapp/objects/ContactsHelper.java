package com.example.prashant.contactapp.objects;

import java.util.ArrayList;

/***
 * Return contact object for selected id
 */
public class ContactsHelper {
    public static ArrayList<Contacts> contactList = new ArrayList<>();

    public static Contacts getContact(String id) {
        for (Contacts c : contactList) {
            if (c.getId().equals(id)) {
                return c;
            }
        }

        return null;
    }
}
