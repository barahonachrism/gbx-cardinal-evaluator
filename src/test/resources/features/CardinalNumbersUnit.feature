Feature: Pruebas unitarias. El objetivo es que traduzca frases de todo tipo, que tengan numeros escritos en letras adentro, como por ejemplo: "Quisiera transferir ciento cincuenta pesos a Juan Perez que vive en la calle Rivadavia cuarenta y tres"-> "Quisiera transferir 150 pesos a Juan Perez que vive en la calle Rivadavia 43"
  Scenario: Identificar numeros en una frase
    Given La frase "Quiero hacer una transferencia de cuatro mil quinientos sesenta y seis millones trescientos cuarenta y cinco mil seiscientos setenta y ocho y que me envien el correo de confirmacion"
    And la tabla de numeros cardinales en letras adjunta
      |Cardinal name|
      |cuatro|
      |mil|
      |quinientos|
      |sesenta|
      |seis|
      |millones|
      |trescientos|
      |cuarenta|
      |cinco|
      |seiscientos|
      |setenta|
      |ocho|
    Then Obtener los tokes segun la tabla adjunta
      | Token | Type |
      | Quiero  | WORD |
      | hacer  | WORD |
      | una  | NUMBER |
      | transferencia  | WORD |
      | de  | WORD |
      | cuatro | NUMBER |
      |mil | NUMBER |
      |quinientos| NUMBER |
      |sesenta| NUMBER |
      |y| CONNECTOR |
      |seis| NUMBER |
      |millones| NUMBER |
      |trescientos| NUMBER |
      |cuarenta| NUMBER |
      |y | CONNECTOR |
      |cinco| NUMBER |
      |mil | NUMBER |
      |seiscientos| NUMBER |
      |setenta| NUMBER |
      |y| CONNECTOR |
      |ocho  | NUMBER |
      | y        |   CONNECTOR     |
    |    que    |  WORD      |
    |  me    |  WORD      |
    | envien   |  WORD      |
    |     el    |   WORD     |
    |  correo    |   WORD     |
    |  de     |  WORD      |
    |  confirmacion      |   WORD     |
  Scenario: Identificar dos un valor a transferir y una calle
    Given La frase "Quisiera transferir ciento cincuenta pesos a Juan Perez que vive en la calle Rivadavia cuarenta y tres"
    And la tabla de numeros cardinales en letras adjunta
      |Cardinal name|
      |ciento|
      |cincuenta|
      |cuarenta|
      |tres|
    Then Obtener los tokes segun la tabla adjunta
      | Token | Type |
      | Quisiera  | WORD |
      | transferir  | WORD |
      | ciento  | NUMBER |
      | cincuenta  | NUMBER |
      | pesos  | WORD |
      | a  | WORD |
      | Juan  | WORD |
      | Perez  | WORD |
      | que  | WORD |
      | vive  | WORD |
      | en  | WORD |
      | la  | WORD |
      | calle  | WORD |
      | Rivadavia  | WORD |
      | cuarenta | NUMBER |
      | y | CONNECTOR |
      | tres | NUMBER |
  Scenario Outline: Si la frase es un numero cardinal menor a veinte, se escribe directamente su correspondiente valor numerico
    Given El numero cardinal "<Cardinal name>"
    Then el valor numerico correspondiente sera <Numeric value>
    Examples:
      |Cardinal name|Numeric value|
      |uno|1|
      |dos|2|
      |tres|3|
      |cuatro|4|
      |cinco|5 |
      |seis|6  |
      |siete|7 |
      |ocho|8  |
      |nueve|9 |
      |diez|10|
      |once|11|
      |doce|12|
      |trece|13|
      |catorce|14 |
      |quince|15  |
      |dieciséis|16 |
      |diecisiete|17  |
      |dieciocho|18 |
      |diecinueve|19 |
      |veinte|20 |

  Scenario Outline: Validar escenarios de numeros que sean invalidos
    Given El numero cardinal "<Cardinal name>"
    Then throw parsing exception
    Examples:
      |Cardinal name|
      |    uno uno         |
      |    uno y uno         |
      |    uno veinte         |
      |    dos noventa         |
      |    billones millones         |
      |    millones dos |
    |      cinco y cinco    |
    |       cien dos                |
    |  cien doscientos  |
    |  ciento mil  |
    |  ciento novecientos  |
    | dos ciento|

  Scenario Outline: Si la frase es un numero cardinal mayor a 20 y menor o igual a 99, se escribe directamente su correspondiente valor numerico o se compone
    de decenas mas unidades mediante el conector "y"
    Given El numero cardinal "<Cardinal name>"
    Then el valor numerico correspondiente sera <Numeric value>
    Examples:
      |Numeric value|Cardinal name|
      |21|veintiuno |
      |21|veinte y uno |
      |21|veintiuna|
      |21|veinte y una |
      |21|veintiún |
      |21|veinte y un |
      |22|veintidós|
      |22|veinte y dos |
      |23|veintitrés|
      |23|veinte y tres |
      |24|veinticuatro|
      |24|veinte y cuatro |
      |25|veinticinco|
      |25|veinte y cinco |
      |26|veintiséis|
      |26|veinte y seis |
      |27|veintisiete|
      |27|veinte y siete |
      |28|veintiocho |
      |28|veinte y ocho|
      |29|veintinueve|
      |29|veinte y nueve |
      |30|treinta    |
      |31|treinta y uno|
      |31|treinta y una|
      |31|treinta y un |
      |32|treinta y dos|
      |33|treinta y tres|
      |34|treinta y cuatro|
      |35|treinta y cinco |
      |36|treinta y seis  |
      |37|treinta y siete |
      |38|treinta y ocho  |
      |39|treinta y nueve |
      |40|cuarenta        |
      |41|cuarenta y uno  |
      |41|cuarenta y una|
      |41|cuarenta y un |
      |42|cuarenta y dos|
      |43|cuarenta y tres|
      |44|cuarenta y cuatro|
      |45|cuarenta y cinco |
      |46|cuarenta y seis  |
      |47|cuarenta y siete |
      |48|cuarenta y ocho  |
      |49|cuarenta y nueve |
      |50|cincuenta        |
      |51|cincuenta y uno  |
      |51|cincuenta y una|
      |51|cincuenta y un |
      |52|cincuenta y dos|
      |53|cincuenta y tres|
      |54|cincuenta y cuatro|
      |55|cincuenta y cinco |
      |56|cincuenta y seis  |
      |57|cincuenta y siete |
      |58|cincuenta y ocho  |
      |59|cincuenta y nueve |
      |60|sesenta           |
      |61|sesenta y uno     |
      |61|sesenta y una|
      |61|sesenta y un |
      |62|sesenta y dos|
      |63|sesenta y tres|
      |64|sesenta y cuatro|
      |65|sesenta y cinco |
      |66|sesenta y seis  |
      |67|sesenta y siete |
      |68|sesenta y ocho  |
      |69|sesenta y nueve |
      |70|setenta         |
      |71|setenta y uno   |
      |71|setenta y una|
      |71|setenta y un |
      |71| setenta y una|
      |72|setenta y dos |
      |73|setenta y tres|
      |74|setenta y cuatro|
      |75|setenta y cinco |
      |76|setenta y seis  |
      |77|setenta y siete |
      |78|setenta y ocho  |
      |79|setenta y nueve |
      |80|ochenta         |
      |81|ochenta y uno   |
      |81|ochenta y una|
      |81|ochenta y un |
      |82|ochenta y dos|
      |83|ochenta y tres|
      |84|ochenta y cuatro|
      |85|ochenta y cinco |
      |86|ochenta y seis  |
      |87|ochenta y siete |
      |88|ochenta y ocho  |
      |89|ochenta y nueve |
      |90|noventa         |
      |91|noventa y uno   |
      |91|noventa y una|
      |91|noventa y un |
      |92|noventa y dos|
      |93|noventa y tres|
      |94|noventa y cuatro|
      |95|noventa y cinco |
      |96|noventa y seis  |
      |97|noventa y siete |
      |98|noventa y ocho  |
      |99|noventa y nueve|

  Scenario Outline: Validar escenarios de numeros que sean validos
    Given El numero cardinal "<Cardinal name>"
    Then El valor numerico es "<Numeric value>"
    Examples:
      |Cardinal name|Numeric value|
      | cuatrocientos cincuenta y seis mil millones trescientos cuarenta y cinco mil seiscientos setenta y ocho|456000345678|
      | un millón dos | 1000002 |
      | un millon dos | 1000002 |
      | un Millón dos | 1000002 |
      | un MILLON dos | 1000002 |
      | un MILLÓN dos | 1000002 |
      | mil doscientos millones cuatrocientos mil quinientos |1200400500|
      | doscientos mil |200000|
      | mil cien millones |1100000000|
      | ciento cincuenta |150|
      | noventa mil doscientos |90200|
      | trescientos cuarenta y cinco mil seiscientos setenta y ocho | 345678 |

  Scenario Outline: Convertir una frase con numeros en texto con numeros en valores numericos
    Given La frase en texto "<Given phrase>"
    Then La frase en numeros "<Expected phrase>"
    Examples:
      |Given phrase|Expected phrase|
      | Quisiera transferir ciento cincuenta pesos a Juan Perez que vive en la calle Rivadavia cuarenta y tres|Quisiera transferir 150 pesos a Juan Perez que vive en la calle Rivadavia 43|
      | Quisiera transferir diez mil doscientos treinta dólares a Dario Roback que vive en la calle de las Verbenitas ciento treinta y ocho A y Stefano Maderno|Quisiera transferir 10230 dólares a Dario Roback que vive en la calle de las Verbenitas 138 A y Stefano Maderno|

