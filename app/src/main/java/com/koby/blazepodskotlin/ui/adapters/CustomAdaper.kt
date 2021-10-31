package com.koby.blazepodskotlin.ui.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.koby.blazepodskotlin.R
import com.koby.blazepodskotlin.data.model.Item
import kotlinx.android.synthetic.main.row_item_layout.view.*

class CustomAdaper: ListAdapter<Item, CustomAdaper.MyAdapterViewHolder>(REPO_COMPARATOR) {

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Item, newItem: Item) =
                oldItem == newItem
        }
    }

    var onItemClickListener : ((String)->Unit)? = null

    fun setOnClick(listner : ((String) -> Unit)){
        onItemClickListener = listner
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapterViewHolder {
        return MyAdapterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_item_layout, parent, false))
    }

    inner class MyAdapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val textView = itemView.findViewById<TextView>(R.id.textView)
        fun bind(position: Int) {
            with(itemView) {
                itemView.textView.text = getItem(position).title.toString()
                itemView.setOnClickListener{
                    onItemClickListener?.invoke(getItem(position).title.toString())
                }
            }
        }
    }

    override fun onBindViewHolder(holder: MyAdapterViewHolder, position: Int) {
        holder.bind(position)
    }
}