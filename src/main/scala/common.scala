import scala.collection.mutable.Queue

/** Defines a dependency (plug-in contract) on a run method that processes an input stream. */
trait Output{
  def doOutput(current: Int, count: Int, stats:Iterator[Option[(Double, Double,Double)]])

}

/** Provides a reusable output observer tied to println/stdout. */
trait OutputToStdOut extends Output{

  override def doOutput(current: Int, count: Int, stats: Iterator[Option[(Double, Double,Double)]]) {
    print(current + " " + count + " ")
    for (stat <- stats) {
      if (stat == None)
        print("?" + " ")
      else
        print(stat.get + " ")
    }
    println()

  }
}


trait Task {
  def scanLeftUsingIterate(input: Iterator[Int], winsize: Array[Int]): Unit
}

/**
 * Provides a reusable main task tied to stdin and stdout.
 * Depends on a suitable run method.
 */
trait Main extends Task with OutputToStdOut {
  def main(args: Array[String]): Unit = {
    val lines = scala.io.Source.stdin.getLines
    val words = lines.flatMap(_.split("\\W+"))
    val numbers = words.map(_.toInt)
     scanLeftUsingIterate(numbers, args.map(_.toInt))
    //result.foreach(println)
  }
}