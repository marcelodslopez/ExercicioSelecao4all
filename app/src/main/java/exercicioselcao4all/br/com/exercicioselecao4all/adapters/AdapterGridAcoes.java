package exercicioselcao4all.br.com.exercicioselecao4all.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import exercicioselcao4all.br.com.exercicioselecao4all.R;

/**
 * Created by Marcelo on 04/06/2016.
 */
public class AdapterGridAcoes extends BaseAdapter {
    List<String> listacoes;
    LayoutInflater layoutInflater;
    Context context;

    public AdapterGridAcoes(Context context) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    public void setLista(List<String> listacoes) {
        this.listacoes = listacoes;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listacoes.size();
    }

    @Override
    public Object getItem(int i) {
        return listacoes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int position, View contextView, ViewGroup viewGroup) {
        LinearLayout layout = (LinearLayout) contextView;
        ViewHolder view;

        if (layout == null) {

            layout = (LinearLayout) layoutInflater.inflate(R.layout.adapter_grid_acoes, null);

            view = new ViewHolder();

            view.imagem =
                    (ImageView) layout.findViewById(R.id.adapter_grid_imagem);

            layout.setTag(view);

        } else {
            view = (ViewHolder) layout.getTag();
        }

        //conforme o titulo de cada ação, é inserida a imagem correspondente
        switch (listacoes.get(position)){
            case "ligar":
                view.imagem.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_ligar));
                break;

            case "servicos":
                view.imagem.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_servicos));
                break;


            case "endereco":
                view.imagem.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_endereco));
                break;


            case "comentarios":
                view.imagem.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_comentarios));
                break;

            case "favoritos":
                view.imagem.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_favoritos));
                break;
        }


        return layout;
    }

    static class ViewHolder {
        ImageView imagem;
    }

}
