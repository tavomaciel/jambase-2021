package com.tavomaciel.game.util

import ktx.collections.GdxIntArray

inline fun GdxIntArray.forEach(crossinline action: (Int) -> Unit) {
    for (i in 0 until size) {
        action(items[i])
    }
}
