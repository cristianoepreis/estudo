package com.estudos.desafio.tratamento_erros.infrastructure.exceptions.handler;

import java.time.LocalDateTime;

public class ErrorResponse {

    private String mensagem;
    private LocalDateTime data;
    private int status;
    private String path;

    public ErrorResponse(String mensagem, LocalDateTime data, int status, String path) {
        this.mensagem = mensagem;
        this.data = data;
        this.status = status;
        this.path = path;
    }

    public ErrorResponse() {
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
