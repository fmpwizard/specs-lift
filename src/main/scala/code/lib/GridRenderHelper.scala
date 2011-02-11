package code
package lib

import scala.collection.immutable.SortedMap
import scala.xml.{Elem, Text, NodeSeq}


import net.liftweb.util._
import Helpers._
import net.liftweb.common.{Box, Full, Logger}

object GridRenderHelper extends SimpleInjector with Logger{



  def renderGrid(showingVersion: String, in: Box[List[Map[String,(String, String)]]]) = {

    val testResults= new Inject[Box[List[Map[String,(String, String)]]]](
      Full(List(Map("N/A"->("N/A", "N/A"))))
    ){}

    testResults.default.set(in)

    case class Result(OS: String, pass: Boolean) {
      def clss = if (pass) "success" else "error"
    }

    case class TestRow(tests: SortedMap[Int, Option[Result]])
    val tests: List[Map[String,(String, String)]]= testResults.vend open_!


    // a set of the keys of the tests
    val osSet = Set(tests.flatMap(_.keys) :_*)

    val results: SortedMap[String, TestRow] = SortedMap(osSet.toSeq.map{
      set =>
        (set, TestRow(SortedMap(tests.zipWithIndex.map {
          case (test, idx) => idx -> test.get(set).map{
                                  case (os, pass) => Result(os, pass == "PASS")
                                                      }
        } :_*)))
      }:_*)

      debug(results)


    def dd(f: NodeSeq => NodeSeq): NodeSeq => NodeSeq = {
      case e: Elem => f(e.child)
      case x => f(x)
    }

    ClearClearable andThen
    "h2 *" #> dd(_ ++ Text(showingVersion)) &
    "#row *" #> results.map {
      case (name, row) => "#col" #> (
        ("* *" #> name) :: row.tests.toList.map {
          case (pos, Some(res)) => "* *" #> name   & "* [class]" #> res.clss
          case (pos, _) => "* *" #> "N/A" & "* [class]" #> "notice"
        }
        )
    }







  }
}
