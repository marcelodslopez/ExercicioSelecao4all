package exercicioselcao4all.br.com.exercicioselecao4all.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseTarefa {
    @SerializedName("lista")
    @Expose
    private List<String> lista = null;

    public List<String> getLista() {
        return lista;
    }

    public void setLista(List<String> lista) {
        this.lista = lista;
    }

}
