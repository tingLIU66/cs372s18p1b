import scala.collection.immutable.Queue

/** Defines a dependency (plug-in contract) on a run method that processes an input stream. */
trait Task {
  def run(input: Iterator[Int]): Iterator[((Queue[Int], Int, Int))]

}

/**
 * Provides a reusable main task tied to stdin and stdout.
 * Depends on a suitable run method.
 */
trait Main extends Task {
  def main(args: Array[String]): Unit = {
    import sun.misc.Signal
    Signal.handle(new Signal("PIPE"), _ => scala.sys.exit())

    val lines = scala.io.Source.stdin.getLines
    val words = lines.flatMap(_.split("\\W+"))
    val numbers = words.map(_.toInt)
    val result = run(numbers)

    for (item <- result) {

      print(item._2 + " " + item._3 + " ")

      for (winsize <- args.map(_.toInt)) {
        if (item._1.length - 1 >= winsize) {
          val newqueue = item._1.drop(item._1.length - winsize)
          print(newqueue.min * 1.0 + " " + newqueue.sum * 1.0 / newqueue.length + " " + newqueue.max * 1.0 + " ")
        } else
          print("? ? ? ")

      }

      println()

    }
  }
}