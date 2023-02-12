package com.demir.mybook.ui

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.demir.mybook.R
import com.demir.mybook.adepter.LibraryAdepter
import com.demir.mybook.booksViewModel.BooksViewModel
import com.demir.mybook.booksViewModel.LibraryViewModel
import com.demir.mybook.databinding.FragmentLibraryBinding
import com.demir.mybook.databinding.LibraryItemBinding
import com.demir.mybook.model.Books
import com.demir.mybook.model.BooksLocal
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LibraryFragment : Fragment() {
    private lateinit var binding: FragmentLibraryBinding
    private val libraryViewModel: LibraryViewModel by viewModels()
    private lateinit var libraryAdepter: LibraryAdepter
    private lateinit var booksLocal:BooksLocal
    private lateinit var books: Books

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentLibraryBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().getWindow().setStatusBarColor(requireActivity().getColor(R.color.white))
        setUpRec()
        observeLibrary()
        binding.backTo.setOnClickListener {
            findNavController().navigateUp()
        }

        deleteSlelected(view)

    }

    private fun deleteSlelected(view: View) {
        binding.deleteIcon.setOnClickListener {
            libraryAdepter.itemSelectedList.forEach {
                booksLocal = libraryAdepter.differ.currentList[it]
                libraryViewModel.deleteBooks(booksLocal)
              //  libraryAdepter.itemSelectedList
                showDeleteIcon(false)
            }
            libraryAdepter.itemSelectedList.clear()
            libraryAdepter.isEnable=false

            Snackbar.make(view,"Delete Succesfully",Snackbar.LENGTH_SHORT).show()
            }
    }

    private fun showDeleteIcon(show:Boolean) {
        binding.deleteIcon.isVisible=show

    }

    private fun observeLibrary() {
        libraryViewModel.realAllData().observe(viewLifecycleOwner, Observer {
            libraryAdepter.differ.submitList(it)
        })
    }

    private fun setUpRec() {
        showDeleteIcon(false)
        libraryAdepter= LibraryAdepter(){showIcon->showDeleteIcon(showIcon)
        }
       // libraryAdepter.isLong=false
        binding.libRec.apply {
            setHasFixedSize(true)
            adapter=libraryAdepter
            layoutManager= GridLayoutManager(context,2, GridLayoutManager.VERTICAL,false)
        }
/*
        binding.frame.setOnLongClickListener {
            libraryAdepter.isLong=true
            libraryAdepter.notifyItemRangeChanged(0,libraryAdepter.differ.currentList.size)
            return@setOnLongClickListener true
        }

 */

        }
}