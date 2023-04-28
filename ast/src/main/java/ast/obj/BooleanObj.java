package ast.obj;

public class BooleanObj implements CheckTypeObject {

  private Boolean value;

  public BooleanObj(Boolean value) {
    this.value = value;
  }

  public BooleanObj() {
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
  public CheckTypeObject add(CheckTypeObject obj2) {
    throw new RuntimeException("Cannot add boolean");
  }
}
