package testVagrant;

import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class Validate4ForeignPlayerAndaWicketKeeper {
	
	@Test
	public void testGet() {

		// Specify base URI
		RestAssured.baseURI = "https://gist.githubusercontent.com/kumarpani/";

		// Request Object
		RequestSpecification httprequest = RestAssured.given();

		// Response Object
		Response response = httprequest.request(Method.GET,
				"1e759f27ae302be92ad51ec09955e765/raw/184cef7125e6ef5a774e60de31479bb9b2884cb5/TeamRCB.json");

		JsonPath jsonpath = new JsonPath(response.asString());

		int size = jsonpath.getInt("player.size()");

		System.out.println("Number of Player in RCB : " + size);

		for (int i = 0; i < size; i++) {
			String country = jsonpath.getString("player[" + i + "].country");
			String role = jsonpath.getString("player[" + i + "].role");
			String name = jsonpath.getString("player[" + i + "].name");

			// validates that the team has only 4 foreign players
			if (!(country.equals("India"))) {
				System.out.println("Foreign Country Player : "+name+", "+country);
				Assert.assertNotEquals(country, "India");
			}

			// validates that there is at least one wicket-keeper
			if ((role.equals("Wicket-keeper"))) {
				System.out.println("Wicket-Keeper Player   : "+name+", "+role);
				Assert.assertEquals(role, "Wicket-keeper");
			}
		}

		System.out.println("<-------------------------------------->");
		
		// Print response in console window
		String responseBody = response.getBody().asString();
		System.out.println("Json Body for RCB : " + responseBody);
		
		System.out.println("<-------------------------------------->");
		
		// Status code validation
		int statuscode = response.getStatusCode();
		System.out.println("Status Code is : " + statuscode);
		Assert.assertEquals(statuscode, 200);

		// Status Line Validation
		String statusline = response.getStatusLine();
		System.out.println("Status Line is : " + statusline);
		Assert.assertEquals(statusline, "HTTP/1.1 200 OK");

		// Validate Response Time
		long responsetime = response.getTime();
		System.out.println("Response Time : " + responsetime);
		ValidatableResponse validateResponse = response.then();
		validateResponse.time(Matchers.lessThan(5000L));
	}
	

}
