package com.tavomaciel.game.util

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.utils.Align

fun BitmapFont.glyphs(
    text: String,
    start: Int = 0,
    end: Int = text.length,
    color: Color = this.color,
    targetWidth: Float = 0f,
    halign: Int = Align.left,
    wrap: Boolean = false,
    truncate: String? = null
) = GlyphLayout(this, text, start, end, color, targetWidth, halign, wrap, truncate)
