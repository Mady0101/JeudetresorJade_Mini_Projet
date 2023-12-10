package behaviours.gamemaster;

import agents.GameMaster;
import agents.Player;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import util.AgentLogger;

public class PlayBehaviour extends OneShotBehaviour {
	private static final long serialVersionUID = -2810893152176054082L;
	GameMaster gm;
	int nextState;
	
	public PlayBehaviour(GameMaster gm) {
		this.gm = gm;
		nextState = 1;
	}

	@Override
	public void action() {
		gm.doWait();
		
		ACLMessage hintRequest = gm.receive();
		AgentLogger.log(hintRequest);
		String playerMoveDirection = hintRequest.getContent();
		String hint = gm.evaluateProximity(playerMoveDirection);
		AID agentRequest = hintRequest.getSender();
		
		if(hint.equals("win")){
			nextState = 0;
			if(agentRequest.getLocalName().equals("player1")){
				AID aid = new AID("player2" , AID.ISLOCALNAME);
				ACLMessage message = new ACLMessage(ACLMessage.INFORM);
				message.addReceiver(aid);
				message.setContent("player1 won");
				gm.send(message);
			}else{
				AID aid = new AID("player1" , AID.ISLOCALNAME);
				ACLMessage message = new ACLMessage(ACLMessage.INFORM);
				message.addReceiver(aid);
				message.setContent("player2 won");
				gm.send(message);
			}
		}

			ACLMessage reply = hintRequest.createReply();
			reply.setPerformative(ACLMessage.INFORM);
			reply.setContent(hint);
			gm.send(reply);



	}
	
	@Override
	public int onEnd() {
		return nextState;
	}

}
