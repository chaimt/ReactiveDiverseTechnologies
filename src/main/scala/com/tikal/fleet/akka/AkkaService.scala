package com.clearforest.importer.akka

import akka.actor._
import com.tikal.fleet.akka.spring.SpringExtention
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.stereotype.Service

/**
 * Created by Haim.Turkel on 7/16/2015.
 */

@Service
class AkkaService {

  @Autowired
  implicit val context: ConfigurableApplicationContext = null

  @Autowired
  var actorSystem: ActorSystem = null

  @Autowired
  val springExtention : SpringExtention = null



}
