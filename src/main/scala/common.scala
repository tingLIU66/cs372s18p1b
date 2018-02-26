

/** Defines a dependency (plug-in contract) on a run method that processes an input stream. */
trait Task {
  def run(input: Iterator[Int]): Iterator[(Int,Int)]
}

/**
  * Provides a reusable main task tied to stdin and stdout.
  * Depends on a suitable run method.
  */
trait Main extends Task {
  def main(args: Array[String]): Unit = {
    val lines = scala.io.Source.stdin.getLines
    val words = lines.flatMap(_.split("\\W+"))
    val numbers = words.map(_.toInt)
    val result = run(numbers)
    result.foreach(println)
  }
}