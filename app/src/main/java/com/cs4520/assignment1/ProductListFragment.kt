package com.cs4520.assignment1

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ProductListFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.product_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val productsRecyclerView = view.findViewById<RecyclerView>(R.id.productsRecyclerView)
        productsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        val productsList = convertDatasetToProducts(productsDataset)
        productsRecyclerView.adapter = MyProductAdapter(productsList)
    }

    private fun convertDatasetToProducts(dataset: List<List<Any?>>): List<Product> {
        return dataset.map { item ->
            val name = item[0] as String
            val type = item[1] as String
            val expiryDate = item[2] as? String
            val price = item[3] as Int
            when (type) {
                "Equipment" -> Product.Equipment(name, price, R.drawable.equipment)
                "Food" -> Product.Food(name, price, expiryDate ?: "", R.drawable.food)
                else -> throw IllegalArgumentException("Unknown product type")
            }
        }
    }

    sealed class Product(val name: String, val price: Int, val imageResId: Int) {
        class Equipment(name: String, price: Int, imageResId: Int) : Product(name, price, imageResId)
        class Food(name: String, price: Int, val expiryDate: String, imageResId: Int) : Product(name, price, imageResId)
    }

    class MyProductAdapter(private val products: List<Product>) : RecyclerView.Adapter<MyProductAdapter.ProductViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
            return ProductViewHolder(itemView)
        }

        override fun getItemCount(): Int = products.size

        override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
            val product = products[position]
            holder.bind(product)
        }

        class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val nameTextView: TextView = itemView.findViewById(R.id.productName)
            private val priceTextView: TextView = itemView.findViewById(R.id.productPrice)
            private val expiryDateTextView: TextView = itemView.findViewById(R.id.productExpiryDate)
            private val productImageView: ImageView = itemView.findViewById(R.id.productImage)

            fun bind(product: Product) {
                nameTextView.text = product.name
                priceTextView.text = itemView.context.getString(R.string.price_format, product.price.toFloat())
                productImageView.setImageResource(product.imageResId)

                if (product is Product.Food && product.expiryDate.isNotEmpty()) {
                    expiryDateTextView.visibility = View.VISIBLE
                    expiryDateTextView.text = "Exp: ${product.expiryDate}"
                } else {
                    expiryDateTextView.visibility = View.GONE
                }

                itemView.setBackgroundColor(
                    if (product is Product.Food) Color.parseColor("#FFD965") // Light Yellow for Food
                    else Color.parseColor("#E06666") // Light Red for Equipment
                )
            }
        }
    }
}


