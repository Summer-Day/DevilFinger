package com.example.administrator.devilfinger.ui.fragment;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.devilfinger.R;
import com.example.administrator.devilfinger.activity.MainActivity;
import com.example.administrator.devilfinger.model.Category;
import com.example.administrator.devilfinger.ui.adapter.DrawerAdapter;
import com.example.administrator.wangshuobaselib.BaseFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DrawerFragment extends BaseFragment {
    @InjectView(R.id.like)
    TextView like;
    private ListView mListView;

    private DrawerAdapter mAdapter;

    private MainActivity mActivity;


    public DrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = (MainActivity) getActivity();

        try
        {
                Typeface iconfont = Typeface.createFromFile("src/main/iconfont/iconfont.ttf");   //(DFApplication.getContext().getAssets(), "src/main/iconfont/iconfont.ttf");
            like.setTypeface(iconfont);
            like.setTextSize(40);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        View contentView = inflater.inflate(R.layout.fragment_drawer, null);
        mListView = (ListView) contentView.findViewById(R.id.listView);
        mAdapter = new DrawerAdapter(mListView);
        mListView.setAdapter(mAdapter);
        mListView.setItemChecked(0, true);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListView.setItemChecked(position, true);
                mActivity.setCategory(Category.values()[position]);
            }
        });
        ButterKnife.inject(this, contentView);
        return contentView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
