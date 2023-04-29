package ast.obj;

public abstract class AttributeObject {

  private boolean modifiable;

  public AttributeObject(boolean modifiable) {
    this.modifiable = modifiable;
  }

  public abstract Object getValue();

  public boolean isModifiable() {
    return modifiable;
  }

  public void setModifiable(boolean modifiable) {
    this.modifiable = modifiable;
  }

  public abstract boolean typeIsCorrect(Object value);

  public abstract AttributeObject add(AttributeObject obj2);
}
