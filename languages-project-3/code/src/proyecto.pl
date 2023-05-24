% Definición de rutas entre puntos con sus respectivas comisiones y distancias
% ruta(Origen, Destino, Distancia, Comision).
ruta(a, b, 10, 5).
ruta(b, c, 3, 1).
ruta(b, e, 5, 2).
ruta(b, d, 5, 2).
ruta(e, h, 2, 1).
ruta(d, h, 1, 0.5).
ruta(c, f, 2, 1).
ruta(f, i, 3, 2).
ruta(f, j, 10, 5).
ruta(g, j, 1, 0.5).
ruta(h, f, 3, 1).
ruta(h, i, 6, 3).

% Predicado que verifica si dos puntos están conectados y la comisión y distancia asociadas
% conectado(Punto1, Punto2, Comision, Distancia).
conectado(X, Y, Comision, Distancia) :- ruta(X, Y, Distancia, Comision).
conectado(X, Y, Comision, Distancia) :- ruta(Y, X, Distancia, Comision).

% Primera cláusula de Dijkstra: caso base cuando no quedan puntos sin visitar
% dijkstra(PuntoInicial, PuntoFinal, PuntosVisitados, PuntosSinVisitar, DistanciaParcial, ComisionParcial, DistanciaAcumulada, ComisionAcumulada, Camino).
% Si no quedan puntos sin visitar, devuelve la distancia y comisión parciales acumuladas.
dijkstra(_, _, _, [], DistanciaParcial, ComisionParcial, DistanciaParcial, ComisionParcial, _).

% Segunda cláusula de Dijkstra: caso en el que todavía quedan puntos sin visitar
% Si el punto adyacente A no es el punto final y no ha sido visitado, lo añade a la lista de visitados y lo elimina de la lista de sin visitar. 
% Luego, agrega la distancia y comisión de la ruta a los totales acumulados y llama recursivamente a Dijkstra con el nuevo punto, la lista actualizada de visitados y sin visitar, y los totales acumulados.
dijkstra(I, F, S, Unvisited, DistanciaParcial, ComisionParcial, DistanciaAcumulada, ComisionAcumulada, L) :-
    conectado(I, A, Comision, Distancia),
    A \= F,
    not(member(A, S)), 
    append(S, [A], Lista1),
    eliminar(A, Unvisited, Lista2),
    NuevaDistanciaParcial is DistanciaParcial + Distancia,
    NuevaComisionParcial is ComisionParcial + Comision,

    dijkstra(A, F, Lista1, Lista2, NuevaDistanciaParcial, NuevaComisionParcial, DistanciaAcumulada, ComisionAcumulada, L).

% Tercera cláusula de Dijkstra: caso en que el punto adyacente es el punto final
% Si el punto adyacente es el punto final, lo añade a la lista de visitados, suma la distancia y comisión de la ruta a los totales acumulados y devuelve la lista de visitados y los totales acumulados.
dijkstra(I, F, S, _, DistanciaParcial, ComisionParcial, DistanciaAcumulada, ComisionAcumulada, L) :-
    conectado(I, F, Comision, Distancia),
    append(S, [F], Lista),
    NuevaDistanciaParcial is DistanciaParcial + Distancia,
    NuevaComisionParcial is ComisionParcial + Comision,
    DistanciaAcumulada = NuevaDistanciaParcial,
    ComisionAcumulada = NuevaComisionParcial,
    L = Lista.

% Predicado para eliminar un punto de la lista
% eliminar(Punto, Lista, ListaResultante).
eliminar(_, [], []).
eliminar(X, [X|R], R).
eliminar(X, [C|R], [C|R1]) :- X \= C, eliminar(X, R, R1).

