# Known issues


## CrayCray can’t go to sleep when application process is dead

**Cause:** When the application is dead the thread isn’t running and the values can’t be updated until the application is opened again. The database saves the need values when closing down the application and updates them on start. Because of this CrayCray can’t go to sleep until the application is reopened.

**Solution:** We wan’t the user to be able to feed or cure CrayCray if he is about to die when the application is started. If the energy has reached zero while the application was down we set the energy level to one so that the user will have some time to save CrayCray before it goes to sleep and tragically dies when the user can’t do anything about it.


## If the application process is killed and reopened it won't remember if the music was muted

**Cause** The database doesn't save the mute option. We decided not to do anything about it because the music is not always on in the game.


## Sometimes the reaction of the application is slow

**Probable cause:** The user doesn’t get feedback instantly probably because the view won’t be updated until the next time the thread runs. The thread is updated every second so the actual delay is not significant.



## Known issues without known cause or solution:
* Sometimes the image of CrayCray disappears for a while. (rare)
