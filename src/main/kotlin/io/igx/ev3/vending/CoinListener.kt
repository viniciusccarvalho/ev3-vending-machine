package io.igx.ev3.vending

import io.igx.ev3.vending.model.SensorListener
import lejos.hardware.port.Port
import lejos.hardware.sensor.NXTColorSensor

/**
 * @author vinicius
 * Uses the color sensor to detect luminance changes (passage of a coin) through the slot.
 * Notifies the CandyDispenser on a event
 */
class CoinListener(val port: Port) : SensorListener {

    val lightSensor = NXTColorSensor(port)
    val sensorMode = lightSensor.ambientMode

    override fun readInput() {

        val samples = FloatArray(sensorMode.sampleSize())
        sensorMode.fetchSample(samples, 0)

    }



}