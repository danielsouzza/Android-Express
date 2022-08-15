package co.tiagoaguiar.fitnesstracker

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var rvMain : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val mainItems = mutableListOf<MainItem>()
        mainItems.add(
            MainItem(
                id = 1,
                drawableId = R.drawable.ic_base_imc,
                textStringId = R.string.label_imc,
                color = Color.DKGRAY
            )
        )

        mainItems.add(
            MainItem(
                id = 2,
                drawableId = R.drawable.ic_base_tmp,
                textStringId = R.string.label_tmb,
                color = Color.YELLOW
            )
        )

        val adapter = MainAdapter(mainItems){id ->
                when(id){
                    1 -> {
                        val intent = Intent(this@MainActivity,ImcActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        rvMain = findViewById(R.id.rv_main)
        rvMain.adapter = adapter
        rvMain.layoutManager = GridLayoutManager(this,2)

    }


    private inner class MainAdapter(
        private val mainItem: List<MainItem>,
        private val onItemClickListener: (Int) -> Unit
        ) : RecyclerView.Adapter<MainAdapter.MainViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val  view = layoutInflater.inflate(R.layout.main_item, parent, false)
            return MainViewHolder(view)
        }

        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            val itemCurrent = mainItem[position]
            holder.bind(itemCurrent)
        }

        override fun getItemCount(): Int {
            return mainItem.size
        }

        private inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            fun bind(item: MainItem){
                val img: ImageView = itemView.findViewById(R.id.item_img_icon)
                val name: TextView = itemView.findViewById(R.id.item_txt_name)
                val container: LinearLayout = itemView.findViewById(R.id.item_container_imc)

                img.setImageResource(item.drawableId)
                name.setText(item.textStringId)
                container.setBackgroundColor(item.color)

                container.setOnClickListener {
                    onItemClickListener.invoke(item.id)
                }
            }
        }
    }
}