/*
 * Copyright (C) 2016  Hendrik Borghorst & Frederik Luetkes
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.gateshipone.malp.application.utils;


import android.widget.AbsListView;
import android.widget.GridView;

import org.gateshipone.malp.application.adapters.ScrollSpeedAdapter;
import org.gateshipone.malp.application.listviewitems.GenericGridItem;

public class ScrollSpeedListener implements AbsListView.OnScrollListener {

    private long mLastTime = 0;
    private int mLastFirstVisibleItem = 0;
    private int mScrollSpeed = 0;

    private final ScrollSpeedAdapter mAdapter;
    private final GridView mRootGrid;

    public ScrollSpeedListener(ScrollSpeedAdapter adapter, GridView rootGrid) {
        super();
        mRootGrid = rootGrid;
        mAdapter = adapter;
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            mScrollSpeed = 0;
            mAdapter.setScrollSpeed(0);
            for (int i = 0; i <= mRootGrid.getLastVisiblePosition() - mRootGrid.getFirstVisiblePosition(); i++) {
                GenericGridItem gridItem = (GenericGridItem) mRootGrid.getChildAt(i);
                gridItem.startCoverImageTask();
            }
        }
    }

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (firstVisibleItem != mLastFirstVisibleItem) {
            long currentTime = System.currentTimeMillis();
            if (currentTime == mLastTime) {
                return;
            }
            long timeScrollPerRow = currentTime - mLastTime;
            mScrollSpeed = (int) (1000 / timeScrollPerRow);
            mAdapter.setScrollSpeed(mScrollSpeed);

            mLastFirstVisibleItem = firstVisibleItem;
            mLastTime = currentTime;

            if (mScrollSpeed < visibleItemCount) {
                for (int i = 0; i < visibleItemCount; i++) {
                    GenericGridItem gridItem = (GenericGridItem) mRootGrid.getChildAt(i);
                    gridItem.startCoverImageTask();
                }
            }
        }

    }
}
