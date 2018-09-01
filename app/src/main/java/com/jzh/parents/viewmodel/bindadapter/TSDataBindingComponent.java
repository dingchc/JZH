package com.jzh.parents.viewmodel.bindadapter;

import com.jzh.parents.widget.RegisterContentItem;


/**
 * DataBing组件
 *
 * @author Ding
 */
public class TSDataBindingComponent implements android.databinding.DataBindingComponent {

    @Override
    public TSBindingAdapter.Companion getCompanion1() {
        return TSBindingAdapter.Companion;
    }

    @Override
    public RegisterContentItem.Companion getCompanion2() {
        return RegisterContentItem.Companion;
    }
}
