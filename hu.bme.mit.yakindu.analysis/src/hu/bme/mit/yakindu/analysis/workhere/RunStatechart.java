package hu.bme.mit.yakindu.analysis.workhere;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import hu.bme.mit.yakindu.analysis.RuntimeService;
import hu.bme.mit.yakindu.analysis.TimerService;
import hu.bme.mit.yakindu.analysis.example.ExampleStatemachine;
import hu.bme.mit.yakindu.analysis.example.IExampleStatemachine;



public class RunStatechart {
	
	public static void main(String[] args) throws IOException {
		ExampleStatemachine s = new ExampleStatemachine();
		s.setTimer(new TimerService());
		RuntimeService.getInstance().registerStatemachine(s, 200);
		s.init();
		s.enter();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String inString = reader.readLine();
		while(inString != null) {
			switch(inString) {
			case "start":
				s.raiseStart();
				break;
			case "white":
				s.raiseWhite();
				break;
			case "black":
				s.raiseBlack();
				break;
			case "exit":
				System.exit(0);
			default: 
				System.out.println("Unknown event. Try again!");
				break;
			}
			inString = reader.readLine();
			s.runCycle();
			print(s);
		}
		
	}

	public static void print(IExampleStatemachine s) {
		System.out.println("W = " + s.getSCInterface().getWhiteTime());
		System.out.println("B = " + s.getSCInterface().getBlackTime());
	}
}
