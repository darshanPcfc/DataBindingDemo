package com.example.databindingdemo.ui.moduletwo.activities

import android.os.Bundle
import com.example.databindingdemo.BR
import com.example.databindingdemo.R
import com.example.databindingdemo.base.BaseActivity
import com.example.databindingdemo.databinding.ActivityScrollingBinding
import org.koin.android.viewmodel.ext.android.viewModel

class ModuleTwoActivity : BaseActivity<ActivityScrollingBinding, ModelTwoActivityViewModel>(),
    IModelTwoNavigator {
    override val viewModel: ModelTwoActivityViewModel by viewModel()
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_scrolling
    lateinit var acttivityTwoBinding: ActivityScrollingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        acttivityTwoBinding = this.viewDataBinding!!
        viewModel.setNavigator(this)
    }
}