package com.example.administrator.devilfinger.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.administrator.devilfinger.R;
import com.example.administrator.wangshuobaselib.BaseFragment;
import com.example.administrator.wangshuobaselib.widget.blurredView.BlurredView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by storm on 14-3-25.
 */
public class GsFragment extends BaseFragment {


    @InjectView(R.id.activity_main_seekbar)
    SeekBar mSeekBar;
    @InjectView(R.id.activity_main_progress_tv)
    TextView mProgressTv;
    @InjectView(R.id.activity_main)
    LinearLayout activityMain;
    @InjectView(R.id.activity_main_blurredview)
    BlurredView mBlurredView;

    public static GsFragment newInstance() {
        GsFragment fragment = new GsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.blurrediview_basic, container, false);
        ButterKnife.inject(this, contentView);

        // 初始化视图
        initViews();

        // 处理seekbar滑动事件
        setSeekBar();

        return contentView;
    }


    /**
     * 初始化视图
     */
    private void initViews() {

        // 可以在代码中使用setBlurredImg()方法指定需要模糊的图片
        mBlurredView.setBlurredImg(getResources().getDrawable(R.drawable.dayu));

        // 设置完成后需要调用showBlurredView方法显示要模糊的图片
        mBlurredView.showBlurredView();
    }

    /**
     * 处理seekbar滑动事件
     */
    private void setSeekBar() {
        mSeekBar.setMax(100);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mBlurredView.setBlurredLevel(progress);
                mProgressTv.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
