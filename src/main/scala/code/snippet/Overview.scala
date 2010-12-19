package code {
package snippet {


import _root_.scala.xml.{NodeSeq, Text}

import _root_.net.liftweb._
import util._
import common.Logger
import http.SHtml._
import http.S._
import http.js.JsCmds.{SetHtml, SetValueAndFocus}

import Helpers._

class Overview extends Logger {

  /**
    * Generate the Test Result view section
    */

  val showingVersion= http.S.param("v") openOr("ERROR") 
  info(showingVersion)
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
    bind("tests",xhtml, "version" -> showingVersion, "testResultListRow" -> bindTestResults _)
  }
  



}

}
}
