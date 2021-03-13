package hu.bme.mit.yakindu.analysis.workhere;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.junit.Test;
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
		HashSet<String> stateNames = new HashSet<String>();
		System.out.println("public static void print(IExampleStatemachine s) {");
		while (iterator.hasNext()) {
			EObject content = iterator.next();
			/*
			if(content instanceof State) {
				State state = (State) content;
				if(state.getName().isEmpty()) {
					String newName = "Unnamed State" + unnamedCount;
					while(stateNames.contains(newName)) {
						unnamedCount++;
						newName = "Unnamed State" + unnamedCount;
					}
					System.out.println("Unnamed state found. Suggested name: " + newName);
					state.setName(newName);
				} else {
					System.out.println("State: " + state.getName());
				}
				
				if(state.getOutgoingTransitions().isEmpty()) {
					System.out.println(state.getName() + " is a trap state.");
				}
			}*/
			/*
			if(content instanceof Transition) {
				Transition transition = (Transition) content;
				System.out.println(transition.getSource().getName() + "  ->  " + transition.getTarget().getName());
			}*/
			if(content instanceof VariableDefinition) {
				VariableDefinition variable = (VariableDefinition) content;
				System.out.println("	System.out.println(\"W = \" + s.getSCInterface().get" + variable.getName() + "());"  );	
			}
			if(content instanceof EventDefinition) {
				EventDefinition event = (EventDefinition) content;
				System.out.println("	System.out.println(" + event.getName() + "());" );
		
			}
		}
		System.out.println("}");
		
		// Transforming the model into a graph representation
		String content = model2gml.transform(root);
		// and saving it
		manager.saveFile("model_output/graph.gml", content);
	}
}
