package io.igx.ev3.vending.model

/**
 * @author vinicius
 *
 */

sealed class Event(val timestamp: Long)
class CoinEvent : Event(System.currentTimeMillis())
data class ButtonPressedEvent(val index: Int) : Event(System.currentTimeMillis())

interface SensorListener {
    /**
     * Reads the sensor input
     */
    fun readInput()
}