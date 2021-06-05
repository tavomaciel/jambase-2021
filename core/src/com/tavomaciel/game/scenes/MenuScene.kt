package com.tavomaciel.game.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Buttons.LEFT
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.ScreenUtils
import com.tavomaciel.game.G.batch
import com.tavomaciel.game.input.Input
import com.tavomaciel.game.util.forEach
import com.tavomaciel.game.util.glyphs
import ktx.assets.disposeSafely
import ktx.graphics.use

class MenuScene : Scene() {
    val img: Texture by loader("badlogic2.jpg")
    lateinit var font: BitmapFont
    lateinit var proceedGlyphs: GlyphLayout

    override suspend fun load() {
        super.load()
        font = BitmapFont()
        proceedGlyphs = font.glyphs(
            text = "Press Enter to proceed",
            halign = Align.center
        )
    }

    override suspend fun unload() {
        font.disposeSafely()
    }

    override fun input() {
        var shouldChangeScene = false
        Input.touchDowns.forEach {
            if (it.button == LEFT)
                shouldChangeScene = true
        }
        Input.keyDowns.forEach {
            if (it == Keys.ENTER)
                shouldChangeScene = true
        }
        if (shouldChangeScene) changeScene<GameScene>()
    }

    override fun render(alpha: Float) {
        ScreenUtils.clear(1f, 0f, 0f, 1f)
        batch.use {
            font.draw(it, proceedGlyphs, Gdx.graphics.width / 2f, Gdx.graphics.height / 2f)
        }
    }
}
