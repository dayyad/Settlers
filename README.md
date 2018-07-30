
## A quick note


I created this during my first year of University when I was first getting used to the Java programming language. Doing little projects such as these was critical in helping me build my knowledge in the Java programming language, and helped keep my enthusiasm for programmign alive.


# Settlers

An early attempt to build a game with networking functionality that resembles the classic "Settlers of Catan" board game. 

## What is Settlers? 


In the original board game, Settlers of Catan, 2 or more players compete by gathering resources on the board by building around various tiles. A player can win the game by collecting victory points. 

For further reading please see : https://en.wikipedia.org/wiki/Catan

This project was a way for me to try and recreate something I really like playing the physical version of. 

## A look inside

The application can act as both the server or the client.
Here the initialisation of a new game can be seen via the start of a server.
![Alt text](https://github.com/dayyad/Settlers/blob/master/assets/desc1.PNG?raw=true)

The user view pictured below is displayed when the user connects to the server. The board is downloaded as soon as a connection is established.
![Alt text](https://github.com/dayyad/Settlers/blob/master/assets/desc2.PNG?raw=true)

Another view from the servers perspective of the data coming in from the client. A visual representation of where the user has clicked is drawn to help with debugging. I also just found this very satisfying to see so left it in :)
![Alt text](https://github.com/dayyad/Settlers/blob/master/assets/desc3.PNG?raw=true)

Currently the game does not have too much functionality in terms of gameplay, but the basic structure is there. All logic is performed on the server side, with updates being sent to the client. This implementation uses TCP as speed is not too much of an issue. 

### Dependancies
The only Lib required for running this is the ecs100 library by Victoria University of Wellington. It is bundles into the repo.
 
