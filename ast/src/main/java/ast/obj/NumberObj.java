package ast.obj;

public class NumberObj implements CheckTypeObject {

  private Double value;

  public NumberObj(Double value) {
    this.value = value;
  }

  public NumberObj() {
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
  public CheckTypeObject add(CheckTypeObject obj2) {
    return new NumberObj(this.getValue() + (double) obj2.getValue());
  }
}
