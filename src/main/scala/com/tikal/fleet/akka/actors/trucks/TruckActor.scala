package com.tikal.fleet.akka.actors.trucks

import akka.actor.SupervisorStrategy.{Escalate, Restart, Resume, Stop}
import akka.actor.{Actor, ActorRef, ActorSystem, OneForOneStrategy, Props}
import com.tikal.fleet.akka.actors.IsTruckAvailable
import com.tikal.fleet.akka.actors.exceptions.ConnectionException
import com.tikal.fleet.akka.actors.trucks.TruckStatusActor.TruckTelemetry
import com.tikal.fleet.akka.model.{Driver, TruckStatus}

import scala.concurrent.duration._

/**
  * Created by chaimturkel on 6/19/16.
  */
class TruckActor(truckId : String) extends Actor{
  var ipAddress : String = "127.0.0.1"
  var driver : Option[Driver] = None
  var status : Option[TruckStatus] = None
  var truckStatus : ActorRef = context.actorOf(Props(new TruckStatusActor(ipAddress)))
  var mileage : Int = 0
  var speed : Int = 0

  override def receive: Receive = {
    case message : Driver => driver = Some(message)
    case IsTruckAvailable => sender() ! status.getOrElse(TruckStatus(false)).isTruckReady
    case TruckTelemetry(mileage , speed ) => { this.speed = speed; this.mileage = mileage}
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

/*
this object is for testing only
 */
object TruckActorExample extends App{
  val system = ActorSystem("CarSystem")
  val assignTruck : ActorRef  = system.actorOf(Props(new TruckActor("1234")))
  assignTruck ! Driver("chaim")
}
