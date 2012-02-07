/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package base.network;

import base.ActionManager;

/**
 *
 * @author mauro
 */
public class FakeNetworkManager extends NetworkManager{

	public FakeNetworkManager(ActionManager aManager, ActionManager bManager, ActionManager cManager) {
		super (aManager);
	}
}
