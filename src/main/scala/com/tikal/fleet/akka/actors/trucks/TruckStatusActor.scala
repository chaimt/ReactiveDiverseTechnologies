package com.tikal.fleet.akka.actors.trucks

import akka.actor.Actor
import TruckStatusActor._
import com.tikal.fleet.akka.actors.exceptions.ConnectionException

/**
  * Created by chaimturkel on 7/6/16.
  */
class TruckStatusActor extends Actor {
  private var status : Int = 0
  private var requestTimes : Int = 0

  //send request to actual truck and calculate status

  def getStatus(): Unit ={
    requestTimes += 1
    status = if (requestTimes % 3 == 0) -1 else 0
    if (status == -1)
      throw new ConnectionException()
  }

  override def receive: Receive = {
    case GetStatus => sender() ! CurrentStatus(status)
  }
}

object TruckStatusActor {
  case object GetStatus
  case class CurrentStatus(status : Int)
}
