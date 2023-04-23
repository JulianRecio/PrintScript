package interpreter;

public class StringObj implements MyObject {

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
  public void setValue(Object value) {
    this.value = (String) value;
  }

  @Override
  public MyObject add(MyObject obj2) {
    return new StringObj(this.value + (String) obj2.getValue());
  }
}
