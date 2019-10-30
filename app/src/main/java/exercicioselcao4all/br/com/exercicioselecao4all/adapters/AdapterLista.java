package exercicioselcao4all.br.com.exercicioselecao4all.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import exercicioselcao4all.br.com.exercicioselecao4all.R;

/**
 * Created by Marcelo on 04/06/2016.
 */
public class AdapterLista  extends RecyclerView.Adapter<ListaViewHolder> {
    List<String> listaIds;
    LayoutInflater layoutInflater;
    Context context;
    private final Activity activity;

    public AdapterLista(Context context, Activity activity, List<String> list) {
        this.context = context;
        this.activity = activity;
        this.listaIds = list;
    }

    //Metodo para setar a lista no adapter
    public void setLista(List<String> listaIds) {
        this.listaIds = listaIds;
        this.notifyDataSetChanged();
    }



    @NonNull
    @Override
    public ListaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(activity).inflate(R.layout.adapter_list, parent, false);
        return new ListaViewHolder(inflate, listaIds);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaViewHolder holder, int position) {
        String id_tarefa = listaIds.get(position);
        String titulo = context.getResources().getString(R.string.texto_adapter_list)+ id_tarefa;
        holder.adapter_list_texto.setText(titulo);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return listaIds.size();
    }

}
