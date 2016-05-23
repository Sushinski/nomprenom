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

    public List<String> getNames(String[] groups, String sex, String zod ){
        List<NameRecord> res = NameRecord.getNames(groups, sex, zod);
        List<String> ret = new ArrayList<>();
        for(NameRecord n : res) {
            ret.add(n.name);
        }
        return ret;
    }

    public abstract void onOk();
}
