

/**
 * JUnit-style testing in ScalaTest with individual tests described
 * by strings and using assertions.
 *
 * [[http://www.scalatest.org/user_guide/selecting_a_style ScalaTest testing styles]]
 */
class TestMovingStats extends FunSuite {

  def input = new InputProcess with OutputToBuffer

  test("NonNumber input will gives a NumberFormatException") {

    intercept[NumberFormatException] {

      val data = Seq("a")
      val words = data.flatMap(_.split("\\W+"))
      val numbers = words.map(_.toInt)

      input.calavg(numbers.iterator, Array(1))
    }
  }
}
