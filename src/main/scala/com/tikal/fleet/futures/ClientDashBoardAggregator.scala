package com.tikal.fleet.futures

import com.google.gson.JsonObject
import com.tikal.fleet.rxjava.Utils
import org.apache.http.HttpStatus

import scala.collection.immutable.Iterable
import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.Future
import scala.language.postfixOps
import scala.util.{Failure, Success}

/**
  * Created by chaimturkel on 6/30/16.
  */
object ClientDashBoardAggregator extends App {

  val urls = Map("gps" -> "http://jsonplaceholder.typicode.com/posts/1",
    "trucksavailability" -> "http://jsonplaceholder.typicode.com/posts/2",
    "truckshealth" -> "http://jsonplaceholder.typicode.com/posts/3")


  type ServerResult = (String, String)


  def getData(segment: Int): Unit = {
    val urls = Map("gps" -> s"http://gpsservice/$segment/134",
      "trucksavailability" -> s"http://vehiclemanagement/$segment/availability",
      "truckshealth" -> s"http://Analyticsservice/trucks/$segment/health")


    val listFuture: Iterable[Future[Option[ServerResult]]] =
      for (serverResult: ServerResult <- urls)
        yield Future {
          Utils.getResponse(serverResult._2)
        }
          .map(http => {
            if (http.getStatusLine.getStatusCode == HttpStatus.SC_OK)
              Some(serverResult._1, Utils.httpEntityToString(http.getEntity()))
            else None
          })

    val list: Future[Iterable[Option[ServerResult]]] = Future.sequence(listFuture)
    list onComplete {
      case Success(serverResults) => {
        val base = new JsonObject
        for (jsonResult <- serverResults) if (jsonResult.isDefined) base.addProperty(jsonResult.get._1, jsonResult.get._2)
//        httpresponse.send(base)
      }
      case Failure(t) => {
        val base = new JsonObject
        base.addProperty("error", t.getMessage)
//        httpresponse.send(base)
      }
    }
  }


}

