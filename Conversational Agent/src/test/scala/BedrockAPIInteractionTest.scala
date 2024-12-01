class BedrockAPIInteractionTest {

}
import io.github.ollama4j.OllamaAPI
import io.github.ollama4j.models.response.OllamaResult

import scala.concurrent.Future

class BedrockAPIInteractionTest extends WordSpec with Matchers with ScalatestRouteTest {

  "Bedrock API" should {
    "generate a creative description for the sunset query" in {
      val mockQuery = "Generate a creative description of a sunset."

      // Mock response from Ollama (Bedrock API)
      val mockBedrockResponse = new OllamaResult("The sky turns into a palette of deep oranges, pinks, and purples as the sun sets.")

      // Stub the Ollama API to return the mocked response
      val mockOllamaAPI: OllamaAPI = new OllamaAPI("http://localhost")
      val mockResult = Future.successful(mockBedrockResponse)

      // Simulate the HTTP request to the server
      val requestEntity = HttpEntity(ContentTypes.`text/plain(UTF-8)`, mockQuery)
      Post("/chat", requestEntity) ~> HttpServer.route ~> check {
        status shouldBe StatusCodes.OK
        responseAs[String] should include("The sky turns into a palette of deep oranges, pinks, and purples as the sun sets.")
      }
    }
  }
}
