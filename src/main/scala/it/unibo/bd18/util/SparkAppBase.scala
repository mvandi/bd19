package it.unibo.bd18.util

import org.apache.spark.{SparkConf, SparkContext}

private[util] trait SparkAppBase extends App {

  protected[this] val conf: SparkConf

  protected[this] def sc: SparkContext

  object Implicits {
    println("Implicits")
    implicit lazy val _sc: SparkContext = sc
  }

}
