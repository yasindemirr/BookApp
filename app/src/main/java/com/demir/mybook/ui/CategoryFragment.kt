package com.demir.mybook.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.demir.mybook.R
import com.demir.mybook.adepter.HomeAdepter
import com.demir.mybook.booksViewModel.BooksViewModel
import com.demir.mybook.databinding.FragmentCategoryBinding
import com.demir.mybook.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.log

@AndroidEntryPoint
class CategoryFragment : Fragment() {
    private lateinit var binding:FragmentCategoryBinding
    private lateinit var categoryAdapter: HomeAdepter
    private val categoryViewModel: BooksViewModel by viewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentCategoryBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().getWindow().setStatusBarColor(requireActivity().getColor(R.color.white))
        setUpRec()
        val categories = resources.getStringArray(R.array.Categories)
        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_layout, categories)
        binding.autoTextView.setAdapter(adapter)

        binding.autoTextView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->

                    categoryViewModel.CategoryBooks(categories[position],1L)

            }
        observeData()
        binding.backTo.setOnClickListener {
            findNavController().navigateUp()
        }
        setOnItemClickListener()
    }

    private fun setOnItemClickListener() {
        categoryAdapter.onlick={
            val bundle=Bundle().apply {
                putParcelable("books",it)
            }
            findNavController().navigate(R.id.action_categoryFragment_to_bookDetailFragment,bundle)
        }
    }

    private fun observeData() {
        lifecycleScope.launchWhenStarted {
            categoryViewModel.bookState.collect{books->
                when(books){
                    is Resource.Success->{
                        categoryAdapter.differ.submitList(books.data?.results)

                    }
                    is Resource.loading->{

                    }
                    is Resource.Error->{
                        Log.d("Error",books.message.toString())
                    }
                    else->Unit

                }
            }
        }
    }

    private fun setUpRec() {
        categoryAdapter= HomeAdepter()
        binding.catRec.apply {
            setHasFixedSize(true)
            adapter=categoryAdapter
            layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}