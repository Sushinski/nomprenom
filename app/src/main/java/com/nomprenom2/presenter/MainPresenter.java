package com.nomprenom2.presenter;
import android.content.Context;

import com.nomprenom2.model.NameRecord;

import java.util.ArrayList;
import java.util.List;

public class MainPresenter {
    public MainPresenter(Context context){

    }

    public List<String> getNames(String[] groups, String sex ){
        List<NameRecord> res = NameRecord.getNames(groups, sex);
        List<String> ret = new ArrayList<>();
        for(NameRecord n : res) {
            ret.add(n.name);
        }
        return ret;
    }
}
