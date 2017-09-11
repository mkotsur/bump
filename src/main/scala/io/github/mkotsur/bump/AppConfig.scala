package io.github.mkotsur.bump

import java.util.concurrent.Executors

import com.typesafe.config.{Config, ConfigFactory}

import scala.concurrent.ExecutionContext

object AppConfig {

  private val config = ConfigFactory.load()


  object implicits {
    implicit val scriptExecutionContext = {
      val executorService = Executors.newFixedThreadPool(config.getInt("threadPoolSize"))
      ExecutionContext.fromExecutorService(executorService)
    }
  }



}
