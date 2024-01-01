package Serialization;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class SerializationTest {
	public static void main(String args[])
	{
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
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
		
		String res =  given().log().all().queryParam("key", "qaclick123").body(a)
		.when().post("/maps/api/place/add/json")
		.then().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println(res);
	}
}
