package io.igx.ev3.vending

import io.igx.ev3.Images
import lejos.hardware.ev3.LocalEV3
import lejos.hardware.lcd.GraphicsLCD
import lejos.hardware.lcd.Image
import lejos.utility.Delay
import java.util.concurrent.ExecutorService

/**
 * @author vinicius
 *
 */
class DisplayManager(private val executor: ExecutorService, private val creditsFn: (() -> Int)) {
    val images = arrayOf(Images.SKITTLES, Images.MMS)
    val graphics = LocalEV3.get().graphicsLCD
    val w = graphics.width
    val h = graphics.height

    fun printWelcome(){
        val credits = creditsFn()
        graphics.clear()
        graphics.drawString("Credits: $credits", h-20, w/2 + 10, GraphicsLCD.BOTTOM or GraphicsLCD.HCENTER)
        graphics.refresh()
    }

    fun display(index : Int) {
        val image = images[index]
        val xpos = (w-image.width)/2
        val ypos = (h-image.height)/2
        graphics.clear()
        graphics.drawImage(image, xpos, ypos, 0)
        graphics.refresh()
    }

    fun animate(index: Int){
        executor.submit {
            val image = images[index]
            val xpos = (w-image.width)/2
            var ypos = (h-image.height)/2
            for (i in 0 until h/2){
                graphics.clear()
                graphics.drawImage(image, xpos, ypos + i, 0)
                graphics.refresh()
                Delay.msDelay(33)
            }
            printWelcome()
        }
    }
}