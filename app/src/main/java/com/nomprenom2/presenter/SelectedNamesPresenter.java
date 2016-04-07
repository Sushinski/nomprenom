package com.nomprenom2.presenter;

import android.content.Context;

import com.nomprenom2.model.NameRecord;

import java.util.ArrayList;
import java.util.List;

public class SelectedNamesPresenter extends AbsPresenter{
    public SelectedNamesPresenter(Context c){
        super(c);
    }

    public void onOk(){
    }

    public List<String> getNames(int selected){
        List<String> res = new ArrayList<>();
        for(NameRecord n : NameRecord.getSelected(selected)){
            res.add(n.toString());
        }
        return res;
    }
}