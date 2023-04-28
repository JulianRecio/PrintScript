package interpreter;

import ast.obj.*;

public class Resolver {

  public Resolver() {}

  public CheckTypeObject add(CheckTypeObject obj1, CheckTypeObject obj2) {
    return obj1.add(obj2);
  }

  public CheckTypeObject subtract(CheckTypeObject obj1, CheckTypeObject obj2) {
    CheckTypeObject object;
    try {
      object = new NumberObj((double) obj1.getValue() - (double) obj2.getValue());
    } catch (Exception e) {
      throw new RuntimeException("Can only subtract Double");
    }
    return object;
  }

  public CheckTypeObject multiply(CheckTypeObject obj1, CheckTypeObject obj2) {
    CheckTypeObject object;
    try {
      object = new NumberObj((double) obj1.getValue() * (double) obj2.getValue());
    } catch (Exception e) {
      throw new RuntimeException("Can only multiply Double");
    }
    return object;
  }

  public CheckTypeObject divide(CheckTypeObject obj1, CheckTypeObject obj2) {
    CheckTypeObject object;
    try {
      object = new NumberObj((double) obj1.getValue() / (double) obj2.getValue());
    } catch (Exception e) {
      throw new RuntimeException("Can only divide Double");
    }
    return object;
  }
}
