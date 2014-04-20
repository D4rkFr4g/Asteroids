------------------------------------------------------------------------
This is the project README file. Here, you should describe your project.
Tell the reader (someone who does not know anything about this project)
all he/she needs to know. The comments should usually include at least:
------------------------------------------------------------------------

PROJECT TITLE: Asteroids
PURPOSE OF PROJECT: CS46A - Project
VERSION or DATE: 11/29/09
HOW TO START THIS PROJECT:
AUTHORS: Zane Melcho, Tin Tran, Jeremy Lozano
USER INSTRUCTIONS:

Left and Right Arrow keys rotate the ship
Up Arrow Key accelerates ship forward
Down Arrow Key activates Hyperspace and moves you to a random location
Enter Key will restart the game if all lives are lost


Known issues:	
?	Bullets travel at the speed your ship is moving. i.e. If your going full speed right and shoot straight down your bullets will go really fast as opposed to if you were standing still and shooting down.
?	Hitting Esc at the GameOver screen will close the frame but it doesn't shut down the Java app. So if you try to relaunch you'll get a buffer overflow error message. When you close using the X it will close the app completely and you'll be able to launch the game normally.
?	Asteroid Boundary is larger than the asteroids so it will trigger a death when getting close to the asteroid. This is more noticeably on hitting an asteroid on the bottom.
?	Sounds for shooting bullets will sometimes skip and not play a sound for every bullet fired.
?	HyperSpace doesn't keep you on the screen always.
