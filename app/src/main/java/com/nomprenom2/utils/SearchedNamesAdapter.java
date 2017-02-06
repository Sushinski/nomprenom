package com.nomprenom2.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;

import com.nomprenom2.R;
import com.nomprenom2.model.GroupRecord;
import com.nomprenom2.model.NameRecord;
import com.nomprenom2.model.SelectedName;
import com.nomprenom2.model.ZodiacRecord;

import java.util.ArrayList;
import java.util.List;


public class SearchedNamesAdapter extends SelectedNameAdapter {
    private boolean bsearched;
    static String[] zodiac_repr_names;
    public SearchedNamesAdapter(Context context, int textViewResourceId,
                               List<NameRecord> nameList, String patronymic, String sex, String zod) {
        super(context, textViewResourceId, nameList, patronymic, sex, zod);
        if( patronymic != null || sex != null || zod != null ) {
            bsearched = true;
            setInfoPrefx(context.getResources().getText(R.string.compabl_pref).toString());
        }else{
            bsearched = false;
            setInfoPrefx(context.getResources().getText(R.string.zod_pref).toString());
        }
        zodiac_repr_names = getContext().getResources().getStringArray(R.array.zod_sels);
    }

    @Override
    public void onClick(View v) {
        CheckBox cb = (CheckBox) v;
        NameRecord nm = (NameRecord) cb.getTag();
        nm.selected = cb.isChecked() ? 1 : 0;
        NameRecord.setSelection(nm.name, nm.selected);
    }

    @Override
    public String getInfoText(NameRecord nr){
        String c;
        if(bsearched){
             c = getContext().getResources().getText(R.string.compabl_pref) +
                     getCompatibility(nr);
        }else {
            c = getContext().getResources().getText(R.string.descr_sex) + getContext().getResources().getStringArray(R.array.sex_sels)[nr.sex];
            //[ToDo названия зодиака]
            List<String> zod_repr = new ArrayList<>();
            for (String s: nr.zodiacs) {
                zod_repr.add(zodiac_repr_names[ZodiacRecord.ZodMonth.valueOf(s).getId()]);
            }
            String str = TextUtils.join(", ", zod_repr);
            //String str = ZodiacRecord.getMonthsForName(nr.);
            GroupRecord gr = GroupRecord.getGroupForName(nr);
            c += getContext().getResources().getString(R.string.region_repr) +
                    ( gr== null ? getContext().getResources().getText(R.string.unknown) : gr);
            c += "\n" + getContext().getResources().getText(R.string.descr_zod) +
                    (str.equals("") ? getContext().getResources().getText(R.string.unknown) : str);
        }
        return c;
    }
}
