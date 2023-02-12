package com.demir.mybook.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.demir.mybook.R
import com.demir.mybook.adepter.HomeAdepter
import com.demir.mybook.booksViewModel.BooksViewModel
import com.demir.mybook.databinding.FragmentHomeBinding
import com.demir.mybook.util.Resource
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    val HEADAER_IMAGE="https://static01.nyt.com/images/2022/05/25/world/00ireland-library01/merlin_207199329_533e8788-cb70-46e3-8981-f991913ff987-videoSixteenByNine3000.jpg"
    private lateinit var homeAdepter: HomeAdepter
    var page=1L
    private val homeViewModel:BooksViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentHomeBinding.inflate(layoutInflater,container,false)
        // Inflate the layout for this fragment
        requireActivity().getWindow().setStatusBarColor(requireActivity().getColor(R.color.orange))
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            val toolBarHeight=binding.toolBar.measuredHeight
            val appBarHeight=binding.appBar.measuredHeight
            if (Math.abs(verticalOffset)>= (appBarHeight-toolBarHeight)){
                binding.searchIcon.visibility=View.VISIBLE
                binding.toolBarText.visibility=View.VISIBLE
            }else{
                binding.searchIcon.visibility=View.GONE
                binding.toolBarText.visibility=View.GONE
            }
    })
        setUpRecycler()
        homeViewModel.getAllBooks(page)
        observeData()
        itemClickListener()
        binding.searchIcon.setOnClickListener {
        findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
        binding.homeEdit.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

    private fun itemClickListener() {
        homeAdepter.onlick={
            val bundle=Bundle().apply {
                putParcelable("books",it)
            }
            findNavController().navigate(R.id.action_homeFragment_to_bookDetailFragment,bundle)
        }
    }

    private fun observeData() {
        /*
        homeViewModel.booksLiveData.observe(viewLifecycleOwner, Observer {
                it.let { books->
                    homeAdepter.UpdatePopularMeals(books.results)
                }
        })

         */

        lifecycleScope.launchWhenStarted {
            homeViewModel.bookState.collect{books->
                when(books){
                    is Resource.Success->{
                        homeAdepter.differ.submitList(books.data?.results)
                        binding.recHome.visibility=View.VISIBLE
                        binding.progressBar.visibility=View.GONE
                    }
                    is Resource.loading->{
                        binding.recHome.visibility=View.GONE
                        binding.progressBar.visibility=View.VISIBLE
                    }
                    is Resource.Error->{}
                    else->Unit

                }
            }
        }


    }

    private fun setUpRecycler() {
        homeAdepter= HomeAdepter()
        binding.recHome.apply {
            setHasFixedSize(true)
            adapter=homeAdepter
            layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        }
    }

}