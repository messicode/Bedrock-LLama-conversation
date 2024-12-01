import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.http.scaladsl.model._
import org.scalatest.{Matchers, WordSpec}

class InvalidQueryLambdaTest extends WordSpec with Matchers with ScalatestRouteTest {

  "Lambda Function" should {
    "return an error message when the query is empty" in {
      // Test with an empty query
      val emptyQuery = ""
      val requestEntity = HttpEntity(ContentTypes.`text/plain(UTF-8)`, emptyQuery)

      // Send a request to the HTTP server
      Post("/chat", requestEntity) ~> HttpServer.route ~> check {
        status shouldBe StatusCodes.BadRequest
        responseAs[String] should include("Query cannot be empty.")
      }
    }
  }
}
