package fr.entasia.apis.other;

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

    @Override
    public String toString() {
        return "Pair{"+"a="+ key +" | b="+ value +"}";
    }
}
