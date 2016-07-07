package com.tikal.fleet.akka.actors

import akka.actor.Actor
import akka.actor.Actor.Receive

/**
  * Created by chaimturkel on 6/20/16.
  */
class LifeCycleActor extends Actor{

  override def receive: Receive = {
    case e : Exception =>  throw e;
  }

  override def preStart() = {
    println("preStart -> called on FIRST of actor instance")
  }

  override def postStop() = {
    println("postStop -> called on ANY actor instance shutdown")
  }

  override def preRestart(reason : scala.Throwable, message : scala.Option[scala.Any]) = {
    println("preRestart -> called on ANY running actor about to be restarted")
  }

  override def postRestart(reason : Throwable) = {
    println("postRestart -> called on a NEW INSTANCE of actor after restart")
  }

}
