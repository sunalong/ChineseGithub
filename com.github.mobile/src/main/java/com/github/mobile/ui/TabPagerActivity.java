/*
 * Copyright 2012 GitHub Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.mobile.ui;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.github.kevinsawicki.wishlist.ViewUtils;
import com.github.mobile.R.drawable;
import com.github.mobile.R.id;
import com.github.mobile.R.layout;
import com.github.mobile.util.TypefaceUtils;

/**
 * Activity with tabbed pages
 *注意Adapter的声明（protected V adapter;）
 * @param <V>
 */
public abstract class TabPagerActivity<V extends PagerAdapter & FragmentProvider>
        extends PagerActivity implements OnTabChangeListener, TabContentFactory {

    /**
     * View pager
     */
    protected ViewPager pager;

    /**
     * Tab host
     */
    protected TabHost host;

    /**
     * Pager adapter
     */
    protected V adapter;

    @Override
    public void onPageSelected(final int position) {
        super.onPageSelected(position);

        host.setCurrentTab(position);
    }

    @Override
    public void onTabChanged(String tabId) {
        updateCurrentItem(host.getCurrentTab());
    }

    @Override
    public View createTabContent(String tag) {
        return ViewUtils.setGone(new View(getApplication()), true);
    }

    /**
     * Create pager adapter
     *
     * @return pager adapter
     */
    protected abstract V createAdapter();

    /**
     * Get title for position
     *
     * @param position
     * @return title
     */
    protected String getTitle(final int position) {
        return adapter.getPageTitle(position).toString();
    }

    /**
     * Get icon for position
     *为每个标签设置名字，由子类覆写
     * @param position
     * @return icon
     */
    protected String getIcon(final int position) {
        return null;
    }

    /**
     * Set tab and pager as gone or visible
     *
     * @param gone
     * @return this activity
     */
    protected TabPagerActivity<V> setGone(boolean gone) {
        ViewUtils.setGone(host, gone);
        ViewUtils.setGone(pager, gone);
        return this;
    }

    /**
     * Set current item to new position
     * <p>
     * This is guaranteed to only be called when a position changes and the
     * current item of the pager has already been updated to the given position
     * <p>
     * Sub-classes may override this method
     *
     * @param position
     */
    protected void setCurrentItem(final int position) {
        // Intentionally left blank
    }

    /**
     * Get content view to be used when {@link #onCreate(Bundle)} is called
     *
     * @return layout resource id
     */
    protected int getContentView() {
        return layout.pager_with_tabs;
    }

    private void updateCurrentItem(final int newPosition) {
        if (newPosition > -1 && newPosition < adapter.getCount()) {
            pager.setItem(newPosition);
            setCurrentItem(newPosition);
        }
    }

    /**
     * ①：初始化adapter
     * ②：更新Menu菜单
     * ③：为ViewPager设置adapter
     */
    private void createPager() {
        adapter = createAdapter();
        invalidateOptionsMenu();
        pager.setAdapter(adapter);
    }

    /**
     * Create tab using information from current adapter<br>
     * 根据当前的Adapter的信息创建tab
     * <p>
     * This can be called when the tabs changed but must be called after an
     * initial call to {@link #configureTabPager()}<br>
     * 此方法可以在Tabs变化后被调用，但是它必须在初始化的调用：configureTabPager()之后才能调用
     *
     */
    protected void createTabs() {
        /*
         * 如果host中的TabWidget不空，则先将其清空
         */
        if (host.getTabWidget().getTabCount() > 0) {
            // Crash on Gingerbread if tab isn't set to zero since adding a
            // new tab removes selection state on the old tab which will be
            // null unless the current tab index is the same as the first
            // tab index being added
            host.setCurrentTab(0);
            host.clearAllTabs();
        }

        LayoutInflater inflater = getLayoutInflater();
        int count = adapter.getCount();
        for (int i = 0; i < count; i++) {
            TabSpec spec = host.newTabSpec("tab" + i);
            spec.setContent(this);
            View view = inflater.inflate(layout.tab, null);
            TextView icon = (TextView) view.findViewById(id.tv_icon);
            String iconText = getIcon(i);
            if (!TextUtils.isEmpty(iconText))
                icon.setText(getIcon(i));
            else
                ViewUtils.setGone(icon, true);
            TypefaceUtils.setOcticons(icon);//为标签名设置样式
            ((TextView) view.findViewById(id.tv_tab)).setText(getTitle(i));

            spec.setIndicator(view);
            host.addTab(spec);

            //设置背景图片
            int background;
            if (i == 0)
                background = drawable.tab_selector_right;//最左边的背景选择器
            else if (i == count - 1)
                background = drawable.tab_selector_left;//最右边的背景选择器
            else
                background = drawable.tab_selector_left_right;//左右两边都有标签的标签的背景选择器
            ((ImageView) view.findViewById(id.iv_tab))
                    .setImageResource(background);
        }
    }

    /**
     * Configure tabs and pager
     * 配置tabs和viewPager
     * ①：
     *
     */
    protected void configureTabPager() {
        if (adapter == null) {
            createPager();
            createTabs();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getContentView());
        pager = (ViewPager) findViewById(id.vp_pages);
        pager.setOnPageChangeListener(this);
        host = (TabHost) findViewById(id.th_tabs);
        host.setup();
        host.setOnTabChangedListener(this);
    }

    @Override
    protected FragmentProvider getProvider() {
        return adapter;
    }
}
