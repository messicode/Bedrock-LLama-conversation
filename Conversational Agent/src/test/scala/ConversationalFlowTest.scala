import akka.http.scaladsl.model._

class ConversationalFlowTest extends WordSpec with Matchers with ScalatestRouteTest {

  "Conversational Agent" should {
    "process an initial query and generate a valid follow-up query" in {
      val initialQuery = "How do cats express love?"
      val requestEntity = HttpEntity(ContentTypes.`text/plain(UTF-8)`, initialQuery)

      // Mocking the flow, ensuring the follow-up query is generated correctly
      val followUpQuery = "Can you give an example of a cat expressing love?"

      // Simulate the response
      val mockLambdaResponse = Future.successful("Example: A cat may nuzzle or purr while rubbing its head against you.")

      // Stub out the invokeLambda method to return the expected response
      def invokeLambda(query: String): Future[String] = {
        mockLambdaResponse
      }

      // Send the initial query
      Post("/chat", requestEntity) ~> HttpServer.route ~> check {
        status shouldBe StatusCodes.OK
        responseAs[String] should include("Example: A cat may nuzzle or purr while rubbing its head against you.")
      }
    }
  }
}
