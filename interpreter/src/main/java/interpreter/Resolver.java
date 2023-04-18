package interpreter;

public class Resolver{

    public Resolver(){

    }

    public MyObject add(MyObject obj1, MyObject obj2){
        return obj1.add(obj2);
    }

    public MyObject subtract(MyObject obj1, MyObject obj2){
        MyObject object;
        try {
            object = new NumberObj((double) obj1.getValue() - (double) obj2.getValue());
        }
        catch (Exception e){
            throw new RuntimeException("Can only subtract Double");
        }
        return object;
    }

    public MyObject multiply(MyObject obj1, MyObject obj2){
        MyObject object;
        try {
            object = new NumberObj((double) obj1.getValue() * (double) obj2.getValue());
        }
        catch (Exception e){
            throw new RuntimeException("Can only multiply Double");
        }
        return object;
    }

    public MyObject divide(MyObject obj1, MyObject obj2){
        MyObject object;
        try {
            object = new NumberObj((double) obj1.getValue() / (double) obj2.getValue());
        }
        catch (Exception e){
            throw new RuntimeException("Can only divide Double");
        }
        return object;
    }
}

