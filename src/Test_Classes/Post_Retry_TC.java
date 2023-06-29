package Test_Classes;
import java.io.IOException;
import java.time.LocalDateTime;

import org.testng.Assert;
import org.testng.annotations.Test;
import Common_API_Methods.Post_API_Methods;
import Post_Req_Repository.Post_Req_Repository;
import io.restassured.path.json.JsonPath;
public class Post_Retry_TC {
 @Test(priority =2)
	public static void extractor() throws IOException {
	 System.out.println("extractor validator method call");
		for (int i = 0; i < 5; i++) {

			int statusCode = Post_API_Methods.ResponseStatusCode(Post_Req_Repository.BaseURI(),
					Post_Req_Repository.Post_Resource(), Post_Req_Repository.Post_Req_TC1());
			if (statusCode == 201) {
				System.out.println(statusCode);

				String ResponseBody = Post_API_Methods.ResponseBody(Post_Req_Repository.BaseURI(),
						Post_Req_Repository.Post_Resource(), Post_Req_Repository.Post_Req_TC1());
				System.out.println(ResponseBody);
				String RequestBody = Post_Req_Repository.Post_Req_TC1();
				validator(RequestBody, ResponseBody);
				break;
			} else {
				System.out.println("Invalid Status Code");
			}
		}
	}
  @Test(priority =1)
	public static void validator(String RequestBody, String ResponseBody) {
	  System.out.println("validator method call");

		JsonPath JspRequest = new JsonPath(RequestBody);
		String Req_name = JspRequest.getString("name");
		String Req_job = JspRequest.getString("job");
		LocalDateTime current = LocalDateTime.now();
		String currenttime = current.toString().substring(0,11);

		JsonPath JspResponse = new JsonPath(ResponseBody);
		String Res_name = JspResponse.getString("name");
		String Res_job = JspResponse.getString("job");
		String Res_createdAt = JspResponse.getString("createdAt");
		Res_createdAt = Res_createdAt.substring(0, 11);

		Assert.assertEquals(Res_name, Req_name);
		Assert.assertEquals(Res_job, Req_job);
		Assert.assertEquals(Res_createdAt, currenttime);
	}

}