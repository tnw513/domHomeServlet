import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class ThermometerAgent extends Agent {
	private Float currentTemperature;

	/**
	 * 
	 */
	private static final long serialVersionUID = -8355663085154105053L;

	protected void setup() {

		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("thermometer-manager");
		sd.setName("JADE-thermometer");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		}
		catch(FIPAException fe) {
			fe.printStackTrace();
		}
		addBehaviour(new getCurrentTemperature(this, 2000));
		//addBehaviour(new Behaviour1bis());
	}

	protected void takeDown() {
		// Deregister from the yellow pages
		try {
			DFService.deregister(this);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}
		System.out.println("ThermometerAgent "+getAID().getName()+" terminating.");
	}
	
	private class tempService extends CyclicBehaviour {

		@Override
		public void action() {
			
			
		}
		
	}

	private class getCurrentTemperature extends TickerBehaviour {

		public getCurrentTemperature(Agent a, long period) {
			super(a, period);
			// TODO Auto-generated constructor stub
		}
		/**
		 * 
		 */
		private static final long serialVersionUID = 9072626078728707911L;
		
		@Override
		public void onTick() {
			Object[] argList=myAgent.getArguments();
			SerialComm arduino = (SerialComm) argList[0];
			String currTemp=null;
			/*
			try {
				arduino.send("therm1\n"); //invia comando
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				currTemp=arduino.receive(); //temperatura
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
			try {
				currTemp = arduino.sendreceive("therm1\n");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//Float currTempFloat = Float.parseFloat(currTemp);
			//System.out.println(currTempFloat.compareTo((float) 31));
			currentTemperature = Float.parseFloat(currTemp);
			System.out.println(currTemp);
		}
	}
}

