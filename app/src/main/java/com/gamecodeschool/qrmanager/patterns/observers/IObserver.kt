package com.gamecodeschool.qrmanager.patterns.observers

import com.gamecodeschool.qrmanager.patterns.observers.eventargs.EventArgs

interface IObserver {
    fun update(arg: EventArgs)
}