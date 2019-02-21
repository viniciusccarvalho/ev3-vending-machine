package io.igx.ev3.vending

import io.igx.ev3.vending.model.ButtonPressedEvent
import io.igx.ev3.vending.model.Event
import io.igx.ev3.vending.model.SensorListener
import lejos.hardware.port.Port
import lejos.hardware.sensor.NXTTouchSensor

/**
 * @author vinicius
 *
 */
class ButtonListener(private val index: Int, private val sensorPort: Port, private val callback: (Event) -> Unit) : SensorListener{

    private val touchSensor =  NXTTouchSensor(sensorPort)
    private val sensorMode = touchSensor.touchMode

    override fun readInput() {
        val sample = FloatArray(sensorMode.sampleSize())
        sensorMode.fetchSample(sample, 0)
        val touched = sample[0] == 1.0f
        if(touched){
            callback(ButtonPressedEvent(index))
        }
    }

}