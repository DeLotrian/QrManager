package com.gamecodeschool.qrmanager.patterns.observers

import com.gamecodeschool.qrmanager.patterns.observers.eventargs.EventArgs

interface IObservable {
    val observers: ArrayList<IObserver>

    fun add(observer: IObserver) {
        observers.add(observer)
    }

    fun remove(observer: IObserver) {
        observers.remove(observer)
    }

    fun sendUpdateEvent(args: EventArgs) {
        observers.forEach { it.update(args) }
    }
}