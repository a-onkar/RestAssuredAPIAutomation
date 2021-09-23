import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;

public class TC_01 {

	@Test(enabled=false)
	void createUser() {
		RestAssured.baseURI = "http://dummy.restapiexample.com/api/v1";
		RequestSpecification httpRequest = RestAssured.given();

		//Request paylaod sending along with post request
		JSONObject requestParams = new JSONObject();
		requestParams.put("name", "API Automation"); // Cast
		requestParams.put("salary", "9999999");
		requestParams.put("age", "10");

		httpRequest.header("Content-Type","application/json");
		httpRequest.body(requestParams.toJSONString());

		//Response object
		Response response = httpRequest.request(Method.POST,"/create");

		//Printing the response
		String responseBody = response.getBody().asString();
		System.out.println("Response Body is:" +responseBody);

		//validations

		int statusCode = response.getStatusCode();
		System.out.println("Status code is: "+statusCode);
		Assert.assertEquals(statusCode, 200);
		String jsonString =response.asString();
		System.out.println(jsonString);
		Assert.assertEquals(jsonString.contains("Successfully! Record has been added."), true);

		//		Assert.assertEquals(statusCode, 201); // This line will fail the test, as it returns 200. creates temp records & which when requested gives Null

	}

	@Test(enabled=true)
	void getUser() {
		RestAssured.baseURI = "http://dummy.restapiexample.com/api/v1";
		RequestSpecification httpRequest = RestAssured.given();	

		// Add a header stating the Request body is a JSON
		httpRequest.header("Content-Type", "application/json");	

		// Get request to list employee with id
		Response response = httpRequest.request(Method.GET,"/employee/11");

		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 200);

		String jsonString = response.asString();
		System.out.println(jsonString);
		Assert.assertEquals(jsonString.contains("Successfully! Record has been fetched."), true);
		Assert.assertEquals(jsonString.contains("Jena Gaines"), true);

		// Fails to get the JsonPath object instance from the Response interface. Seems its the API restrictions that returns Null
//		JsonPath jsonPathEvaluator = response.jsonPath();
//		String emp_name = jsonPathEvaluator.get("employee_name");
//		System.out.println(emp_name);
//		Assert.assertEquals(emp_name , "Jena Gaines", "Correct employees retrieved");
	}

	@Test(enabled=false)
	void updateUser() {
		RestAssured.baseURI = "http://dummy.restapiexample.com/api/v1";

		RequestSpecification httpRequest = RestAssured.given();	

		//Request paylaod sending along with post request
		JSONObject requestParams = new JSONObject();
		requestParams.put("name", "Poppulo"); // Cast
		requestParams.put("salary", "9999999");
		requestParams.put("age", "80");

		httpRequest.header("Content-Type","application/json");

		Response response = httpRequest.request(Method.PUT,"/update/11");

		//		httpRequest.body(requestParams.toJSONString());
		//		Response response = httpRequest.put("/update/8720");

		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 200);

		String jsonString =response.asString();
		System.out.println(jsonString);
		Assert.assertEquals(jsonString.contains("Successfully! Record has been updated."), true);
	}
	
	@Test(enabled=false)
	void deleteUser() {
		RestAssured.baseURI = "http://dummy.restapiexample.com/api/v1";

		RequestSpecification httpRequest = RestAssured.given();	

		// Add a header stating the Request body is a JSON
		httpRequest.header("Content-Type", "application/json");	

		// Delete the request and check the response
		Response response = httpRequest.request(Method.DELETE,"/delete/10");

		int statusCode = response.getStatusCode();
		System.out.println(response.asString());
		Assert.assertEquals(statusCode, 200);

		String jsonString =response.asString();
		Assert.assertEquals(jsonString.contains("Successfully! Record has been deleted"), true);

	}

}
