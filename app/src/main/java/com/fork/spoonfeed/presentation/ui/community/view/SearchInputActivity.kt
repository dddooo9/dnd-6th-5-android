package com.fork.spoonfeed.presentation.ui.community.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import com.fork.spoonfeed.R
import com.fork.spoonfeed.databinding.ActivitySearchInputBinding
import com.fork.spoonfeed.presentation.base.BaseViewUtil
import com.fork.spoonfeed.presentation.base.BaseViewUtil.BaseCategoryBottomDialogFragment.Companion.ALL
import com.fork.spoonfeed.presentation.base.BaseViewUtil.BaseCategoryBottomDialogFragment.Companion.DWELLING
import com.fork.spoonfeed.presentation.base.BaseViewUtil.BaseCategoryBottomDialogFragment.Companion.FINANCE
import com.fork.spoonfeed.presentation.ui.community.adapter.TabLayoutAdapter
import com.fork.spoonfeed.presentation.ui.community.viewmodel.SearchViewModel
import com.fork.spoonfeed.presentation.util.setBackBtnClickListener
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchInputActivity :
    BaseViewUtil.BaseAppCompatActivity<ActivitySearchInputBinding>(R.layout.activity_search_input) {

    private lateinit var searchInputAdapter: TabLayoutAdapter
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    override fun initView() {
        initTabLayoutAdapter()
        initTabLayout()
        setObserver()
        setOnClickListener()
        setSearchView()
        //       setInputField()
        this.setBackBtnClickListener(binding.ivSearchInputBack)
    }

    private fun initTabLayoutAdapter() {
        val fragmentList = listOf(
            SearchInputResultFragment().apply {
                arguments = bundleOf(POST_CATEGORY to ALL)
            },
            SearchInputResultFragment().apply {
                arguments = bundleOf(POST_CATEGORY to DWELLING)
            },
            SearchInputResultFragment().apply {
                arguments = bundleOf(POST_CATEGORY to FINANCE)
            }
        )
        searchInputAdapter = TabLayoutAdapter(this)
        searchInputAdapter.fragments.addAll(fragmentList)
        binding.vpSearchInput.adapter = searchInputAdapter
    }

    private fun initTabLayout() {
        val tabLabel = listOf("전체", "주거", "금융")
        TabLayoutMediator(binding.tlSearchInput, binding.vpSearchInput) { tab, position ->
            tab.text = tabLabel[position]
        }.attach()
        binding.tlSearchInput.isVisible = false
        binding.ivSearchInputClear.isVisible = false
    }

    private fun setObserver() {
        viewModel.searchQuery.observe(this) {
            if (!binding.tlSearchInput.isVisible) {
                binding.tlSearchInput.isVisible = true
            }
            viewModel.searchPost()
        }
    }

    private fun setOnClickListener() {
        binding.ivSearchInputClear.setOnClickListener {
            //      binding.etSearchInputBar.setText("")
        }
    }

    /*    private fun setInputField() {
            with(binding.etSearchInputBar) {
                setOnKeyListener { _, keyCode, event ->
                    if ((event.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                        viewModel.updateSearchQuery(text.toString())
                        true
                    } else {
                        false
                    }
                }
                addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        binding.ivSearchInputClear.isVisible = s?.length ?: 0 != 0
                    }

                    override fun afterTextChanged(s: Editable?) {}
                })
            }
        }*/
    private fun setSearchView() {
        binding.etSearchInputBar.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(newText: String?): Boolean { //완료버튼 클릭시
                viewModel.updateSearchQuery(newText.toString())
                //     myPageViewModel.getNameSearchFriendData(newText.toString())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean { //검색어 변경시
                val userInputText = newText ?: ""
                if (userInputText.count() == 0) {  //검색어 아무것도 없을 때
                    Log.d("아무것도 없음", "아무것도 없음")
                //    myPageViewModel.initFriend()
                    Log.d("getFriend", "getFriend실행")
                }
                return false
            }
        })
    }

    companion object {
        const val POST_CATEGORY = "com.fork.spoonfeed.presentation.ui.community.view POST_CATEGORY"
    }
}
