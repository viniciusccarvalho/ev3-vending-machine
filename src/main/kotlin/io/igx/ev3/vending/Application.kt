package io.igx.ev3.vending

import io.igx.ev3.Images
import lejos.hardware.Button
import lejos.hardware.ev3.LocalEV3
import lejos.hardware.lcd.Image


/**
 * @author vinicius
 *
 */

fun main() {

    val graphics = LocalEV3.get().graphicsLCD
    val w = graphics.width
    val h = graphics.height
    val image = Images.SKITTLES
    graphics.drawImage(image, (w - image.width)/2, (h - image.height)/2, 0)
    val candyDispenser = CandyDispenser()
    //candyDispenser.start()
    Button.waitForAnyPress()
    if(Button.ESCAPE.isDown){
        candyDispenser.stop()
        System.exit(0)
    }
}