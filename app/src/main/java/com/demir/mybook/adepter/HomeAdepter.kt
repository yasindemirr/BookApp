package com.demir.mybook.adepter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.demir.mybook.databinding.HomeItemBinding
import com.demir.mybook.model.Books

class HomeAdepter():RecyclerView.Adapter<HomeAdepter.HomeViewHolder>() {
    class HomeViewHolder(val binding: HomeItemBinding) : RecyclerView.ViewHolder(binding.root)


    private val diffUtillCallBack=object :DiffUtil.ItemCallback<Books>(){
        override fun areItemsTheSame(oldItem: Books, newItem: Books): Boolean {
            return oldItem.title==newItem.title
        }

        override fun areContentsTheSame(oldItem: Books, newItem: Books): Boolean {
            return oldItem==newItem
        }

    }
    val differ=AsyncListDiffer(this,diffUtillCallBack)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = HomeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val books=differ.currentList[position]
        holder.binding.title.text = books.title
        holder.binding.aurthor.text = books.authors?.get(0)?.name
        holder.binding.language.text=books.languages[0]
            holder.binding.subjects.text= books.subjects?.get(0)

        holder.itemView.apply {
            Glide.with(this).load(books.formats?.imagejpeg).into(holder.binding.homeImage)
        }
        holder.itemView.setOnClickListener {
            onlick?.let {
                it.invoke(books)
            }
        }

    }
    var onlick:((Books)-> Unit)? = null

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    /*
    fun UpdatePopularMeals(newPopularList: List<Books>) {
        bookList.clear()
        bookList.addAll(newPopularList)
        notifyDataSetChanged()
    }

     */
}