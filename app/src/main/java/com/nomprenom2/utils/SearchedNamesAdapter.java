package com.nomprenom2.utils;

import android.content.Context;
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
                     getCompatibility(nr.name);
        }else {
            c = getContext().getResources().getText(R.string.descr_sex) +
                    NameRecord.Sex.fromInt(nr.sex);
                    String str = ZodiacRecord.getMonthsForName(nr.name);
            GroupRecord gr = GroupRecord.getGroupForName(nr);
            c += "  Region: " +
                    ( gr== null ? getContext().getResources().getText(R.string.unknown) : gr);
            c += "\n" + getContext().getResources().getText(R.string.descr_zod) +
                    (str.equals("") ? getContext().getResources().getText(R.string.unknown) : str);
        }
        return c;
    }
}
