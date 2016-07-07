package com.tikal.fleet.akka.actors

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.actor.Actor.Receive
import akka.routing.RoundRobinPool
import com.tikal.fleet.akka.model.Client

/**
  * Created by chaimturkel on 6/20/16.
  */
class CarComputations extends Actor{
  val router = context.actorOf(RoundRobinPool(10).props(Props[CarCalculateAvailability]),"router1")

  override def receive: Receive = {
    case i : Integer => router ! i
  }
}

class CarCalculateAvailability extends Actor {
  override def receive: Receive = {
    case i: Integer =>{
      //do complex calculation on car to see if ready
      println(Thread.currentThread().getName +  "-"  + i)
    }

  }
}

/*
this object is for testing only
 */
object CarComputationsExample extends App{
  val system = ActorSystem("CarSystem")
  val computeCar : ActorRef  = system.actorOf(Props[CarComputations])
  computeCar ! new Integer(5)
  computeCar ! new Integer(7)
}
