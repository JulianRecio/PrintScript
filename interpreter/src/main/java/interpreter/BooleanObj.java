package interpreter;

public class BooleanObj implements MyObject{

    private Boolean value;

    public BooleanObj(Boolean value) {
        this.value = value;
    }

    public BooleanObj(){
        this.value = null;
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {

    }

    @Override
    public MyObject add(MyObject obj2) {
        throw new RuntimeException("Cannot add boolean");
    }
}
