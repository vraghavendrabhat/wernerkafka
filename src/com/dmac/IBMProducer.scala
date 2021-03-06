package com.dmac

import java.util.Properties

import org.apache.kafka.clients.producer.{Callback, KafkaProducer, ProducerRecord, RecordMetadata}

object IBMProducer {


  def main(args:Array[String]) = {


    // zkNode - 2181
    // kafka - localhost - 9092

    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("client.id", "UNIQUE-PRODUCER_ID")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("linger.ms", "1")
    props.put("batch.size","445")
    props.put("request.required.acks", "-1")
    props.put("partitioner.class", "com.dmac.KafkaUserCustomPartitioner");



    val producer = new KafkaProducer[String, String](props)

    val data = new ProducerRecord[String, String]("PHANTHOM-TOPIC","zeon", "kafka is ultimate messaging")

    val data2 = new ProducerRecord[String, String]("PHANTHOM-TOPIC", "chinnasamy", "or is it mq ultimate")




    val callback = new Callback {

      override def onCompletion(recordMetadata: RecordMetadata, e: Exception) = {


        println("Offset = " + recordMetadata.offset())
        println("Topic = " + recordMetadata.topic())
        println("Partition = " + recordMetadata.partition())

      }
    }
    try {


      // Scala producer with a callback
      producer.send(data, callback)
      producer.send(data2, callback)



    }
    catch {
      case ex: Exception =>{
        println(ex)
      }

    }


    // Kafka producer produces a flush method to ensure all previously sent messages have been actually completed.
    producer.flush()
    producer.close()

    //println("OOO")

  }
}
