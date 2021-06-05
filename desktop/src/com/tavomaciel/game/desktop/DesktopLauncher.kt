package com.tavomaciel.game.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.tavomaciel.game.Game

fun main() {
    val config = LwjglApplicationConfiguration()
    LwjglApplication(Game(), config)
}
