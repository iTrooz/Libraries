package fr.entasia.apis.other;

import java.util.Map;

public class Triplet<A, B, C> {

    public A a;
    public B b;
    public C c;

    public Triplet(){

    }

    public Triplet(A a, B b, C c){
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public String toString() {
        return "Triplet{"+"a="+ b +" | b="+ b +" | c="+c+"}";
    }

}
