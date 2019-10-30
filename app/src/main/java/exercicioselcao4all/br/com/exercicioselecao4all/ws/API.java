package exercicioselcao4all.br.com.exercicioselecao4all.ws;

import exercicioselcao4all.br.com.exercicioselecao4all.model.response.ResponseTarefa;
import exercicioselcao4all.br.com.exercicioselecao4all.model.Tarefa;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface API {
    @GET("tarefa")
    Call<ResponseTarefa> getTarefas();

    @GET("tarefa/{id}")
    Call<Tarefa> getTarefa(@Path("id") String tarefaId);


}
