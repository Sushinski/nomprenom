/*
 * created by Pavel Golubev golubev.pavel.spb@gmail.com
 * no license applied
 * You may use this file without any restrictions
 */

package com.nomprenom2.utils;

/**
 * Describes Event Bus subscription event
 */
public class ActionEvent {
    /**
     * Load new names event key
     */
    public final static int TYPE_LOAD_NEW_NAMES = 1;
    private final boolean is_successful;
    private final String message;
    private final int type;

    ActionEvent(int type, boolean is_successful, String message) {
        this.type = type;
        this.is_successful = is_successful;
        this.message = message;
    }

    public boolean isSuccessful() {
        return is_successful;
    }

    public String getMessage() {
        return message;
    }

    public int getType() {
        return type;
    }
}
