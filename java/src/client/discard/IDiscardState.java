package client.discard;

import java.util.Observable;

import client.misc.IWaitView;
import shared.definitions.ResourceType;

public interface IDiscardState 
{
	public IDiscardState modelUpdated(Observable o, Object arg, IDiscardView disView, IWaitView waitView);
	public void increaseAmount(ResourceType resource, IDiscardView view);
	public void decreaseAmount(ResourceType resource, IDiscardView view);
	public IDiscardState discard(IDiscardView disView, IWaitView waitView);
}
