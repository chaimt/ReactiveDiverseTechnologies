package com.tikal.common.futures

import com.google.gson.{JsonElement, JsonParser}
import com.tikal.fleet.rxjava.Utils
import org.apache.http.{HttpResponse, HttpStatus}

import scala.concurrent.Future
import scala.util.{Failure, Success}

/**
  * Created by chaimturkel on 6/30/16.
  */
object ServiceMonitorThreaded extends App{

  import scala.concurrent.ExecutionContext.Implicits._
  val urls = List("http://jsonplaceholder.typicode.com/posts/1",
    "http://jsonplaceholder.typicode.com/posts/2",
    "http://jsonplaceholder.typicode.com/error/3")
  val jsonParser = new JsonParser

  var listFuture : List[Future[Option[JsonElement]]] =
    for (url <- urls)
      yield Future { Utils.getResponse(url) }
        .map(http => {
        if (http.getStatusLine.getStatusCode == HttpStatus.SC_OK)
          Some(jsonParser.parse(Utils.httpEntityToString(http.getEntity())))
        else None
      })

  val list : Future[List[Option[JsonElement]]] = Future.sequence(listFuture)
  list onComplete {
    case Success(posts) => for (post <- posts) if (post.isDefined) println(post.toString)
    case Failure(t) => println("An error has occurred: " + t.getMessage)
  }

  Thread.sleep(100000)
}

