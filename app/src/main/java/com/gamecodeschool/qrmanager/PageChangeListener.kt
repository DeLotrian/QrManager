package com.gamecodeschool.qrmanager

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.RelativeLayout
import androidx.viewpager.widget.ViewPager
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_scanqr.*


class PageChangeListener(val activity: Activity) : ViewPager.OnPageChangeListener {
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        var appBar: AppBarLayout = activity.appBarLayout
        val params : RelativeLayout.LayoutParams = appBar.layoutParams as RelativeLayout.LayoutParams


        var indicatorWidth = activity.tabs.getWidth() / activity.tabs.getTabCount()
        val translationOffset: Float = (positionOffset + position) * indicatorWidth
        params.leftMargin = translationOffset.toInt()
        activity.appBarLayout.setLayoutParams(params)
    }

    override fun onPageSelected(position: Int) {
        hideKeyboard()
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    private fun hideKeyboard() {
        val view = activity.currentFocus
        if(view != null) {
            val imm = activity.applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}