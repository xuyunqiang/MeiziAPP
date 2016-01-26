package com.yunq.gankio.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.yunq.gankio.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 16/1/5.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    protected Toolbar mToolbar;

    protected abstract int getLayout();

    protected abstract int getMenuRes();

    protected  void initPresenter(){}

//    protected D getActivityComponent() {
//        if (mActivityComponent == null) {
//            mActivityComponent = (D) DaggerActivityComponent.builder()
//                    .activityModule(new ActivityModule(this))
//                    .applicationComponent(GankApp.get(this).getComponent())
//                    .build();
//        }
//        return mActivityComponent;
//    }

    protected abstract void initInjection();



    final private void initToolBar() {
        if (mToolbar == null) {
            throw new NullPointerException("请在布局文件中加入Toolbar");
        }
        setSupportActionBar(mToolbar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
      //  initPresenter();
      //  checkPresenterIsNull();
        initToolBar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(getMenuRes(), menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setTitle(String tilte, boolean showHome) {
        setTitle(tilte);
        getSupportActionBar().setDisplayHomeAsUpEnabled(showHome);
        getSupportActionBar().setDisplayShowHomeEnabled(showHome);
    }
}
