package ast.obj;

public class NumberObj extends AttributeObject {

  private Double value;

  public NumberObj(Double value, boolean modifiable) {
    super(modifiable);
    this.value = value;
  }

  public NumberObj(boolean modifiable) {
    super(modifiable);
    this.value = null;
  }

  public Double getValue() {
    return value;
  }

  @Override
  public boolean typeIsCorrect(Object value) {
    try {
      this.value = (double) value;
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  @Override
  public AttributeObject add(AttributeObject obj2) {
    return new NumberObj(this.getValue() + (double) obj2.getValue(), false);
  }
}
