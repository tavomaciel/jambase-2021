package com.tavomaciel.game.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.utils.ScreenUtils
import com.tavomaciel.game.G
import com.tavomaciel.game.G.batch
import ktx.graphics.use

class LoadingScene : Scene() {
    lateinit var img: Texture

    override suspend fun load() {
        img = G.assetStorage.loadSync("badlogic.jpg")
        changeScene<MenuScene>()
    }

    override fun render(alpha: Float) {
        Gdx.app.log("loadingScene", "rendering!")
        ScreenUtils.clear(1f, 0f, 0f, 1f)
        batch.use {
            batch.draw(img, 0f, 0f, 60f, 60f)
        }
    }
}
