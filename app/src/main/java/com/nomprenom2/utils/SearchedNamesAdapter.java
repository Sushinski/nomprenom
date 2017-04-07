/*
 * created by Pavel Golubev golubev.pavel.spb@gmail.com
 * no license applied
 * You may use this file without any restrictions
 */

package com.nomprenom2.utils;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;

import com.nomprenom2.R;
import com.nomprenom2.model.GroupRecord;
import com.nomprenom2.model.NameRecord;
import com.nomprenom2.model.ZodiacRecord;

import java.util.List;

/**
 * Data adapter for searched names list
 */
public class SearchedNamesAdapter extends SelectedNameAdapter {
    private static String[] zodiac_repr_names;
    private final boolean bsearched;

    public SearchedNamesAdapter(Context context, int textViewResourceId,
                                List<NameRecord> nameList, String patronymic, int sex, int zod) {
        super(context, textViewResourceId, nameList, patronymic, sex, zod);
        if (patronymic != null || sex > 0 || zod > 0) { // if at least one parameter sat
            bsearched = true;
            setInfoPrefx(context.getResources().getText(R.string.compabl_pref).toString());
        } else {
            bsearched = false;
            setInfoPrefx(context.getResources().getText(R.string.zod_pref).toString());
        }
        zodiac_repr_names = getContext().getResources().getStringArray(R.array.zod_sels);
    }

    /**
     * processes checkbox and sets appropriate database field for selection
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        CheckBox cb = (CheckBox) v;
        NameRecord nm = (NameRecord) cb.getTag();
        nm.selected = cb.isChecked() ? 1 : 0;
        NameRecord.setSelection(nm.name, nm.selected);
    }

    /**
     * Return list item description text
     *
     * @param nr name model object to get description for
     * @return
     */
    @Override
    public String getInfoText(NameRecord nr) {
        String c;
        if (bsearched && !patronymic.equals("")) {
            c = getContext().getResources().getText(R.string.compabl_pref) +
                    getCompatibility(nr) + "%";
        } else {
            c = getContext().getResources().getText(R.string.descr_sex) +
                    getContext().getResources().getStringArray(R.array.sex_sels)[nr.sex];
            //[ToDo названия зодиака]
            String str = ZodiacRecord.getMonthsForName(nr.name, zodiac_repr_names);
            GroupRecord gr = GroupRecord.getGroupForName(nr);
            c += " " + getContext().getResources().getString(R.string.region_repr) +
                    (gr == null ? getContext().getResources().getText(R.string.unknown) : gr);
            c += "\n" + getContext().getResources().getText(R.string.descr_zod) +
                    (str.equals("") ? getContext().getResources().getText(R.string.unknown) : str);
        }
        return c;
    }
}
