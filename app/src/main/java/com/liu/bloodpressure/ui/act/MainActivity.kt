package com.liu.bloodpressure.ui.act

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.RadioGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.liu.bloodpressure.BuildConfig
import com.liu.bloodpressure.R
import com.liu.bloodpressure.adapter.MainAdapter
import com.liu.bloodpressure.adapter.MainRVAdapter
import com.liu.bloodpressure.base.BaseActivity
import com.liu.bloodpressure.database.RecordDataBase
import com.liu.bloodpressure.model.BloodEntity
import com.liu.bloodpressure.model.HistoryTop
import com.liu.bloodpressure.model.News
import com.liu.bloodpressure.model.Record
import com.liu.bloodpressure.model.ItemType
import com.liu.bloodpressure.model.Setting
import com.liu.bloodpressure.ui.view.TitleView
import com.liu.bloodpressure.util.AssetsKt
import com.liu.bloodpressure.util.Decoration
import com.liu.bloodpressure.util.PageType
import com.liu.bloodpressure.util.ResourceKt
import com.liu.bloodpressure.util.logE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MainActivity : BaseActivity() {

    private lateinit var mRadioGroup: RadioGroup
    private lateinit var mViewPager: ViewPager
    private lateinit var mTitleMain: TitleView
    private var homeList: MutableList<BloodEntity> = mutableListOf()
    private var historyList: MutableList<BloodEntity> = mutableListOf()
    private var newsList: MutableList<BloodEntity> = mutableListOf()
    private var settingList: MutableList<BloodEntity> = mutableListOf()
    private lateinit var homeRecyclerView: RecyclerView
    private lateinit var historyRecyclerView: RecyclerView
    private lateinit var newsRecyclerView: RecyclerView
    private lateinit var settingRecyclerView: RecyclerView
    override fun contentLayout(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        mViewPager = findViewById(R.id.vp_main)
        mRadioGroup = findViewById(R.id.rg_main)
        mTitleMain = findViewById(R.id.title_main)
    }

    override fun initData() {
        mRadioGroup.setOnCheckedChangeListener { _, checkId ->
            when (checkId) {
                R.id.rb_main_home -> {
                    mViewPager.currentItem = 0
                }

                R.id.rb_main_history -> {
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
            add(mainHistory())
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
                        mTitleMain.leftText.text = getString(R.string.main_time)
                        mTitleMain.rightButton.visibility = View.GONE
                    }

                    1 -> {
                        mRadioGroup.check(R.id.rb_main_history)
                        mTitleMain.leftText.text = getString(R.string.main_history)
                        mTitleMain.rightButton.visibility = View.GONE
                    }

                    2 -> {
                        mRadioGroup.check(R.id.rb_main_news)
                        mTitleMain.leftText.text = getString(R.string.main_news)
                        mTitleMain.rightButton.visibility = View.GONE
                    }

                    3 -> {
                        mRadioGroup.check(R.id.rb_main_setting)
                        mTitleMain.leftText.text = getString(R.string.main_setting)
                        mTitleMain.rightButton.visibility = View.GONE
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })
    }

    private fun mainHome(): View {
        val view: View = LayoutInflater.from(this).inflate(R.layout.fragment_home, null, false)
        homeRecyclerView = view.findViewById<RecyclerView>(R.id.home_rv)
        homeRecyclerView.addItemDecoration(Decoration(6))
        homeRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        homeRecyclerView.adapter = MainRVAdapter(context = this, data = homeList).apply {
            setInvoke(this)
        }
        return view
    }

    private fun mainHistory(): View {
        val view: View = LayoutInflater.from(this).inflate(R.layout.fragment_history, null, false)
        historyRecyclerView = view.findViewById<RecyclerView>(R.id.history_rv)
        historyRecyclerView.addItemDecoration(Decoration(6))
        historyRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        historyRecyclerView.adapter = MainRVAdapter(context = this, data = historyList).apply {
            setInvoke(this)
        }
        return view
    }

    private fun mainNews(): View {
        val view: View = LayoutInflater.from(this).inflate(R.layout.fragment_news, null, false)
        newsRecyclerView = view.findViewById<RecyclerView>(R.id.news_rv)
        newsRecyclerView.addItemDecoration(Decoration(6))
        newsRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        newsRecyclerView.adapter = MainRVAdapter(context = this, data = newsList).apply {
            setInvoke(this)
        }
        return view
    }

    private fun mainSetting(): View {
        val view: View = LayoutInflater.from(this).inflate(R.layout.fragment_setting, null, false)
        settingRecyclerView = view.findViewById<RecyclerView>(R.id.setting_rv)
        settingRecyclerView.addItemDecoration(Decoration(1))
        settingRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        settingRecyclerView.adapter = MainRVAdapter(context = this, data = settingList).apply {
            setInvoke(this)
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        setListData()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setListData() {
        setHomeList()
        setHistoryList()
        setNewsList()
        setSettingList()

        (homeRecyclerView.adapter as MainRVAdapter).setData(homeList)
        (historyRecyclerView.adapter as MainRVAdapter).setData(historyList)
        (newsRecyclerView.adapter as MainRVAdapter).setData(newsList)
        (settingRecyclerView.adapter as MainRVAdapter).setData(settingList)
    }


    private fun setHomeList() {
        homeList = mutableListOf<BloodEntity>().apply {
            (Gson().fromJson(AssetsKt.getJson(this@MainActivity, "${country}/main.json"), object : TypeToken<News>() {}.type) as News).let {
                it.iconUrl = R.mipmap.icon_record
                add(BloodEntity(type = ItemType.MAINTOP, mainTop = it))
            }
            (Gson().fromJson(
                AssetsKt.getJson(this@MainActivity, "${country}/news.json"),
                object : TypeToken<MutableList<News>>() {}.type
            ) as MutableList<News>).let {
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
        }
    }

    private fun setHistoryList() {
        historyList = mutableListOf<BloodEntity>().apply {
            CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
                kotlin.runCatching {
                    val list = RecordDataBase.getDatabase(this@MainActivity).recordDao().queryAll()
                    var sys = 0
                    var dia = 0
                    list.forEach {
                        sys += it.systolic
                        dia += it.diastolic
                    }
                    add(
                        BloodEntity(
                            type = ItemType.HISTORYTOP,
                            historyTop = HistoryTop(systolic = sys.div(list.size), diastolic = dia.div(list.size))
                        )
                    )
                    list.let {
                        it.subList(
                            0, if (it.size > 3) {
                                3
                            } else {
                                it.size
                            }
                        )
                    }.forEach {
                        add(BloodEntity(type = ItemType.RECORD, record = it))
                    }
                }
            }
        }
    }

    private fun setNewsList() {
        newsList = mutableListOf<BloodEntity>().apply {
            (Gson().fromJson(
                AssetsKt.getJson(this@MainActivity, "${country}/news.json"),
                object : TypeToken<MutableList<News>>() {}.type
            ) as MutableList<News>).forEach {
                it.iconUrl = ResourceKt.newsIcon.random()
                add(BloodEntity(type = ItemType.NEWS, news = it))
            }
        }
    }

    private fun setSettingList() {
        settingList = mutableListOf<BloodEntity>().apply {
            (Gson().fromJson(
                AssetsKt.getJson(this@MainActivity, "${country}/setting.json"),
                object : TypeToken<MutableList<Setting>>() {}.type
            ) as MutableList<Setting>).let {
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

        }
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
                ItemType.HISTORYTOP,
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
        startWebContentActivity(PageType.web, url = "https://blog.csdn.net/lyglostangel/article/details/120860852", title = title)
    }

    private fun settingPolicy(title: String?) {
        startWebContentActivity(
            PageType.web,
            url = "https://www.jianshu.com/p/282c7c4537b3?utm_campaign=maleskine&utm_content=note&utm_medium=seo_notes&utm_source=recommendation",
            title = title
        )
    }


}