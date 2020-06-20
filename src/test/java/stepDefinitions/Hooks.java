package stepDefinitions;

import java.io.IOException;

import io.cucumber.java.Before;




public class Hooks {
	
	@Before("@DeletePlace")
	public void beforeScenario() throws IOException {
		PlaceValidations placeValidation = new PlaceValidations();
		if(PlaceValidations.place_id == null) {
			placeValidation.add_Place_Payload_with("vivek", "Hindi", "India");
			placeValidation.use_calls_with_http_request("AddPlaceAPI", "POST");
			placeValidation.verify_place_Id_created_maps_to_using("vivek", "getPlaceAPI");
		}
		
	}

}
