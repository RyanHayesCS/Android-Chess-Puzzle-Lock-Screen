# Android-Chess-Puzzle-Lock-Screen

***This version of the application is currently not working due to the java servlet which the application relies on being down indefiitely. Providing the application with a file of syntactically correct puzzles should get around this issue.*** 

  The Chess Puzzle Lock Screen is an Android application which is meant to be used as a replacement for the standard lock screen. Rather than asking the user for a passcode, it requires them to solve a chess puzzle in order to unlock their phone. This project was created in Android Studio.

  Upon downloading the application, the user is brought to the settings screen where they are asked to set a default passcode. They also have the option to receive notifications through the lock screen.  

  When the user locks their phone, the application’s LS_Reciever class communicates to the LS_Service class that the screen has been locked. From here, the LS_Service class reads a new chess puzzle from the list of chess puzzles which is stored on the phone. The NewLockscreen class then takes this puzzle and initializes the lock screen. This is done by using an alert dialog in order to create a window which cannot be by-passed through the phone’s physical buttons, as well as touch gestures. A gridview layout is then used to draw a chessboard by using a collection of image resources. The Board class is used in order to draw the entire board, as well as handle touch gestures. The Tile class is used to draw individual squares, as well as (in conjunction with the Board class) calculate if moves are legal (a feature which is not currently necessary).

  When going to unlock or turn on their phone, the user is presented with a chess puzzle. If they are able to solve the puzzle within 3 attempts, their phone will be unlocked. Otherwise, they will be brought to a default lock screen which asks them to enter the aforementioned default passcode. 

  To solve the problem of how this lock screen would receive new puzzles, we chose to create a server which would send the puzzles to the user’s phone. The server runs on the Ubuntu 16.04 operating system. We installed Apache Tomcat 7 onto our server in order to implement a Java Servlet onto our server.

  The Chess Puzzle Lock Screen communicates with this server to receive chess puzzles. In order to send these problems, we chose to use a RESTful Service as a means of sending a resource (chess puzzle) from a server to a client (a cell phone). The service was created in Eclipse as a Java EE project. In order to utilize RESTful service APIs in Java, I recommend downloading the Jersey libraries. These libraries can be added to your Eclipse project under the “lib” directory. The RESTful service reads these problems from a textfile and converts them to an easily readable format (xml or json). Upon creating the service, it is exported as a .war file and placed into the webapps directory of tomcat7. The server then uses this package to send our puzzles. The phone’s LS_Service class is also used in order to set up a connection with our server on a separate thread. Once these puzzles are received by our phone, it parses each puzzle with the PuzzleXMLParser class. These puzzles are converted from xml to Puzzle objects so our application can easily read them and set up the chess board accordingly. These Puzzle objects are all written to file on the phone and read through until the phone once again runs out of puzzles and this process needs to be repeated.
