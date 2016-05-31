package com.nomprenom2.utils;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;

import com.nomprenom2.model.NameRecord;
import com.nomprenom2.model.SelectedName;
import java.util.ArrayList;
import java.util.List;


public class SearchedNamesAdapter extends SelectedNameAdapter {
    public SearchedNamesAdapter(Context context, int textViewResourceId,
                               List<String> nameList, String patronymic, boolean isMale, int zod) {
        super(context, textViewResourceId, nameList, patronymic, isMale, zod);
    }

    @Override
    public void onClick(View v) {
        CheckBox cb = (CheckBox) v;
        SelectedName nm = (SelectedName) cb.getTag();
        NameRecord.setSelection(nm.getName(), cb.isChecked() ? 1 : 0 );
    }
}
