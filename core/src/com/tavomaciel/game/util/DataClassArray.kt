package com.tavomaciel.game.util

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.StringBuilder


// In place elastic array that avoid object instantiation the maximum it can.
class DataClassArray<T : Copyable<T>>(capacity: Int = 16, val initializer: (Int) -> T) {
    var items: Array<T>
    var size = 0

    init {
        items = Array<Copyable<T>>(capacity, initializer) as Array<T>
    }

    inline fun add(crossinline mutator: (T) -> Unit) {
        var items = items
        if (size == items.size) items = resize(Math.max(8, (size * 1.75f).toInt()))
        mutator(items[size++])
    }

    fun add(other: T) {
        var items = items
        if (size == items.size) items = resize(Math.max(8, (size * 1.75f).toInt()))
        items[size++].copy(other)
    }

    fun addAll(others: DataClassArray<T>) {
        val sizeNeeded: Int = size + others.size
        if (sizeNeeded > items.size) items = resize(
            Math.max(
                Math.max(8, sizeNeeded),
                (size * 1.75f).toInt()
            )
        )
        val previousSize = size
        size += others.size
        for (i in 0 until others.size) {
            items[previousSize + i].copy(others[i])
        }
    }

    operator fun get(index: Int): T {
        if (index >= size) throw IndexOutOfBoundsException("index can't be >= size: $index >= $size")
        return items[index]
    }

    inline operator fun set(index: Int, crossinline mutator: (T) -> Unit) {
        if (index >= size) throw IndexOutOfBoundsException("index can't be >= size: $index >= $size")
        mutator(items[index])
    }

    inline fun forEach(crossinline action: (T) -> Unit) {
        for (i in 0 until size) action(items[i])
    }

    operator fun contains(value: T): Boolean {
        var i = size - 1
        val items = items
        while (i >= 0) if (items[i--] == value) return true
        return false
    }

    /** Removes and returns the last item.  */
    fun pop(): T {
        return items[--size]
    }

    /** Returns the last item.  */
    fun peek(): T {
        return items[size - 1]
    }

    /** Returns the first item.  */
    fun first(): T {
        check(size != 0) { "Array is empty." }
        return items[0]
    }

    /** Returns true if the array has one or more items.  */
    fun notEmpty(): Boolean {
        return size > 0
    }

    /** Returns true if the array is empty.  */
    val isEmpty: Boolean
        get() = size == 0

    fun clear() {
        size = 0
    }

    /** Reduces the size of the backing array to the size of the actual items. This is useful to release memory when many items
     * have been removed, or if it is known that more items will not be added.
     * @return [.items]
     */
    fun shrink(): Array<T> {
        if (items.size != size) resize(size)
        return items
    }

    /** Increases the size of the backing array to accommodate the specified number of additional items. Useful before adding many
     * items to avoid multiple backing array resizes.
     * @return [.items]
     */
    fun ensureCapacity(additionalCapacity: Int): Array<T> {
        require(additionalCapacity >= 0) { "additionalCapacity must be >= 0: $additionalCapacity" }
        val sizeNeeded = size + additionalCapacity
        if (sizeNeeded > items.size) resize(
            Math.max(
                Math.max(8, sizeNeeded),
                (size * 1.75f).toInt()
            )
        )
        return items
    }

    /** Sets the array size, leaving any values beyond the current size undefined.
     * @return [.items]
     */
    fun setSize(newSize: Int): Array<T> {
        require(newSize >= 0) { "newSize must be >= 0: $newSize" }
        if (newSize > items.size) resize(Math.max(8, newSize))
        size = newSize
        return items
    }

    fun resize(newSize: Int): Array<T> {
        val newItems = Array<Copyable<T>>(newSize, initializer) as Array<T>
        val items = items
        System.arraycopy(items, 0, newItems, 0, Math.min(size, newItems.size))
        this.items = newItems
        return newItems
    }

    /** Reduces the size of the array to the specified size. If the array is already smaller than the specified size, no action is
     * taken.  */
    fun truncate(newSize: Int) {
        if (size > newSize) size = newSize
    }

    /** Returns a random item from the array, or zero if the array is empty.  */
    fun random(): T? {
        return if (size == 0) null else items[MathUtils.random(0, size - 1)]
    }

    override fun hashCode(): Int {
        val items = items
        var h = 1
        var i = 0
        val n = size
        while (i < n) {
            h = h * 31 + items[i].hashCode()
            i++
        }
        return h
    }

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other !is DataClassArray<*>) return false
        val array = other
        val n = size
        if (n != array.size) return false
        val items1 = items
        val items2 = array.items
        for (i in 0 until n) if (items1[i] != items2[i]) return false
        return true
    }

    override fun toString(): String {
        if (size == 0) return "[]"
        val items = items
        val buffer = StringBuilder(32)
        buffer.append('[')
        buffer.append(items[0])
        for (i in 1 until size) {
            buffer.append(", ")
            buffer.append(items[i])
        }
        buffer.append(']')
        return buffer.toString()
    }
}
