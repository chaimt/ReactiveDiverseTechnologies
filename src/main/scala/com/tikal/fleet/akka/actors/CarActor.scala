package com.tikal.fleet.akka.actors

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import com.tikal.fleet.akka.model.{CarStatus, Client}

/**
  * Created by chaimturkel on 6/19/16.
  */
class CarActor extends Actor{
  var client : Option[Client] = None
  var status : Option[CarStatus] = None

  override def receive: Receive = {
    case message : Client => {
      client = Some(message)
      println(message.name)
    };
    case IsCarAvailable => sender() ! status.getOrElse(CarStatus(false)).isCarReady
  }
}

/*
this object is for testing only
 */
object CarActorExample extends App{
  val system = ActorSystem("CarSystem")
  val assignCar : ActorRef  = system.actorOf(Props[CarActor])
  assignCar ! Client("chaim")
}
