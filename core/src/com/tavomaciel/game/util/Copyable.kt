package com.tavomaciel.game.util

interface Copyable<T> {
    fun copy(other: T)
}