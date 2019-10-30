package exercicioselcao4all.br.com.exercicioselecao4all.util;

import android.content.Context;
import android.graphics.drawable.Drawable;

import exercicioselcao4all.br.com.exercicioselecao4all.R;

public class Utils {
    public static Drawable getNotaImamge(int id, Context context) {
        Drawable nota;
        switch (id){
            case 1:
                nota =  context.getResources().getDrawable(R.drawable.estrela1);
                break;
            case 2:
                nota = context.getResources().getDrawable(R.drawable.estrelas2);
                break;
            case 3:
                nota =  context.getResources().getDrawable(R.drawable.estrelas3);
                break;
            case 4:
                nota =  context.getResources().getDrawable(R.drawable.estrelas4);
                break;
            case 5:
                nota =  context.getResources().getDrawable(R.drawable.estrelas5);
                break;
                default:
                    nota =  context.getResources().getDrawable(R.drawable.estrela1);

        }
        return  nota;
    }
}
