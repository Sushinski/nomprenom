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

    public ArrayList<String> getNames(int selected){
        ArrayList<String> res = new ArrayList<>();
        for(NameRecord n : NameRecord.getSelected(selected)){
            res.add(n.toString());
        }
        return res;
    }

    public void deselectNames( String[] names ){
        if( names != null )
            NameRecord.setSelection(names, 0);
    }
}