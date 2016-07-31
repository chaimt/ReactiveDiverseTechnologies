package com.tikal.fleet.akka.actors

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.routing.RoundRobinPool

/**
  * Created by chaimturkel on 6/20/16.
  */
class TruckComputations extends Actor{
  val router = context.actorOf(RoundRobinPool(10).props(Props[TruckCalculateAvailability]),"router1")

  override def receive: Receive = {
    case test : TruckSelfTest => router ! test
  }
}

class TruckCalculateAvailability extends Actor {
  override def receive: Receive = {
    case test: TruckSelfTest =>{
//      autoTestTruck(test.truckId)
    }
  }
}

case class TruckSelfTest(truckId : String)

/* this object is for testing only */
object TruckComputationsExample extends App{
  val system = ActorSystem("TruckSystem")
  val computeTruck : ActorRef  = system.actorOf(Props[TruckComputations])
  computeTruck ! TruckSelfTest("id1234")
  computeTruck ! TruckSelfTest("id454")
}
