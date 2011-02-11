package code
package snippet

import bootstrap.liftweb.Boot

import org.specs._
import org.specs.runner.JUnit3
import org.specs.runner.ConsoleRunner
import org.specs.matcher._
import org.specs.specification._

import net.liftweb._
import net.liftweb.mockweb._
import net.liftweb.mocks.{MockHttpServletRequest}
import net.liftweb.http._
import net.liftweb.util._
import net.liftweb.common._
import Helpers._


class AgentDetailsTestSpecsAsTest extends JUnit3(AgentDetailsTestSpecs)
object AgentDetailsTestSpecsRunner extends ConsoleRunner(AgentDetailsTestSpecs)

object AgentDetailsTestSpecs extends WebSpec {

  "AgentDetails" should {
    setSequential() // This is important for using SessionVars, etc.

    val testUrl = "http://127.0.0.1:8080/agent-details/2.4.0.1090"

    val testReq =
      new MockHttpServletRequest(
        "http://127.0.0.1:8080/agent-details/2.4.0.1090", ""
      )

    // Instantiate your project's Boot file
    val b = new Boot()
    // Boot your project
    b.boot

    // Create a new session for use in the tests
    val testSession = MockWeb.testS(testUrl) {
      S.session
    }

    object TestVar extends SessionVar[String]("Empty")

    "display service manager test results, all FAIL" withSFor(testReq) in {
      val agentDetails = new AgentDetails
      val res= Full(List(Map("N/A"->("N/A", "N/A"))))

      val x= S.runTemplate(List("agent-details"))

      agentDetails.testResults.doWith(
        Box[List[Map[String,(String, String)]]](
          List(
            Map("RHEL5 64bit"->("RHEL5", "FAIL")),
            Map("RHEL5 64bit"->("RHEL5", "FAIL")),
            Map("RHEL5 64bit"->("RHEL5", "FAIL")),
            Map("RHEL5 64bit"->("RHEL5", "FAIL")),
            Map("RHEL5 64bit"->("RHEL5", "FAIL")),
            Map("RHEL5 64bit"->("RHEL5", "FAIL")),
            Map("RHEL5 64bit"->("RHEL5", "FAIL"))
          )
        )
      ){
        //agentDetails.renderAgentResult mustEqual(<table>
        //Once choosing the template qorks, this test will fail, I just have it
        // compared to the word "temp" to simplify things
        x mustEqual ("temp") 
/*<table>
      <tr>
        <th>Platform</th>
        <th>Installer available</th>
        <th>Starts</th>
        <th>Smoke Test</th>
        <th>Other tests</th>
        <th>CPU Usage</th>
        <th>RAM Usage</th>
        <th>QUAN help link</th>
      </tr>
      <tr id="row">
        <td id="col">RHEL5 64bit</td><td class="error">RHEL5 64bit</td><td class="error">RHEL5 64bit</td><td class="error">RHEL5 64bit</td><td class="error">RHEL5 64bit</td><td class="error">RHEL5 64bit</td><td class="error">RHEL5 64bit</td><td class="error">RHEL5 64bit</td>
      </tr>
    </table>).toString*/
      }

    }






  }



}

