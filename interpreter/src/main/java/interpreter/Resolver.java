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
      object = new NumberObj((Integer) obj1.getValue() - (Integer) obj2.getValue(), false);
    } catch (Exception e) {
      throw new RuntimeException("Can only subtract Double");
    }
    return object;
  }

  public AttributeObject multiply(AttributeObject obj1, AttributeObject obj2) {
    AttributeObject object;
    try {
      object = new NumberObj((Integer) obj1.getValue() * (Integer) obj2.getValue(), false);
    } catch (Exception e) {
      throw new RuntimeException("Can only multiply Double");
    }
    return object;
  }

  public AttributeObject divide(AttributeObject obj1, AttributeObject obj2) {
    AttributeObject object;
    try {
      object = new NumberObj((Integer) obj1.getValue() / (Integer) obj2.getValue(), false);
    } catch (Exception e) {
      throw new RuntimeException("Can only divide Double");
    }
    return object;
  }
}
