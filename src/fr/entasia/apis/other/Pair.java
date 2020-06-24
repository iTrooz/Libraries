package fr.entasia.apis.other;

public class Pair<A, B> {

    public A a;
    public B b;

    public Pair(){

    }

    public Pair(A a, B b){
        this.a = a;
        this.b = b;
    }

    @Override
    public String toString() {
        return "Pair{"+"a="+a+" | b="+b+"}";
    }
}
