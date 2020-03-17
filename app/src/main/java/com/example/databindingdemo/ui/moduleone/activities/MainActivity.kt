package com.example.databindingdemo.ui.moduleone.activities

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.databindingdemo.BR
import com.example.databindingdemo.R
import com.example.databindingdemo.base.BaseActivity
import com.example.databindingdemo.base.BaseApplication
import com.example.databindingdemo.databinding.ActivityMainBinding
import com.example.databindingdemo.ni.remote.response.Users
import com.example.databindingdemo.ui.MainActivityViewModel
import com.example.databindingdemo.ui.moduleone.activities.daodata.Words
import com.example.databindingdemo.ui.moduleone.activities.viewmodel.UserViewModel
import com.example.databindingdemo.ui.moduleone.adapters.DataAdapter
import com.example.databindingdemo.ui.widget.SwipeHelper
import com.example.databindingdemo.ui.widget.SwipeHelper.UnderlayButtonClickListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity<ActivityMainBinding, MainActivityViewModel>(),
    IMainActivityNavigator {

    override val viewModel: MainActivityViewModel by viewModel()
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_main
    lateinit var activityMainBinding: ActivityMainBinding
    private var isApiNeedToBeCalled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = this.viewDataBinding!!
        viewModel.setNavigator(this)
        initToobaarComponentStyle()
        insertInDataSet()
    }

    fun initToobaarComponentStyle() {
        toolbar_layout.setExpandedTitleTextAppearance(R.style.collapsingToolbarCollapsedLayoutTitleColor)
        toolbar_layout.setCollapsedTitleTextAppearance(R.style.collapsingToolbarLayoutTitleColor)
        tip_search.requestFocus()
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onBackPressed() {
        finishAffinity()
    }

    fun insertInDataSet() {
        try {
            if (!isApiNeedToBeCalled) {
                viewModel.callmainApi().observe(this, Observer {
                    if (it != null) {
                        viewModel.insertInsideUser(it.users)
                        populatedata(it.users)
                    }
                })
            }
        } catch (e: Throwable) {
        }
    }

    override fun setData() {
    }

    override fun fetchAllUsers() {
        val userViewModel = UserViewModel(BaseApplication.instance!!)
        userViewModel.alluser.observe(this, Observer {
            if (it.size > 0) {
                populatedata(it)
                isApiNeedToBeCalled = true
            } else {
                errorLayoutDisplay()
                isApiNeedToBeCalled = false
                insertInDataSet()
            }
        })
    }

    private fun populatedata(users: List<Users>) {
        tv_error.visibility = View.GONE
        rv_contact_list.visibility = View.VISIBLE
        rv_contact_list?.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter =
                DataAdapter(users)
        }
        swipeDataDesign()
    }

    private fun swipeDataDesign() {
        object : SwipeHelper(this, rv_contact_list) {
            override fun instantiateUnderlayButton(
                viewHolder: RecyclerView.ViewHolder,
                underlayButtons: MutableList<UnderlayButton>
            ) {
                underlayButtons.add(
                    UnderlayButton(
                        "Add",
                        0,
                        Color.parseColor("#FF3C30"),
                        UnderlayButtonClickListener {
                            Toast.makeText(
                                this@MainActivity,
                                "Add Button Clicked",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    ))
                underlayButtons.add(
                    UnderlayButton(
                        "Delete",
                        0,
                        Color.parseColor("#C7C7CB"),
                        UnderlayButtonClickListener {
                            Toast.makeText(
                                this@MainActivity,
                                "Delete Button Clicked",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    ))
            }
        }
    }


    override fun searchBlacklistStringData(string: String) {
        val userViewModel = UserViewModel(BaseApplication.instance!!)
        userViewModel.fetchSearchWord(search = string).observe(this@MainActivity, Observer {
            if (it.isEmpty()) {
                //search in userTable as string not present in blacklist table
                searchInUserTable(string)
            } else {
                //display error string as string in blacklist table
                errorLayoutDisplay()
                Log.e("MainAc", "pop ==>> $string")
                //insertInWord(string)
            }
        })
    }

    /* private fun errorLayoutDisplay() {
         rv_contact_list.visibility = View.GONE
     }*/

    override fun errorLayoutDisplay() {
        var count = 0
        Log.e("MainAc", "err diplay count == " + count)
        rv_contact_list.visibility = View.GONE
        tv_error.text = resources.getText(R.string.str_no_data)
        tv_error.visibility = View.VISIBLE
        count++
    }

    fun searchInUserTable(string: String) {
        val userViewModel = UserViewModel(BaseApplication.instance!!)
        userViewModel.fetchUser(string).observe(this@MainActivity, Observer {
            if (it.isEmpty()) {
                //insertText in black list
                errorLayoutDisplay()
                insertInWord(string)
                Log.e("MainAc", "black ==<< $string")
            } else {
                //populate data on recyclerview
                populatedata(it)
            }
        })
    }

    private fun insertInWord(string: String) {
        val userViewModel = UserViewModel(BaseApplication.instance!!)
        GlobalScope.launch {
            val word = Words(string)
            userViewModel.insertWords(word)
        }
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}
