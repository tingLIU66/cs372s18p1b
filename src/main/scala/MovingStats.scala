

import scala.collection.immutable.Queue

/** Reads lines and prints cumulative length of all lines so far along with most recent line itself. */
object CumulativeLengthFunctionalModular extends Main {

  // def iterate[T](start: T)(f: (T) ⇒ T): Iterator[T]
  // def scanLeft[B](z: B)(op: B ⇒ B): Iterator[B]
  // https://github.com/scala/scala/blob/v2.12.4/src/library/scala/collection/Iterator.scala#L595

  /**
   * Simple implementation of `scanLeft` using `iterate` and `takeWhile`.
   * For `takeWhile` to work, this requires lifting the resulting iterator to `Option`.
   * Alternatively, one could use `null`, but that would be less stylish.
   */
  def scanLeftUsingIterate(it: Iterator[Int])(z: ((Queue[Int], Int, Int)))(op: ((Queue[Int], Int, Int)) => ((Queue[Int], Int, Int))):
  Iterator[((Queue[Int], Int, Int))] =

    Iterator.iterate(Option(z)) {
      case Some(r) =>
        if (it.hasNext)

          Option(op((r._1, it.next(), r._3)))

        else None

    } takeWhile (_.isDefined) map (_.get)

  def createQueue(stat: (Queue[Int], Int, Int)) = {

    (stat._1.enqueue(stat._2), stat._2, stat._3 + 1)



  }

  def run(numbers: Iterator[Int]): Iterator[((Queue[Int], Int, Int))] =

    scanLeftUsingIterate(numbers)(Queue(0), 0, 0)(createQueue).drop(1)

}
