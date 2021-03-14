package com.miguel

import com.google.gson.*
import com.miguel.neural.Network
import com.miguel.utils.Numbers
import com.miguel.utils.Vector
import com.miguel.webots.Controller
import com.miguel.webots.youbot.YouBot
import java.io.FileWriter
import kotlin.math.*

object Main {

    @JvmStatic
    fun main(args: Array<String>) {
        val controller = Controller(50, false);
        val youBot = YouBot(controller)

        val topology = listOf(2, 16, 16, 16, 3)

        val center = youBot.position
        val initialPosition = Vector(center.x, center.y, center.z)

        val pos = Vector(center.x, .05, center.z);

        var angle = .0
        var comp = .003
        var lastTime = .0
        var count = 0
        var current = 0

        val maxVelocity = 5.0
        val targetFitness = .001
        val maxGenerations = 200
        val maxPerGeneration = 5
        val timeInterval = 90

        val networks = ArrayList<Network>()

        val generationFitness: MutableList<Double> = ArrayList()
        val errors = ArrayList<Double>()

        for (i in 0..maxPerGeneration) {
            networks.add(Network(topology))
        }

        var network = networks[0]

        while (controller.step() != -1) {
            val time = controller.supervisor.time

            val youBotPosition = youBot.position
            val youBotAngle = youBot.rotationAngle

            angle += comp

            val x = 0.8 * cos(angle)
            val z = 0.8 * sin(angle)

            pos.add(x, .0, z)

            controller.setObjectPosition("box", listOf(pos.x, .05, pos.z).toDoubleArray())

            pos.subtract(x, .0, z)

            val boxPosition = Vector(controller.getObjectPosition("box"))

            val theta = youBotPosition.diffAngle(boxPosition)

            val angleError = Numbers.normalize(youBotAngle + theta)

            errors.add(abs(angleError))

            if (angle > PI || angle < -PI) {
                comp *= -1;
            }

            if (time > lastTime + 1 &&  time.toInt() % timeInterval == 0) {
                lastTime = time;

                if (count < maxGenerations) {
                    var fitness = (errors.sum()) / errors.size

                    fitness += initialPosition.distance(youBotPosition)

                    val fitnessError = .5 * (targetFitness - fitness).pow(2.0)

                    errors.clear()

                    network.fitness = fitness

                    controller.setObjectPosition("youBot", arrayOf(initialPosition.x, initialPosition.y, initialPosition.z).toDoubleArray());

                    println("Individuo $current Fitness $fitness FE $fitnessError")

                    current += 1

                    if (current == networks.size) {
                        count += 1

                        networks.sortBy { net -> net.fitness }

                        val bestFitness = networks[0].fitness

                        generationFitness.add(bestFitness)

                        var father = networks[0].copy()
                        var mother = networks[1].copy()

                        for (i in 0..maxPerGeneration) {
                            networks.remove(networks[i])

                            val net = Network(topology)

                            Network.crossOver(net, father, mother);

                            net.mutate(.2)

                            networks.add(net);
                        }

                        father = null
                        mother = null

                        println("Best Fitness $bestFitness")

                        println("Geracao $count de $maxGenerations")

                        current = 0
                    }
                }

                network = networks[current]

                System.gc()
            }

            var output = network.predict(listOf(abs(angleError), if (angleError > 0) 1.0 else .0))

            if (output[0] > .0) {
                youBot.setWheelsSpeed(arrayOf(maxVelocity, -maxVelocity, maxVelocity, -maxVelocity).toDoubleArray())
            }

            if (output[1] > .0) {
                youBot.setWheelsSpeed(arrayOf(-maxVelocity, maxVelocity, -maxVelocity, maxVelocity).toDoubleArray())
            }

            if (output[2] > .0) {
                youBot.setWheelsSpeed(arrayOf(.0, .0, .0, .0).toDoubleArray())
            }

            output.clear();

            output = null
        }

        println(generationFitness)

        val gson = GsonBuilder().setPrettyPrinting().create()

        val data = HashMap<String, Any>()

        data["fitness"] = generationFitness.toList()

        val dataWriter = FileWriter("fitness.json")
        val netWriter = FileWriter("network.json")

        gson.toJson(data, dataWriter)

        gson.toJson(network, Network::class.java, netWriter)

        dataWriter.close()
        netWriter.close()

        controller.supervisor.delete();
    }
}