package design_pattern.factory_method;

import design_pattern.factory_method.model.CheesePizza;
import design_pattern.factory_method.model.ClamPizza;
import design_pattern.factory_method.model.PepperoniPizza;
import design_pattern.factory_method.model.Pizza;
import design_pattern.factory_method.model.VeggiePizza;

public class SimplePizzaFactory {
	public Pizza createPizza(String type) {
		Pizza pizza = null;

		if (type.equals("cheese")) {
			pizza = new CheesePizza();
		} else if (type.equals("pepperoni")) {
			pizza = new PepperoniPizza();
		} else if (type.equals("clam")) {
			pizza = new ClamPizza();
		} else if (type.equals("veggie")) {
			pizza = new VeggiePizza();
		}

		return pizza;
	}
}
