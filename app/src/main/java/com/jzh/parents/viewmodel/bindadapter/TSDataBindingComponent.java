package com.jzh.parents.viewmodel.bindadapter;

import com.jzh.parents.widget.RegisterContentItem;

/**
 * Created by Ding on 2018/8/25.
 */

public class TSDataBindingComponent implements android.databinding.DataBindingComponent {

    @Override
    public RegisterContentItem.Companion getCompanion() {
        return RegisterContentItem.Companion;
    }
}
