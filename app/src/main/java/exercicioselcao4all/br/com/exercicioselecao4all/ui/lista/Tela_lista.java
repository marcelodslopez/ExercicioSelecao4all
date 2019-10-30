package exercicioselcao4all.br.com.exercicioselecao4all.ui.lista;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agrawalsuneet.dotsloader.loaders.TrailingCircularDotsLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import exercicioselcao4all.br.com.exercicioselecao4all.R;
import exercicioselcao4all.br.com.exercicioselecao4all.adapters.AdapterLista;
import exercicioselcao4all.br.com.exercicioselecao4all.model.Tarefa;
import exercicioselcao4all.br.com.exercicioselecao4all.ui.principal.TelaPrincipal;
import exercicioselcao4all.br.com.exercicioselecao4all.ws.Tarefa_WS;


public class Tela_lista extends AppCompatActivity {

    private static final String TAG = "Tela_lista";
    RecyclerView tela_lista_rvTarefas;
    TrailingCircularDotsLoader tela_lista_progressBar;
    TextView toolbar_title_list;
    boolean doubleBackToExitPressedOnce = false;
    AdapterLista adapterLista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_lista);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_lista);
        setSupportActionBar(toolbar);

        Window window = Tela_lista.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Tela_lista.this.getResources().getColor(R.color.colorPrimary));
        }

        tela_lista_rvTarefas = (RecyclerView) findViewById(R.id.tela_lista_rvTarefas);
        tela_lista_progressBar = (TrailingCircularDotsLoader) findViewById(R.id.tela_lista_progressBar);

        toolbar_title_list = (TextView) toolbar.findViewById(R.id.toolbar_title_list);
        toolbar_title_list.setText("Lista de tarefas");

        //inserido um handler para visualizar o load na tela a modo de avaliação
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                new Tarefa_WS().buscarTarefas();
            }
        }, 2000);




    }


    @Subscribe
    public void onEvent(String id){
        tela_lista_progressBar.setVisibility(View.VISIBLE);
        tela_lista_rvTarefas.setVisibility(View.GONE);
        //inserido um handler para visualizar o load na tela a modo de avaliação
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                new Tarefa_WS().buscarTarefaPeloId(id);
            }
        }, 2000);
    }

    @Subscribe
    public void onEvent(List<String> list)
    {
        adapterLista = new AdapterLista(getBaseContext(), this, list);

        tela_lista_rvTarefas.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        tela_lista_rvTarefas.setAdapter(adapterLista);
        adapterLista.notifyDataSetChanged();
        tela_lista_progressBar.setVisibility(View.GONE);
        tela_lista_rvTarefas.setVisibility(View.VISIBLE);
   }

    @Subscribe
    public void onEvent(Tarefa tarefa)
    {
        Intent intent = new Intent(getApplicationContext(), TelaPrincipal.class);
        intent.putExtra("tarefa", tarefa);
        //Insere a tarefa no carregamento em memória.
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
        tela_lista_progressBar.setVisibility(View.GONE);
        finish();
    }


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }
    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onBackPressed() {
        //Se pressionou o botão back duas vezes sai da aplicacao
        if (doubleBackToExitPressedOnce) {
            System.exit(0);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(Tela_lista.this, getResources().getString(R.string.aviso_sair), Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }


}
