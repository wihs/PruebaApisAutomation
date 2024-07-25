#language:es
@FLUJOE2E_BOOKING
Característica: Booking

  @Prueba @HappyCrearLibroPost
  Escenario: Crear un Libro con Post
    Dado que configuro el body del servicio: "crearLibro"
      | key                   | valor      |
      | firstname             | Jim        |
      | lastname              | Brown      |
      | totalprice            | 111        |
      | depositpaid           | true       |
      | bookingdates.checkin  | 2018-01-01 |
      | bookingdates.checkout | 2019-01-01 |
      | additionalneeds       | Breakfast  |
    Y configuro las cabeceras
      | cabeceras    | valor            |
      | Content-Type | application/json |
    Cuando ejecuto el servicio con la configuracion del body y cabeceras
      | servicio                | url                                  | path     | metodo | formato |
      | Booking - CreateBooking | https://restful-booker.herokuapp.com | /booking | POST   | json    |
    Entonces valido que el codigo de respuesta sea "200"
    Y guardo la respuesta de la ejecucion


  @Prueba @UnHappyCrearLibroPost
  Escenario: Crear un Libro con Post
    Dado que configuro el body del servicio: "crearLibro"
      | key                   | valor      |
      | firstname             | Jim        |
      | lastname              | Brown      |
      | totalprice            | 111        |
      | depositpaid           | true       |
      | bookingdates.checkin  | 2018-01-01 |
      | bookingdates.checkout | 2019-01-01 |
      | additionalneeds       | Breakfast  |
    Y configuro las cabeceras
      | cabeceras    | valor            |
      | Content-Type | application/json |
    Cuando ejecuto el servicio con la configuracion del body y cabeceras
      | servicio                | url                                  | path      | metodo | formato |
      | Booking - CreateBooking | https://restful-booker.herokuapp.com | /bookings | POST   | json    |
    Entonces valido que el codigo de respuesta sea "404"
    Y guardo la respuesta de la ejecucion


  @Prueba @HappyDeleteLibro
  Escenario: Eliminar un Libro con Delete
    Dado configuro las cabeceras
      | cabeceras     | valor                          |
      | Cookie        | token=<token_value>            |
      | Authorization | Basic YWRtaW46cGFzc3dvcmQxMjM= |
    Cuando ejecuto el servicio con la configuracion del body y cabeceras
      | servicio                | url                                  | path       | metodo | formato |
      | Booking - DeleteBooking | https://restful-booker.herokuapp.com | /booking/1 | DELETE | json    |
    Entonces valido que el codigo de respuesta sea "201"
    Y guardo la respuesta de la ejecucion


  @Prueba @UnHappyDeleteLibro
  Escenario: Eliminar un Libro con Delete
    Dado configuro las cabeceras
      | cabeceras     | valor                          |
      | Cookie        | token=<token_value>            |
      | Authorization | Basic YWRtaW46cGFzc3dvcmQxMjM= |
    Cuando ejecuto el servicio con la configuracion del body y cabeceras
      | servicio                | url                                  | path       | metodo | formato |
      | Booking - DeleteBooking | https://restful-booker.herokuapp.com | /booking/1 | get    | json    |
    Entonces valido que el codigo de respuesta sea "404"
    Y guardo la respuesta de la ejecucion


  @Prueba @HappyPutLibro
  Escenario: Actualizar un Libro con Put
    Dado que configuro el body del servicio: "LibroPut"
      | key                   | valor      |
      | firstname             | James      |
      | lastname              | Brown      |
      | totalprice            | 111        |
      | depositpaid           | true       |
      | bookingdates.checkin  | 2018-01-01 |
      | bookingdates.checkout | 2019-01-01 |
      | additionalneeds       | Breakfast  |
    Y configuro las cabeceras
      | cabeceras     | valor                          |
      | Content-Type  | application/json               |
      | Authorization | Basic YWRtaW46cGFzc3dvcmQxMjM= |
    Cuando ejecuto el servicio con la configuracion del body y cabeceras
      | servicio                | url                                  | path       | metodo | formato |
      | Booking - UpdateBooking | https://restful-booker.herokuapp.com | /booking/2 | PUT    | json    |
    Entonces valido que el codigo de respuesta sea "200"
    Y guardo la respuesta de la ejecucion


  @Prueba @HappyPatchLibro
  Escenario: Actualizar un Libro con Patch
    Dado que configuro el body del servicio: "LibroPatch"
      | key       | valor |
      | firstname | James |
      | lastname  | Brown |
    Y configuro las cabeceras
      | cabeceras     | valor                          |
      | Content-Type  | application/json               |
      | Authorization | Basic YWRtaW46cGFzc3dvcmQxMjM= |
    Cuando ejecuto el servicio con la configuracion del body y cabeceras
      | servicio                       | url                                  | path       | metodo | formato |
      | Booking - PartialUpdateBooking | https://restful-booker.herokuapp.com | /booking/2 | PATCH  | json    |
    Entonces valido que el codigo de respuesta sea "200"
    Y guardo la respuesta de la ejecucion



