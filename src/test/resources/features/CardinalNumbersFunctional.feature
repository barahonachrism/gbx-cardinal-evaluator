Feature: Pruebas funcionales. El objetivo es que traduzca frases de todo tipo, que tengan numeros escritos en letras adentro, como por ejemplo: "Quisiera transferir ciento cincuenta pesos a Juan Perez que vive en la calle Rivadavia cuarenta y tres"-> "Quisiera transferir 150 pesos a Juan Perez que vive en la calle Rivadavia 43"
  Scenario Outline: Convertir una frase con numeros en texto con numeros en valores numericos
    Given La frase en texto "<Given phrase>" por servicio web
    Then La frase en numeros "<Expected phrase>" por servicio web
    Examples:
      |Given phrase|Expected phrase|
      | Quisiera transferir ciento cincuenta pesos a Juan Perez que vive en la calle Rivadavia cuarenta y tres|Quisiera transferir 150 pesos a Juan Perez que vive en la calle Rivadavia 43|
      | Quisiera transferir diez mil doscientos treinta dólares a Dario Roback que vive en la calle de las Verbenitas ciento treinta y ocho A y Stefano Maderno|Quisiera transferir 10230 dólares a Dario Roback que vive en la calle de las Verbenitas 138 A y Stefano Maderno|

