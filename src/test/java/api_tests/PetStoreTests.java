package api_tests;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import java.io.File;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utilities.BrowserUtils;

public class PetStoreTests {
	
	BrowserUtils utils = new BrowserUtils();
	//String baseUrl = "https://petstore.swagger.io/v2";
	Response response;
	int petId;
	
	@BeforeTest
	public void setup() {
		baseURI = "https://petstore.swagger.io/v2";
	}
	
	
	@Test
	public void uploadApet() {
//			Testcase 1: 
//			Perform a POST request to upload a PET
//			Verify the status code is 200 
//			Verify the content type is application.json
//			Verify the pet name and status match your request body input
		
		petId = 101 + utils.randomNumber();
		
		String endpoint = "/pet";
		String body = "{\r\n"
				+ "  \"id\": 1001,\r\n"
				+ "  \"category\": {\r\n"
				+ "    \"id\": 0,\r\n"
				+ "    \"name\": \"Dog\"\r\n"
				+ "  },\r\n"
				+ "  \"name\": \"PewPewDog\",\r\n"
				+ "  \"photoUrls\": [\r\n"
				+ "    \"string\"\r\n"
				+ "  ],\r\n"
				+ "  \"tags\": [\r\n"
				+ "    {\r\n"
				+ "      \"id\": 0,\r\n"
				+ "      \"name\": \"Dog_1001\"\r\n"
				+ "    }\r\n"
				+ "  ],\r\n"
				+ "  \"status\": \"sold\"\r\n"
				+ "}";
		response = given()
		.accept("application/json")
		.contentType(ContentType.JSON)
		.body(body)
		.when()
		.post(endpoint)
		.thenReturn();
		
		response.prettyPrint();
		
		response
		.then().assertThat().statusCode(200)
		.and().assertThat().contentType("application/json")
		.and().assertThat().body("name", Matchers.equalTo("PewPewDog"))
		.and().assertThat().body("status", Matchers.equalTo("sold"));
		
		
		
	}
	
	
	@Test (dependsOnMethods = "uploadApet")
	public void updateApet() {
//			Testcase 2: 
//			Perform a PUT request to update a pet with existing ID 
//			Verify the status code is 200 and content type is application.json
//			Verify the response body matches your request body
		String endpoint = "/pet";
		String body = "{\r\n"
				+ "  \"id\": 1001,\r\n"
				+ "  \"category\": {\r\n"
				+ "    \"id\": 0,\r\n"
				+ "    \"name\": \"Dog\"\r\n"
				+ "  },\r\n"
				+ "  \"name\": \"PewDogPew\",\r\n"
				+ "  \"photoUrls\": [\r\n"
				+ "    \"string\"\r\n"
				+ "  ],\r\n"
				+ "  \"tags\": [\r\n"
				+ "    {\r\n"
				+ "      \"id\": 0,\r\n"
				+ "      \"name\": \"Dog_1001\"\r\n"
				+ "    }\r\n"
				+ "  ],\r\n"
				+ "  \"status\": \"available\"\r\n"
				+ "}";
		response = given()
		.accept("application/json")
		.contentType(ContentType.JSON)
		.body(body)
		.when()
		.put(endpoint)
		.thenReturn();
		
		response.prettyPrint();
		
		response
		.then().assertThat().statusCode(200)
		.and().assertThat().contentType("application/json")
		.and().assertThat().body("name", Matchers.equalTo("PewDogPew"))
		.and().assertThat().body("status", Matchers.equalTo("available"));
		
		
		
	}
	
	
	
	
	@Test
	public void createApet_Post() {

		// Go to https://petstore.swagger.io/
		// 1.Then come to eclipse, create a testNg test called createApet() and
		// implement a pet creation logic.
		// Hint, when you use POST request, you need to provide body() to request header.
		// create a string variable for the body and provide that to body() method.
		// Validate that statuscode is 200,
		// Content type is "applicaion/json"
		
		petId = 101 + utils.randomNumber();
		
		String postEndPoint = "/pet";
		String requestBody = "{\r\n"
				+ "  \"id\": "+petId+",\r\n"
				+ "  \"category\": {\r\n"
				+ "    \"id\": 21,\r\n"
				+ "    \"name\": \"Dog\"\r\n"
				+ "  },\r\n"
				+ "  \"name\": \"PewPewDoggie\",\r\n"
				+ "  \"photoUrls\": [\r\n"
				+ "    \"string\"\r\n"
				+ "  ],\r\n"
				+ "  \"tags\": [\r\n"
				+ "    {\r\n"
				+ "      \"id\": 21999,\r\n"
				+ "      \"name\": \"P_21999\"\r\n"
				+ "    }\r\n"
				+ "  ],\r\n"
				+ "  \"status\": \"available\"\r\n"
				+ "}";
		
		response = given()
		.contentType("application/json")
		.accept(ContentType.JSON)
		.body(requestBody)
		.when()
		.post(postEndPoint)
		.thenReturn();
		
		response.prettyPrint();
		
		assertEquals(response.getStatusCode(), 200);
		assertEquals(response.getContentType(), "application/json");
		assertEquals(response.jsonPath().getInt("id"), petId);
		
		
		System.out.println(response.path("name").toString());
		System.out.println(response.path("category.name").toString());
		
		System.out.println(response.jsonPath().getInt("id"));
		System.out.println(response.jsonPath().getString("status"));
		System.out.println(response.jsonPath().getString("tags[0].name"));	
		
	}
	
	@Test (dependsOnMethods = "createApet_Post")
	public void findaPetById() {
		String endpoint = "/pet/" + petId;
		
		response = given()
		.contentType("application/json")
		.accept(ContentType.JSON)
		.get(endpoint)
		.thenReturn();

		assertEquals(response.getStatusCode(), 200);
		assertEquals(response.getContentType(), "application/json");
		assertEquals(response.jsonPath().getInt("id"), petId);
		assertEquals(response.jsonPath().getInt("category.id"), 21);
		assertEquals(response.jsonPath().getString("category.name"), "Dog");
		assertEquals(response.jsonPath().getString("name"), "PewPewDoggie");
		assertEquals(response.jsonPath().getString("status"), "available");
		
	}
	
	@Test (dependsOnMethods = "createApet_Post")
	public void findaPetById_chainValidation() {
		String endpoint = "/pet/" + petId;
		
		response = given()
		.contentType("application/json")
		.accept(ContentType.JSON)
		.get(endpoint)
		.thenReturn();
		
		response.prettyPrint();
		
		response
		.then().assertThat().statusCode(200)
		.and().assertThat().contentType("application/json")
		.and().assertThat().body("id", Matchers.equalTo(petId))
		.and().assertThat().body("category.id", Matchers.equalTo(21))
		.and().assertThat().body("category.name", Matchers.equalTo("Dog"))
		.and().assertThat().body("name", Matchers.equalTo("PewPewDoggie"))
		.and().assertThat().body("tags[0].name", Matchers.equalTo("P_21999"))
		.and().assertThat().body("status", Matchers.equalTo("available"));
		
		
	}
	
	
	@Test (dependsOnMethods = "createApet_Post")
	public void deleteThePet() {
		String endpoint = "/pet/" + petId;
		
		response = given()
				.accept(ContentType.JSON)
				.contentType("application/json")
				.delete(endpoint)
				.thenReturn();
		
		response.prettyPrint();
		
		assertEquals(response.getStatusCode(), 200);
		assertEquals(response.getContentType(), "application/json");
		assertEquals(response.jsonPath().getString("message"), petId + "");
		
	}
	
	@Test
	public void petNotFound() {
		
		String endpoint = "/pet/" + 505;
		response = given()
		.accept(ContentType.JSON)
		.contentType("application/json")
		.get(endpoint)
		.thenReturn();
		
		assertEquals(response.getStatusCode(), 404);
		assertEquals(response.getContentType(), "application/json");
		
		response.prettyPrint();
		
		assertEquals(response.jsonPath().getString("message"), "Pet not found");
		
	}
	
	
	
	
	@Test
	public void createApet_with_json_file() {

		// Go to https://petstore.swagger.io/
		// 1.Then come to eclipse, create a testNg test called createApet() and
		// implement a pet creation logic.
		// Hint, when you use POST request, you need to provide body() to request header.
		// create a string variable for the body and provide that to body() method.
		// Validate that statuscode is 200,
		// Content type is "applicaion/json"
		
		
		String endpoint = "/pet";
		File requestBody = new File("./src/test/resources/json_files/createApet.json");
		
		response = given()
		.contentType("application/json")
		.accept(ContentType.JSON)
		.body(requestBody)
		.when()
		.post(endpoint)
		.thenReturn();
		
		petId = response.jsonPath().getInt("id");
		
		response.prettyPrint();
		
		assertEquals(response.getStatusCode(), 200);
		assertEquals(response.getContentType(), "application/json");
		assertEquals(response.jsonPath().getInt("id"), petId);
		
		
		System.out.println(response.path("name").toString()); 
		System.out.println(response.path("category.name").toString());
		
		System.out.println(response.jsonPath().getInt("id"));
		System.out.println(response.jsonPath().getString("status"));
		System.out.println(response.jsonPath().getString("tags[0].name"));	
		
	}
	
	@Test (dependsOnMethods = "createApet_Post")
	public void updatePet_Put() {


		String postEndPoint = "/pet";
		String requestBody = "{\r\n"
				+ "  \"id\": "+petId+",\r\n"
				+ "  \"category\": {\r\n"
				+ "    \"id\": 21,\r\n"
				+ "    \"name\": \"Dog\"\r\n"
				+ "  },\r\n"
				+ "  \"name\": \"PewPew\",\r\n"
				+ "  \"photoUrls\": [\r\n"
				+ "    \"string\"\r\n"
				+ "  ],\r\n"
				+ "  \"tags\": [\r\n"
				+ "    {\r\n"
				+ "      \"id\": 21999,\r\n"
				+ "      \"name\": \"P_21999\"\r\n"
				+ "    }\r\n"
				+ "  ],\r\n"
				+ "  \"status\": \"pending\"\r\n"
				+ "}";
		
		response = given()
		.contentType("application/json")
		.accept(ContentType.JSON)
		.body(requestBody)
		.when()
		.put(postEndPoint)
		.thenReturn();
		
		response.prettyPrint();
		
		assertEquals(response.getStatusCode(), 200);
		assertEquals(response.getContentType(), "application/json");
		assertEquals(response.jsonPath().getInt("id"), petId);
		assertEquals(response.jsonPath().getString("name"), "PewPew");
		
		System.out.println(response.path("name").toString());
		System.out.println(response.path("category.name").toString());
		
		System.out.println(response.jsonPath().getInt("id"));
		System.out.println(response.jsonPath().getString("status"));
		System.out.println(response.jsonPath().getString("tags[0].name"));	
		
	}
	
	
	
	
	
	
	@Test
	public void findPetsByStatus() {
		// The test is directly calling to the end point and validating just two items
		// statuscode and content type
		
		//     variable
		String endpoint = "/pet/findByStatus";
		given().contentType("application/json")
		.accept(ContentType.JSON)
		.when().get(endpoint + "?status=sold")
		.then().statusCode(200)
		.and().contentType("application/json")
		.log().all();
		
		
	}
	
	@Test
	public void findPetsByStatus_providingQueryParameter() {
		// The test is directly calling to the end point with query parameter as a header
		// And getting the response into a Response object,
		// Then validating statuscode and content type
		
		//     variable
		String endpoint = "/pet/findByStatus";
		response = given().contentType("application/json")
		.accept(ContentType.JSON)
		.queryParam("statuc", "sold")
		.when().get(endpoint)
		.thenReturn();
		
		response.prettyPrint();
		
		assertEquals(response.getStatusCode(),200);
		assertEquals(response.getContentType(),"application/json");
		
	}

	

}
