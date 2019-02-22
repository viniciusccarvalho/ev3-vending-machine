package io.igx.ev3.vending

import lejos.hardware.Button


/**
 * @author vinicius
 *
 */

fun main() {


    val candyDispenser = CandyDispenser()
    candyDispenser.start()
    Button.waitForAnyPress()

}