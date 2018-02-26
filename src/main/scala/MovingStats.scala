import scala.collection.mutable.{ ArrayBuffer, Queue }

/**
 * Provides a main method for reading lines and printing line count along with line itself.
 * Depends on a suitable Output provider.
 */
trait InputProcess extends Task with Output {

  def scanLeftUsingIterate(input: Iterator[Int], winsize: Array[Int])= {

    var currentnum = 0
    var count = 0
    var min = 0.0
    var max = 0.0
    var avg = 0.0
   // val result = Iterator.empty
    val sizes = new ArrayBuffer[Int]

    // def scanLeftUsingIterate(input: Iterator[Int], winsize: Array[Int]) = {
    for (size <- winsize) {
      sizes += size
    }

    for (number <- input) {

      val innumbers = Iterator.iterate(1) {
        _ * number
      } drop (1)

      count += 1

      if (count > sizes.max) {
        innumbers.drop(1)
      }

      /** calculate min, avg, max for each input windowsize */

      for (size <- sizes) {
        val result = Iterator.iterate(Option(0.0, 0.0, 0.0)) {
          case Some((_, _, _)) =>
            if (count >= size) {
              /** if the number of the input is more than windowsize, drop the first n numbers in the queue */
              val nums = innumbers.drop(count - size).toList
              min = nums.min
              max = nums.max
              avg = nums.sum / size
              Option((min, avg, max))
            }
            else None
        }
        doOutput (currentnum, count, result)
      }
    }
  }
}

/** A concrete main application object. */
object MovingStats extends Main with InputProcess

