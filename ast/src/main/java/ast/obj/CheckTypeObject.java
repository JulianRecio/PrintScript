package ast.obj;

public interface CheckTypeObject {

  Object getValue();

  boolean typeIsCorrect(Object value);

  CheckTypeObject add(CheckTypeObject obj2);
}
