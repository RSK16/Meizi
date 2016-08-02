package com.spark.meizi.meizi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.spark.meizi.R;
import com.spark.meizi.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends BaseActivity {

    @BindView(R.id.pager)
    ViewPager pager;
    private ArrayList<String> meiziUrls;
    private DetailPagerAdapter detailPagerAdapter;

    @Override
    public void initSubViews(View view) {
        super.initSubViews(view);
        ButterKnife.bind(this);
        supportStartPostponedEnterTransition();
        meiziUrls = getIntent().getStringArrayListExtra("meiziUrls");
        int index = getIntent().getIntExtra("index", 0);
        log("index url " + index + ": " + meiziUrls.get(index));
        detailPagerAdapter = new DetailPagerAdapter();
        pager.setAdapter(detailPagerAdapter);
        pager.setCurrentItem(index);
        setEnterSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                sharedElements.clear();
                sharedElements.put(meiziUrls.get(pager.getCurrentItem()),
                        detailPagerAdapter.getCurrent().getSharedElement());
            }
        });

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_detail;
    }

    @Override
    public void supportFinishAfterTransition() {
        Intent data = new Intent();
        data.putExtra("index", pager.getCurrentItem());
        setResult(RESULT_OK, data);

        super.supportFinishAfterTransition();
    }

    private class DetailPagerAdapter extends FragmentStatePagerAdapter {
        public DetailPagerAdapter() {
            super(getSupportFragmentManager());
        }

        @Override
        public Fragment getItem(int position) {
            return DetailFragment.newInstance(meiziUrls.get(position));
        }

        @Override
        public int getCount() {
            return meiziUrls.size();
        }

        public DetailFragment getCurrent() {
            return (DetailFragment)detailPagerAdapter.instantiateItem(pager, pager.getCurrentItem());
        }

    }


}
