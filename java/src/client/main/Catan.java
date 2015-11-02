package client.main;

import javax.swing.*;

import client.catan.*;
import client.login.*;
import client.join.*;
import client.misc.*;
import client.base.*;
import clientcommunicator.Server.ServerProxy;
import clientcommunicator.modelserverfacade.ModelServerFacadeFactory;

/**
 * Main entry point for the Catan program
 */
@SuppressWarnings("serial")
public class Catan extends JFrame
{
	private static Catan catanObj;
	
	private static PlayerWaitingView playerWaitingView;
	private static PlayerWaitingController playerWaitingController;
	private static JoinGameView joinView;
	private static NewGameView newGameView;
	private static SelectColorView selectColorView;
	private static MessageView joinMessageView;
	private static JoinGameController joinController;
	private static LoginView loginView;
	private static MessageView loginMessageView;
	private static LoginController loginController;
	private CatanPanel catanPanel;
	
	private Catan()
	{
		client.base.OverlayView.setWindow(this);
		
		this.setTitle("Settlers of Catan");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		catanPanel = new CatanPanel();
		this.setContentPane(catanPanel);
		
		display();
	}
	
	private void display()
	{
		pack();
		setVisible(true);
	}
	
	//
	// Main
	//
	public static void main(final String[] args)
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run()
			{
                ServerProxy.setSERVER_HOST(args[0]);
                ServerProxy.setSERVER_PORT(args[1]);
                            
				catanObj = new Catan();
				playerWaitingView = new PlayerWaitingView();
				playerWaitingController = new PlayerWaitingController(playerWaitingView);
				playerWaitingView.setController(playerWaitingController);
				
				joinView = new JoinGameView();
				newGameView = new NewGameView();
				selectColorView = new SelectColorView();
				joinMessageView = new MessageView();
				joinController = new JoinGameController(joinView, newGameView, selectColorView, joinMessageView);
				joinController.setJoinAction(new IAction() {
					@Override
					public void execute()
					{
						playerWaitingController.start();
					}
				});
				joinView.setController(joinController);
				newGameView.setController(joinController);
				selectColorView.setController(joinController);
				joinMessageView.setController(joinController);
				
				loginView = new LoginView();
				loginMessageView = new MessageView();
				loginController = new LoginController(loginView, loginMessageView);
				loginController.setLoginAction(new IAction() {
					@Override
					public void execute()
					{
						joinController.start();
					}
				});
				loginView.setController(loginController);
				loginView.setController(loginController);
				
				loginController.start();
			}
		});
	}
	
	public static void thankYouTas()
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		playerWaitingController.stopPoller();
		catanObj.dispose();
		catanObj = new Catan();
		playerWaitingView = new PlayerWaitingView();
		playerWaitingController = new PlayerWaitingController(playerWaitingView);
		playerWaitingView.setController(playerWaitingController);
		
		joinView = new JoinGameView();
		newGameView = new NewGameView();
		selectColorView = new SelectColorView();
		joinMessageView = new MessageView();
		joinController = new JoinGameController(joinView, newGameView, selectColorView, joinMessageView);
		joinController.setJoinAction(new IAction() {
			@Override
			public void execute()
			{
				playerWaitingController.start();
			}
		});
		joinView.setController(joinController);
		newGameView.setController(joinController);
		selectColorView.setController(joinController);
		joinMessageView.setController(joinController);
		joinController.start();
		
		loginView = new LoginView();
		loginMessageView = new MessageView();
		loginController = new LoginController(loginView, loginMessageView);
		loginController.setLoginAction(new IAction() {
			@Override
			public void execute()
			{
				joinController.start();
			}
		});
		loginView.setController(loginController);
		loginView.setController(loginController);
		
		loginController.start();
	}
}

