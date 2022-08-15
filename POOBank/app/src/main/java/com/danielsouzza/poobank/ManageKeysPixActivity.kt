package com.danielsouzza.poobank

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.danielsouzza.poobank.controler.BankController

class ManageKeysPixActivity : AppCompatActivity() {

    private val controller = BankController()
    private lateinit var rvKeyItem: RecyclerView
    private lateinit var btnNewKey: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_keys_pix)

        // Get user current
        val currentUser = controller.getUserCurrent()
        // Get all keys related to user
        val keys = controller.getAllKeys(currentUser.getNumber())

        // Item and adapter of recycle view
        val itemKeys = mutableListOf<KeysItem>()
        val adapter = KeysAdapter(itemKeys) {}

        // Button of register new key
        btnNewKey = findViewById(R.id.btn_new_key)

        for ((key, value) in keys) {
            itemKeys.add(
                KeysItem(
                    id = 1,
                    textTypeKeyId = key,
                    textKeyId = value
                )
            )
        }

        // Recycle view
        rvKeyItem = findViewById(R.id.rv_keys_item)
        rvKeyItem.adapter = adapter
        rvKeyItem.layoutManager = LinearLayoutManager(this)

        btnNewKey.setOnClickListener {
            val registerKey = Intent(this, RegisterKeyPixActivity::class.java)
            startActivity(registerKey)
        }
    }

    private inner class KeysAdapter(
        private val keysItem: List<KeysItem>,
        private val onItemClickListener: (Int) -> Unit
    ) : RecyclerView.Adapter<KeysAdapter.KeysViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeysViewHolder {
            val view = layoutInflater.inflate(R.layout.key_pix_item, parent, false)
            return KeysViewHolder(view)
        }

        override fun onBindViewHolder(holder: KeysViewHolder, position: Int) {
            holder.bind(keysItem[position])
        }

        override fun getItemCount(): Int = keysItem.size

        private inner class KeysViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(item: KeysItem) {
                val textTypeKey: TextView = itemView.findViewById(R.id.text_typeKey_item)
                val textKey: TextView = itemView.findViewById(R.id.text_key_item)
                textTypeKey.text = item.textTypeKeyId
                textKey.text = item.textKeyId

                itemView.setOnClickListener {
                    onItemClickListener.invoke(item.id)
                }
            }
        }
    }
}