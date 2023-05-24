# IC-4700 | Project 3 - Logical Coding

The purpose of this project is to apply the knowledge gained during the course on logical programming and to design and implement functionality using Prolog and Java.

## Work Description

The project consists of two exercises. 

### Exercise 1: 

A food distribution company uses a Prolog program to determine the shortest route on a map that connects 10 urbanizations where it distributes its products. For each trip, the company charges a commission calculated by the distance covered. Your task is to implement, using recursion, a system that can navigate a weighted graph, find the shortest path from one node to another, and return the sum of the weight (included in the edges).

The following table presents the distance in kilometers between urbanizations and the corresponding commission for covering that distance:

| Origin Urbanization | Destination Urbanization | Distance | Commission |
|---------------------|-------------------------|----------|------------|
| A                   | B                       | 10 Km    | 5.0%       |
| B                   | C                       | 3 Km     | 1.0%       |
| B                   | E                       | 5 Km     | 2.0%       |
| B                   | D                       | 5 Km     | 2.0%       |
| E                   | H                       | 2 Km     | 1.0%       |
| D                   | H                       | 1 Km     | 0.5%       |
| C                   | F                       | 2 Km     | 1.0%       |
| F                   | I                       | 3 Km     | 2.0%       |
| F                   | J                       | 10 Km    | 5.0%       |
| G                   | J                       | 1 Km     | 0.5%       |
| H                   | F                       | 3 Km     | 1.0%       |
| H                   | I                       | 6 Km     | 3.0%       |

Define the facts and rules necessary to answer the following type of query:
* Given an origin point and a delivery point, what is the shortest route and the corresponding commission for the delivery? Consider that the routes are bidirectional.

### Exercise 2:

Develop a diagnostic system using Prolog. The group of students will need to create a knowledge base for:
* Technical support: A knowledge base that can help support technicians solve technical problems and answer frequently asked questions.

Your group will need to investigate the subject matter, define the questions your system should be able to answer, design the knowledge base, and implement the diagnostic system.

Deliverables include:
* Documentation of the diagnostic system (described in point 3).
* Model of the knowledge base duly documented and implemented in Prolog. The knowledge base will be evaluated according to the complexity of the defined system.
* At least one recursive query should be posed, and lists should be used in at least one of the predicates.
* At least 7 attributes and at least 10 possible diagnostic results must be contemplated.
* Query mechanism. For this, you must develop a program in Java using the JPL interface.

For additional information, please refer to the [link](Proyectos_tp03_Lenguajes_TP3-Programación_logica.pdf).