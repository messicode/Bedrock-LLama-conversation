
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.Materializer
import io.grpc.{ManagedChannel, ManagedChannelBuilder}
import lambdaService.{GenerateTextRequest, GenerateTextResponse, LambdaServiceGrpc}

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.util.{Failure, Success}

object HttpServer extends App {
  implicit val system: ActorSystem = ActorSystem("AkkaHttpServer")
  implicit val materializer: Materializer = Materializer(system)
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  // gRPC Client to invoke Lambda through API Gateway, Needs gateway api url
  def invokeLambda(query: String): Future[String] = {
    val channel: ManagedChannel = ManagedChannelBuilder.forTarget("https://api-id.execute-api.region.amazonaws.com/stage/")
      .usePlaintext().build()  // For testing, make sure your API Gateway is set to support plain text


    val stub = LambdaServiceGrpc.blockingStub(channel)

    val request = GenerateTextRequest(query = query)

    try {
      val response: GenerateTextResponse = stub.generateText(request)
      Future.successful(response.response)
    } catch {
      case e: Exception => Future.failed(new Exception("Error invoking Lambda", e))
    } finally {
      channel.shutdownNow()
    }
  }

  // Route for handling HTTP requests
  val route =
    path("chat") {
      post {
        entity(as[String]) { query =>
          val lambdaResponse = invokeLambda(query)
          onComplete(lambdaResponse) {
            case Success(response) => complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, response))
            case Failure(ex) => complete(StatusCodes.InternalServerError, s"Error: ${ex.getMessage}")
          }
        }
      }
    }

  // Start server
  Http().newServerAt("localhost", 8080).bind(route).onComplete {
    case Success(binding) =>
      println(s"Server running at http://${binding.localAddress.getHostName}:${binding.localAddress.getPort}")
//      system.scheduler.scheduleOnce(1.minutes) {
//        println("Shutting down server after 30 minutes...")
//        system.terminate()
//      }
    case Failure(ex) =>
      println(s"Failed to bind HTTP server, reason: ${ex.getMessage}")
      system.terminate()
  }

  println("Server is running. Press Ctrl+C to stop. Aborting in 2 mins")
  Thread.sleep(2*60*1000)
}
