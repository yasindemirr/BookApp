package com.demir.mybook.adepter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.util.Util
import com.demir.mybook.R
import com.demir.mybook.databinding.LibraryItemBinding
import com.demir.mybook.model.Books
import com.demir.mybook.model.BooksLocal

class LibraryAdepter(val showIcon:(Boolean)->Unit):RecyclerView.Adapter<LibraryAdepter.LibraryHolder>() {

   var isEnable=false
    val itemSelectedList= mutableListOf<Int>()
    class LibraryHolder(val binding:LibraryItemBinding):RecyclerView.ViewHolder(binding.root)





    private val diffUtil=object :DiffUtil.ItemCallback<BooksLocal>(){
        override fun areItemsTheSame(oldItem: BooksLocal, newItem: BooksLocal): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: BooksLocal, newItem: BooksLocal): Boolean {
            return oldItem==newItem
        }
    }
    val differ=AsyncListDiffer(this,diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryHolder {
        val view=LibraryItemBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return LibraryHolder(view)
    }
    override fun onBindViewHolder(holder: LibraryHolder, position: Int) {
        val book=differ.currentList[position]
        holder.binding.libText.text=book.title
        holder.itemView.apply {
            Glide.with(this).load(book.imagejpeg).into(holder.binding.libImage)
        }

        holder.binding.frame.setOnLongClickListener {
                selectedItem(holder, book, position)
            if (book.selected==true){
                holder.binding.checkButton.visibility=View.VISIBLE

            }else{
                holder.binding.checkButton.visibility=View.GONE

            }
            true
        }
        holder.binding.frame.setOnClickListener {
                if (isEnable==false){
                    onlick?.let {
                        it.invoke(book)
                    }
                }
            if(itemSelectedList.contains(position)){
                itemSelectedList.remove(position)
                book.selected=false
                if (itemSelectedList.isEmpty()){
                    showIcon(false)
                    isEnable=false
                   book.selected=false //differ.currentList.forEach{it.selected=false}

                }
            }else if(isEnable==true){
                selectedItem(holder,book,position)
            }

            if (book.selected==true){
                holder.binding.checkButton.visibility=View.VISIBLE
            }else{
                holder.binding.checkButton.visibility=View.GONE


            }
        }

    }
    var onlick:((BooksLocal)-> Unit)? = null

    private fun selectedItem(holder:LibraryHolder, book: BooksLocal, position: Int) {
        isEnable=true
        itemSelectedList.add(position)
        book.selected=true
        showIcon(true)

    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}