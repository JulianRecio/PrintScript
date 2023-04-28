package ast.obj;

public class StringObj implements CheckTypeObject {

  private String value;

  public StringObj(String value) {
    this.value = value;
  }

  public StringObj() {
    this.value = null;
  }

  public String getValue() {
    return value;
  }

  @Override
  public boolean typeIsCorrect(Object value) {
    try {
      this.value = (String) value;
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  @Override
  public CheckTypeObject add(CheckTypeObject obj2) {
    return new StringObj(this.value + (String) obj2.getValue());
  }
}
