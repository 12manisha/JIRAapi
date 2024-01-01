package Serialization;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class SerializationTest {
	public static void main(String args[])
	{
//		RestAssured.baseURI = "https://rahulshettyacademy.com";
		RequestSpecification response = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick")
				.setContentType(ContentType.JSON).build();
		

		
		AddPlace a = new AddPlace();
		a.setAccuracy(50);
		a.setAddress("Kalika Coloney , Phase 4");
		a.setLanguage("EN");
		
		Location l = new Location();
		l.setLat(-34.94);
		l.setLng(38.83);
		a.setLocation(l);
		
		a.setName("Manisha Kunj");
		a.setWebsite("https://rahulshettyacademy.com");
		
		List<String> myList = new ArrayList<String>();
		myList.add("Badminton");
		myList.add("Cricket");
		a.setTypes(myList);
		
		ResponseSpecification resspec =  new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		RequestSpecification res = given().spec(response).body(a);
		
		String responseString =  res.when().post("/maps/api/place/add/json").then().spec(resspec).extract().response().asString();
		
		System.out.println(responseString);
	}
}
