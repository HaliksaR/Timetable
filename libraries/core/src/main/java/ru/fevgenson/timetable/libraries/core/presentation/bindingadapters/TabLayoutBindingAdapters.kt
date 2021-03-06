package ru.fevgenson.timetable.libraries.core.presentation.bindingadapters

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.google.android.material.tabs.TabLayout

@BindingAdapter("selectedTab")
fun TabLayout.setSelectedTab(selectedTab: Int) {
    getTabAt(selectedTab)?.let {
        it.customView?.post { it.select() }
    }
}

@InverseBindingAdapter(attribute = "selectedTab", event = "selectedTabAttrChanged")
fun TabLayout.getSelectedTab() = selectedTabPosition

@BindingAdapter("selectedTabAttrChanged", requireAll = false)
fun TabLayout.listenSelectedTabChanging(inverseBindingListener: InverseBindingListener) {
    addOnTabSelectedListener(
        object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabSelected(tab: TabLayout.Tab?) {
                inverseBindingListener.onChange()
            }
        }
    )
}