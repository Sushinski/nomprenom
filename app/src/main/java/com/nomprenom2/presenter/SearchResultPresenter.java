/*
 * created by Pavel Golubev golubev.pavel.spb@gmail.com
 * no license applied
 * You may use this file without any restrictions
 */

package com.nomprenom2.presenter;

import android.content.Context;

import com.nomprenom2.model.NameRecord;

import java.util.Iterator;
import java.util.List;


public class SearchResultPresenter extends AbsPresenter {
    public SearchResultPresenter(Context c) {
        super(c);
    }
    // TODO: move data here from activity class

    /**
     * Wrapper for model`s getNames method
     *
     * @param groups Array of regions names to get names for
     * @param sex    Gender of names to get for
     * @param zod    Zodiacal sign to get names for
     * @return List od name model objects
     */
    public List<NameRecord> getNames(String[] groups, int sex, int zod) {
        return NameRecord.getNames(groups, sex, zod);
    }

    /**
     * @param name_part Starting part of name
     * @param regions   Array of regions names
     * @param sex       Gender of names
     * @param zod       Zodiacal sign for names
     * @return List of name model objects
     */
    public List<NameRecord> getSuggestions(String name_part, String[] regions, int sex, int zod) {
        List<NameRecord> n_l = NameRecord.getNames(regions, sex, zod); //TODO: cache candidate
        for (Iterator<NameRecord> nr = n_l.iterator(); nr.hasNext(); ) {
            if (!nr.next().name.regionMatches(true, 0, name_part, 0, name_part.length()))
                nr.remove();
        }
        return n_l;
    }
}
