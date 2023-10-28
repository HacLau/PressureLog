package com.liu.bloodpressure.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.liu.bloodpressure.R
import com.liu.bloodpressure.model.BloodEntity
import com.liu.bloodpressure.util.DateKt
import com.liu.bloodpressure.util.type.ItemType

class MainRVAdapter(
    private val context: Context,
    private var data: MutableList<BloodEntity>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var recordNew: () -> Unit
    lateinit var recordMore: () -> Unit
    lateinit var itemClick: (BloodEntity) -> Unit
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ItemType.MAINTOP -> {
                TitleViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_main_top, null, false))
            }

            ItemType.NEWS -> {
                NewsViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_news_item, null, false))
            }

            ItemType.RECORD -> {
                RecordViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_record_item, null, false))
            }

            ItemType.RECORDTOP -> {
                RecordTopViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_record_top, null, false))
            }

            ItemType.SETTINGTOP -> {
                SettingTopViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_setting_top, null, false))
            }

            ItemType.SETTING -> {
                SettingViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_setting_item, null, false))
            }

            else -> {
                TitleViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_news_item, null, false))
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ItemType.MAINTOP -> {
                if (holder is TitleViewHolder) {
                    data[position].mainTop?.let {
                        holder.title.text = it.title
                        holder.content.text = it.content
                        holder.addRecord.setOnClickListener {
                            recordNew.invoke()
                        }
                        holder.icon.setBackgroundResource(it.iconUrl)
                    }
                }
            }

            ItemType.NEWS -> {
                if (holder is NewsViewHolder) {
                    data[position].news?.let {
                        holder.title.text = it.title
                        holder.content.text = it.content
                        holder.icon.setBackgroundResource(it.iconUrl)
                        holder.newsItem.setOnClickListener {
                            itemClick.invoke(data[position])
                        }
                    }
                }
            }

            ItemType.RECORD -> {
                if (holder is RecordViewHolder) {
                    data[position].record?.let {
                        holder.systolic.text = it.systolic.toString()
                        holder.diastolic.text = it.diastolic.toString()
                        holder.degree.text = when (it.degree) {
                            0 -> {
                                context.getString(R.string.degree_title_hypotension)
                            }

                            1 -> {
                                context.getString(R.string.degree_title_normal)
                            }

                            2 -> {
                                context.getString(R.string.degree_title_elevated)
                            }

                            3 -> {
                                context.getString(R.string.degree_title_hs1)
                            }

                            4 -> {
                                context.getString(R.string.degree_title_hs2)
                            }

                            5 -> {
                                context.getString(R.string.degree_title_crisis)
                            }

                            else -> {
                                ""
                            }
                        }
                        holder.time.text = DateKt.getDate(it.showTime)
                        holder.icon.background = when (it.degree) {

                            0 -> {
                                ContextCompat.getDrawable(context, R.mipmap.ic_degree_1)
                            }

                            1 -> {
                                ContextCompat.getDrawable(context, R.mipmap.ic_degree_0)
                            }

                            2 -> {
                                ContextCompat.getDrawable(context, R.mipmap.ic_degree_2)
                            }

                            3 -> {
                                ContextCompat.getDrawable(context, R.mipmap.ic_degree_3)
                            }

                            4 -> {
                                ContextCompat.getDrawable(context, R.mipmap.ic_degree_4)
                            }

                            5 -> {
                                ContextCompat.getDrawable(context, R.mipmap.ic_degree_5)
                            }

                            else -> {
                                ContextCompat.getDrawable(context, R.mipmap.ic_degree_0)
                            }
                        }
                        holder.recordicon.setOnClickListener {
                            itemClick.invoke(data[position])
                        }
                    }
                }
            }

            ItemType.RECORDTOP -> {
                if (holder is RecordTopViewHolder) {
                    data[position].recordTop?.let {
                        holder.recordNew.setOnClickListener {
                            recordNew.invoke()
                        }
                        holder.recordMore.setOnClickListener {
                            recordMore.invoke()
                        }
                        if (data.size > 0 && position == 0) {
                            holder.clMore.visibility = View.VISIBLE
                        } else {
                            holder.clMore.visibility = View.GONE
                        }
                        holder.recordItemMore.setOnClickListener {
                            recordMore.invoke()
                        }
                        holder.systolic.text = it.systolic.toString()
                        holder.diastolic.text = it.diastolic.toString()
                    }
                }
            }

            ItemType.SETTINGTOP -> {
                if (holder is SettingTopViewHolder) {
                    data[position].settingTop?.let {
                        holder.title.text = it.title
                        holder.settingTop.setOnClickListener {
                            itemClick.invoke(data[position])
                        }
                    }
                }
            }

            ItemType.SETTING -> {
                if (holder is SettingViewHolder) {
                    data[position].setting?.let {
                        holder.title.text = it.title
                        if (it.content.isNullOrBlank()) {
                            holder.next.visibility = View.VISIBLE
                        } else {
                            holder.next.visibility = View.GONE
                            holder.content.text = it.content
                        }
                        holder.settingItem.setOnClickListener {
                            itemClick.invoke(data[position])
                        }
                    }
                }
            }

            else -> {
                if (holder is NewsViewHolder) {
                    data[position].news?.let {
                        holder.title.text = it.title
                        holder.content.text = it.content
                        holder.icon.setBackgroundResource(it.iconUrl)
                    }
                }
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return data[position].type
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: MutableList<BloodEntity>){
        this.data = list
        notifyDataSetChanged()
    }

    inner class TitleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.top_title)
        var content: TextView = view.findViewById(R.id.top_content)
        var addRecord: TextView = view.findViewById(R.id.top_button)
        var icon: ImageView = view.findViewById(R.id.top_icon)
    }

    inner class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.item_title)
        var content: TextView = view.findViewById(R.id.item_content)
        var icon: ImageView = view.findViewById(R.id.item_icon)
        var newsItem: ConstraintLayout = view.findViewById(R.id.news_item)
    }

    inner class RecordViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var systolic: TextView = view.findViewById(R.id.item_systolic)
        var diastolic: TextView = view.findViewById(R.id.item_diastolic)
        var degree: TextView = view.findViewById(R.id.item_degree)
        var time: TextView = view.findViewById(R.id.item_time)
        var icon: ImageView = view.findViewById(R.id.icon_degree)
        var recordicon: ImageView = view.findViewById(R.id.item_record_icon)
        var recordItem: ConstraintLayout = view.findViewById(R.id.record_item)
    }

    inner class RecordTopViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var recordNew: TextView = view.findViewById(R.id.record_new)
        var recordMore: TextView = view.findViewById(R.id.record_more)
        var systolic: TextView = view.findViewById(R.id.record_systolic)
        var diastolic: TextView = view.findViewById(R.id.record_diastolic)
        var recordItemMore: TextView = view.findViewById(R.id.record_item_more)
        var clMore: ConstraintLayout = view.findViewById(R.id.cl_more)
    }

    inner class SettingTopViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.setting_top_title)
        var settingTop: ConstraintLayout = view.findViewById(R.id.setting_top)
    }

    inner class SettingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.setting_item_title)
        var content: TextView = view.findViewById(R.id.setting_item_content)
        var next: ImageView = view.findViewById(R.id.setting_item_next)
        var settingItem: ConstraintLayout = view.findViewById(R.id.setting_item)
    }

}