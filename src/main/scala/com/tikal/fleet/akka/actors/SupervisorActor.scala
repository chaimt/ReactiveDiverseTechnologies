package com.tikal.fleet.akka.actors

import akka.actor.SupervisorStrategy.{Escalate, Restart, Resume, Stop}
import akka.actor.{Actor, OneForOneStrategy}

import scala.concurrent.duration._

/**
  * Created by chaimturkel on 7/5/16.
  */
class SupervisorActor extends Actor{


  override val supervisorStrategy =
    OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute) {
      case _: ArithmeticException      => Resume
      case _: NullPointerException     => Restart
      case _: IllegalArgumentException => Stop
      case _: Exception                => Escalate
    }

  override def receive: Receive = ???
}
