package com.tikal.fleet.futures

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


/**
  * Created by chaimturkel on 7/2/16.
  */
object ComposableExample {

  import scala.util.{Success, Failure}

  val f: Future[List[String]] = Future {
    //session.getRecentPosts
    List("one", "two", "three")
  }

  val calc : Future[List[Int]] = f.map(list => list.map(_.length))

  calc onComplete {
    case Success(posts) => for (postLength <- posts) println(postLength)
    case Failure(t) => println("An error has occured: " + t.getMessage)
  }
}
