package com.tavomaciel.game.scenes

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.utils.ScreenUtils
import com.tavomaciel.game.G.batch
import ktx.graphics.use

class GameScene : Scene() {
    val img: Texture by loader("badlogic2.jpg")

    override fun render(alpha: Float) {
        ScreenUtils.clear(1f, 0f, 0f, 1f)
        batch.use {
            it.draw(img, 0f, 0f, 200f, 200f)
        }
    }
}
