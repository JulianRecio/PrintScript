package ast.obj;

public class NumberObj extends AttributeObject {

  private Integer value;

  public NumberObj(Integer value, boolean modifiable) {
    super(modifiable);
    this.value = value;
  }

  public NumberObj(boolean modifiable) {
    super(modifiable);
    this.value = null;
  }

  public Integer getValue() {
    return value;
  }

  @Override
  public boolean typeIsCorrect(Object value) {
    try {
      this.value = (Integer) value;
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  @Override
  public AttributeObject add(AttributeObject obj2) {
    return new NumberObj(this.getValue() + (Integer) obj2.getValue(), false);
  }
}
