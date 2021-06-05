package com.tavomaciel.game

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.tavomaciel.game.scenes.GameScene
import com.tavomaciel.game.scenes.LoadingScene
import com.tavomaciel.game.scenes.MenuScene
import com.tavomaciel.game.scenes.Scene
import ktx.assets.async.AssetStorage

object G {
    var realDeltaTime = 0.0f
    val UPDATE_TIME = 1 / 120.0f
    val scenes = arrayOf(
        LoadingScene(),
        MenuScene(),
        GameScene()
    )
    lateinit var assetStorage: AssetStorage
    var currentSceneIndex = 0

    val currentScene: Scene get() = scenes[currentSceneIndex]

    lateinit var batch: SpriteBatch
}

