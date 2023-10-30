package com.liu.bloodpressure.ui.act

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.liu.bloodpressure.R
import com.liu.bloodpressure.ui.adapter.MainRVAdapter
import com.liu.bloodpressure.base.BaseActivity
import com.liu.bloodpressure.database.RecordDataBase
import com.liu.bloodpressure.model.BloodEntity
import com.liu.bloodpressure.util.type.ItemType
import com.liu.bloodpressure.ui.view.TitleView
import com.liu.bloodpressure.ui.view.Decoration
import com.liu.bloodpressure.util.type.PageType
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
            finish()
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