
# Hibernate Locking


* Optimistic Locking: no se reserva nada, sino que guardamos la versión de un recurso en particular
  en el momento en que lo recuperamos. Si al momento de realizar una transacción, el estado del
  recurso cambió, Hibernate lo notará y lanzará una excepción. En este momento se debe reiniciar
  toda la transacción. Con Optimistic Locking, esperamos que los conflictos de transacciones sean poco frecuentes y no bloqueamos nada en la base de datos, pero pagamos un precio más alto cuando ocurre el conflicto.


* Pessimistic Locking: un recurso compartido se bloquea desde el momento en que
  se recupera por primera vez hasta el momento en que se confirma la transacción. Ninguna otra
  transacción podrá acceder al recurso en ese momento. Nuestra transacción está efectivamente
  "reservando" un conjunto de datos en particular hasta que se realice.