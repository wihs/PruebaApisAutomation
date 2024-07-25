#language: es
@FLUJOE2E_AUTH
Característica: Auth

  #--1--
  @Prueba @CrearTokenDinamicoHappyPath
  Escenario: Crear token dinamico correctamente
    Dado que configuro el body del servicio: "crearToken"
      | key      | valor       |
      | username | admin       |
      | password | password123 |
    Y configuro las cabeceras
      | cabeceras    | valor            |
      | Content-Type | application/json |
    Cuando ejecuto el servicio con la configuracion del body y cabeceras
      | servicio           | url                                  | path  | metodo | formato |
      | Auth - CreateToken | https://restful-booker.herokuapp.com | /auth | POST   | txt     |
    Entonces valido que el codigo de respuesta sea "200"
    Y guardo la respuesta de la ejecucion