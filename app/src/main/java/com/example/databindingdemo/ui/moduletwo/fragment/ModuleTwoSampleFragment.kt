package com.example.databindingdemo.ui.moduletwo.fragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.databindingdemo.BR
import com.example.databindingdemo.R
import com.example.databindingdemo.base.BaseFragment
import com.example.databindingdemo.databinding.FragmentModuleOneBinding
import org.koin.android.viewmodel.ext.android.viewModel

class ModuleTwoSampleFragment:BaseFragment<FragmentModuleOneBinding,ModTwoViewModel>(),IModelTwoNavigation {
    override val viewModel: ModTwoViewModel by viewModel()
    private lateinit var fragmentPanValidateBinding: FragmentModuleOneBinding
    override val bindingVariable: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.fragment_module_one


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentPanValidateBinding = this.viewDataBinding!!

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setNavigator(this)
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity).supportActionBar!!.show()
    }
}