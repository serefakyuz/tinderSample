package com.serefakyuz.tindersample.model.charachter

import com.serefakyuz.tindersample.R

enum class Status(val value: String) {
    ONLINE("alive"),
    OFFLINE("dead"),
    UNKNOWN("unknown");


    companion object{
        private val statusDrawableMap: Map<Status, Int> = hashMapOf(
            ONLINE to R.drawable.status_online,
            OFFLINE to R.drawable.status_offline,
            UNKNOWN to R.drawable.status_unknown
        )
        fun getStatusDrawable(status: Status): Int = statusDrawableMap[status]?: R.drawable.status_unknown

        fun safeValueOf(value: String): Status{
            return when(value.lowercase()){
                ONLINE.value.lowercase() -> ONLINE
                OFFLINE.value.lowercase() -> OFFLINE
                else -> UNKNOWN

            }
        }
    }
}