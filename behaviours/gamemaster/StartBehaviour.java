package behaviours.gamemaster;

import agents.GameMaster;
import agents.Player;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class StartBehaviour extends OneShotBehaviour {
	private static final long serialVersionUID = -6034272650953747851L;
	
	GameMaster gm;
	public StartBehaviour(GameMaster gm) {
		this.gm = gm;
	}
	
	@Override
	public void action() {
		// wait 10 seconds before starting game
		gm.doWait(20000);
		gm.initGame();
		
		ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
		message.setContent("START GAME");
		message.addReceiver(new AID("player1" , AID.ISLOCALNAME));
		message.addReceiver(new AID("player2" , AID.ISLOCALNAME));
		gm.send(message);
	}

}
