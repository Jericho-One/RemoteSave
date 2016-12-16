package com.test.tech.remotesave;

import android.support.annotation.NonNull;

/**
 * Created by Aaron Dishman on 12/11/16.
 */

public class Comment {
    private int hash = 0;
    private String firstName;
    private String lastName;
    private String comment;

    public Comment(int hash, @NonNull String firstName, @NonNull String lastName, @NonNull String comment) {
        this.hash = hash;
        this.firstName = firstName;
        this.lastName = lastName;
        this.comment = comment;
    }

    public static int generateHash(String firstName, String lastName) {
        String hashString = firstName + lastName + System.currentTimeMillis();
       return hashString.hashCode();
    }

    public int getHash() {
        if (hash == 0) {
            hash = generateHash(firstName, lastName);
        }
        return hash;
    }

    public void setHash(int hash) {
        this.hash = hash;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
