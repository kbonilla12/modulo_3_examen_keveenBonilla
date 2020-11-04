import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import pojo.User;

public class ApiValidation extends BaseTest
{
    private static String RESOURCE = "/v1/examen";


    @Test
    public void getValidarStatus(){
        request
            .given()
            .contentType("application/json")
        .when()
            .get(String.format("%s/status", RESOURCE))
        .then()
            .statusCode(200)
            .body("status",equalTo("Listos para el examen"));
    }

    @Test
    public void putNameNoBody(){
        request
            .given()
            .contentType("application/json")
        .when()
            .put(String.format("%s/updateName", RESOURCE))
        .then()
            .statusCode(406)
            .body("message",equalTo("Name was not provided"));
    }

    @Test
    public void getRandomNameNoAuth(){
        request
            .given()
            .contentType("application/json")
        .when()
            .get(String.format("%s/randomName", RESOURCE))
        .then()
            .statusCode(401)
            .body("message",equalTo("Please login first"));
    }

    @Test
    public void getRandomName_And_SameName() {

        User _User = new User("testuser", "testpass");

        String nombre_luchador =
             given()
            .auth()
            .basic(_User.email, _User.password)
        .when()
            .get(String.format("%s/randomName", RESOURCE))
        .then()
            .statusCode(200)
            .extract()
            .response().path("name");

        //System.out.println(nombre_luchador);
        String mismo_luchador = String.format("{ \"name\":\"%s\"}", nombre_luchador);

        request
            .given()
            .body(mismo_luchador)
        .when()
            .post(String.format("%s/sameName", RESOURCE))
        .then()
            .statusCode(200)
            .body("name", equalTo(nombre_luchador));

    }


}
