package com.nomprenom2.presenter;

import android.content.Context;

import com.nomprenom2.model.NameRecord;

import java.util.ArrayList;
import java.util.List;


public abstract class AbsPresenter{
    protected Context context;
    public AbsPresenter(Context c){
        context = c;
    }

    public List<NameRecord> getNames(String[] groups, String sex, String zod ){
        return NameRecord.getNames(groups, sex, zod);
    }



    public abstract void onOk();
}
