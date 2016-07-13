package com.nomprenom2.utils;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;

import com.nomprenom2.R;
import com.nomprenom2.model.NameRecord;
import com.nomprenom2.model.SelectedName;
import com.nomprenom2.model.ZodiacRecord;

import java.util.ArrayList;
import java.util.List;


public class SearchedNamesAdapter extends SelectedNameAdapter {
    private boolean bsearched;
    public SearchedNamesAdapter(Context context, int textViewResourceId,
                               List<String> nameList, String patronymic, String sex, String zod) {
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
        SelectedName nm = (SelectedName) cb.getTag();
        nm.setSelected(cb.isChecked());
        NameRecord.setSelection(nm.getName(), cb.isChecked() ? 1 : 0);
    }

    @Override
    public String getInfoText(String name){
        String c = getInfoPrefix();
        if(bsearched){
            c += getCompatibility(name);
        }else {
            c += ZodiacRecord.getMonthsForName(name);
        }
        return c;
    }
}
