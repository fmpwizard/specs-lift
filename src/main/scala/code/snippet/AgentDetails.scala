package code
package snippet

//import code.model.{AutomatedTests}
//import code.snippet.Param._
import code.lib.GridRenderHelper._

import net.liftweb._
import util._
import common.{Box, Full, Logger}



class AgentDetails extends Logger  with SimpleInjector{

  /**
    * Generate the Test Result view section
    */
  //hard coded for testing purposes
  val showingVersion= "2.3.4.0.1099"

  debug(showingVersion)

  val testResults= new Inject[Box[List[Map[String,(String, String)]]]](
    Full(List(Map("N/A"->("N/A", "N/A"))))
  ){}
  testResults.default.set(getTestResults)


  // Inject the method call, not just the val
  def getTestResults: Box[List[Map[String,(String, String)]]]= {
    Full(List(Map("N/A"->("N/A", "N/A"))))
  }


  def renderAgentResult= {
    renderGrid(showingVersion, testResults.vend)
  }

}

