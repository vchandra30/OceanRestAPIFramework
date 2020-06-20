# download natural plugin for Cucumber Gherkin language
Feature: Validating Place APIs

	@AddPlace @Regression
	Scenario Outline: Verify if place is being successfully added using AddPlaceAPI
		Given Add Place Payload with "<name>" "<language>" "<address>"
		When use calls "AddPlaceAPI" with "Post" http request
		Then the API call is success with status code 200
		And "status" in response body is "OK"
		And "scope" in response body is "APP"
		And verify place_Id created maps to "<name>" using "getPlaceAPI"
		
	Examples:
	|name	|language|address			|
	|AAHouse|English|World cross center	|
	|BBHouse|Spanish|Sea Cross Center	|
	
	@DeletePlace @Regression
    Scenario: Verify if Delete Place functionality is working
    	Given DeletePlace Payload
    	When use calls "AddPlaceAPI" with "Post" http request
		Then the API call is success with status code 200
		And "status" in response body is "OK"