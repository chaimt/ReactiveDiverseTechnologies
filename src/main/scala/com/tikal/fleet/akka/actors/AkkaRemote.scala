package com.tikal.fleet.akka.actors

import akka.actor.{Actor, ActorRef, ActorSystem, Address, Deploy, Props}
import akka.remote.RemoteScope

/**
  * Created by chaimturkel on 7/10/16.
  */
class AkkaRemote extends Actor{
  override def receive: Receive = {
    case _ => throw new RuntimeException
  }
}


object AkkaRemote extends App{
  val address : Address = new Address("tcp","AkkaRemote")
  val system = ActorSystem("AkkaRemote")
  val local : ActorRef  = system.actorOf(Props[AkkaRemote])
  val remote : ActorRef  = system.actorOf(Props[AkkaRemote].withDeploy(Deploy(scope = RemoteScope(address))))

}