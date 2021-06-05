package com.tavomaciel.game.input

import com.tavomaciel.game.util.Copyable

data class TouchEvent(
    var x: Int, var y: Int,
    var pointer: Int, var button: Int
) : Copyable<TouchEvent> {
    override fun copy(other: TouchEvent) {
        this.x = other.x
        this.y = other.y
        this.pointer = other.pointer
        this.button = other.button
    }
}