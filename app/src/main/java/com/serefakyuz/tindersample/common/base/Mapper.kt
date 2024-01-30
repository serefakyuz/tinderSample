package com.serefakyuz.tindersample.common.base

interface Mapper<F, T> {
    fun mapFrom(from: F): T
}