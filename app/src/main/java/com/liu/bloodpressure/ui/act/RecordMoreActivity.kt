package com.liu.bloodpressure.ui.act

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.liu.bloodpressure.R
import com.liu.bloodpressure.adapter.MainRVAdapter
import com.liu.bloodpressure.base.BaseActivity
import com.liu.bloodpressure.database.RecordDataBase
import com.liu.bloodpressure.model.BloodEntity
import com.liu.bloodpressure.model.HistoryTop
import com.liu.bloodpressure.model.Record
import com.liu.bloodpressure.model.ItemType
import com.liu.bloodpressure.ui.view.TitleView
import com.liu.bloodpressure.util.AssetsKt
import com.liu.bloodpressure.util.Decoration
import com.liu.bloodpressure.util.PageType
import com.liu.bloodpressure.util.ResourceKt
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class RecordMoreActivity : BaseActivity() {
    private lateinit var mRecyclerView:RecyclerView
    private lateinit var mLinearLayout:LinearLayout
    private lateinit var mTitle:TitleView
    private lateinit var mToRecord:TextView
    private var mDataList: MutableList<BloodEntity> = mutableListOf()


    override fun contentLayout(): Int {
        return R.layout.activity_record_more
    }

    override fun initView() {
        mRecyclerView = findViewById(R.id.record_more_rv)
        mLinearLayout = findViewById(R.id.record_more_ll)
        mTitle = findViewById(R.id.record_more_title)
        mToRecord = findViewById(R.id.record_more_to_record)
    }

    override fun initData() {
        mTitle.leftImage.setOnClickListener {
            finish()
        }
        mToRecord.setOnClickListener {
            startRecordNewActivity()
        }
        mRecyclerView.addItemDecoration(Decoration(6))
        mRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mRecyclerView.adapter = MainRVAdapter(context = this, data = mDataList).apply {
            this.recordMore = {
                startRecordMoreActivity()
            }

            this.recordNew = {
                startRecordNewActivity()
            }

            this.itemClick = {
                startRecordNewActivity(PageType.edit,it.record)
            }

        }
    }

    override fun onResume() {
        super.onResume()
        mDataList= mutableListOf<BloodEntity>().apply {
//            (Gson().fromJson(
//                AssetsKt.getJson(this@RecordMoreActivity, "${country}/record.json"),
//                object : TypeToken<MutableList<Record>>() {}.type
//            ) as MutableList<Record>)
            CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
                kotlin.runCatching {
                    RecordDataBase.getDatabase(this@RecordMoreActivity).recordDao().queryAll().apply {
                        if (this.isEmpty()){
                            mLinearLayout.visibility = View.VISIBLE
                            mRecyclerView.visibility = View.GONE
                        }else{
                            mRecyclerView.visibility = View.VISIBLE
                            mLinearLayout.visibility = View.GONE
                        }
                    }.forEach {
                        add(BloodEntity(type = ItemType.RECORD, record = it))
                    }
                }
            }

        }
        (mRecyclerView.adapter as MainRVAdapter).setData(mDataList)
    }
}