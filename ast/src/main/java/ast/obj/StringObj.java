package ast.obj;

public class StringObj extends AttributeObject {

  private String value;

  public StringObj(String value, boolean modifiable) {
    super(modifiable);
    this.value = value;
  }

  public StringObj(boolean modifiable) {
    super(modifiable);
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
  public AttributeObject add(AttributeObject obj2) {
    return new StringObj(this.value + obj2.getValue().toString(), false);
  }
}
