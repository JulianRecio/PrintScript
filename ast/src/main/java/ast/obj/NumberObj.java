package ast.obj;

public class NumberObj extends AttributeObject {

  private Number value;

  public NumberObj(Number value, boolean modifiable) {
    super(modifiable);
    this.value = value;
  }

  public NumberObj(boolean modifiable) {
    super(modifiable);
    this.value = null;
  }

  public Number getValue() {
    return value;
  }

  @Override
  public boolean typeIsCorrect(Object value) {
    return value instanceof Number;
  }

  @Override
  public AttributeObject add(AttributeObject obj2) {
    if (this.getValue() instanceof Integer && obj2.getValue() instanceof Integer) {
      return new NumberObj((Integer) this.getValue() + (Integer) obj2.getValue(), false);
    } else {
      double result =
          ((Number) this.getValue()).doubleValue() + ((Number) obj2.getValue()).doubleValue();
      return new NumberObj(result, false);
    }
  }
}
