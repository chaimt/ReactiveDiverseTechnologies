package com.tikal.fleet.akka.actors

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import java.util.concurrent.TimeUnit.MILLISECONDS

import com.tikal.fleet.akka.model.Client

import scala.concurrent.duration.FiniteDuration

/**
  * Created by chaimturkel on 6/20/16.
  */
class SchedulingMaintenance extends Actor{

  val delay = FiniteDuration(100, MILLISECONDS);

  override def receive: Receive = {
    case RunMaintenance => println("doing maintenence")
  }

  override def preStart() = { schedualeNextMaintenance }

  def schedualeNextMaintenance = {
    import context.dispatcher
    context.system.scheduler.scheduleOnce(delay,self,RunMaintenance)
  }
}

/*
this object is for testing only
 */
object SchedulingMaintenanceExample extends App{
  val system = ActorSystem("CarSystem")
  system.actorOf(Props[SchedulingMaintenance])
}
