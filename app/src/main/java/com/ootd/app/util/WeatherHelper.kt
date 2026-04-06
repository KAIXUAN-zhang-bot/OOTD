package com.ootd.app.util

object WeatherHelper {

    /**
     * 判断温度区间
     */
    fun getTemperatureCategory(temp: Double): String {
        return when {
            temp < 0 -> "极寒 (Very Cold)"
            temp in 0.0..10.0 -> "寒冷 (Cold)"
            temp in 10.1..20.0 -> "凉爽 (Cool)"
            temp in 20.1..30.0 -> "温暖 (Warm)"
            else -> "炎热 (Hot)"
        }
    }

    /**
     * 根据天气图标获取本地资源图标名称 (可选功能示例)
     */
    fun getWeatherIconRes(iconCode: String): String {
        return "ic_weather_$iconCode"
    }
}
