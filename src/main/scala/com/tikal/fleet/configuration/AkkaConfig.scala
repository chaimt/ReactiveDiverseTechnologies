package com.tikal.fleet.configuration

import akka.actor.ActorSystem
import com.typesafe.config.{Config, ConfigFactory}
import com.typesafe.scalalogging.slf4j.LazyLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation._
import com.tikal.fleet.akka.spring.SpringExtention

/**
 * Created by Haim.Turkel on 7/26/2015.
 */
@Configuration
@ComponentScan(basePackages = Array("com.clearforest.importer.akka.actors", "com.clearforest.importer.akka.spring"))
class AkkaConfig extends LazyLogging {

  @Autowired
  val applicationContext: ApplicationContext = null

  @Autowired
  val springExtension: SpringExtention = null

  @Bean
  def akkaConfiguration(): Config = {
    ConfigFactory.load ()
  }

  @Bean
  def actorSystem(): ActorSystem = {
    val system = ActorSystem
      .create("AkkaTaskProcessing", akkaConfiguration())
    springExtension.initialize(applicationContext)
    logger.info("akka started")
    system
  }

}
