package com.example.databindingdemo.ui.moduleone.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.databindingdemo.BR
import com.example.databindingdemo.R
import com.example.databindingdemo.base.BaseFragment
import com.example.databindingdemo.databinding.FragmentSearchBinding
import com.example.databindingdemo.ui.moduleone.adapters.DataAdapter
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.android.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>(),
    ISearchObserver {
    override val viewModel: SearchViewModel by viewModel()
    private lateinit var fragmentSearchBinding: FragmentSearchBinding
    override val bindingVariable: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.fragment_search

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentSearchBinding = this.viewDataBinding!!
        try {
            viewModel.callmainApi().observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    println("API Call Value fragment" + it.users.get(0).display_name)

                    rv_contact_list?.apply {
                        layoutManager = LinearLayoutManager(context)
                        addItemDecoration(
                            DividerItemDecoration(
                                context,
                                DividerItemDecoration.VERTICAL
                            )
                        )
                        adapter =
                            DataAdapter(it.users)
                    }
                }
            })
        }catch (e:Throwable){}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setNavigator(this)

    }

    override fun setData() {
        //viewModel.callApiData()
        viewModel.callmainApi().observe(this, Observer {
            if (it != null) {
                println("API Call Value " + it.users.get(0).display_name)

                rv_contact_list?.apply {
                    layoutManager = LinearLayoutManager(context)
                    addItemDecoration(
                        DividerItemDecoration(
                            context,
                            DividerItemDecoration.VERTICAL
                        )
                    )
                    adapter =
                        DataAdapter(it.users)
                }
            }
        })
    }

    companion object {
        private val TAG = SearchFragment::class.java.simpleName
    }
}