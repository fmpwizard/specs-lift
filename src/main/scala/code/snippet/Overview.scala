package code
package snippet

import scala.xml.{NodeSeq, Text}
import net.liftweb._
import util._
import common.{Box,Logger}
import http._
import SHtml._
import S._
import js.JsCmds.{SetHtml, SetValueAndFocus}
import Helpers._

class Overview extends Logger with SimpleInjector{
  /**
    * Generate the Test Result view section
    */

  //val showingVersion= http.S.param("v") openOr("ERROR") 
  val showingVersion= new Inject[Box[String]]( (http.S.param("v")  ) ){}
  showingVersion.default.set(http.S.param("v"))

  info(showingVersion.vend)
  def renderAgentResult( xhtml: NodeSeq ) = {

    val testResultFiltered= List(("test name 1", "PASS"), ("test name 2", "PASS")   )  

    def bindTestResults(template: NodeSeq): NodeSeq = {
      testResultFiltered.flatMap{ case (testName, testResult) => bind(
        "test", template,
        FuncAttrBindParam(
            "classname", { 
              ns : NodeSeq => Text(if (testResult == "FAIL") "error" else "success" )
            }, "class"
          ),
        "name" -> testName,
        "result" -> testResult
      )}
    }
    bind("tests",xhtml, "version" -> showingVersion.vend.openOr(""), "testResultListRow" -> bindTestResults _)
  }
}

