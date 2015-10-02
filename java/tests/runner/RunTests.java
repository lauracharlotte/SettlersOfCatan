package runner;

import clientcommunicator.operations.RequestOperationsTests;
import clientcommunicator.Server.ServerPollerTest;
import clientcommunicator.Server.ServerProxyTest;
import guicommunicator.MapModelFacadeTest;
import guicommunicator.ResourceModelFacadeTest;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 *
 * @author Scott
 */
public class RunTests {
    
    public static void main(String[] args) {
      List testCases = new ArrayList();
      
      testCases.add(ServerProxyTest.class);
      testCases.add(RequestOperationsTests.class);
      testCases.add(ServerPollerTest.class);
      testCases.add(MapModelFacadeTest.class);
      testCases.add(ResourceModelFacadeTest.class);
      
        for (Iterator it = testCases.iterator(); it.hasNext();) {
            Class testCase = (Class) it.next();
            runTestCase(testCase);
        }
        System.out.println("ALL TESTS PASSED");
   }
    
   private static void runTestCase(Class testCase)
   {
      System.out.println("RUNNING " + testCase.getName().toUpperCase() + " TEST CASES");
      System.out.println("");
      Result result = JUnitCore.runClasses(testCase);
      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
      System.out.println(result.wasSuccessful());
      System.out.println("-------------------");
      System.out.println("");
   }
}
