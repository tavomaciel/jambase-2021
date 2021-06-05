package com.tavomaciel.game.scenes

import com.badlogic.gdx.Gdx
import com.tavomaciel.game.G
import kotlinx.coroutines.launch
import ktx.async.KtxAsync
import ktx.async.skipFrame
import kotlin.reflect.KProperty

inline fun <reified T> loader(name: String) = Loader(name, T::class.java)

class Loader<T>(val name: String, val type: Class<T>) {
    var loadedValue: Any? = null

    @Suppress("UNCHECKED_CAST")
    operator fun getValue(thisRef: Scene, property: KProperty<*>) =
        loadedValue as T ?: throw IllegalStateException("Need to be loaded first.")
}

var changingScene = false

inline fun <reified T : Scene> changeScene() {
    if (changingScene) return
    changingScene = true
    KtxAsync.launch {
        val sceneIndex = G.scenes.indexOfFirst { it is T }
        if (sceneIndex == -1) {
            Gdx.app.error("Scene", "Scene of type ${T::class.simpleName} is not registered.")
            return@launch
        }
        if (sceneIndex == G.currentSceneIndex) {
            Gdx.app.error("Scene", "Trying to load same scene ${T::class.simpleName}.")
            return@launch
        }
        val currentScene = G.currentScene
        val newScene = G.scenes[sceneIndex]
        Gdx.app.log("Scene", "Loading ${T::class.simpleName}")
        newScene.load()
        Gdx.app.log(
            "Scene",
            "Switching scenes from ${currentScene::class.simpleName} to ${T::class.simpleName}"
        )
        G.currentSceneIndex = sceneIndex
        // Wait a frame to unload, just in case
        skipFrame()
        currentScene.unload()
        changingScene = false
    }
}

