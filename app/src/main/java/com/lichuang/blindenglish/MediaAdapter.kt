package com.lichuang.blindenglish

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class MediaAdapter(
    private val itemClickedListener: (MediaItem) -> Unit,
    private var items: ArrayList<MediaItem>
) : RecyclerView.Adapter<MediaAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parentView: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parentView.context)
            .inflate(R.layout.mediaitem, parentView, false)
        return ViewHolder(view, itemClickedListener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.title?.text = item.title
        holder.subtitle?.text = item.subtitle
        holder.icon?.setImageResource(item.icon)
        holder.item = item
    }

    class ViewHolder(
        row: View,
        itemClickedListener: (MediaItem) -> Unit
    ) : RecyclerView.ViewHolder(row) {
        var title: TextView? = null
        var subtitle: TextView? = null
        var icon: ImageView? = null

        var item: MediaItem? = null

        init {
            this.title = row.findViewById(R.id.title)
            this.subtitle = row.findViewById(R.id.subtitle)
            this.icon = row.findViewById(R.id.mediaIcon)
            row.setOnClickListener {
                item?.let { itemClickedListener(it) }
            }
        }
    }
}