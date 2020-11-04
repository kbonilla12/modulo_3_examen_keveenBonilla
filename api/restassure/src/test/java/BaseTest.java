import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseTest
{

    public static RequestSpecification request;

    @BeforeClass
    @Parameters("baseUrl")
    public void SetupRestAssured(@Optional String baseUrl){

        RestAssured.baseURI = baseUrl;
        System.out.println(String.format("Running on %s environment", baseUrl));
    }

    //this is to clean the request object
    @BeforeMethod
    public void Setup(){
        request = RestAssured.given();
    }


}
