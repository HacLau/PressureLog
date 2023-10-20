package com.liu.bloodpressure.ui.act

import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import com.liu.bloodpressure.R
import com.liu.bloodpressure.base.BaseActivity
import com.liu.bloodpressure.model.News
import com.liu.bloodpressure.ui.view.TitleView
import com.liu.bloodpressure.util.type.IntentName
import com.liu.bloodpressure.util.type.PageType

class WebViewActivity : BaseActivity() {
    private lateinit var mTitleView: TitleView

    private lateinit var mScrollView: ScrollView
    private lateinit var mWebView: WebView

    private lateinit var mImage: ImageView
    private lateinit var mContent: TextView
    private lateinit var mFrom: TextView
    override fun contentLayout(): Int {
        return R.layout.activity_web_view
    }

    override fun initView() {
        mTitleView = findViewById(R.id.web_view_title)

        mScrollView = findViewById(R.id.sv_content)
        mWebView = findViewById(R.id.wb_content)

        mImage = findViewById(R.id.web_view_image)
        mContent = findViewById(R.id.web_view_content)
        mFrom = findViewById(R.id.web_view_from)
    }

    override fun initData() {
        mTitleView.leftImage.setOnClickListener {
            finish()
        }
        when (intent.getStringExtra(IntentName.pagType)) {
            PageType.web -> {
                setWebContent()
            }

            PageType.news -> {
                setNewsContent()
            }
        }

    }

    private fun setWebContent() {
        mWebView.visibility = View.VISIBLE
        intent.getStringExtra(IntentName.url)?.let { mWebView.loadUrl(it) }
        intent.getStringExtra(IntentName.title)?.let { mTitleView.title.text = it }
    }

    private fun setNewsContent() {
        mScrollView.visibility = View.VISIBLE
        val news = intent.getParcelableExtra<News>(IntentName.content)
        mContent.text = news?.content
        mFrom.text = news?.from
        mTitleView.title.text = news?.title
    }
}