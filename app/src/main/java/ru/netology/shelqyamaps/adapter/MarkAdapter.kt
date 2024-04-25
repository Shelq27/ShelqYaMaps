package ru.netology.shelqyamaps.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.netology.shelqyamaps.R
import ru.netology.shelqyamaps.databinding.MarkItemBinding
import ru.netology.shelqyamaps.dto.Mark


class MarkAdapter(
    private val listener: Listener,
) : ListAdapter<Mark, MarkAdapter.MarksViewHolder>(DiffCallback) {

    interface Listener {
        fun onClick(mark: Mark)
        fun onDelete(mark: Mark)
        fun onEdit(mark: Mark)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarksViewHolder {
        val binding = MarkItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        val holder = MarksViewHolder(binding)

        with(binding) {
            root.setOnClickListener {
                val mark = getItem(holder.adapterPosition)
                listener.onClick(mark)
            }

            menuMark.setOnClickListener {
                PopupMenu(root.context, it).apply {
                    inflate(R.menu.menu_mark)

                    setOnMenuItemClickListener { item ->
                        val mark = getItem(holder.adapterPosition)
                        when (item.itemId) {


                            R.id.edit -> {
                                listener.onEdit(mark)
                                true
                            }

                            R.id.delete -> {
                                listener.onDelete(mark)
                                true
                            }

                            else -> false
                        }
                    }

                    show()
                }
            }
        }

        return holder
    }

    override fun onBindViewHolder(holder: MarksViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MarksViewHolder(
        private val binding: MarkItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(mark: Mark) {
            with(binding) {
                title.text = mark.name
            }
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<Mark>() {
        override fun areItemsTheSame(oldItem: Mark, newItem: Mark): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Mark, newItem: Mark): Boolean =
            oldItem == newItem
    }
}

