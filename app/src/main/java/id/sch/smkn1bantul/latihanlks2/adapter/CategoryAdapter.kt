package id.sch.smkn1bantul.latihanlks2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.sch.smkn1bantul.latihanlks2.databinding.ItemCategoryBinding
import id.sch.smkn1bantul.latihanlks2.model.products.Category
import id.sch.smkn1bantul.latihanlks2.utils.loadImageWithUri
import okhttp3.internal.http2.FlowControlListener


class CategoryAdapter(val listener: CategoryClickListener?) :
    ListAdapter<Category, CategoryAdapter.CategoryViewHolder>(DiffCallback()) {

    inner class CategoryViewHolder(val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    listener?.onCategoryClicked(item)
                }
            }

            binding.ivDelete.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    listener?.onCategoryDeleted(item)
                }
            }

            binding.ivEdit.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    listener?.onCategoryEdited(item)
                }
            }
        }

        fun bind(item: Category) {
            binding.tvId.text = item.id.toString()
            binding.tvName.text = item.name.toString()

//            Glide.with(binding.root.context)
//                .load(item.imageUrl)
//                .into(binding.ivCategory)
        }
    }

    interface CategoryClickListener {
        fun onCategoryClicked(item: Category)
        fun onCategoryDeleted(item: Category)

        fun onCategoryEdited(item: Category)
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoryBinding.inflate(layoutInflater, parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CategoryViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        holder.bind(item)
    }
}


class DiffCallback : DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(
        oldItem: Category,
        newItem: Category
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Category,
        newItem: Category
    ): Boolean {
        return oldItem == newItem
    }

}