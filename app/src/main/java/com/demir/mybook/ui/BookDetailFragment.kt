package com.demir.mybook.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.demir.mybook.booksViewModel.LibraryViewModel
import com.demir.mybook.databinding.FragmentBookDetailBinding
import com.demir.mybook.model.Books
import com.demir.mybook.model.BooksLocal
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookDetailFragment : Fragment() {
    private lateinit var binding:FragmentBookDetailBinding
    val args by navArgs<BookDetailFragmentArgs>()
    private val libraryViewModel: LibraryViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentBookDetailBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val books=args.books
        initDetail(books)
        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }

        val booksLocal=BooksLocal(books.title!!,books.formats?.imagejpeg,books.id,
            books.authors?.get(0)?.name)
        binding.savIcon.setOnClickListener {
            libraryViewModel.upsertBook(booksLocal)
            Snackbar.make(view,"You saved succesfully",Snackbar.LENGTH_SHORT).show()
        }



    }

    private fun initDetail(books:Books) {
        binding.detailAuthor.text="${books.authors?.get(0)?.name}(${books.authors?.get(0)?.birth_year}-${books.authors?.get(0)?.death_year})"
        binding.detailTitle.text=books.title
        Glide.with(this).load(books.formats?.imagejpeg).into(binding.detailImage)

    }


}