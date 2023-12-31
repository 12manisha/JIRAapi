package files;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import  static io.restassured.RestAssured.*;

import java.io.File;

public class JiraTest {
	
	public static void main(String args[])
	{
		RestAssured.baseURI="http://localhost:8080";
		
		SessionFilter session = new SessionFilter();
		
		//LOGIN
		
		String response = given().log().all().header("Content-Type","application/json").body("{ \"username\": \"manishajoshi\", \"password\": \"12manisha\" }")
		.filter(session)
		.when().post("/rest/auth/1/session")
		.then().extract().response().asString();
		System.out.println(response);
		
		//CREATE ISSUE
		
		given().pathParam("id","10002").header("Content-Type","application/json").body("{\r\n"
				+ "    \"body\": \"End Year Comment\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}")
		.filter(session)
		.when().post("/rest/api/2/issue/{id}/comment")
		.then().assertThat().statusCode(201);
		
		//ADD ATTACHMENT
		
		given().header("X-Atlassian-Token"," no-check").filter(session).pathParam("id", "10002")
		.header("Content-Type","multipart/form-data")
		.multiPart("file", new File("jira.txt")).when().post("/rest/api/2/issue/{id}/attachments")
		.then().log().all().assertThat().statusCode(200);
		
		//GET ISSUE
		
		String issueDetails = given().filter(session).pathParam("id", "10002")
		.queryParam("fields", "comment")
		.log().all().when().get("/rest/api/2/issue/{id}").then()
		.log().all().extract().response().asString();
		System.out.println(issueDetails);
		
		JsonPath js = new JsonPath(issueDetails);
		int cc = js.getInt("fields.comment.comments.size()");
		for(int i=0;i<cc;i++)
		{
			System.out.println(js.getInt("fields.comment.comments["+i+"].id"));
		}
		
	}
}
