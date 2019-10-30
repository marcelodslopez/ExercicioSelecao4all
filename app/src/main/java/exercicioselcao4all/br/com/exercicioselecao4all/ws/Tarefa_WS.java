package exercicioselcao4all.br.com.exercicioselecao4all.ws;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import exercicioselcao4all.br.com.exercicioselecao4all.model.Tarefa;
import exercicioselcao4all.br.com.exercicioselecao4all.model.response.ResponseTarefa;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Tarefa_WS {
    private static final String TAG = "Terafa_WS";
    public void buscarTarefaPeloId(String id){
        API api = RetrofitInstance.getRetrofitInstance().create(API.class);
        Call<Tarefa> call = api.getTarefa(id);
        //new buscarWSTarefa(getApplicationContext(), URL, listaIds.get(position)).execute();
        call.enqueue(new Callback<Tarefa>() {
            @Override
            public void onResponse(Call<Tarefa> call, Response<Tarefa> response) {
                Tarefa tarefa = (Tarefa) response.body();
                //inicia a atividade de tela inicial passando o objeto tarefa serializado
                EventBus.getDefault().post(tarefa);
            }
            @Override
            public void onFailure(Call<Tarefa> call, Throwable t) {
                Log.e(TAG, "Error: "+t.getMessage());
            }
        });
    }


    public void buscarTarefas(){
/** Create handle for the RetrofitInstance interface*/
        API api = RetrofitInstance.getRetrofitInstance().create(API.class);
        Call<ResponseTarefa> call = api.getTarefas();

        call.enqueue(new Callback<ResponseTarefa>() {
            @Override
            public void onResponse(Call<ResponseTarefa> call, Response<ResponseTarefa> response) {
                List<String> lista = (ArrayList<String>) response.body().getLista();
                EventBus.getDefault().post(lista);
            }
            @Override
            public void onFailure(Call<ResponseTarefa> call, Throwable t) {
                Log.e(TAG, "Error: "+t.getMessage());
            }
        });
    }


}
