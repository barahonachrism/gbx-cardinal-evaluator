package com.globantx.cardinalevaluator.unit;

import com.globantx.cardinalevaluator.*;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

public class CardinalNumberTest {
    private String phrase;
    private List<CardinalNumber> cardinalNumbers;

    private String cardinalValue;

    @DataTableType
    public Token tokenType(Map<String, String> entry) {
        return Token.builder().value(entry.get("Token")).type(TokenType.valueOf(entry.get("Type"))).build();
    }

    @DataTableType
    public CardinalNumber cardinalNumberType(Map<String, String> entry) {
        return CardinalNumber.builder().singularCardinalName(entry.get("Cardinal name")).build();
    }

    @DataTableType
    public Integer integerNumberType(Map<String, String> entry) {
        return Integer.valueOf(entry.get("Numeric value"));
    }

    @Given("La frase {string}")
    public void la_frase(String text) {
        phrase = text;
    }

    @Given("la tabla de numeros cardinales en letras adjunta")
    public void la_tabla_de_numeros_cardinales_en_letras_adjunta(List<CardinalNumber> cardinalNumbers) {
        this.cardinalNumbers = cardinalNumbers;
    }

    @Then("Obtener los tokes segun la tabla adjunta")
    public void obtener_los_tokes_segun_la_tabla_adjunta(List<Token> tokenList) throws URISyntaxException, IOException {
        Lexer lexer = new Lexer(phrase);
        Assertions.assertEquals(tokenList,lexer.readAllTokens());
    }

    @Given("El numero cardinal {string}")
    public void el_numero_cardinal(String cardinalValue) {
        this.cardinalValue = cardinalValue;
    }
    @Then("el valor numerico correspondiente sera {int}")
    public void el_valor_numerico_correspondiente_sera(Integer numericValue) throws URISyntaxException, IOException {
        Parser parser = new Parser();
        Assertions.assertEquals(String.valueOf(numericValue),parser.parsePhrase(cardinalValue));
    }

    @Then("throw parsing exception")
    public void throw_parsing_exception() {
        Parser parser = new Parser();
        Assertions.assertThrows(ParseException.class,()->{parser.parsePhrase(cardinalValue);});
    }

    @Then("El valor numerico es {string}")
    public void el_valor_numerico_es(String expectedValue) throws URISyntaxException, IOException {
        Parser parser = new Parser();
        Assertions.assertEquals(expectedValue,parser.parsePhrase(cardinalValue));
    }

}
