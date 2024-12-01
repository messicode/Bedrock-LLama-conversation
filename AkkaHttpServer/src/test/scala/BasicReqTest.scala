
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.http.scaladsl.model._
import org.scalatest.{Matchers, WordSpec}

class BasicReqTest extends WordSpec with Matchers with ScalatestRouteTest {

  "Akka HTTP Server" should {
    "process a POST request and return a valid response from Lambda" in {
      // Simulate the request payload
      val query = "How do cats express love?"
      val requestEntity = HttpEntity(ContentTypes.`text/plain(UTF-8)`, query)

      // Test the POST route
      Post("/chat", requestEntity) ~> HttpServer.route ~> check {
        status shouldBe StatusCodes.OK
        responseAs[String] should include("Response to query: How do cats express love?")
      }
    }
  }
}
