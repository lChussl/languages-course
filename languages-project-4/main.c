#include <stdlib.h>
#include <stdio.h>
#include <stdbool.h>
// Created by Jesús Araya Chaves

/**
 * Represents a node in the graph.
 *
 * @param vertex The number of the vertex that this node represents in the graph.
 * @param player The player this node belongs to.
 */
struct Node {
    int vertex;
    int player; // 0 = empty, 1 = player 1, 2 = player 2
};

/**
 * Forms a part of the adjacency list for each node in the graph.
 *
 * @param node A pointer to the node that this list node represents.
 * @param next A pointer to the next list node in the adjacency list.
 */
struct ListNode {
    struct Node* node;
    struct ListNode* next;
};

/**
 * Forms a part of the list of possible paths for each node in the graph.
 *
 * @param val The value of the node that this list node represents.
 * @param next A pointer to the next list node in the possible paths list.
 */
struct ListNodePath {
    int val;
    struct ListNodePath* next;
};

/**
 * Represents the entire graph, including all nodes, their adjacency lists,
 * and lists of possible paths.
 *
 * @param gridSize The size of the grid used to generate the graph.
 * @param numVertices The total number of vertices in the graph.
 * @param nodes An array of pointers to all nodes in the graph.
 * @param adjLists An array of pointers to the head nodes of the adjacency lists
 *                 for each node in the graph.
 * @param paths An array of pointers to the head nodes of the possible paths lists
 *              for each node in the graph.
 */
struct Graph {
    int gridSize;
    int numVertices;
    struct Node** nodes;
    struct ListNode** adjLists;
    struct ListNodePath** paths;
};

/**
 * Creates a new node for the graph, initializes it with a given vertex number
 * and assigns it to a specific player. By default all nodes are initialized as player 0
 *
 * @param vertex The number of the vertex that this node represents in the graph.
 * @param player The player this node belongs to.
 * @return A pointer to the newly created node.
 */
struct Node* createNode(int vertex, int player) {
    struct Node* newNode = malloc(sizeof(struct Node));

    newNode->vertex = vertex;
    newNode->player = player;

    return newNode;
}

/**
 * Creates a new list node, which forms a part of the adjacency list for each
 * node in the graph.
 *
 * @param node A pointer to the node that this list node represents.
 * @return A pointer to the newly created list node.
 */
struct ListNode* createListNode(struct Node* node) {
    struct ListNode* newListNode = malloc(sizeof(struct ListNode));

    newListNode->node = node;
    newListNode->next = NULL;

    return newListNode;
}

/**
 * Adds an undirected edge between two nodes in the graph. It modifies the
 * adjacency lists of both the source and destination nodes.
 *
 * @param graph A pointer to the graph where the edge is to be added.
 * @param src The vertex number of the source node.
 * @param dest The vertex number of the destination node.
 */
void addEdge(struct Graph* graph, int src, int dest) {
    struct ListNode* newNode = createListNode(graph->nodes[dest]);
    newNode->next = graph->adjLists[src];
    graph->adjLists[src] = newNode;

    newNode = createListNode(graph->nodes[src]);
    newNode->next = graph->adjLists[dest];
    graph->adjLists[dest] = newNode;
}

/**
 * Initializes a game board based on the provided grid size.
 *
 * This function creates two grid boards connected by a single node,
 * each cell of the grid is a vertex of the graph. The game board is represented by
 * a graph where each node is a cell of the grid, and the adjacency list for each node
 * represents the cells directly connected to it.
 *
 * @param gridSize The size of the grid for each player's board.
 * @return A pointer to the created Graph structure that represents the game board.
 */
struct Graph* initializeBoard(int gridSize) {
    // Total nodes including the node used to connect both grids
    int totalNodes = gridSize * gridSize * 2 + 1;

    // Initialize graph and variables
    struct Graph* graph = malloc(sizeof(struct Graph));
    graph->gridSize = gridSize;
    graph->numVertices = totalNodes;
    graph->nodes = malloc(totalNodes * sizeof(struct Node*));
    graph->adjLists = malloc(totalNodes * sizeof(struct ListNode*));
    graph->paths = malloc(gridSize * sizeof(struct ListNodePath*));

    // Initialize nodes and adjacency lists
    for(int i = 0; i < totalNodes; i++) {
        int player = 0;

        graph->nodes[i] = createNode(i, player);
        graph->adjLists[i] = NULL;
    }

    // Create list of paths for the nodes
    int startingNode = 0, startingNodeBack = (gridSize * gridSize) * 2;
    for(int i = 0; i < gridSize; i++) {
        graph->paths[i] = malloc(sizeof(struct ListNodePath));

        struct ListNodePath* tmp = graph->paths[i];

        for(int j = 0; j < gridSize; j++) {
            tmp->val = startingNode;
            tmp->next = malloc(sizeof(struct ListNodePath));

            startingNode++;

            tmp = tmp->next;
        }

        tmp->val = gridSize * gridSize;
        tmp->next = malloc(sizeof(struct ListNodePath));
        tmp = tmp->next;

        for(int k = 0; k < gridSize; k++) {
            if(k == gridSize - 1) {
                tmp->val = startingNodeBack;
                tmp->next = NULL;

                startingNodeBack--;

                break;
            }

            tmp->val = startingNodeBack;
            tmp->next = malloc(sizeof(struct ListNodePath));

            startingNodeBack--;

            tmp = tmp->next;
        }
    }

    int connectingNodeIndex = gridSize * gridSize;

    // Edges for first grid
    for(int i = 0; i < gridSize; i++) {
        for(int j = 0; j < gridSize; j++) {
            int nodeIndex = i * gridSize + j;

            if(i != gridSize - 1) {
                addEdge(graph, nodeIndex, nodeIndex + gridSize); // Connect node downwards
            }
            if(j != gridSize - 1) {
                addEdge(graph, nodeIndex, nodeIndex + 1); // Connect node to the right
            }

            // Connect to the connecting node
            if(j == gridSize - 1) {
                addEdge(graph, nodeIndex, connectingNodeIndex);
            }
        }
    }

    // Edges for second grid
    for(int i = 0; i < gridSize; i++) {
        for(int j = 0; j < gridSize; j++) {
            int nodeIndex = connectingNodeIndex + 1 + i * gridSize + j; // Adjust the node index for the second grid

            if(i != gridSize - 1) {
                addEdge(graph, nodeIndex, nodeIndex + gridSize); // Connect node downwards
            }
            if(j != gridSize - 1) {
                addEdge(graph, nodeIndex, nodeIndex + 1); // Connect node to the right
            }

            // Connect to the connecting node
            if(j == 0) {
                addEdge(graph, nodeIndex, connectingNodeIndex);
            }
        }
    }

    // Fill players in both grids
    int player1Pieces = 9, player2Pieces = 9, row = 0, column = 0;
    while(player1Pieces) {
        for(int i = 0; i < gridSize; i++) {
            if(player1Pieces) {
                graph->nodes[row]->player = 1;

                player1Pieces--;
                row += gridSize;
            }
        }

        row = column + 1;
        column++;
    }

    row = (gridSize * gridSize) * 2;
    column = (gridSize * gridSize) * 2;
    while(player2Pieces) {
        for(int i = 0; i < gridSize; i++) {
            if(player2Pieces) {
                graph->nodes[row]->player = 2;

                player2Pieces--;
                row -= gridSize;
            }
        }

        row = column - 1;
        column--;
    }

    return graph;
}

/**
 * Prints the game board in the console.
 *
 * This function iterates over all nodes in the graph that represent the game board.
 * Each node is assigned a color based on the player it belongs to (RED for player 1, BLUE for player 2,
 * and WHITE for empty nodes). The function prints the node number with its assigned color.
 * The board is printed in two parts to represent two grids for two players.
 * A special connecting node is printed between the two grids.
 *
 * @param graph The Graph structure representing the game board.
 * @param gridSize The size of the grid for each player's board.
 */
void printBoard(struct Graph* graph, int gridSize) {
    char* RESET = "\033[0m";
    char* RED = "\033[0;31m";
    char* BLUE = "\033[0;34m";
    char* WHITE = "\033[0;37m";

    int connectingNodeIndex = gridSize * gridSize;

    for(int i = 0; i < gridSize; i++) {
        for(int g = 0; g < 2; g++) {
            for(int j = 0; j < gridSize; j++) {
                int nodeIndex = g * gridSize * gridSize + i * gridSize + j;

                // Adjusting the nodeIndex for the second grid
                if(g == 1) {
                    nodeIndex += 1;
                }

                // Skip printing the connecting node here
                if(nodeIndex != connectingNodeIndex) {
                    int player = graph->nodes[nodeIndex]->player;
                    int pos = graph->nodes[nodeIndex]->vertex;

                    char* color;
                    switch(player) {
                        case 0:
                            color = WHITE;
                            break;
                        case 1:
                            color = RED;
                            break;
                        case 2:
                            color = BLUE;
                            break;
                    }

                    printf("%s%d%s \t", color, pos, RESET);
                }
            }

            // Print the connecting node between the two grids
            if(g == 0) {
                int player = graph->nodes[connectingNodeIndex]->player;

                char* color;
                switch(player) {
                    case 0:
                        color = WHITE;
                        break;
                    case 1:
                        color = RED;
                        break;
                    case 2:
                        color = BLUE;
                        break;
                }

                printf("- %s%d%s - \t", color, connectingNodeIndex, RESET);
            }
        }
        printf("\n");
    }
}

/**
 * Checks if a node is in a specified path in the graph.
 *
 * @param graph    The Graph object which represents the game board.
 * @param pathPos  The index of the path in the paths list in the graph to be checked.
 * @param nodePos  The position of the node to be checked if it exists in the path.
 *
 * @return True if the node is in the path, false otherwise. If pathPos is -5, it always returns true.
 */
bool validPath(struct Graph* graph, int pathPos, int nodePos) {
    struct ListNodePath* tmp = graph->paths[pathPos];

    // If pathPos is -5, there is always a path (connecting node)
    if(pathPos == -5) {
        return true;
    }

    while(tmp) {
        if(tmp->val == nodePos) {
            return true;
        }

        tmp = tmp->next;
    }

    return false;
}

/**
 * Prints all the possible moves for a player in the game.
 *
 * @param graph  The Graph object which represents the game board.
 * @param player The player number (1 or 2).
 */
void possiblePlays(struct Graph* graph, int player) {
    printf("Possible plays for player %d:\n", player);

    // Define the opponent's player number
    int opponent = (player == 1) ? 2 : 1;

    // Iterate through all the nodes in the graph
    for(int i = 0; i < graph->numVertices; i++) {
        struct Node* temp = graph->nodes[i];

        // Check if the node belongs to the player
        if(temp->player == player) {
            struct ListNode* adjListTemp = graph->adjLists[i];
            int pathPos = -1;

            if(temp->vertex == 9) {
                pathPos = -5;
            } else {
                for(int k = 0; k < graph->gridSize; k++) {
                    struct ListNodePath* tmp = graph->paths[k];
                    if(pathPos != -1) {
                        break;
                    }

                    while(tmp) {
                        if(temp->vertex == tmp->val) {
                            pathPos = k;
                            break;
                        }

                        tmp = tmp->next;
                    }
                }
            }

            // Go through all the adjacent nodes
            while(adjListTemp) {
                struct Node* adjNode = graph->nodes[adjListTemp->node->vertex];

                // Check if the adjacent node is an opponent's node
                if(adjNode->player == opponent) {
                    // Iterate through all adjacent nodes of the opponent's node
                    struct ListNode* oppAdjListTemp = graph->adjLists[adjNode->vertex];

                    while(oppAdjListTemp) {
                        struct Node* oppAdjNode = graph->nodes[oppAdjListTemp->node->vertex];

                        // Check if the opponent's adjacent node is empty
                        if(oppAdjNode->player == 0 && (validPath(graph, pathPos, oppAdjNode->vertex) || ((abs(temp->vertex - oppAdjNode->vertex) == graph->gridSize * 2) && abs(temp->vertex - adjNode->vertex) == graph->gridSize))) {
                            printf("%d : %d  \t", temp->vertex, oppAdjNode->vertex);
                        }

                        oppAdjListTemp = oppAdjListTemp->next;
                    }
                } else if (adjNode->player == 0) { // Check if the adjacent node is empty
                    printf("%d : %d  \t", temp->vertex, adjNode->vertex);
                }

                adjListTemp = adjListTemp->next;
            }
        }
    }
    printf("\n");
}

/**
 * Prints the adjacency list of each vertex in the graph. (Mostly debugging)
 *
 * @param graph The Graph object which represents the game board.
 */
void printAdjacencyList(struct Graph* graph) {
    for (int i = 0; i < graph->gridSize; i++) {
        struct ListNodePath* temp = graph->paths[i];
        printf("\n Adjacency list of vertex %d\n ", i);

        while (temp) {
            printf("%d -> ", temp->val);
            temp = temp->next;
        }
        printf("\n");
    }
}

/**
 * Moves a player's piece from one position to another on the game board.
 *
 * @param graph The Graph object which represents the game board.
 * @param src The current position of the piece.
 * @param dest The target position to move the piece to.
 * @param player The player number (1 or 2).
 *
 * @return
 * Returns 1 if the move is successful without capturing an opponent's piece.
 * Returns 2 if the move is successful with capturing an opponent's piece.
 * Returns -1 if the destination slot is not empty.
 * Returns -2 if the destination is not adjacent.
 */
int movePiece(struct Graph* graph, int src, int dest, int player) {
    if(graph->nodes[dest]->player != 0) {
        return -1; // Destination slot is not empty
    }

    int pathPos = -1;

    for(int k = 0; k < graph->gridSize; k++) {
        struct ListNodePath* tmp = graph->paths[k];
        if(pathPos != -1) {
            break;
        }

        while(tmp) {
            if(src == tmp->val) {
                pathPos = k;
                break;
            }

            tmp = tmp->next;
        }
    }

    struct ListNode* srcAdjList = graph->adjLists[src];
    while(srcAdjList) {
        // Check if the destination is adjacent to the source
        if(srcAdjList->node->vertex == dest) {
            graph->nodes[src]->player = 0;
            graph->nodes[dest]->player = player;

            return 1; // Move is successful without capturing an opponent's piece.
        }

        // If not adjacent, check if the node belongs to the other player
        if(srcAdjList->node->player != player) {
            struct ListNode* nextAdjNodeList = graph->adjLists[srcAdjList->node->vertex];

            while(nextAdjNodeList) {
                if(nextAdjNodeList->node->vertex == dest && nextAdjNodeList->node->player == 0 && (validPath(graph, pathPos, dest) || ((abs(src - dest) == graph->gridSize * 2) && abs(src - srcAdjList->node->vertex) == graph->gridSize))) {
                    graph->nodes[src]->player = 0;
                    graph->nodes[dest]->player = player;
                    srcAdjList->node->player = 0; // Remove the opponent's piece
                    return 2; // Move is successful with capturing an opponent's piece.
                }
                nextAdjNodeList = nextAdjNodeList->next;
            }
        }

        srcAdjList = srcAdjList->next;
    }

    return -2; // Destination is not adjacent
}

/**
 * Deallocates memory used by a Graph structure and all associated structures.
 *
 * @param graph A pointer to the Graph structure to be destroyed.
 */
void freeMemory(struct Graph* graph) {
    // Free each ListNodePath
    for(int i = 0; i < graph->gridSize; i++) {
        struct ListNodePath* temp = graph->paths[i];
        while(temp) {
            struct ListNodePath* next = temp->next;
            free(temp);
            temp = next;
        }
    }

    // Free paths array
    free(graph->paths);

    // Free each Node
    for(int i = 0; i < graph->numVertices; i++) {
        free(graph->nodes[i]);
    }

    // Free nodes array
    free(graph->nodes);

    // Free each ListNode in adjacency lists
    for(int i = 0; i < graph->numVertices; i++) {
        struct ListNode* temp = graph->adjLists[i];
        while(temp) {
            struct ListNode* next = temp->next;
            free(temp);
            temp = next;
        }
    }

    // Free adjacency lists array
    free(graph->adjLists);

    // Free the graph itself
    free(graph);
}


int main() {
    // Console colors
    char* RESET = "\033[0m";
    char* GREEN = "\033[0;32m";
    char* RED = "\033[0;31m";

    // Variables declaration
    int player, src, dest, result, size, player1 = 9, player2 = 9;

    // Select the player that will play first
    printf("Who will start playing? (1 or 2): ");
    scanf("%d", &player);
    while (player != 1 && player != 2) {
        printf("Invalid choice. Please enter 1 or 2: ");
        scanf("%d", &player);
    }

    // Input the board size
    printf("Enter the size of the board: ");
    scanf("%d", &size);

    // Initialize the graph with the size selected by the user, print the board and check the possible plays for the player
    struct Graph* graph = initializeBoard(size);
    //printAdjacencyList(graph);
    printBoard(graph, size);
    possiblePlays(graph, player);

    // Loop forever, until a player have no pieces left
    while(1) {
        printf("\nPieces available for player 1 & player 2: \n");
        printf("%d : %d\n\n", player1, player2);
        if(player1 == 0) {
            printf("- %s%s%s - \t", GREEN, "Player 2 Wins!", RESET);
            freeMemory(graph);
            break;
        } else if(player2 == 0) {
            printf("- %s%s%s - \t", GREEN, "Player 1 Wins!", RESET);
            freeMemory(graph);
            break;
        }

        printf("Player %d's turn.\n", player);

        // Input the src index and dest index
        printf("Enter the index of the piece you want to move: ");
        scanf("%d", &src);
        printf("Enter the index of where you want to move the piece to: ");
        scanf("%d", &dest);

        // Make the move and get the result
        result = movePiece(graph, src, dest, player);

        // Valid move if result is 1 or 2, invalid move if its -1 or something else
        if(result == 1) {
            printf("%s%s%s", GREEN, "Move successful!\n", RESET);
            printBoard(graph, size);

            // Switch the player for the next turn
            player = player == 1 ? 2 : 1;
        } else if(result == 2) {
            printf("%s%s%s", GREEN, "Move successful!\n", RESET);
            printBoard(graph, size);

            if(player == 1) {
                player2--;
            } else {
                player1--;
            }

            player = player == 1 ? 2 : 1;
        } else if(result == -1) {
            printf("%s%s%s", RED, "Invalid move, destination slot is not empty!\n", RESET);
        } else {
            printf("%s%s%s", RED, "Invalid move, destination slot is not adjacent!\n", RESET);
        }

        possiblePlays(graph, player);
    }

    return 0;
}