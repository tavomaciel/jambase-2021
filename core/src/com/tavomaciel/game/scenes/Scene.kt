package com.tavomaciel.game.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.Disposable
import com.tavomaciel.game.G
import com.tavomaciel.game.input.Input
import ktx.assets.async.Identifier
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible


abstract class Scene : Disposable {
    var accm = 0.0f

    /**
     * Called when a scene goes in focus
     */
    @Suppress("UNCHECKED_CAST")
    open suspend fun load() {
        G.assetStorage.progress
        val scene = this
        Gdx.app.log("Scene Loader", "Starting to load")
        (this::class).declaredMemberProperties.forEach { property ->
            property.isAccessible = true
            val delegate = (property as KProperty1<Scene, *>).getDelegate(scene)
            if (delegate is Loader<*>) delegate.loadedValue =
                G.assetStorage.load(Identifier(delegate.name, delegate.type))
        }
    }

    /**
     * Called when a scene goes out of focus.
     * Unload any crazy big assets here
     */
    open suspend fun unload() {}

    open fun process() {
        Input.startFrame()
        accm += G.realDeltaTime
        input()
        while (accm >= G.UPDATE_TIME) {
            logic()
            accm -= G.UPDATE_TIME
        }
        val alpha = accm / G.UPDATE_TIME
        render(alpha)
    }

    open fun input() {}
    open fun logic() {}
    open fun render(alpha: Float) {}
    override fun dispose() {}
}
