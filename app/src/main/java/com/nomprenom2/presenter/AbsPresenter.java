/*
 * created by Pavel Golubev golubev.pavel.spb@gmail.com
 * no license applied
 * You may use this file without any restrictions
 */

package com.nomprenom2.presenter;

import android.content.Context;

import com.nomprenom2.model.NameRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Central presenter abstraction
 */
public abstract class AbsPresenter {
    private Context context;

    AbsPresenter(Context c) {
        context = c;
    }

    /**
     * Gets List of Name model objects for certain parameters
     *
     * @param groups Array of regions names to get names for
     * @param sex    Gender of names to get for
     * @param zod    Zodiacal sign to get names for
     * @return List of name madel objects
     */
    public List<NameRecord> getNames(String[] groups, int sex, int zod) {
        return new ArrayList<>();
    }

    /**
     * Gets names for start part of name(used for fast search)
     *
     * @param name_part Starting part of name
     * @param regions   Array of regions names
     * @param sex       Gender of names
     * @param zod       Zodiacal sign for names
     * @return List of name model objects
     */
    public List<NameRecord> getSuggestions(String name_part, String[] regions, int sex, int zod) {
        return getNames(regions, sex, zod);
    }

}
