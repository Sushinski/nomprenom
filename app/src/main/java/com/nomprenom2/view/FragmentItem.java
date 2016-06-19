package com.nomprenom2.view;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.nomprenom2.R;

public class FragmentItem extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private OnFragmentInteractionListener mCallback;
    private String mParam1;
    private TextView frag_text;
    private Button del_btn;

    public static FragmentItem newInstance(String param1, OnFragmentInteractionListener lstnr ) {
        FragmentItem fragment = new FragmentItem();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        fragment.mCallback = lstnr;
        return fragment;
    }

    public FragmentItem() {
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_item, container, false);
        frag_text = (TextView)v.findViewById(R.id.frag_text);
        frag_text.setText(mParam1);
        del_btn = (Button)v.findViewById(R.id.del_btn);
        del_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String fr_str = frag_text.getText().toString();
                mCallback.onFragmentInteraction(fr_str);
            }
        });
        return v;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String frag_name);
    }

}
