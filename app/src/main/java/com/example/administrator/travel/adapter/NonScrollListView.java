package com.example.administrator.travel.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by Administrator on 08/10/2018.
 */

public class NonScrollListView extends ListView {

    public NonScrollListView(Context context) {
        super(context);
    }
    public NonScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public NonScrollListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(MeasureSpec.UNSPECIFIED, 0));

        int childHeight = getMeasuredHeight() - (getListPaddingTop() + getListPaddingBottom() +  getVerticalFadingEdgeLength() * 2);

        // on a first run let's have a space for at least one child so it'll trigger remeasurement
        int fullHeight = getListPaddingTop() + getListPaddingBottom() + childHeight*(getCount());

        int newChildHeight = 0;
        for (int x = 0; x<getChildCount(); x++ ){
            View childAt = getChildAt(x);

            if (childAt != null) {
                int height = childAt.getHeight();
                newChildHeight += height;
            }
        }

        //on a second run with actual items - use proper size
        if (newChildHeight != 0)
            fullHeight = getListPaddingTop() + getListPaddingBottom() + newChildHeight;

        setMeasuredDimension(getMeasuredWidth(), fullHeight);
    }
}
