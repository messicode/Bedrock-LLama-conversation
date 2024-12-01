import akka.actor.ActorSystem
import akka.stream.Materializer
import io.github.ollama4j.OllamaAPI
import io.github.ollama4j.models.response.OllamaResult

import scala.concurrent.Future

class BedrockIntegrationTest extends WordSpec with Matchers with ScalatestRouteTest {

  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: Materializer = Materializer(system)

  "Lambda invoking Bedrock" should {
    "correctly generate a response from Bedrock" in {
      val mockQuery = "Generate a creative description of a sunset."

      // Mock Ollama API response from Bedrock model
      val mockBedrockResponse = new OllamaResult("The sky blazes with shades of orange, pink, and purple...")

      // Stub out the Bedrock API to simulate a successful response
      val mockOllamaAPI: OllamaAPI = new OllamaAPI("http://localhost")
      val mockResult = Future.successful(mockBedrockResponse)

      // Stub the Lambda invoke function
      def invokeLambda(query: String): Future[String] = {
        mockResult.map(_.getResponse)
      }

      // Test the response from the Akka HTTP server
      val requestEntity = HttpEntity(ContentTypes.`text/plain(UTF-8)`, mockQuery)
      Post("/chat", requestEntity) ~> HttpServer.route ~> check {
        status shouldBe StatusCodes.OK
        responseAs[String] should include("The sky blazes with shades of orange, pink, and purple...")
      }
    }
  }
}
