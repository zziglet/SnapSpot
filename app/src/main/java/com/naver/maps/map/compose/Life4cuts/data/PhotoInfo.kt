package com.naver.maps.map.compose.Life4cuts.data

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.naver.maps.map.compose.demo.R

@Composable
public fun LoadPhotosFromLocal(boothName:String) : List<Int>{
    // boothName을 입력하여 drawable 내의 boothName으로 시작하는 img 파일의 resource Id 불러오기

    val context = LocalContext.current
    val drawableClass = R.drawable::class.java
    val fieldList = drawableClass.fields
    val resourceIdList = mutableListOf<Int>()
    val pattern = Regex("^pic.*$boothName.*$")
    for(field in fieldList){
        try {
            val resourceId = field.getInt(null)
            val resourceName = context.resources.getResourceEntryName(resourceId)
            if (pattern.matches(resourceName)){
                resourceIdList.add(resourceId)
            }

        }catch (e:Exception){
            e.printStackTrace()
        }
    }
    return resourceIdList
}
@Composable
fun LoadAllPhotosFromLocal() : List<Int>{
    // drawable 폴더 내에 pic으로 시작하는 사진들 전체 불러오기

    val context = LocalContext.current
    val drawableClass = R.drawable::class.java
    val fieldList = drawableClass.fields
    val resourceIdList = mutableListOf<Int>()

    for(field in fieldList){
        try {
            val resourceId = field.getInt(null)
            val resourceName = context.resources.getResourceEntryName(resourceId)
            if (resourceName.startsWith("pic")){
                resourceIdList.add(resourceId)
            }

        }catch (e:Exception){
            e.printStackTrace()
        }
    }
    return resourceIdList
}