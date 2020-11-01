package com.example.childdevelopment
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_view_adapter.view.*


class RecyclerAdapter(
    private val list: List<String>, val context: Context,
    private val cellClickListener: CellClickListener
) :
    RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_view_adapter, parent, false)
        return ViewHolder((view))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    // https://medium.com/@aayushpuranik/recycler-view-using-kotlin-with-click-listener-46e7884eaf59
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.listType.text = list[position]
        holder.itemView.setOnClickListener {
            cellClickListener.onCellClickListener(list[position])
        }

        holder.itemView.setOnLongClickListener {
            cellClickListener.onLongClick(list[position])
            true
        }
    }

    interface CellClickListener {
        fun onCellClickListener(selected: String)
        fun onLongClick(selected: String)
    }
}

// ViewHolder class holds list view
class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val listType = view.menu_text
}
