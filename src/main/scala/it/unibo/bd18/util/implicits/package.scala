package it.unibo.bd18.util

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SQLContext}

import scala.reflect.ClassTag

package object implicits {

  implicit class RichSeq[T](private val seq: Seq[T]) {
    def toRDD(parallelism: Int)(implicit sc: SparkContext, ct: ClassTag[T]): RDD[T] = sc.makeRDD(seq, parallelism)

    def toRDD(implicit sc: SparkContext, ct: ClassTag[T]): RDD[T] = toRDD(sc.defaultParallelism)
  }

  implicit class RichSeqWithNewPartitions[T](private val seq: Seq[(T, Seq[String])]) {
    def toRDD(implicit sc: SparkContext, ct: ClassTag[T]): RDD[T] = sc.makeRDD(seq)
  }

  implicit class RichSQLContext(private val sqlContext: SQLContext) {
    def apply(sqlText: String): DataFrame = sqlContext.sql(sqlText)
  }

}
