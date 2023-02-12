package com.demir.mybook.ui

import android.app.Activity
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.demir.mybook.R
import com.demir.mybook.adepter.HomeAdepter
import com.demir.mybook.booksViewModel.BooksViewModel
import com.demir.mybook.databinding.FragmentSearchBinding
import com.demir.mybook.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
val SEARCH_NEWS_TIME_DELAY=500L
@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding:FragmentSearchBinding
    private val searchViewModel: BooksViewModel by viewModels()
    private lateinit var homeAdepter: HomeAdepter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentSearchBinding.inflate(layoutInflater,container,false)
        // Inflate the layout for this fragment
        requireActivity().getWindow().setStatusBarColor(requireActivity().getColor(R.color.white))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRec()
        observeData()
        initData()
        setItemClickListener()
        binding.backTo.setOnClickListener {
            findNavController().navigateUp()
        }

    }

    private fun setItemClickListener() {
        homeAdepter.onlick={
            val bundle=Bundle().apply {
                putParcelable("books",it)
            }
            findNavController().navigate(R.id.action_searchFragment_to_bookDetailFragment,bundle)
        }
    }

    private fun initData() {
        var job: Job?=null
        binding.searhTable.addTextChangedListener {editable->
            job?.cancel()
            job= MainScope().launch {
                delay(SEARCH_NEWS_TIME_DELAY)
                editable?.let {
                    if (editable.toString().isNotEmpty()){
                        searchViewModel.SearchBooks(editable.toString())
                    }else{
                        binding.searcRec.visibility=View.GONE
                    }
                }

            }

        }

    }

    private fun observeData() {
        lifecycleScope.launchWhenStarted {
            searchViewModel.bookState.collect{books->
                when(books){
                    is Resource.Success->{
                        homeAdepter.differ.submitList(books.data?.results)
                        binding.searcRec.visibility=View.VISIBLE
                    }
                    is Resource.loading->{}
                    is Resource.Error->{}
                    else->Unit

                }
            }
        }
    }

    private fun setUpRec() {
        homeAdepter= HomeAdepter()
        binding.searcRec.apply {
            setHasFixedSize(true)
            adapter=homeAdepter
            layoutManager= LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.searhTable.showKeyboard()

        binding.searhTable.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus){
                hideKeyboardFrom(requireContext(), requireView())
            }
        }
    }
    fun EditText.showKeyboard() {
        if (requestFocus()) {
            (context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager)
                .showSoftInput(this, SHOW_IMPLICIT)
            setSelection(text.length)
        }
    }

    fun hideKeyboardFrom(context: Context, view: View) {
        val imm =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


}