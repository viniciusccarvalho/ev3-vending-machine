package io.igx.ev3.vending

import lejos.hardware.Sound
import java.io.File
import java.util.concurrent.ExecutorService

/**
 * @author vinicius
 *
 */
class MediaManager(private val executor: ExecutorService) {

    fun playSound(){
        executor.submit {
            Sound.setVolume(Sound.VOL_MAX)
            Sound.playSample(File("/home/lejos/programs/coin_2.wav"))
        }
    }
}