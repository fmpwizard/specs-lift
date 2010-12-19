package code {
package snippet {


// First I create Mocks for the lift session
//import javax.servlet.http._
import net.liftweb.http.{ S, Req, LiftSession, provider }
import org.specs.mock.Mockito


import org.specs._
import org.specs.specification._


//import _root_.code.snippet.Overview._

trait MockRequest extends Mockito { this: Specification =>
  var request = mock[Req]
//  var httpRequest = mock[HttpServletRequest]
  var httpRequest = mock[provider.HTTPRequest]

  var session = mock[LiftSession]

  def createMocks: Unit = {
    request = mock[Req]
//    httpRequest = mock[HttpServletRequest]
    httpRequest = mock[provider.HTTPRequest]
    session = mock[LiftSession]
    request.request returns httpRequest
  }

  // this method can be used to executed any action inside a mocked session
  def inSession(f: =>Any) {  S.init(request, session) { f }  }

  def unsetParameter(name: String) {
    request.param(name)
    return None
  
  }

  def setParameter(name: String, value: String)  {
    request.param(name)
    return Some(value)
  }
}

// Context creation for the specification
//
// This "Specification context" specifies the User table must be cleaned up before each example.
//  It also makes sure that the example expectations are executed in a mocked session
// see http://code.google.com/p/specs/wiki/DeclareSpecifications#Specification_context_(_from_1.6.1_) for more information

object DatabaseContext extends Specification with Contexts with MockRequest {
  val setup = new SpecContext {
    //beforeExample(inSession(Users.createQuery("deleteUser").executeUpdate)) //delete the User table before each example
    aroundExpectations(inSession(_))  // execute each example inside a mocked session
 }
}

// and finally the specification itself

import org.specs._
import org.specs.specification._

class OverviewTest extends SpecificationWithJUnit with MockRequest with Contexts {

  //DatabaseContext.setup(this) // set the specification context on this specification

  "Overview" can {
    "return a list of Agent results" in {
      setParameter("v", "2.2.0.1033")
      val overview= new Overview
      val xhtml= overview.renderAgentResult(<lift:Overview.renderAgentResult>
    <h2>Results for version: <tests:version /></h2>
</lift:Overview.renderAgentResult>).toString
      xhtml.indexOf("2.2.0.1033") must be_>=(0)

    }

  }


}


}
}
