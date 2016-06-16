package com.nomprenom2.view;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.nomprenom2.R;
import com.nomprenom2.presenter.MainPresenter;
import com.nomprenom2.utils.NothingSelectedSpinnerAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;


public class NameParamsFragment extends Fragment implements
        FragmentItem.OnFragmentInteractionListener {
    public HashSet<String> regions;
    public String[] sex_sel;
    public String[] zod_sel;
    protected Spinner sex_spinner;
    protected Spinner zod_spinner;
    private List<WeakReference<Fragment>> frag_list;

    public static NameParamsFragment newInstance() {
        NameParamsFragment fragment = new NameParamsFragment();
        return fragment;
    }

    public NameParamsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_name_params, container, false);
                sex_sel = getResources().getStringArray(R.array.sex_sels);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, sex_sel);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sex_spinner = (Spinner) v.findViewById(R.id.sex_spinner);
        sex_spinner.setAdapter(new NothingSelectedSpinnerAdapter(adapter,
                        R.layout.contact_spinner_row_nothing_selected, getActivity()));
        // zodiac spinner
        zod_sel = getResources().getStringArray(R.array.zod_months);
        ArrayAdapter<String> zod_adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, zod_sel);
        zod_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        zod_spinner = (Spinner) v.findViewById(R.id.zodiac_spinner);
        zod_spinner.setAdapter(new NothingSelectedSpinnerAdapter(zod_adapter,
                        R.layout.zodiac_spinner_row_nothing_selected, getActivity()));

        regions = new HashSet<>();
        frag_list = new ArrayList<>();
        if(savedInstanceState!=null)
            setGroupList();

        Button regs = (Button) v.findViewById(R.id.select_region_button);
        regs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NameParamsFragment.this.selectRegion();
            }
        });
        return v;
    }

    private void setGroupList(){
        View v = getView();
        if (v != null && v.findViewById(R.id.fragment_container) != null) {
            FragmentManager mngr = getFragmentManager();
            FragmentTransaction tr = mngr.beginTransaction();
            Fragment fr;
            for (String s : regions) {
                fr = mngr.findFragmentByTag(s);
                if (fr == null) {
                    fr = FragmentItem.newInstance(s, this);
                    tr.add(R.id.fragment_container, fr, s);
                }
            }
            for (WeakReference<Fragment> f : frag_list) {
                fr = f.get();
                if (fr != null) {
                    String tg = fr.getTag();
                    if (!regions.contains(tg))
                        tr.remove(fr);
                } else {
                    frag_list.remove(f);
                }
            }
            tr.commit();
        }
    }


   /* @Override
    public void onAttachFragment(Fragment fragment) {
        frag_list.add(new WeakReference<>(fragment));
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == MainActivity.GROUP_REQUEST) {
            regions.clear();
            if(data.hasExtra(MainActivity.REGIONS)) {
                regions.addAll(Arrays.asList(data.getStringArrayExtra(MainActivity.REGIONS)));
            }
            setGroupList();
        }
    }

    public void selectRegion() {
        Intent intent = new Intent(getActivity(), SelectedRegionActivity.class);
        if(!regions.isEmpty()) {
            String[] sa = new String[regions.size()];
            regions.toArray(sa);
            intent.putExtra(MainActivity.REGIONS, sa);
        }
        startActivityForResult(intent, MainActivity.GROUP_REQUEST);
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void onFragmentInteraction(String frag_name){
        FragmentManager mngr = getFragmentManager();
        FragmentTransaction tr = mngr.beginTransaction();
        Fragment fr = mngr.findFragmentByTag(frag_name);
        if( fr != null ) {
            tr.remove(fr);
            regions.remove(frag_name);
        }
        tr.commit();
    }

}
