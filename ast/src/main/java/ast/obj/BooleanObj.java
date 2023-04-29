package ast.obj;

public class BooleanObj extends AttributeObject {

  private Boolean value;

  public BooleanObj(Boolean value, boolean modifiable) {
    super(modifiable);
    this.value = value;
  }

  public BooleanObj(boolean modifiable) {
    super(modifiable);
    this.value = null;
  }

  @Override
  public Boolean getValue() {
    return value;
  }

  @Override
  public boolean typeIsCorrect(Object value) {
    try {
      this.value = (boolean) value;
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  @Override
  public AttributeObject add(AttributeObject obj2) {
    throw new RuntimeException("Cannot add boolean");
  }
}
