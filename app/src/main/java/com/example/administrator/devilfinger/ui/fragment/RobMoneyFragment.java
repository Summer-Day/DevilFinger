package com.example.administrator.devilfinger.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.devilfinger.R;
import com.example.administrator.wangshuobaselib.BaseFragment;

import butterknife.ButterKnife;

/**
 * Created by storm on 14-3-25.
 */
public class RobMoneyFragment extends BaseFragment {

    public static RobMoneyFragment newInstance() {
        RobMoneyFragment fragment = new RobMoneyFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_robmoney, container, false);
        ButterKnife.inject(this, contentView);

        return contentView;
    }


}
