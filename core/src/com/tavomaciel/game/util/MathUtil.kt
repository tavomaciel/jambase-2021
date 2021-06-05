package com.tavomaciel.game.util

import kotlin.math.absoluteValue
import kotlin.math.sign


inline val Float.squared: Float
    get() = this * this

inline val Float.sqrt: Float
    get() = Math.sqrt(this.toDouble()).toFloat()

inline val Float.sqrtd: Double
    get() = Math.sqrt(this.toDouble())

inline val Double.squared: Double
    get() = this * this

inline val Double.sqrt: Double
    get() = Math.sqrt(this)

inline val Double.sqrtf: Float
    get() = Math.sqrt(this).toFloat()

fun acosf(value: Float) = Math.acos(value.toDouble()).toFloat()

fun Float.limit(limit: Float) = Math.max(this.absoluteValue, limit) * this.sign