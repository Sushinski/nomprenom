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

    public List<NameRecord> getNames(String[] groups, int sex, int zod ){
        return new ArrayList<>();
    }

    public NameRecord getLastName(){
        return null;
    }


    public abstract void onOk();
}
