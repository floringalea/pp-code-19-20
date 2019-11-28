package actor

import akka.actor.{Actor, ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

object AskPatternApp extends App {
  implicit val timeout = Timeout(500 millis)
  val system = ActorSystem("BlockingApp")
  val echoActor = system.actorOf(Props[EchoActor])
  val future: Future[Any] = echoActor ? "Hello"
  val message = Await.result(future, timeout.duration).asInstanceOf[String]
  println(message)
}

class EchoActor extends Actor {
  def receive = {
    case msg => sender ! msg
  }
}