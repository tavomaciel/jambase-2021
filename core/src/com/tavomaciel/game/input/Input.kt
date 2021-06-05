package com.tavomaciel.game.input

import com.badlogic.gdx.InputProcessor
import com.tavomaciel.game.util.DataClassArray
import ktx.collections.GdxCharArray
import ktx.collections.GdxFloatArray
import ktx.collections.GdxIntArray

/**
 * Simple buffered input processor
 */
object Input : InputProcessor {
    val keyDowns = GdxIntArray()
    val keyUps = GdxIntArray()
    val keyTypeds = GdxCharArray()
    val mouseScrolleds = GdxFloatArray()
    val touchDowns = DataClassArray { TouchEvent(0, 0, 0, 0) }
    val touchUps = DataClassArray { TouchEvent(0, 0, 0, 0) }
    val touchDraggeds = DataClassArray { TouchEvent(0, 0, 0, 0) }
    val mouseMoveds = DataClassArray { TouchEvent(0, 0, 0, 0) }

    private val keyDownsBuffer = GdxIntArray()
    private val keyUpsBuffer = GdxIntArray()
    private val keyTypedsBuffer = GdxCharArray()
    private val mouseScrolledsBuffer = GdxFloatArray()
    private val touchDownsBuffer = DataClassArray { TouchEvent(0, 0, 0, 0) }
    private val touchUpsBuffer = DataClassArray { TouchEvent(0, 0, 0, 0) }
    private val touchDraggedsBuffer = DataClassArray { TouchEvent(0, 0, 0, 0) }
    private val mouseMovedsBuffer = DataClassArray { TouchEvent(0, 0, 0, 0) }


    fun startFrame() {
        keyDowns.clear()
        keyDowns.addAll(keyDownsBuffer)
        keyDownsBuffer.clear()

        keyUps.clear()
        keyUps.addAll(keyUpsBuffer)
        keyUpsBuffer.clear()

        keyTypeds.clear()
        keyTypeds.addAll(keyTypeds)
        keyTypedsBuffer.clear()

        mouseScrolleds.clear()
        mouseScrolleds.addAll(mouseScrolledsBuffer)
        mouseScrolledsBuffer.clear()

        touchDowns.clear()
        touchDowns.addAll(touchDownsBuffer)
        touchDownsBuffer.clear()

        touchUps.clear()
        touchUps.addAll(touchUpsBuffer)
        touchUpsBuffer.clear()

        touchDraggeds.clear()
        touchDraggeds.addAll(touchDraggedsBuffer)
        touchDraggedsBuffer.clear()

        mouseMoveds.clear()
        mouseMoveds.addAll(mouseMovedsBuffer)
        mouseMovedsBuffer.clear()
    }

    override fun keyDown(keycode: Int): Boolean {
        keyDownsBuffer.add(keycode)
        return true
    }

    override fun keyUp(keycode: Int): Boolean {
        keyUpsBuffer.add(keycode)
        return true
    }

    override fun keyTyped(character: Char): Boolean {
        keyTypedsBuffer.add(character)
        return true
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        touchDownsBuffer.add {
            it.x = screenX
            it.y = screenY
            it.pointer = pointer
            it.button = button
        }
        return true
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        touchUpsBuffer.add {
            it.x = screenX
            it.y = screenY
            it.pointer = pointer
            it.button = button
        }
        return true
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        touchDraggedsBuffer.add {
            it.x = screenX
            it.y = screenY
            it.pointer = pointer
            it.button = -1
        }
        return true
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        mouseMovedsBuffer.add {
            it.x = screenX
            it.y = screenY
            it.pointer = -1
            it.button = -1
        }
        return true
    }

    override fun scrolled(amountX: Float, amountY: Float): Boolean {
        mouseScrolledsBuffer.add(amountY)
        return true
    }
}

