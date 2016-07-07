package com.tikal.fleet.akka.actors.trucks

import akka.actor.SupervisorStrategy.{Escalate, Restart, Resume, Stop}
import akka.actor.{Actor, ActorRef, OneForOneStrategy}
import com.tikal.fleet.akka.actors.exceptions.ConnectionException

import scala.concurrent.duration._

/**
  * Created by chaimturkel on 7/6/16.
  */
class TruckActor extends Actor{


  val truckStatus :  ActorRef = context.actorOf()


  override def receive: Receive = {

  }



  override val supervisorStrategy =
    OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute) {
      case _: ConnectionException      => Restart //maybe connection parameters are incorrect
      case _: ArithmeticException      => Resume
      case _: NullPointerException     => Restart
      case _: IllegalArgumentException => Stop
      case _: Exception                => Escalate
    }
}
