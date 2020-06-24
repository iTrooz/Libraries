package fr.entasia.apis.other;

public class Mutable <T> {

    public T value;

    public Mutable(){
    }

    public Mutable(T value){
        this.value = value;
    }


    @Override
    public String toString() {
        return "Mutavle{"+"value="+value+"}";
    }
}
