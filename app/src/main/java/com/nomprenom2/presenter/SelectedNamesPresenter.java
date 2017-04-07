/*
 * created by Pavel Golubev golubev.pavel.spb@gmail.com
 * no license applied
 * You may use this file without any restrictions
 */
package com.nomprenom2.presenter;

import android.content.Context;

import com.nomprenom2.model.NameRecord;

import java.util.ArrayList;

/**
 * Presenter layer for selected names activity
 */
public class SelectedNamesPresenter extends AbsPresenter {
    public SelectedNamesPresenter(Context c) {
        super(c);
    }
    // TODO: move data here from activity class

    public void onOk() {
    }

    public ArrayList<String> getNames(int selected) {
        ArrayList<String> res = new ArrayList<>();
        for (NameRecord n : NameRecord.getSelected(selected)) {
            res.add(n.toString());
        }
        return res;
    }

    public void deselectNames(String[] names) {
        for (String s : names) {
            NameRecord.setSelection(s, 0);
        }
    }
}