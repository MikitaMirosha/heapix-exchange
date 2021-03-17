package com.heapix.exchange.utils.rx

import io.reactivex.Scheduler

interface SchedulerProvider {
    fun io(): Scheduler

    fun ui(): Scheduler

    fun computation(): Scheduler
}