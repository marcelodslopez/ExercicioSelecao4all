package exercicioselcao4all.br.com.exercicioselecao4all.adapters;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import exercicioselcao4all.br.com.exercicioselecao4all.R;

public class ListaViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{
    TextView adapter_list_texto;
    CardView cardView;
    List<String> list;
    public ListaViewHolder(@NonNull View itemView, List<String> list) {
        super(itemView);
        adapter_list_texto = (TextView)  itemView.findViewById(R.id.adapter_list_texto);
        cardView = (CardView) itemView.findViewById(R.id.card_view);
        this.list = list;
        itemView.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int itemPosition = getAdapterPosition();
        String id_tarefa = list.get(itemPosition);
        EventBus.getDefault().post(id_tarefa);
        Log.d("ADAPTER", "Elemento " + getAdapterPosition() + " clicado.");
    }

}
