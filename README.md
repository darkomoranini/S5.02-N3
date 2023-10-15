# S5.02-N3

Hola! Para poder diversificar la persistencia he creado servicios y controladores aplicados a mongo y sql además de controladores y servicios genéricos que identifican el tipo de persistencia para aplicar la clase correspondiente. La clase "DataBaseProperties" funciona como componente de la aplicación con el propósito de gestionar estas bases de datos, en concreto para acceder a la propiedad "database.type" y a través de un método obtener su valor.
