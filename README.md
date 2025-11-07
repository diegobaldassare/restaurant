# Ejemplo 1.er parcial: Gestión de Restaurante Digital

## Introducción:
Una nueva cadena de restaurantes, “GourmetFlow”, está modernizando su operación. Necesitan un sistema de software para gestionar los pedidos de los clientes en las mesas y el estado de servicio de los mismos. Tu tarea es diseñar e implementar un sistema de clases para este restaurante, asegurándose de que cumpla con los siguientes requisitos y criterios de aprobación de la Programación Orientada a Objetos (POO).

## Requisitos del Sistema
1. Se pueden registrar plato en el menú del restaurante. Cada plato debe tener un título, un precio y un identificador único.
2. El sistema debe poder crear nuevos pedidos asociados a un número de mesa específico. Cada pedido tiene un identificador único y un estado inicial (‘Pendiente’).
3. Se pueden agregar plato a un pedido existente, siempre que el plato esté en el menú del restaurante.
4. Un pedido que ya ha sido marcado como ‘Servido’ no puede modificarse (no se pueden agregar ni eliminar plato).
5. Se debe poder marcar un pedido como ‘Servido’ para indicar que ha sido completado.
6. El restaurante, clase principal, debe gestionar el menú (lista de plato disponibles) y la lista de todos los pedidos activos.
7. Un método debe calcular el costo total de un pedido específico, sumando los precios de todos los plato que contiene.
8. Un método en el sistema debe calcular el número total de platos que contiene un pedido. Este método debe ser implementado de forma recursiva para demostrar tu comprensión del concepto.
9. El sistema debe permitir al personal consultar todos los pedidos que se encuentran en estado ‘Pendiente’.
10. Se deben implementar validaciones necesarias (por ejemplo, solo agregar plato del menú; no modificar pedidos servidos).
