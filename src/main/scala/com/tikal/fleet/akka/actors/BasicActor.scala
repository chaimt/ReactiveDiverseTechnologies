package com.tikal.fleet.akka.actors

import java.util.Calendar

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout
import com.tikal.fleet.akka.actors.BasicActor.{CurrentTime, WhatTimeIsIt}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.util.{Failure, Success}

/**
  * Created by chaimturkel on 7/4/16.
  */
class BasicActor extends Actor{
  var setTime : Option[String] = _

  override def receive: Receive = {
    case WhatTimeIsIt => sender() ! setTime.getOrElse(CurrentTime(Calendar.getInstance().getTime.toString))
    case CurrentTime(time) => setTime = Some(time)
    case _ => unhandled("unknow message type")

  }
}

object BasicActor {
  case object WhatTimeIsIt
  case class CurrentTime(time : String)
}

object BasicActorExample extends App{
  val system = ActorSystem("BasicSystem")
  val basic : ActorRef  = system.actorOf(Props[BasicActor])

  basic ! CurrentTime("10:15")
  implicit val timeout = Timeout(5 seconds)
  val res = basic ? WhatTimeIsIt
  res onComplete {
    case Success(currentTime : CurrentTime) => println("time is - " + currentTime.time)
    case Failure(t) => println("An error has occured: " + t.getMessage)
  }
}
