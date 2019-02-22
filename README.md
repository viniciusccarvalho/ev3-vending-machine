# Lejos EV3 Vending Machine

A Kotlin based Lejos EV3 brick project to simulate a vending machine.

Project consists of:

* Two motors to operate the candy shuttle
* Two touch buttons for the candy selection
* One light sensor to capture coins

# Deploying it:
I've made a blog post about the choices taken in this project, specially why I
create a slim jar with custom MANIFEST.MF file, instead of a fat jar.

The maven build package will deploy the application to the brick, if you have configured
the `ev3.host` property.

Make sure you have installed `ev3classes.jar` on your local maven before attempting to build this project locally.

Copy the media folder contents into your `ev3` programs folder

You will need to copy all dependencies files to your `ev3.lib` folder, otherwise the application won't start on the brick.

TODO: Add a maven task for that

# EV3Dev
There's a much newer EV3 project called EV3dev, one that is active and is based on a
real linux distro instead of a Lego OS running a dated Oracle JDK.

However, they lack support for NXT motors/sensors that are used in this project :(
