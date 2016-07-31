package com.tikal.fleet.akka.actors.trucks

import akka.actor.Actor
import com.tikal.fleet.akka.actors.exceptions.ConnectionException
import com.tikal.fleet.akka.actors.trucks.TruckStatusActor._

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by chaimturkel on 7/6/16.
  */
class TruckStatusActor(truckIp: String) extends Actor {
  private var requestTimes: Int = 0
  private var speed: Int = 80
  private var mileage: Int = 0

  context.system.scheduler.schedule(0 seconds, 5 minutes)(self ! CalculateStatus)

  def connectToTruck(): Boolean = {
//    ...
    true
  }

  def calcStatus(): Unit = {
    requestTimes += 1
    if (!connectToTruck())
      throw new ConnectionException()
//    mileage =...
//    speed =...
//    parent ! TruckTelemetry(mileage, speed)
  }

  override def receive: Receive = {
    case CalculateStatus => calcStatus
    case GetStatus => sender() ! TruckTelemetry(mileage, speed)
  }
}

object TruckStatusActor {
  case class TruckTelemetry(mileage : Int, speed : Int)
  case object GetStatus
  case object CalculateStatus
  case class CurrentStatus(status : Int)
}
