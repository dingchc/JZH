package com.jzh.parents.viewmodel.bindadapter;

import com.jzh.parents.widget.RegisterContentItem;


/**
 * DataBing组件
 *
 * @author Ding
 */
public class TSDataBindingComponent implements android.databinding.DataBindingComponent {

    /**
     * 注册页面，自定义组件支持双向绑定
     *
     * @return Companion 静态类
     */
    @Override
    public RegisterContentItem.Companion getCompanion() {
        return RegisterContentItem.Companion;
    }
}
