package com.example.groceryapp

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.grocery_add_dialog.*

class MainActivity : AppCompatActivity(), GroceryRVAdapter.GroceryItemClickListener {

    lateinit var list: List<GroceryItems>
    lateinit var groceryRVAdapter: GroceryRVAdapter
    lateinit var groceryViewModel: GroceryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        list = ArrayList()
        groceryRVAdapter = GroceryRVAdapter(list, this)
        rvItems?.layoutManager = LinearLayoutManager(this)
        rvItems?.adapter = groceryRVAdapter
        val groceryRepository = GroceryRepository(GroceryDatabase(this))
        val factory = GroceryViewModelFactory(groceryRepository)
        groceryViewModel = ViewModelProvider(this, factory)[GroceryViewModel::class.java]
        groceryViewModel.getAllGroceryItems().observe(this) {
            groceryRVAdapter.list = it
            groceryRVAdapter.notifyDataSetChanged()
        }

        FABAdd.setOnClickListener {
            openDialog()
        }
    }

    private fun openDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.grocery_add_dialog)
        val cancelBtn = dialog.btnCancel
        val addBtn = dialog.btnAdd
        val itemEdit = dialog.edAddItemName
        val itemQuantityEdit = dialog.edAddItemQuantity
        val itemPriceEdit = dialog.edAddItemPrice

        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }
        addBtn.setOnClickListener {
            val itemName = itemEdit.text.toString()
            val itemQuantity = itemQuantityEdit.text.toString()
            val itemPrice = itemPriceEdit.text.toString()
            val qty = itemQuantity.toInt()
            val pr = itemPrice.toInt()

            if (itemName.isNotEmpty() && itemPrice.isNotEmpty() && itemQuantity.isNotEmpty()) {
                val items = GroceryItems(itemName, qty, pr)
                groceryViewModel.insert(items)
                Toast.makeText(this, "Item Inserted...", Toast.LENGTH_SHORT).show()
                groceryRVAdapter.notifyDataSetChanged()
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Please Enter all the Data...", Toast.LENGTH_SHORT).show()
            }
        }
        dialog.show()
    }

    override fun onItemClick(groceryItems: GroceryItems) {
        groceryViewModel.delete(groceryItems)
        Toast.makeText(this, "Item Deleted....", Toast.LENGTH_SHORT).show()
    }
}
