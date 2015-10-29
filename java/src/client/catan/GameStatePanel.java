package client.catan;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import client.base.IAction;
import shared.definitions.CatanColor;


@SuppressWarnings("serial")
public class GameStatePanel extends JPanel
{
	private JButton button;
	private Color currentButtonColor;
        
	public GameStatePanel()
	{
		this.setLayout(new FlowLayout());
		this.setBackground(Color.white);
		this.setOpaque(true);
		this.currentButtonColor = null;
		button = new JButton(){
                    @Override
                    public void paintComponent(Graphics g)
                    {
                        
                        if(currentButtonColor != null)
                        {
                            g.setColor(currentButtonColor);
                            g.fillRect(0, 0, getSize().width, getSize().height);
                            this.setContentAreaFilled(false);
                        }
                        else
                        {
                            this.setContentAreaFilled(true);
                        }
                        super.paintComponent(g);
                    }
                };
		
		Font font = button.getFont();
		Font newFont = font.deriveFont(font.getStyle(), 20);
		button.setFont(newFont);
		
		button.setPreferredSize(new Dimension(400, 50));
		
		this.add(button);
		
		updateGameState("Waiting for other Players", false);
	}
	
	public void updateGameState(String stateMessage, boolean enable, CatanColor color)
	{
                if(stateMessage.equals("Finish Turn") && color != null && enable)
                {
                    this.currentButtonColor = color.getJavaColor();
                }
                else
                {
                    this.currentButtonColor = null;
                }
                button.revalidate();
                button.repaint();
		button.setText(stateMessage);
		button.setEnabled(enable);
	}
        
        public void updateGameState(String stateMessage, boolean enable)
        {
            this.updateGameState(stateMessage, enable, null);
        }
	
	public void setButtonAction(final IAction action)
	{
		ActionListener[] listeners = button.getActionListeners();
		for(ActionListener listener : listeners) {
			button.removeActionListener(listener);
		}
		
		ActionListener actionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				action.execute();
			}
		};
		button.addActionListener(actionListener);
	}
}

