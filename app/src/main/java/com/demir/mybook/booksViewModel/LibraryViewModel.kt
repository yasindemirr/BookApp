package com.demir.mybook.booksViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.demir.mybook.model.BooksLocal
import com.demir.mybook.repository.BooksLocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class LibraryViewModel @Inject constructor(
private val localRepository: BooksLocalRepository
):ViewModel() {
    fun realAllData():LiveData<List<BooksLocal>> {
       return localRepository.readAllData().flowOn(Dispatchers.Main)
            .asLiveData(viewModelScope.coroutineContext)
    }
    fun upsertBook(book:BooksLocal)=viewModelScope.launch {
        localRepository.upsertBook(book)
    }
    /*
    fun deleteBooks(list: List<BooksLocal>)=viewModelScope.launch {
        localRepository.deleteBooks(list)
    }

     */
    fun deleteBooks(books:BooksLocal)=viewModelScope.launch {
        localRepository.deleteBooks(books)
    }

}