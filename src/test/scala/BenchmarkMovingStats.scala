import scala.collection.mutable

object BenchmarkMovingStats extends Bench.LocalTime {

  val sut = new InputProcess {
    override def doOutput(current: Int, count: Int, stats: mutable.Queue[Option[Double]]): Unit = {
      var numcount = 0
      numcount += 1
    }
  }
  var windowsize1 = Array(1)
  val sizes = Gen.exponential("size")(1000, 10000000, 10)

  performance of "MovingStats" in {
    measure method "calavg" in {
      using(sizes) in {
        size => sut.calavg(Iterator.fill(size)(1), windowsize1)
      }
    }
  }
}
