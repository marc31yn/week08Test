package test;

import files.Payload;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static io.restassured.RestAssured.given;

public class ApiUser {

    String baseURI = "https://reqres.in/";
    RequestSpecification specHeader;
    JsonPath jsPath;
    SoftAssert sa = new SoftAssert();

    @AfterClass
    public void executeSoftAssert() {
        sa.assertAll("all soft Assert");
    }

    @BeforeClass
    public void setSpecBuilder() {
        specHeader = new RequestSpecBuilder()
                .setBaseUri(baseURI)
                .setContentType(ContentType.JSON)
                .build();
    }

    @Test(priority = 1)
    public void getUsers() {

        Response response = given()
//                .log().all()
                .spec(specHeader)
                .queryParam("page", "2")
                .when().get("api/users")
                .then()
//                .log().all()
                .extract().response();

        System.out.println("---------------------------------------------------------------");
        System.out.println("** The status code: " + response.statusCode());
        Assert.assertEquals(response.statusCode(), 200, "Verify the status code");

        String expectedServer = "Cloudflare";
        String respServerName = response.getHeaders().get("Server").getValue();
        System.out.println("** Response Header :: The server: " + respServerName);
        Assert.assertTrue(respServerName.equalsIgnoreCase(expectedServer), "Verify the Server name");
//        sa.assertEquals(respServerName,expectedServer,"Verify the Server name. Method getUsers");

        // Retrieve the body of the Response
        ResponseBody body = response.getBody();
        String bodyString = body.asString();
        System.out.println("Response Body is: " + bodyString);

        // For parsing jsPathON
        jsPath = new JsonPath(bodyString);
//        System.out.println(jsPath.getInt("page"));
//        System.out.println(jsPath.getInt("per_page"));
//        System.out.println(jsPath.getInt("total"));
//        System.out.println(jsPath.getInt("total_pages"));
//        System.out.println(jsPath.getString("data"));
//        System.out.println(jsPath.getString("support"));
//        String data = jsPath.getString("data");
    }

    @Test(dependsOnMethods = "getUsers")
    public void getUser() {
        int userID = 0;
        String expectedValue = "george.edwards@reqres.in";

        for (int i = 0; i < jsPath.getInt("data.size()"); i++) {
//            System.out.println("The id: " + jsPath.get("data[" + i + "].id"));
//            System.out.println("The email: " + jsPath.get("data[" + i + "].email"));
//            System.out.println("The first_name: " + jsPath.get("data[" + i + "].first_name"));
//            System.out.println("The last_name: " + jsPath.get("data[" + i + "].last_name"));
//            System.out.println("The avatar: " + jsPath.get("data[" + i + "].avatar"));

            if (jsPath.get("data[" + i + "].email").toString().equalsIgnoreCase(expectedValue)) {
                userID = jsPath.get("data[" + i + "].id");
            }

        }

        System.out.println("User id: " + userID);

        given().log().all()
                .spec(specHeader)
                .pathParam("user_id", userID)
                .when().get("api/users/{user_id}")
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response();
    }

    @Test(dataProvider = "userData", priority = 3, enabled = true)
    public void createUser(String nameUser, String jobUser) {

        Response response = given().log().all()
                .spec(specHeader)
                .body(Payload.addUser(nameUser, jobUser))
                .when().post("api/users")
                .then().log().all()
                .assertThat().statusCode(201)
                .extract().response();
    }

    @DataProvider(name = "userData")
    public Object[][] getData() {
        return new Object[][]{
                {"Morpheus", "leader"},
                {"Rafael", "Architect"},
                {"Abigail", "Doctor"}
        };
    }
}
