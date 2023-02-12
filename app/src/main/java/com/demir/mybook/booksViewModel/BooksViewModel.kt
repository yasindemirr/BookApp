package com.demir.mybook.booksViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demir.mybook.model.BookResponse
import com.demir.mybook.repository.BooksRepository
import com.demir.mybook.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class BooksViewModel
@Inject constructor(val repository: BooksRepository):
ViewModel(){
   // val booksLiveData=MutableLiveData<BookResponse>()

    private val _booksState=MutableStateFlow<Resource<BookResponse>>(Resource.unspecifed())
    val bookState=_booksState.asStateFlow()

    fun getAllBooks(page:Long){
        viewModelScope.launch {
            _booksState.emit(Resource.loading())
            repository.getAllBooks(page).catch { e->
                _booksState.emit(Resource.Error(e.message.toString()))
            }.collect{data->
                _booksState.emit(Resource.Success(data))
            }

        }

    }
    fun SearchBooks(searchQuery:String){
        viewModelScope.launch {
            _booksState.emit(Resource.loading())
            repository.searchBooks(searchQuery).catch { e->
                _booksState.emit(Resource.Error(e.message.toString()))
            }.collect{data->
                _booksState.emit(Resource.Success(data))
            }
        }
    }
    fun CategoryBooks(topic:String,page:Long){
        viewModelScope.launch {
            _booksState.emit(Resource.loading())
            repository.categoryBooks(topic,page).catch { e->
                _booksState.emit(Resource.Error(e.message.toString()))
            }.collect{data->
                _booksState.emit(Resource.Success(data))
            }
        }
    }


    /*
    fun getAllBooks(page:String)=viewModelScope.launch {
        val response=repository.getAllBooks(page)
    if (response.isSuccessful){
        response.body()?.let {
            booksLiveData.value=it
        }
    }
}

     */

}