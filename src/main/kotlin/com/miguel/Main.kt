package com.miguel

import com.miguel.webots.Controller
import com.miguel.webots.youbot.YouBot
import kotlin.math.*

object Main {

    @JvmStatic
    fun main(args: Array<String>) {
        val controller = Controller(50, false);
        val youBot = YouBot(controller)

        var angle = .0

        while (controller.step() != -1) {
            angle += .01

            val x = 0.8 * cos(angle);
            val z = 0.8 * sin(angle);

            controller.setObjectPosition("box", listOf(x, .05, z).toDoubleArray())

            println(youBot.rotationAngle)

            System.gc()
        }
    }
}