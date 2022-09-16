package test;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojo.Berry;
import pojo.BerryDetail;

import java.io.File;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;


public class PokemonApi {

    String baseURI = "https://pokeapi.co/api/v2/";
    String newURL;

    @Test()
    public void berryFirmness() {

        RestAssured.baseURI = baseURI;

        Response resp = given()
//                .log().all()
                .header("Content-Type", "application/json")
                .expect().defaultParser(Parser.JSON)
                .when().get("berry-firmness/")
                .then()
                .log().all()
                .extract().response();

        ResponseBody respBody = resp.getBody();
        Assert.assertEquals(resp.statusCode(), 200, "Verify status code. berry-firmness/ API ");

        Berry berry = respBody.as(Berry.class);


        System.out.println("-------------------------- RESPONSE WITH POJO-------------------------------------");
//        System.out.println("count value: " + berry.getCount());

        List<BerryDetail> listBerry = berry.getResults();
        newURL = "";

        listBerry.stream().filter(item -> item.getUrl().contains("/berry-firmness/4/")).forEach(item -> {
            newURL = item.getUrl();
            System.out.println(item.getName());
            System.out.println(item.getUrl());
        });



    }

    @Test(dependsOnMethods = "berryFirmness")
    public void schemaValidation(){

        System.out.println("-------------------------- SCHEMA VALIDATION -------------------------------------");

        File schema = new File(System.getProperty("user.dir")+"\\src\\main\\java\\files\\schemaBerry.json");

        given()
                .log().all()
                .header("Content-Type", "application/json")
                .when().get(newURL)
                .then()
                .log().all()
                .assertThat().statusCode(200)
                .body(matchesJsonSchema(schema));
//                .extract().response();

    }

}
