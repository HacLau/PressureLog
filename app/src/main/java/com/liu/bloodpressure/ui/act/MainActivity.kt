package com.liu.bloodpressure.ui.act

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.RadioGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.liu.bloodpressure.BuildConfig
import com.liu.bloodpressure.R
import com.liu.bloodpressure.ui.adapter.MainAdapter
import com.liu.bloodpressure.ui.adapter.MainRVAdapter
import com.liu.bloodpressure.base.BaseActivity
import com.liu.bloodpressure.database.RecordDataBase
import com.liu.bloodpressure.model.BloodEntity
import com.liu.bloodpressure.model.RecordTop
import com.liu.bloodpressure.model.mainLocalList
import com.liu.bloodpressure.model.newsLocalList
import com.liu.bloodpressure.model.settingLocalList
import com.liu.bloodpressure.ui.view.Decoration
import com.liu.bloodpressure.ui.view.RecordPopupWindow
import com.liu.bloodpressure.ui.view.TitleView
import com.liu.bloodpressure.util.DateKt
import com.liu.bloodpressure.util.ResourceKt
import com.liu.bloodpressure.util.logE
import com.liu.bloodpressure.util.type.ItemType
import com.liu.bloodpressure.util.type.PageType
import com.liu.bloodpressure.util.type.RecordType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.Calendar

class MainActivity : BaseActivity() {

    private lateinit var mRadioGroup: RadioGroup
    private lateinit var mViewPager: ViewPager
    private lateinit var mTitleMain: TitleView
    private lateinit var homeRecyclerView: RecyclerView
    private lateinit var recordRecyclerView: RecyclerView
    private lateinit var newsRecyclerView: RecyclerView
    private lateinit var settingRecyclerView: RecyclerView
    private var recordType = RecordType.ALL
    override fun contentLayout(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        mViewPager = findViewById(R.id.vp_main)
        mRadioGroup = findViewById(R.id.rg_main)
        mTitleMain = findViewById(R.id.title_main)
    }

    override fun initData() {
        mTitleMain.rightButtonText.text = recordType
        mTitleMain.rightButton.setOnClickListener {
            RecordPopupWindow(context = this, onClick = {
                if (recordType == it) {
                    return@RecordPopupWindow
                }
                mTitleMain.rightButtonText.text = it
                recordType = it
                setRecordList()
            }).showAsDropDown(mTitleMain.rightButton)
        }
        mRadioGroup.setOnCheckedChangeListener { _, checkId ->
            when (checkId) {
                R.id.rb_main_home -> {
                    mViewPager.currentItem = 0
                }

                R.id.rb_main_record -> {
                    mViewPager.currentItem = 1
                }

                R.id.rb_main_news -> {
                    mViewPager.currentItem = 2
                }

                R.id.rb_main_setting -> {
                    mViewPager.currentItem = 3
                }
            }
        }

        mViewPager.adapter = MainAdapter(mutableListOf<View>().apply {
            add(mainHome())
            add(mainRecord())
            add(mainNews())
            add(mainSetting())
        })
        mViewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        mRadioGroup.check(R.id.rb_main_home)
                        mTitleMain.leftText.text = "${DateKt.getDay()} ${DateKt.getMonth(DateKt.getMonth() + 1)}"
                        mTitleMain.rightButton.visibility = View.GONE
                        setHomeList()
                    }

                    1 -> {
                        mRadioGroup.check(R.id.rb_main_record)
                        mTitleMain.leftText.text = getString(R.string.main_record)
                        mTitleMain.rightButton.visibility = View.VISIBLE
                        setRecordList()
                    }

                    2 -> {
                        mRadioGroup.check(R.id.rb_main_news)
                        mTitleMain.leftText.text = getString(R.string.main_news)
                        mTitleMain.rightButton.visibility = View.GONE
                        setNewsList()
                    }

                    3 -> {
                        mRadioGroup.check(R.id.rb_main_setting)
                        mTitleMain.leftText.text = getString(R.string.main_setting)
                        mTitleMain.rightButton.visibility = View.GONE
                        setSettingList()
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })
        mTitleMain.leftText.text = "${DateKt.getDay()} ${DateKt.getMonth(DateKt.getMonth() + 1)}"
//        setListData()
    }

    private fun mainHome(): View {
        val view: View = LayoutInflater.from(this).inflate(R.layout.fragment_home, null, false)
        homeRecyclerView = view.findViewById<RecyclerView>(R.id.home_rv)
        homeRecyclerView.addItemDecoration(Decoration(6))
        homeRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        homeRecyclerView.adapter = MainRVAdapter(context = this, data = mutableListOf()).apply {
            setInvoke(this)
        }
        return view
    }

    private fun mainRecord(): View {
        val view: View = LayoutInflater.from(this).inflate(R.layout.fragment_record, null, false)
        recordRecyclerView = view.findViewById<RecyclerView>(R.id.record_rv)
        recordRecyclerView.addItemDecoration(Decoration(6))
        recordRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recordRecyclerView.adapter = MainRVAdapter(context = this, data = mutableListOf()).apply {
            setInvoke(this)
        }
        return view
    }

    private fun mainNews(): View {
        val view: View = LayoutInflater.from(this).inflate(R.layout.fragment_news, null, false)
        newsRecyclerView = view.findViewById<RecyclerView>(R.id.news_rv)
        newsRecyclerView.addItemDecoration(Decoration(6))
        newsRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        newsRecyclerView.adapter = MainRVAdapter(context = this, data = mutableListOf()).apply {
            setInvoke(this)
        }
        return view
    }

    private fun mainSetting(): View {
        val view: View = LayoutInflater.from(this).inflate(R.layout.fragment_setting, null, false)
        settingRecyclerView = view.findViewById<RecyclerView>(R.id.setting_rv)
        settingRecyclerView.addItemDecoration(Decoration(1))
        settingRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        settingRecyclerView.adapter = MainRVAdapter(context = this, data = mutableListOf()).apply {
            setInvoke(this)
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        setListData()
    }

    private fun setListData() {
        setHomeList()
        setRecordList()
        setNewsList()
        setSettingList()
    }


    private fun setHomeList() {

        (homeRecyclerView.adapter as MainRVAdapter).setData(mutableListOf<BloodEntity>().apply {
            mainLocalList.forEach {
                add(BloodEntity(type = ItemType.MAINTOP, mainTop = it))
            }
            newsLocalList.let {
                it.subList(
                    0, if (it.size > 5) {
                        5
                    } else {
                        it.size
                    }
                )
            }.forEach {
                it.iconUrl = ResourceKt.newsIcon.random()
                add(BloodEntity(type = ItemType.NEWS, news = it))
            }
        })
    }

    private fun setRecordList() {

        CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
            kotlin.runCatching {
                var sys = 0
                var dia = 0
                RecordDataBase.getDatabase(this@MainActivity).recordDao().queryAll().filter {
                    when (recordType) {
                        RecordType.RECENT -> {
                            it.showTime > DateKt.getToday()
                        }

                        RecordType.WEEK -> {
                            it.showTime >= DateKt.getDayOfWeek(Calendar.SUNDAY, 0)
                        }

                        RecordType.MONTH -> {
                            it.showTime > DateKt.getThisMonth()
                        }

                        RecordType.LASTMONTH -> {
                            it.showTime < DateKt.getThisMonth() && it.showTime > DateKt.getLastMonth()
                        }

                        else -> {
                            it.showTime < System.currentTimeMillis()
                        }
                    }
                }.let {
                    val recordList = mutableListOf<BloodEntity>()
                    val list = it.subList(
                        0, if (it.size > 5) {
                            5
                        } else {
                            it.size
                        }
                    )

                    list.forEach { record ->
                        sys += record.systolic
                        dia += record.diastolic
                        recordList.add(BloodEntity(type = ItemType.RECORD, record = record))
                    }

                    recordList.add(
                        0,
                        BloodEntity(
                            type = ItemType.RECORDTOP,
                            recordTop = RecordTop(
                                systolic = if (list.isEmpty()) 0 else sys.div(list.size),
                                diastolic = if (list.isEmpty()) 0 else dia.div(list.size)
                            )
                        )
                    )
                    CoroutineScope(Dispatchers.Main + SupervisorJob()).launch {
                        (recordRecyclerView.adapter as MainRVAdapter).setData(recordList)
                    }
                }
            }
        }
    }

    private fun setNewsList() {
        (newsRecyclerView.adapter as MainRVAdapter).setData(mutableListOf<BloodEntity>().apply {
            newsLocalList.forEach {
                it.iconUrl = ResourceKt.newsIcon.random()
                add(BloodEntity(type = ItemType.NEWS, news = it))
            }
        })
    }

    private fun setSettingList() {
        (settingRecyclerView.adapter as MainRVAdapter).setData(mutableListOf<BloodEntity>().apply {
            settingLocalList.let {
                for (i in 0..<it.size) {
                    if (i == 0 || i == 1 || i == it.size - 1) {
                        continue
                    }
                    if (i == 0) {
                        add(BloodEntity(type = ItemType.SETTINGTOP, settingTop = it[0]))
                    } else {
                        add(BloodEntity(type = ItemType.SETTING, setting = it[i]))
                    }
                }
            }

        })
    }


    private fun setInvoke(adapter: MainRVAdapter) {
        adapter.recordMore = {
            startRecordMoreActivity()
        }
        adapter.recordNew = {
            startRecordNewActivity()
        }
        adapter.itemClick = {
            when (it.type) {
                ItemType.MAINTOP,
                ItemType.RECORDTOP,
                ItemType.SETTINGTOP -> {
                }

                ItemType.RECORD -> {
                    startRecordNewActivity(PageType.edit, it.record)
                }

                ItemType.NEWS -> {
                    startWebContentActivity(PageType.news, it.news)
                }

                ItemType.SETTING -> {
                    when (it.setting?.type) {
                        1 -> settingClock()
                        2 -> settingLanguage()
                        3 -> settingShared()
                        4 -> settingPrivacy(it.setting?.title)
                        5 -> settingPolicy(it.setting?.title)
                        6 -> {}
                        else -> {}
                    }
                }

                else -> {}
            }

        }
    }

    private fun settingClock() {

    }

    private fun settingLanguage() {

    }

    private fun settingShared() {
        kotlin.runCatching {
            startActivity(Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}")
            })
        }
    }

    private fun settingPrivacy(title: String?) {
        startWebContentActivity(
            PageType.web,
            url = "https://sites.google.com/view/pressurelog-privacy-policy/home",
            title = title
        )
    }

    private fun settingPolicy(title: String?) {
        startWebContentActivity(
            PageType.web,
            url = "https://sites.google.com/view/pressurelog-terms/home",
            title = title
        )
    }


}