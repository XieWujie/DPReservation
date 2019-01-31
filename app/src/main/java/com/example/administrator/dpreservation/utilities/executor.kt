package com.example.administrator.dpreservation.utilities

import java.util.concurrent.Executors

private val executor = Executors.newSingleThreadExecutor()

fun runOnNewThread(f:()->Unit){
    executor.submit(f)
}