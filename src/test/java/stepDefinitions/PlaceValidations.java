package stepDefinitions;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.io.IOException;

import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

public class PlaceValidations extends Utils{
	
	RequestSpecification res;
	ResponseSpecification resspec;
	Response response;
	TestDataBuild data =  new TestDataBuild();
	static String place_id;
	
	@Given("Add Place Payload with (.+) {string} {string}")
	public void add_Place_Payload_with(String name, String language, String address) throws IOException {
		//ResponseSpecBuilder() is used to write common code
		res = given().spec(requestSpecification()).body(data.addPlacePayLoad(name, language, address));
	}
	

	@When("use calls {string} with {string} http request")
	public void use_calls_with_http_request(String resource, String httpMethod) {
		
		//constructor will be called with the value of resource which you pass
		APIResources resourceAPI = APIResources.valueOf(resource);
		
		resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		if(httpMethod.equals("POST"))
			response  = res.when().post(resourceAPI.getResource());
		else if(httpMethod.equalsIgnoreCase("GET"))
			response  = res.when().post(resourceAPI.getResource());
	}

	@Then("the API call is success with status code {int}")
	public void the_API_call_is_success_with_status_code(Integer int1) {
	   assertEquals(response.getStatusCode(), 200);
	}

	@Then("{string} in response body is {string}")
	public void in_response_body_is(String keyValue, String expectedValue) {
	    assertEquals(getJsonPath(response, keyValue), expectedValue);
	}
	
	@Then("verify place_Id created maps to {string} using {string}")
	public void verify_place_Id_created_maps_to_using(String expectedName, String resource) throws IOException {
		getJsonPath(response,"place_id");
		res = given().spec(requestSpecification()).queryParam("place_id",place_id);
		use_calls_with_http_request(resource, "GET");
		String actualName = getJsonPath(response, "name");
		assertEquals(actualName, expectedName);
		
	}

	@Given("DeletePlace Payload")
	public void deleteplace_Payload() throws IOException {
	   res  = given().spec(requestSpecification()).body(data.deletePlacePayload(place_id));
	}
}
