<h1>
Teleop Draw
</h1>

<p>
Implementing the teleop application from rosjava, this app adds rostextviews to display velocity and pose information, as well as producing a live-updatng map that draws where the robot has traveled as it is currently traveling.  The drawView of the map takes the place where the cameraView of the original application is held.  Make sure you already have the official ros android_apps teleop code and gradle files to go with it downloaded before using this code.  You can do this by visiting <a href="https://github.com/rosjava/android_apps/tree/hydro">https://github.com/rosjava/android_apps/tree/hydro</a>.  One will see the teleop code as well as necessary gradle files as well as some other apps that can be edited to one's liking.  However, the app being focused on in this repo is the teleop app as stated before.  If one wants to use the code, in android studio import all the files by pointing to settings.gradle.  Then, replace the teleop code with the code of this repo, and all should be working.
<br>
The current state of this app shows that switching to other activities causes the program to crash.
</p>
