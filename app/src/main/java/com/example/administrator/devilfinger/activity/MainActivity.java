package com.example.administrator.devilfinger.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.administrator.devilfinger.R;
import com.example.administrator.devilfinger.model.Category;
import com.example.administrator.devilfinger.ui.fragment.DrawerFragment;
import com.example.administrator.devilfinger.ui.fragment.MainFragment;
import com.example.administrator.devilfinger.ui.fragment.RobMoneyFragment;
import com.example.administrator.devilfinger.ui.fragment.SettingFragment;
import com.example.administrator.devilfinger.ui.slideMenu.BlurFoldingActionBarToggle;
import com.example.administrator.devilfinger.ui.slideMenu.FoldingDrawerLayout;
import com.example.administrator.wangshuobaselib.BaseActivity;
import com.example.administrator.wangshuobaselib.BaseFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.content_frame)
    FrameLayout contentLayout;
    @InjectView(R.id.blur_image)
    ImageView blurImage;
    //主控件
    @InjectView(R.id.drawer_layout)
    FoldingDrawerLayout mDrawerLayout;

//    private MainFragment mContentFragment;
//    private SettingFragment mSettingFragment;
//    private RobMoneyFragment mRobMoneyFragment;
      private BaseFragment baseFragment;


    //侧滑和毛玻璃效果
    private BlurFoldingActionBarToggle mDrawerToggle;

    private Category mCategory;

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        //init toolbar
        initToolBar();
    }

    private void initToolBar() {

        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        mDrawerLayout.setScrimColor(Color.argb(100, 255, 255, 255));
        //初始化侧滑和毛玻璃效果
        mDrawerToggle = new BlurFoldingActionBarToggle(
                this,
                mDrawerLayout,
                mToolbar,
                R.string.drawer_open,
                R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View view) {
                super.onDrawerOpened(view);
//                setTitle(R.string.app_name);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
                blurImage.setVisibility(View.GONE);
                blurImage.setImageBitmap(null);
            }
        };
        mDrawerToggle.setBlurImageAndView(blurImage, contentLayout);

        mDrawerToggle.syncState();

        //设置侧滑以及毛玻璃效果
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        setCategory(Category.main);

        replaceFragment(R.id.left_drawer, new DrawerFragment());
    }

    public void setCategory(Category category) {
        mDrawerLayout.closeDrawer(GravityCompat.START);
        if (mCategory == category) {
            return;
        }
        mCategory = category;
        setTitle(mCategory.getDisplayName());
        switch (mCategory) {
            case main:
                baseFragment = MainFragment.newInstance();
                break;
            case robRedMoney:
                baseFragment = RobMoneyFragment.newInstance();
                break;
            case setting:
                baseFragment = SettingFragment.newInstance();
                break;
            default:
                break;
        }

        replaceFragment(R.id.content_frame, baseFragment);
    }

    protected void replaceFragment(int viewId, BaseFragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(viewId, fragment).commit();
    }
}

