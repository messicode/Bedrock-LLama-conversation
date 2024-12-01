import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.Materializer
import com.typesafe.config.ConfigFactory
import io.github.ollama4j.OllamaAPI
import io.github.ollama4j.models.response.OllamaResult
import io.github.ollama4j.utils.OptionsBuilder
import io.github.ollama4j.types.OllamaModelType

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.util.{Failure, Success}

object ConversationalAgent {
  implicit val system: ActorSystem = ActorSystem("ConversationalAgent")
  implicit val materializer: Materializer = Materializer(system)
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher
  private val config = ConfigFactory.load()

  def main(args: Array[String]): Unit = {

    val host:String= config.getString("app.ollama.host")
    println(s"Host at: $host")
    val ollamaAPI: OllamaAPI = new OllamaAPI(host)  // Ollama API URL
    val maxIterations = 5
    var currentQuery = "Initial query: How do cats express love?"  // Initial query, then updated queries
    var iteration = 0

    while (iteration < maxIterations) {
      println(s"Query $iteration: $currentQuery")

      // Step 1: Send the query to the Akka HTTP server
      val serverResponseFuture = sendToServer(currentQuery)

      // Step 2: Handle response from the server and generate follow-up query
      serverResponseFuture.onComplete {
        case Success(serverResponse) =>

          println(s"Server processed: $serverResponse")
          // Step 3: Generate follow-up query using Ollama model
          // Using the correct model type and options builder
          val options = new OptionsBuilder().build()  // Build options (add parameters if needed)
          try {
            val ollamaResult: OllamaResult = ollamaAPI.generate(OllamaModelType.LLAMA2, serverResponse, false, options)
            // Extract the response from OllamaResult and update the current query
            val followUpQuery = ollamaResult.getResponse // Get the model's response
            println(s"Follow-up Query: $followUpQuery")
            currentQuery = followUpQuery  // Update the query for the next iteration
          } catch {
            case exception: Exception =>
              println(s"Error generating follow-up query: ${exception.getMessage}")
          }
        case Failure(exception) =>
          println(s"Error calling server: ${exception.getMessage}")
      }

      // Wait for the response before iterating to the next query
      Thread.sleep(2000)  // Wait 2sec for next prompt
      iteration += 1
    }
  }

  def sendToServer(query: String): Future[String] = {
    val request = HttpRequest(
      method = HttpMethods.POST,
      uri = "http://localhost:8080/chat",  // URL of the Akka HTTP server
      entity = HttpEntity(ContentTypes.`text/plain(UTF-8)`, query)
    )

    // Send the HTTP request to the Akka HTTP server and extract the response as String
    Http().singleRequest(request).flatMap { response =>
      Unmarshal(response.entity).to[String]
    }
  }
}
