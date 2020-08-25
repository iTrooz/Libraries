package fr.entasia.apis.other;

import java.util.Map;

// raison : Bungee ne dispose pas de org.apache.commons
public class Pair<A, B> {

    public A key;
    public B value;

    public Pair(){

    }

    public Pair(A a, B b){
        this.key = a;
        this.value = b;
    }

    public Pair(Map.Entry<A, B> e){
        this.key = e.getKey();
        this.value = e.getValue();
    }

    @Override
    public String toString() {
        return "Pair{"+"a="+ key +" | b="+ value +"}";
    }
}
