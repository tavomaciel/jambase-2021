package com.tavomaciel.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.tavomaciel.game.input.Input
import kotlinx.coroutines.runBlocking
import ktx.assets.async.AssetStorage
import ktx.assets.disposeSafely
import ktx.async.KtxAsync

class Game : ApplicationAdapter() {

    override fun create() {
        Gdx.app.log("", "") // First line blank helps readability

        KtxAsync.initiate()

        G.assetStorage = AssetStorage()
        G.batch = SpriteBatch()
        Gdx.input.inputProcessor = Input

        // First load has to be blocking
        runBlocking {
            Gdx.app.log("Game", "Loading first scene")
            G.currentScene.load()
        }
        Gdx.app.log("Game", "create() finished")
    }

    override fun resize(width: Int, height: Int) {
        G.batch.projectionMatrix.setToOrtho2D(
            0f, 0f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat()
        )
    }

    override fun render() {
        G.realDeltaTime = Gdx.graphics.deltaTime
        G.currentScene.process()
    }

    override fun dispose() {
        G.batch.disposeSafely()
        G.assetStorage.dispose()
        G.scenes.disposeSafely()
    }
}
