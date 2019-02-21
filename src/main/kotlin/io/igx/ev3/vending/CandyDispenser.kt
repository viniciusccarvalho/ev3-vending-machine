package io.igx.ev3.vending

import io.igx.ev3.vending.model.ButtonPressedEvent
import io.igx.ev3.vending.model.CoinEvent
import io.igx.ev3.vending.model.Event
import lejos.hardware.BrickFinder
import lejos.hardware.motor.NXTRegulatedMotor
import lejos.hardware.port.MotorPort
import lejos.hardware.port.SensorPort
import lejos.robotics.RegulatedMotor
import lejos.robotics.RegulatedMotorListener
import java.util.concurrent.Executors
import java.util.concurrent.locks.ReentrantLock

/**
 * @author vinicius
 *
 */
class CandyDispenser {

    private val service = Executors.newFixedThreadPool(4)
    private val motors = arrayOf(NXTRegulatedMotor(MotorPort.A), NXTRegulatedMotor(MotorPort.B))
    val names = arrayOf("Skittles", "M&Ms")
    private val motorRotation = arrayOf(false, false)
    private val callback: (Event) -> Unit = { event -> onEvent(event) }
    private val buttonListeners = arrayOf(ButtonListener(0, SensorPort.S1, callback), ButtonListener(1, SensorPort.S2, callback))
    private val mediaManager = MediaManager(service)
    private val lock = ReentrantLock()
    private var credits = 0

    init {
        motors.forEachIndexed { index, motor ->
            motor.addListener(object: RegulatedMotorListener{
                override fun rotationStarted(p0: RegulatedMotor?, p1: Int, p2: Boolean, p3: Long) {
                    motorRotation[index] = true
                }
                override fun rotationStopped(p0: RegulatedMotor?, p1: Int, p2: Boolean, p3: Long) {
                    motorRotation[index] = false
                    p0?.stop()
                }
            })
        }

    }

    fun start() {
        service.submit {
            while(true){
                buttonListeners.forEach {
                    it.readInput()
                }
            }
        }
    }

    fun stop(){
        service.shutdownNow()
    }

    private fun onEvent(event: Event){
        when(event){
            is CoinEvent -> {
                withLock {
                    credits++
                }
            }
            is ButtonPressedEvent -> {
                //if the motor is running, we just ignore the event, avoids multiple events during a short window of time
                if(!motorRotation[event.index]){
                    withLock {
                        if(credits > -100){
                            mediaManager.playSound()
                            val motor = motors[event.index]
                            motor.setSpeed(750.0f)
                            motor.rotate(360, true)
                            credits--
                        }
                    }
                }
            }
        }
    }


    fun withLock(block: () -> Unit) {
        try{
            lock.lock()
            block()
        }finally {
            lock.unlock()
        }
    }

}