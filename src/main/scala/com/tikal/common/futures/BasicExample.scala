package com.tikal.common.futures

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by chaimturkel on 6/30/16.
  */
object BasicExample {


  import scala.util.{Success, Failure}

  val f: Future[List[String]] = Future {
    //session.getRecentPosts
    List("one", "two", "three")
  }

  f onComplete {
    case Success(posts) => for (post <- posts) println(post)
    case Failure(t) => println("An error has occured: " + t.getMessage)
  }


}
