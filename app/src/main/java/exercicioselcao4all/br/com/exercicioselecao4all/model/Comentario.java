package exercicioselcao4all.br.com.exercicioselecao4all.model;

import java.io.Serializable;

/**
 * Created by Marcelo on 04/06/2016.
 */
public class Comentario implements Serializable{
    String id_tarefa;
    String urlFoto;
    String nome;
    int nota;
    String titulo;
    String comentario;


    public Comentario(String urlFoto, String nome, int nota, String titulo, String comentario) {
        this.urlFoto = urlFoto;
        this.nome = nome;
        this.nota = nota;
        this.titulo = titulo;
        this.comentario = comentario;
    }


    public String getId_tarefa() {
        return id_tarefa;
    }

    public void setId_tarefa(String id_tarefa) {
        this.id_tarefa = id_tarefa;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
