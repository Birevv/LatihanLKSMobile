package id.sch.smkn1bantul.latihanapi.adapter

import android.content.Intent
import android.net.Uri
import android.os.Binder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import id.sch.smkn1bantul.latihanlks2.databinding.ItemProductBinding
import id.sch.smkn1bantul.latihanlks2.model.products.Product
import id.sch.smkn1bantul.latihanlks2.ui.auth.SignUpActivity
import id.sch.smkn1bantul.latihanlks2.utils.loadImageWithUri

class ProductAdapter(val listener: ProductClickListener?) :
    ListAdapter<Product, ProductAdapter.ProductViewHolder>(DiffCallback()) {

    inner class ProductViewHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    listener?.onProductClicked(item)
                }
            }

             binding.ivDelete.setOnClickListener {
                 val position = bindingAdapterPosition
                 if (position != RecyclerView.NO_POSITION) {
                     val item = getItem(position)
                     listener?.onProductDeleted(item)
                 }
             }

            binding.ivEdit.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    listener?.onProductEdited(item)
                }
            }
        }

        fun bind(item: Product) {
            binding.tvName.text = item.name
            binding.tvPrice.text = item.price.toString()

//            Glide.with(binding.root.context)
//                .load(item.imageUrl)
//                .into(binding.ivProduct)

            val imageUrl = item.imageUrl
            binding.ivProduct.loadImageWithUri(imageUrl?.toUri(), true)
        }
    }

    interface ProductClickListener {
        fun onProductClicked(item: Product)
        fun onProductDeleted(item: Product)

        fun onProductEdited(item: Product)
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(layoutInflater, parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        holder.bind(item)
    }
}

class DiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(
        oldItem: Product,
        newItem: Product
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Product,
        newItem: Product
    ): Boolean {
        return oldItem == newItem
    }

}