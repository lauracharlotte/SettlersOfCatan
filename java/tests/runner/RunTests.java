package runner;

import clientcommunicator.operations.RequestOperationsTests;
import clientcommunicator.Server.ServerPollerTest;
import clientcommunicator.Server.ServerProxyTest;
import clientcommunicator.modelserverfacade.JSONParserTest;
import guicommunicator.MapModelFacadeTest;
import guicommunicator.ResourceModelFacadeTest;
import server.command.*;

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
      testCases.add(JSONParserTest.class);
      testCases.add(JoinCommandTest.class);
      testCases.add(CreateCommandTest.class);
      testCases.add(ModelCommandTest.class);
      testCases.add(ListCommandTest.class);
      testCases.add(AcceptTradeCommandTest.class);
      testCases.add(BuildCityCommandTest.class);
      testCases.add(BuildRoadCommandTest.class);
      testCases.add(BuildSettlementCommandTest.class);
      testCases.add(BuyDevCardCommandTest.class);
      testCases.add(DiscardCardsCommandTest.class);
      testCases.add(FinishTurnCommandTest.class);
      testCases.add(LoginCommandTest.class);
      testCases.add(MaritimeTradeCommandTest.class);
      testCases.add(MonopolyCommandTest.class);
      testCases.add(MonumentCommandTest.class);
      testCases.add(OfferTradeCommandTest.class);
      testCases.add(RegisterCommandTest.class);
      testCases.add(RoadBuildingCommandTest.class);
      testCases.add(RobPlayerCommandTest.class);
      testCases.add(RollNumberCommandTest.class);
      testCases.add(SendChatCommandTest.class);
      testCases.add(SoldierCommandTest.class);
      testCases.add(YearOfPlentyCommandTest.class);
      //testCases.add();
        for (Iterator it = testCases.iterator(); it.hasNext();) {
            Class testCase = (Class) it.next();
            runTestCase(testCase);
        }
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
