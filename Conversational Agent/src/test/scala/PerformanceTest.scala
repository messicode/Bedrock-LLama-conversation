import akka.actor.ActorSystem
import akka.http.scaladsl.model._
import akka.util.Timeout

import scala.concurrent.Future
import scala.concurrent.duration._

class PerformanceTest extends WordSpec with Matchers with ScalatestRouteTest {

  implicit val system: ActorSystem = ActorSystem()
  implicit val timeout: Timeout = Timeout(5.seconds)

  "Akka HTTP Server" should {
    "handle multiple concurrent requests" in {
      val queries = List(
        "What is 2 + 2?",
        "Tell me a joke.",
        "How far is the moon from Earth?",
        "What is the capital of Japan?",
        "Generate a creative description of a sunset."
      )

      val futures = queries.map { query =>
        val requestEntity = HttpEntity(ContentTypes.`text/plain(UTF-8)`, query)
        Post("/chat", requestEntity)
      }

      val responses = Future.sequence(futures)

      // Ensure that all requests complete successfully
      responses.onComplete {
        case scala.util.Success(res) =>
          res.foreach { response =>
            response.status shouldBe StatusCodes.OK
            response.entity.toString should not be empty
          }
        case scala.util.Failure(exception) =>
          fail(s"Performance test failed with exception: $exception")
      }
    }
  }
}
