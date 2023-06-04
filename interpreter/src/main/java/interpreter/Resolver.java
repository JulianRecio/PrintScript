package interpreter;

import ast.obj.*;

public class Resolver {

  public Resolver() {}

  public AttributeObject add(AttributeObject obj1, AttributeObject obj2) {
    return obj1.add(obj2);
  }

  public AttributeObject subtract(AttributeObject obj1, AttributeObject obj2) {
    AttributeObject object;
    try {
      if (obj1.getValue() instanceof Integer && obj2.getValue() instanceof Integer) {
        object = new NumberObj((Integer) obj1.getValue() - (Integer) obj2.getValue(), false);
      } else {
        double result =
            ((Number) obj1.getValue()).doubleValue() - ((Number) obj2.getValue()).doubleValue();
        object = new NumberObj(result, false);
      }
    } catch (Exception e) {
      throw new RuntimeException("Can only subtract Number");
    }
    return object;
  }

  public AttributeObject multiply(AttributeObject obj1, AttributeObject obj2) {
    AttributeObject object;
    try {
      if (obj1.getValue() instanceof Integer && obj2.getValue() instanceof Integer) {
        object = new NumberObj((Integer) obj1.getValue() * (Integer) obj2.getValue(), false);
      } else {
        double result =
            ((Number) obj1.getValue()).doubleValue() * ((Number) obj2.getValue()).doubleValue();
        object = new NumberObj(result, false);
      }
    } catch (Exception e) {
      throw new RuntimeException("Can only multiply Number");
    }
    return object;
  }

  public AttributeObject divide(AttributeObject obj1, AttributeObject obj2) {
    AttributeObject object;
    try {
      if (obj1.getValue() instanceof Integer && obj2.getValue() instanceof Integer) {
        object = new NumberObj((Integer) obj1.getValue() / (Integer) obj2.getValue(), false);
      } else {
        double result =
            ((Number) obj1.getValue()).doubleValue() / ((Number) obj2.getValue()).doubleValue();
        object = new NumberObj(result, false);
      }
    } catch (Exception e) {
      throw new RuntimeException("Can only divide Number");
    }
    return object;
  }
}
