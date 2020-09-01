package fr.entasia.apis.other;

// raison : Bungee ne dispose pas de org.apache.commons
public class Mutable <T> {

    public T value;

    public Mutable(){
    }

    public Mutable(T value){
        this.value = value;
    }


    @Override
    public String toString() {
        return "Mutable{"+"value="+value+"}";
    }




}
