import org.scalatest.WordSpec

import scala.collection.mutable.{ ArrayBuffer, Buffer, Queue }

/** Provides an output observer that accumulates the results in a buffer one can inspect later. */
trait OutputToBuffer extends Output {

  private val buffer = Buffer.empty[(Int, Int, Queue[Option[Double]])]

  //var t = (0, 0, Queue(Option(0.0), Option(0.0), Option(0.0)))

  def getResults: Seq[(Int, Int, Queue[Option[Double]])] = buffer.toSeq


  override def doOutput(current: Int, count: Int, stats: Queue[Option[Double]]) = {
    buffer +=  ((current, count, stats))

  }
}

class TestMovingStatsSpec extends WordSpec {

  private val winsizes1 = Array(1)
  private val winsizes2 = Array(1, 3)

  /** Creates a (mutable!) SUT instance. */
  def createSUT() = new InputProcess with OutputToBuffer
  "An MovingStats" when {
    "given a nonempty iterator and 1 windowsize" should {
      "produce the correct nonempty output" in {

        // input data for this test case
        val data = Seq(1)
        // create SUT instance for this test case
        val sut = createSUT()
        // exercise SUT
        sut.calavg(data.iterator, winsizes1)
        // check effect on output observer
        assert(sut.getResults(0)._1 === 1)
        assert(sut.getResults(0)._2 === 3)
        assert(sut.getResults(0)._3 === Queue(Some(1), Some(1), Some(1)))
      }
    }

    "given a nonempty iterator and 2 windowsize" should {
      "produce the correct nonempty output" in {
        // input data for this test case
        val data = Seq(1, 2, 3)
        // create SUT instance for this test case
        val sut = createSUT()
        // exercise SUT
        sut.calavg(data.iterator, winsizes2)
        // check effect on output observer
        assert(sut.getResults(0)._1 === 1)
        assert(sut.getResults(0)._2 === 1)
        //assert(sut.getResults(0)._3 === Queue(Some(1), Some(1), Some(1),None, None, None))
        assert(sut.getResults(1)._1 === 2)
        assert(sut.getResults(1)._2 === 2)
        assert(sut.getResults(1)._3 === Queue(Some(2), Some(2), Some(2),None, None, None))
        assert(sut.getResults(2)._1 === 3)
        assert(sut.getResults(2)._2 === 3)
        assert(sut.getResults(2)._3 === Queue(Some(3), Some(3), Some(3),Some(1), Some(2), Some(3)))
      }
    }
  }
}