import org.scalatest.{Matchers, WordSpec}
import scala.concurrent.Future
import akka.actor.ActorSystem
import akka.http.scaladsl.model._
import akka.stream.Materializer
import akka.http.scaladsl.testkit.ScalatestRouteTest

class LambdaInvocationMockTest extends WordSpec with Matchers with ScalatestRouteTest {

  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: Materializer = Materializer(system)

  "Lambda Invocation" should {
    "return a valid response from the Lambda mock function" in {
      val mockQuery = "What is the meaning of life?"

      // Mock response from Lambda
      val mockLambdaResponse: Future[String] = Future.successful("The meaning of life is 42.")

      // Stub the invokeLambda function to simulate Lambda call
      def invokeLambda(query: String): Future[String] = mockLambdaResponse

      // Simulate the HTTP request to the server
      val requestEntity = HttpEntity(ContentTypes.`text/plain(UTF-8)`, mockQuery)
      Post("/chat", requestEntity) ~> HttpServer.route ~> check {
        status shouldBe StatusCodes.OK
        responseAs[String] should include("The meaning of life is 42.")
      }
    }
  }
}
