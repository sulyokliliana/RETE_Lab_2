package hu.bme.mit.yakindu.analysis.workhere;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.junit.Test;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import org.yakindu.sct.model.sgraph.State;
import org.yakindu.sct.model.sgraph.Statechart;
import org.yakindu.sct.model.sgraph.Transition;
import org.yakindu.sct.model.stext.stext.EventDefinition;
import org.yakindu.sct.model.stext.stext.VariableDefinition;

import hu.bme.mit.model2gml.Model2GML;
import hu.bme.mit.yakindu.analysis.modelmanager.ModelManager;

public class Main {
	
	@Test
	public void test() {
		main(new String[0]);
	}
	
	public static void main(String[] args) {
		ModelManager manager = new ModelManager();
		Model2GML model2gml = new Model2GML();
		
		// Loading model
		EObject root = manager.loadModel("model_input/example.sct");
		
		// Reading model
		Statechart s = (Statechart) root;
		TreeIterator<EObject> iterator = s.eAllContents();
		
		int unnamedCount = 0;
		
		List <EventDefinition> events = new ArrayList();
		List <VariableDefinition> variables = new ArrayList();
		
		HashSet<String> stateNames = new HashSet<String>();
		//System.out.println("public static void print(IExampleStatemachine s) {");
		while (iterator.hasNext()) {
			EObject content = iterator.next();
			if(content instanceof VariableDefinition) {
				VariableDefinition variable = (VariableDefinition) content;
				variables.add(variable);
				//System.out.println("	System.out.println(\"W = \" + s.getSCInterface().get" + variable.getName() + "());"  );	
			}
			if(content instanceof EventDefinition) {
				EventDefinition event = (EventDefinition) content;
				events.add(event);
				//System.out.println("	System.out.println(" + event.getName() + "());" );
		
			}
		}
		print(events, variables);
		
		// Transforming the model into a graph representation
		String content = model2gml.transform(root);
		// and saving it
		manager.saveFile("model_output/graph.gml", content);
	}
	
	private static void print(List<EventDefinition> events, List<VariableDefinition> variables) {
		System.out.println("package hu.bme.mit.yakindu.analysis.workhere;");
		System.out.println("");
		System.out.println("import java.io.BufferedReader;");
		System.out.println("import java.io.IOException;");
		System.out.println("import java.io.InputStreamReader;");
		System.out.println("import hu.bme.mit.yakindu.analysis.RuntimeService;");
		System.out.println("import hu.bme.mit.yakindu.analysis.TimerService;");
		System.out.println("import hu.bme.mit.yakindu.analysis.example.ExampleStatemachine;");
		System.out.println("import hu.bme.mit.yakindu.analysis.example.IExampleStatemachine;");
		System.out.println("");
		
		System.out.println("public class RunStatechart {");
		System.out.println("");
		System.out.println("	public static void main(String[] args) throws IOException {");
		System.out.println("		ExampleStatemachine s = new ExampleStatemachine();");
		System.out.println("		s.setTimer(new TimerService());");
		System.out.println("		RuntimeService.getInstance().registerStatemachine(s, 200);");
		System.out.println("		s.init();");
		System.out.println("		s.enter();");
		System.out.println("");	
		System.out.println("		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));");
		System.out.println("		String inString = reader.readLine();");
		System.out.println("		while(inString != null) {");
		System.out.println("			switch(inString) {");
		
		for(int i = 0; i < events.size(); i++ ) {
			System.out.println("				case \"" + events.get(i).getName() + "\":");
			System.out.println("					s.raise" + events.get(i).getName().substring(0, 1).toUpperCase() + events.get(i).getName().substring(1) + "();");
			System.out.println("					break;");
		}
		
		System.out.println("				case \"exit\":");
		System.out.println("					System.exit(0);");
		System.out.println("				default:");
		System.out.println("					System.out.println(\"Unknown event. Try again!\");");
		System.out.println("					break;");
		System.out.println("			}");
		System.out.println("		inString = reader.readLine();");
		System.out.println("		s.runCycle();");
		System.out.println("		print(s);");
		System.out.println("		}");
		System.out.println("");
		System.out.println("		System.exit(0);");
		System.out.println("	}");
		System.out.println("");
		System.out.println("	public static void print(IExampleStatemachine s) {");
		
		for(int i = 0; i < variables.size(); i++ ) {
			System.out.println("		System.out.println(\"" + variables.get(i).getName() + " = \" + s.getSCInterface().get" + variables.get(i).getName().substring(0, 1).toUpperCase() + variables.get(i).getName().substring(1)+ "());");
		}
		
		System.out.println("	}");
		System.out.println("}");


	}
	
}
