package modelo;

import java.util.ArrayList;
import java.util.List;

public class Observado {
    private List<Observador> observadores = new ArrayList<>();

    public void agregarObservador(Observador o) {
        observadores.add(o);
    }

    public void notificar(String evento, Object data) {
        for (Observador o : observadores) {
            o.actualizar(evento, data);
        }
    }
}
