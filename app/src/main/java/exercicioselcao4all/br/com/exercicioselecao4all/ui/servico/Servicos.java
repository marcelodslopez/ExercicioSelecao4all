package exercicioselcao4all.br.com.exercicioselecao4all.ui.servico;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import exercicioselcao4all.br.com.exercicioselecao4all.R;

public class Servicos extends AppCompatActivity {
    TextView toolbar_title_servicos;
    ImageView toolbar_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicos);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_servicos);
        setSupportActionBar(toolbar);

        Window window = Servicos.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Servicos.this.getResources().getColor(R.color.colorPrimary));
        }

        toolbar_title_servicos = (TextView) toolbar.findViewById(R.id.toolbar_title_servicos);
        toolbar_back = (ImageView) toolbar.findViewById(R.id.toolbar_back);
        toolbar_title_servicos.setText("Tela de serviços");

        //Escuta do botão de voltar na toolbar
        toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(Servicos.this, R.anim.image_click));
                finish();
                overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        super.onBackPressed();
    }

}
