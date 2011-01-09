package code
package snippet


import net.liftweb.http.{ S, Req, LiftSession, provider, ParsePath }
import net.liftweb.common.Box
import org.specs._
import org.specs.specification._

class OverviewTest extends SpecificationWithJUnit { 

 "Overview" can {
   "return a list of Agent results" in {

      object overview extends Overview

      overview.showingVersion.doWith(Box[String]("2.2.0.1033")) {
        val xhtml= overview.renderAgentResult(
          <lift:Overview.renderAgentResult>
            <h2>Results for version: <tests:version /></h2>
          </lift:Overview.renderAgentResult>).toString
    
        xhtml.indexOf("2.2.0.1033") must be_>=(0)
      }
    }
  }
}
