package com.liu.bloodpressure.model

import com.liu.bloodpressure.R


val mainLocalList: MutableList<News> = mutableListOf<News>().apply {
    add(News("Record Blood Pressure", "Monitor your health with ease.", R.mipmap.icon_record, ""))
}
val newsLocalList: MutableList<News> = mutableListOf<News>().apply {
    add(NewsEntity.news1)
    add(NewsEntity.news2)
    add(NewsEntity.news3)
    add(NewsEntity.news4)
    add(NewsEntity.news5)
    add(NewsEntity.news6)
    add(NewsEntity.news7)
    add(NewsEntity.news8)
    add(NewsEntity.news9)
    add(NewsEntity.news10)
    add(NewsEntity.news11)
    add(NewsEntity.news12)
}

val settingLocalList: MutableList<Setting> = mutableListOf<Setting>().apply {
    add(Setting("Clock", 1, ""))
    add(Setting("Language", 2, ""))
    add(Setting("Shared", 3, ""))
    add(Setting("Privacy", 4, ""))
    add(Setting("Policy", 5, ""))
    add(Setting("Contact", 6, "abc@gmail.com"))
}

