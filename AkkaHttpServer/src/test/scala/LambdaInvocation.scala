import org.scalatest.{Matchers, WordSpec}
import scala.concurrent.Future
import akka.actor.ActorSystem
import akka.http.scaladsl.model._
import akka.stream.Materializer
import akka.http.scaladsl.testkit.ScalatestRouteTest

class LambdaInvocation extends WordSpec with Matchers with ScalatestRouteTest {

  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: Materializer = Materializer(system)

  "Lambda Function" should {
    "process the query and return a valid response" in {
      val mockQuery = "What is the capital of France?"

      // Mock Lambda response
      val mockLambdaResponse: Future[String] = Future.successful("The capital of France is Paris.")

      // Stub out the invokeLambda function to simulate successful Lambda invocation
      def invokeLambda(query: String): Future[String] = {
        mockLambdaResponse
      }

      // Simulate the request to the Akka HTTP server that calls the Lambda function
      val requestEntity = HttpEntity(ContentTypes.`text/plain(UTF-8)`, mockQuery)
      Post("/chat", requestEntity) ~> HttpServer.route ~> check {
        status shouldBe StatusCodes.OK
        responseAs[String] should include("The capital of France is Paris.")
      }
    }
  }
}
