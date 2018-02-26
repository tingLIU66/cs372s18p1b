

/** Reads lines and prints cumulative length of all lines so far along with most recent line itself. */
object CumulativeLengthFunctionalModular extends Main{

  // def iterate[T](start: T)(f: (T) ⇒ T): Iterator[T]
  // def scanLeft[B](z: B)(op: (B, A) ⇒ B): Iterator[B]
  // https://github.com/scala/scala/blob/v2.12.4/src/library/scala/collection/Iterator.scala#L595

  /**
    * Simple implementation of `scanLeft` using `iterate` and `takeWhile`.
    * For `takeWhile` to work, this requires lifting the resulting iterator to `Option`.
    * Alternatively, one could use `null`, but that would be less stylish.
    */
  def scanLeftUsingIterate(it: Iterator[Int])(z: (Int,Int))(op: (Int,Int)=>(Int,Int)): Iterator[(Int,Int)] =
    Iterator.iterate(Option(z)) {
      case Some(r) =>
        if (it.hasNext) Option(op(r, it.next())) else None
    } takeWhile (_.isDefined) map (_.get)

  def accumulateCount(acc: Int, line: Int) = (acc, line)

  def run(lines: Iterator[Int]): Iterator[(Int, Int)] =
    scanLeftUsingIterate(lines)("dummy", 0)(accumulateCount).drop(1)

  //scanLeftUsingIterate(lines)("dummy", 0)(accumulateCount).drop()


}
