package interpreter;

public class NumberObj implements MyObject{

    private Double value;

    public NumberObj(Double value){
        this.value = value;
    }

    public NumberObj(){
        this.value = null;
    }

    public Double getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        this.value = (double) value;
    }

    @Override
    public MyObject add(MyObject obj2) {
        return new NumberObj(this.getValue() + (double) obj2.getValue());
    }
}
