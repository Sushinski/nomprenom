package com.nomprenom2.presenter;

import android.content.Context;

import com.nomprenom2.model.NameRecord;

import java.util.List;


public class SearchResultPresenter extends AbsPresenter{
    public SearchResultPresenter(Context c){
        super(c);
    }

    public void onOk(){
    }

    public List<NameRecord> getNames(String[] groups, int sex, int zod ){
        return NameRecord.getNames(groups, sex, zod);
    }

    public NameRecord getLastName(){
        return NameRecord.getLast();
    }
}
