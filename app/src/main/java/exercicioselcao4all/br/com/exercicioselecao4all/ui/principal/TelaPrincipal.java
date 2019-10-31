package exercicioselcao4all.br.com.exercicioselecao4all.ui.principal;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.agrawalsuneet.dotsloader.loaders.TrailingCircularDotsLoader;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import exercicioselcao4all.br.com.exercicioselecao4all.R;
import exercicioselcao4all.br.com.exercicioselecao4all.adapters.AdapterGridAcoes;
import exercicioselcao4all.br.com.exercicioselecao4all.model.Tarefa;
import exercicioselcao4all.br.com.exercicioselecao4all.ui.lista.Tela_lista;
import exercicioselcao4all.br.com.exercicioselecao4all.ui.servico.Servicos;
import exercicioselcao4all.br.com.exercicioselecao4all.util.Utils;

public class TelaPrincipal extends AppCompatActivity implements OnMapReadyCallback {

    Tarefa tarefaEscolhida;
    ImageView tela_principal_imagem, tela_principal_logo,toolbar_back, toolbar_search;
    TrailingCircularDotsLoader tela_principal_pb_principal, tela_principal_pb_logo;
    TextView tela_principal_titulo, tela_principal_texto, tela_principal_endereco, titulo_toolbar;
    GridView tela_principal_grid_botoes;
    AdapterGridAcoes adapterGridAcoes;
    ScrollView tela_principal_scroll;
    LatLng TutorialsPoint;
    private GoogleMap mMap;
    LinearLayout ll_comentarios;
    Toolbar toolbar;
    public static final int CALL_PHONE = 1;
    Intent intentCall;
    ArrayList<String> listaAcoes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);

        initViews();

        //Captura o objeto tarefa vindo da Tela de lista
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (intent.getExtras().containsKey("tarefa")){
            tarefaEscolhida = (Tarefa) extras.getSerializable("tarefa");
        }

        //Começa o download das imagens em AsyncTasks
        new DownloadImage("principal").execute(tarefaEscolhida.getUrlFoto());
        new DownloadImage("logo").execute(tarefaEscolhida.getUrlLogo());

        //Escuta do botão de voltar na toolbar
        toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(TelaPrincipal.this, R.anim.image_click));
                Intent intent = new Intent(getBaseContext(), Tela_lista.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                finish();
            }
        });

        //Adição de comentarios dinamicamente, para cada um um novo adapter_list_comentario
        LayoutInflater layoutInflater = getLayoutInflater();
        for (int i=0; i<tarefaEscolhida.getComentarios().size();i++){
            RelativeLayout inflate = (RelativeLayout) layoutInflater.inflate(R.layout.adapter_list_comentarios, null);
            TextView nome = (TextView) inflate.findViewById(R.id.list_comentarios_nome);
            nome.setText(tarefaEscolhida.getComentarios().get(i).getNome());

            TextView titulo = (TextView) inflate.findViewById(R.id.list_comentarios_titulo);
            titulo.setText(tarefaEscolhida.getComentarios().get(i).getTitulo());

            TextView comentario = (TextView) inflate.findViewById(R.id.list_comentarios_comentario);
            comentario.setText(tarefaEscolhida.getComentarios().get(i).getComentario());

            ImageView nota = (ImageView) inflate.findViewById(R.id.list_comentarios_nota);

            //mostra a imagem de estrela de acordo com a nota
            nota.setImageDrawable(Utils.getNotaImamge(tarefaEscolhida.getComentarios().get(i).getNota(), getApplicationContext()));

            CircleImageView foto = (CircleImageView) inflate.findViewById(R.id.list_comentarios_imagem);
            comentario.setText(tarefaEscolhida.getComentarios().get(i).getComentario());

            //Faz o download da foto do usuário (AsyncTask)
            new DownloadImage(foto,"comentario").execute(tarefaEscolhida.getComentarios().get(i).getUrlFoto());

            //Adiciona no LinearLayout de comentarios o novo comentario com seus dados preenchidos
            ll_comentarios.addView(inflate);

            //Adiciona uma barra cinza entre cada comentario (menos após o último)
            if (i< tarefaEscolhida.getComentarios().size()){
                LinearLayout divisoria = new LinearLayout(getBaseContext());

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                layoutParams.setMargins(10, 10, 10, 10);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    divisoria.setBackground(getResources().getDrawable(R.color.cinza));
                }else{
                    divisoria.setBackgroundDrawable(getResources().getDrawable(R.color.cinza));
                }
                divisoria.setLayoutParams(layoutParams);
                ll_comentarios.addView(divisoria);
            }

        }
        //seta o titulo da toolbar
        titulo_toolbar.setText(tarefaEscolhida.getCidade() + " - " + tarefaEscolhida.getBairro());
        //seta o titulo
        tela_principal_titulo.setText(tarefaEscolhida.getTitulo().toUpperCase());
        //seta o texto da tarefa
        tela_principal_texto.setText(tarefaEscolhida.getTexto());
        //seta o texto de endereco
        tela_principal_endereco.setText(tarefaEscolhida.getEndereco());

        //instancia e inicia o carregamento do mapa, chama método onMapReady
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Ações dos botões do grid
        tela_principal_grid_botoes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.startAnimation(AnimationUtils.loadAnimation(TelaPrincipal.this, R.anim.image_click));
                switch (listaAcoes.get(position)) {
                    case "ligar":
                        if (tarefaEscolhida.getTelefone().substring(0,1).equals("0")){
                            intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tarefaEscolhida.getTelefone()));
                        }else{
                            intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "0"+tarefaEscolhida.getTelefone()));
                        }

                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(TelaPrincipal.this,
                                        new String[]{Manifest.permission.CALL_PHONE},
                                        CALL_PHONE);
                        }else{
                            startActivity(intentCall);
                        }
                        break;
                    case "servicos":
                        Intent intent_servicos = new Intent(getBaseContext(), Servicos.class);
                        startActivity(intent_servicos);
                        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                        break;

                    case "endereco":
                        showDialogEndereco(TelaPrincipal.this,tarefaEscolhida.getEndereco());
                        break;

                    case "comentarios":
                        //Movo o scrool até o topo do linear layout onde se encontram os comentarios
                        ObjectAnimator.ofInt(tela_principal_scroll, "scrollY",  ll_comentarios.getTop()).setDuration(1500).start();
                        break;

                    case "favoritos":
                        //Utils.showToast(getBaseContext(),"favoritos");
                        break;
                }
            }
        });

    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_principal);
        setSupportActionBar(toolbar);

        Window window = TelaPrincipal.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(TelaPrincipal.this.getResources().getColor(R.color.colorPrimary));
        }

        //Inicialização dos componentes visuais
        tela_principal_imagem = (ImageView) findViewById(R.id.tela_principal_imagemPrincipal);
        tela_principal_logo = (ImageView) findViewById(R.id.tela_principal_logo);
        tela_principal_pb_principal = (TrailingCircularDotsLoader) findViewById(R.id.tela_principal_pb_principal);
        tela_principal_pb_logo = (TrailingCircularDotsLoader) findViewById(R.id.tela_principal_pb_logo);
        tela_principal_titulo = (TextView) findViewById(R.id.tela_principal_titulo);
        tela_principal_texto = (TextView) findViewById(R.id.tela_principal_texto);
        tela_principal_endereco = (TextView) findViewById(R.id.tela_principal_endereco);
        tela_principal_grid_botoes = (GridView) findViewById(R.id.tela_principal_grid_botoes);
        tela_principal_scroll = (ScrollView) findViewById(R.id.tela_principal_scroll);
        ll_comentarios = (LinearLayout) findViewById(R.id.ll_comentarios);
        titulo_toolbar= (TextView) toolbar.findViewById(R.id.toolbar_title);
        toolbar_back= (ImageView) toolbar.findViewById(R.id.toolbar_back);
        toolbar_search= (ImageView) toolbar.findViewById(R.id.toolbar_search);

        adapterGridAcoes = new AdapterGridAcoes(getBaseContext());
        //Monta a lista de ações
        listaAcoes.add("ligar");
        listaAcoes.add("servicos");
        listaAcoes.add("endereco");
        listaAcoes.add("comentarios");
        listaAcoes.add("favoritos");

        adapterGridAcoes.setLista(listaAcoes);
        tela_principal_grid_botoes.setAdapter(adapterGridAcoes);

    }

    /**
     * Método para setar a imagem nos objetos da tela principal (chamado no postExecute do método DownloadImage
     * @param  drawable
     * @param  local (string)
     * @return void
     */
    private void setImage(Drawable drawable, String local)
    {
        if (local.equals("principal")){
            tela_principal_imagem.setBackgroundDrawable(drawable);
        }else if (local.equals("logo")){
            tela_principal_logo.setBackgroundDrawable(drawable);
        }

    }

    /**
     * Método para mostrar o dialog com o endereco.
     * @param  context - Contexto
     * @param  endereco (string)
     * @return void
     */
    public void showDialogEndereco(Context context, String endereco) {
        final Dialog dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_endereco);

        final TextView dialog_endereco_texto =
                (TextView) dialog.findViewById(R.id.dialog_endereco_texto);

        dialog_endereco_texto.setText(getResources().getString(R.string.endereco)+"\n\n\n"+ endereco);

        final Button btnOk =
                (Button) dialog.findViewById(R.id.dialog_endereco_botao);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    /**
     * Método obrigatorio da implementação OnMapReadyCallback, para setar a posicao
     * @param googleMap
     * @return void
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        //cria o ponto com lat e log
        TutorialsPoint = new LatLng(tarefaEscolhida.getLatitude(), tarefaEscolhida.getLongitude());
        mMap = googleMap;
        //adiciona o marcados na posição setada
        mMap.addMarker(new MarkerOptions().position(TutorialsPoint).title(tarefaEscolhida.getEndereco()));
        //move ao zoom até o ponto definido
        mMap.moveCamera(CameraUpdateFactory.newLatLng(TutorialsPoint));
        //indica o nivel de zoom
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(TutorialsPoint, 14.0f));
    }


    /**
     * Método para download de uma imagem (AsyncTask)
     * @return void
     */
    public class DownloadImage extends AsyncTask<String, Integer, Drawable> {
            ImageView view;
            String local;
            public DownloadImage(String local){
                this.local = local;
            }

             public DownloadImage(ImageView view,String local){
                this.view = view;
                this.local = local;
            }

        @Override
        protected void onPreExecute() {
            if (local.equals("principal")){
                tela_principal_pb_principal.setVisibility(View.VISIBLE);
            }else if(local.equals("logo")){
                tela_principal_pb_logo.setVisibility(View.VISIBLE);
            }
            super.onPreExecute();
        }

        @Override
        protected Drawable doInBackground(String... arg0) {
                return downloadImage(arg0[0]);
            }

        protected void onPostExecute(Drawable image) {
                setImage(image, local);
                if (local.equals("principal")){
                    tela_principal_pb_principal.setVisibility(View.GONE);
                }else if (local.equals("logo")){
                    tela_principal_pb_logo.setVisibility(View.GONE);
                }else if (local.equals("comentario")){
                    //Cria uma imagem arredondada
                    //RoundImage roundedImage = new RoundImage(Utils.drawableToBitmap(image));
                    view.setImageDrawable(image);
                }
            }

        //Download da foto passando a url
        private Drawable downloadImage(String _url) {
                //Prepare to download image
                URL url;
                BufferedOutputStream out;
                InputStream in;
                BufferedInputStream buf;

                //BufferedInputStream buf;
                try {
                    url = new URL(_url);
                    in = url.openStream();

                    buf = new BufferedInputStream(in);

                    Bitmap bMap = BitmapFactory.decodeStream(buf);
                    if (in != null) {
                        in.close();
                    }
                    if (buf != null) {
                        buf.close();
                    }

                    return new BitmapDrawable(bMap);

                } catch (Exception e) {
                    Log.e("Error reading file", e.toString());
                }

                return null;
            }
        }

    //Tratamento da pressão do botão de voltar do aparelho
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getBaseContext(), Tela_lista.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        finish();

        super.onBackPressed();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CALL_PHONE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    permissionListner(Manifest.permission.CALL_PHONE);

                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
                        showDialogOK(getResources().getString(R.string.aviso_permissao_denegada),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which) {
                                            case DialogInterface.BUTTON_POSITIVE:
                                                ActivityCompat.requestPermissions(TelaPrincipal.this,
                                                        new String[]{Manifest.permission.CALL_PHONE},
                                                        CALL_PHONE);
                                                break;
                                            case DialogInterface.BUTTON_NEGATIVE:
                                                break;
                                        }
                                    }
                                });
                    } else {

                        showDialogOK(getResources().getString(R.string.aviso_permissao_config),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which) {
                                            case DialogInterface.BUTTON_POSITIVE:
                                                break;
                                        }
                                    }
                                });
                    }
                }
                return;
            }

        }
    }


    private void permissionListner(String id) {
        int hasWriteContactsPermission;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            switch (id) {
                case Manifest.permission.CALL_PHONE:
                    hasWriteContactsPermission = checkSelfPermission(Manifest.permission.CALL_PHONE);
                    if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, CALL_PHONE);
                        return;
                    }else{
                        startActivity(intentCall);
                    }

                    break;

            }
        }
    }


    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }


}
