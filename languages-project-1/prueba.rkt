#lang racket

; Código creado por Jesús Araya y Fabricio Rios
; Al momento de ejecutar el programa se mostrará el tablero del juego, en el cual los jugadores podrán seleccionar cuál jugador va a comenzar,
; después de esto, los jugadores colocarán fichas hasta que hayan colocado 3 o un jugador gane, después de esto, los jugadores podrán comenzar a
; mover sus fichas a casillas adyacentes hasta que uno de los jugadores gane. Al momento de que uno de los jugadores gané se mostrará un mensaje
; diciendo que "Jugador 1 ha ganado" o "Jugador 2 ha ganado"

(provide initialize display-moves)
(require 2htdp/image)
(require 2htdp/universe)

; Variables globales que requiere la biblioteca universe y variables globales que requieren las funciones.
(define size 150)
(define box (rectangle size size 'outline 'black))
(define 3-boxes (beside box box box))
(define display-text (text "Hello" 24 "orange"))
(define text-area (rectangle (* 3 size) (/ size 3)  'outline 'white))
(define ttt-brd (above 3-boxes 3-boxes 3-boxes text-area))
(define last-move 0)
(define valid-moves '((1 2) (1 1) (1 4) (1 5)
                            (2 2) (2 1) (2 3) (2 5) 
                            (3 2) (3 3) (3 6) (3 5)
                            (4 1) (4 4) (4 5) (4 7) 
                            (5 1)(5 2) (5 3) (5 4) (5 5) (5 6) (5 7) (5 8) (5 9)
                            (6 3) (6 5) (6 6)  (6 9)
                            (7 4) (7 5) (7 7) (7 8)
                            (8 5)  (8 7) (8 8) (8 9)
                            (9 5) (9 6) (9 8) (9 9)))

(define win-states '((1 2 3) (4 5 6) (7 8 9) (1 4 7) (2 5 8) (3 6 9) (1 5 9) (3 5 7)))

; Esta función verifica si un movimiento entre dos posiciones es valida.  Entrada: pos1 y pos2 Salida: True si el movimiento es valido, False en caso contrario.
(define (valid-move? pos1 pos2)
  (and (not (or (= 0 pos1) (= 0 pos2)))
       (member (list pos1 pos2) valid-moves)))

; Esta función actualiza y gestiona el estado del tablero. Entrada: brd, x, y, me. Salida: Estado del tablero actualizado.
(define (display-moves brd x y me)
  (cond [(eq? (first brd) 's) (select-player brd x y me)]
        [(game-over? brd) brd]
        [else
         (local [(define (on-sq? n)
                   (and (<= (abs (- (first (gridsq n)) x)) (/ size 2))
                        (<= (abs (- (second (gridsq n)) y)) (/ size 2))
                        (string=? me "button-down")))

                 (define (create-board n player)
                   
                   (cond [(and (= (length (second brd)) 3) (= (length (third brd)) 3))
                          (set! last-move n)
                          (cond [(and (eq? player 'p) (eq? (first brd) 'p) (member n (second brd)))
                                 (list 'p (remove n (second brd))
                                       (third brd)
                                       (append (fourth brd) (list n)))]
                                [(and (eq? player 'c) (eq? (first brd) 'c) (member n (third brd)))
                                 (list 'c (second brd)
                                       (remove n (third brd))
                                       (append (fourth brd) (list n)))]
                                [else brd])]
                         [else (if (and (not (member n (second brd)))
                                        (not (member n (third brd)))
                                        (eq? player (first brd)))
                                   (if (or ( valid-move? last-move n) (= last-move 0))
                                      
                                   (if (eq? player 'p)
                                       (list 'c (append (second brd) (list n))
                                             (third brd)
                                             (remove n (fourth brd)))
                                       (list 'p (second brd)
                                             (append (third brd) (list n))
                                             (remove n (fourth brd))))brd)
                                   brd)]))]

           (cond
             [(on-sq? 1) (create-board 1 (first brd))]
             [(on-sq? 2) (create-board 2 (first brd))]
             [(on-sq? 3) (create-board 3 (first brd))]
             [(on-sq? 4) (create-board 4 (first brd))]
             [(on-sq? 5) (create-board 5 (first brd))]
             [(on-sq? 6) (create-board 6 (first brd))]
             [(on-sq? 7) (create-board 7 (first brd))]
             [(on-sq? 8) (create-board 8 (first brd))]
             [(on-sq? 9) (create-board 9 (first brd))]
             [else brd]))]))


; Esta función verifica si un elemento está presente en la lista. Entrada: x, lst. Salida: True si el elemento está presente en X y Falso si no.
(define (member? x lst) (not (false? (member x lst))))


; Esta función determina si el juego ha terminado. Entrada: brd Salida: True si el juego ha terminado, False si no.
(define (game-over? brd)
  (or(empty? (fourth brd))
     (win? (second brd))
     (win? (third brd))))

; Esta función determina si un jugador ha ganado. Entrada: moves (lista de jugadas de un jugador) Salida: True si el jugador ha ganado, False si no.
(define (win? moves)
  (ormap (lambda (win-state)
           (andmap (lambda (ele-in-ws) (member? ele-in-ws moves)) win-state))
         win-states))

; Esta función se utliza para mostrar un mensaje en el tablero si uno de los jugadores ha ganado. Entrada: brd Salida: Tablero con un mensaje de ganador si uno de los jugadores ha ganado, devuelve solo el tablero si ninguno ha ganado.
(define (display-winner brd)
  (let ([current-board (foldr (lambda (n im) (p-moves n im))
                              (foldr c-moves ttt-brd (second brd))
                              (third brd))])
    (cond [(win? (second brd)) (place-image (text "¡Jugador 1 ha ganado!" (floor (/ size 4)) 'green)
                                            (floor (* 1.5 size))
                                            (floor (+ (* 3.15 size)))
                                            current-board)]
          [(win? (third brd)) (place-image (text "¡Jugador 2 ha ganado!" (floor (/ size 4)) 'green)
                                           (floor (* 1.5 size))
                                           (floor (+ (* 3.15 size)))
                                           current-board)]
          [else brd])))

; Esta función se utiliza para seleccionar cual jugador comienza en el juego. Entrada: brd, x, y, me. Salida: Tablero con el jugador que se seleccionó colocando fichas de primero.
(define (select-player brd x y me)
  (if (string=? me "button-down")
      (cond [(and (<= (abs (- (* 1.25 size) x)) (/ size 2))
                  (<= (abs (- (* 3 size) y)) (/ size 2)))
             (list 'p (second brd) (third brd) (fourth brd))]
            [(and (<= (abs (- (* 1.75 size) x)) (/ size 2))
                  (<= (abs (- (* 3 size) y)) (/ size 2)))
             (list 'c (second brd) (third brd) (fourth brd))]
            [else brd])
      brd))

; Esta función se utiliza para mostrar el mensaje para elegir que jugador va a comenzar. Entrada: brd Salida: El tablero con el mensaje de seleccione el jugador.
(define (display-player-selection brd)
  (place-image (text "Seleccione el jugador que va primero:" (floor (/ size 7)) 'black)
               (floor (* 1.5 size))
               (floor (* 2.9 size))
               (place-image (text "Jugador 1" (floor (/ size 7)) 'blue)
                            (floor (* 1.1 size))
                            (floor (* 3.2 size))
               (place-image (text "Jugador 2" (floor (/ size 7)) 'red)
                            (floor (* 1.9 size))
                            (floor (* 3.2 size))
                            ttt-brd))))



; Esta función es utilizada para inicializar y verificar estados del tablero.  Entrada: brd Salida: El tablero con su configuración respectica, ya sea, si un jugador ha ganado o el tablero actualizado.
(define (initialize brd)
  (cond [(eq? (first brd) 's) (display-player-selection brd)]
        [(game-over? brd) (display-winner brd)]
        [else (place-image
               (cond [(eq? (first brd) 'p) (text "Jugador 1" (floor (/ size 6)) 'blue)]
                     [(eq? (first brd) 'c) (text "Jugador 2" (floor (/ size 6)) 'red)]
                     [else (error "initialize: Jugador no válido")])
               (floor (* 1.5 size))
               (floor (+ (* 3.15 size)))
               (foldr (lambda (n im) (p-moves n im))
                      (foldr c-moves ttt-brd (second brd))
                      (third brd)))]))

; Esta función se utiliza para colocar las fichas O. Entrada: n, im. Salida: Tablero actualizado con las ficha O colocada.
(define (c-moves n im)
  (place-image (text "O" (/ size 2) 'blue)
               (first (gridsq n))
               (second (gridsq n))
               im))

; Esta función se utiliza para colocar las fichas X. Entrada: n, im. Salida: Tablero actualizado con las ficha X colocada.
(define (p-moves n im)
  (place-image (text "X" (/ size 2) 'red)
               (first (gridsq n))
               (second (gridsq n))
               im))

; Esta función verifica si un jugador ha ganado. Entrada: brd  Salida: Tablero con el mensaje si uno de los jugadores ha ganado, Tablero original si no.
(define (c-turn brd)
  (if (game-over? brd)
      brd
  (cond [(game-over? brd) (display-winner brd)]
        [else brd]))) 

; Esta función se utiliza para convertir n en un par de coordenadas. Entrada: n Salida: Coordenadas del n.
(define (gridsq n)
  (cond
    [(= n 1)(grid 1 1)]
    [(= n 2)(grid 2 1)]
    [(= n 3)(grid 3 1)]
    [(= n 4)(grid 1 2)]
    [(= n 5)(grid 2 2)]
    [(= n 6)(grid 3 2)]
    [(= n 7)(grid 1 3)]
    [(= n 8)(grid 2 3)]
    [(= n 9)(grid 3 3)]))

; Esta función sirve para centrar las fichas en el tablero  Entrada: x, y. Salida: Tablero
(define (grid x y)
  (list (* size (- x .5)) (* size (- y .5))))

; Esta función se utiliza para mantener el ciclo del juego y actualizar sus estados. Entrada: brd Salida: Tablero actualizado.
(define brd (list 's '() '() '(1 2 3 4 5 6 7 8 9)))
(big-bang brd
  (to-draw initialize)
  (on-mouse display-moves)
  (on-tick c-turn))