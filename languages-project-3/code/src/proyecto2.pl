% Hechos: Definicion de atributos y diagnosticos


% Atributos
atributo(computadora, lenta, sin_encender, sin_internet).
atributo(periferico, no_funciona, mal_conectado).
atributo(software, error, lento).
atributo(red, conexion_interrumpida, velocidad_lenta).
atributo(impresora, atascada, sin_tinta).
atributo(pantalla, sin_imagen, parpadeo).
atributo(sonido, sin_audio, distorsionado).


% Diagnosticos
diagnostico(problema_1, [lenta, sin_encender], 'Es posible que la computadora este infectada con malware. Escanea el sistema con un antivirus actualizado.').
diagnostico(problema_2, [lenta, sin_internet], 'Puede haber un problema con el adaptador de red. Verifica las conexiones y reinicia el enrutador si es necesario.').
diagnostico(problema_3, [no_funciona, mal_conectado], 'El periferico puede tener un problema de conexion. Verifica los cables y los controladores correspondientes.').
diagnostico(problema_4, [error, lento], 'Puede haber un error en el software. Intenta reinstalarlo o actualizarlo a la ultima version.').
diagnostico(problema_5, [conexion_interrumpida, velocidad_lenta], 'Hay problemas de conectividad en la red. Verifica los cables y reinicia el enrutador si es necesario.').
diagnostico(problema_6, [atascada, sin_tinta], 'La impresora puede tener un papel atascado. Retira con cuidado el papel atascado y verifica los niveles de tinta.').
diagnostico(problema_7, [sin_imagen, parpadeo], 'Puede haber un problema con la conexion de la pantalla. Verifica los cables de conexion y prueba con otro cable si es posible.').
diagnostico(problema_8, [sin_audio, distorsionado], 'Puede haber un problema con los controladores de sonido. Actualiza los controladores o prueba con otro dispositivo de audio.').
diagnostico(problema_9, [lenta, sin_internet], 'Es posible que haya un conflicto con el firewall. Desactiva temporalmente el firewall y verifica si el problema persiste.').
diagnostico(problema_10, [lenta, sin_encender], 'Puede haber un problema con la fuente de alimentacion. Verifica los cables de conexion de energia y prueba con otra toma de corriente.').

% Reglas: Definicion de consultas y reglas de diagnostico

% Consulta recursiva
consulta([Tipo | Resto]) :- atributo(Tipo, Lista), sublist(Resto, Lista).
consulta([]). % Caso base

% Verifica si una lista es sublista de otra
sublist([], _).
sublist([X | Resto], [X | Lista]) :- sublist(Resto, Lista).
sublist([X | Resto], [_ | Lista]) :- sublist([X | Resto], Lista).

% Reglas de diagnostico
diagnosticar(Atributos) :- diagnostico(Problema, Atributos, Respuesta), write('Diagnostico: '), write(Problema), nl, write('Solucion: '), write(Respuesta), nl.
%diagnosticar(_) :- write('No se pudo determinar el diagnostico.').

