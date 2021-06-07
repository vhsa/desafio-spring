package br.com.desafiospring.dtos;

public class Response<T> {
    private T data;
    private String erros;

    public Response(T data) {
        this.data = data;
    }

    public Response(String erros) {
        this.erros = erros;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErros() {
        return erros;
    }

    public void setErros(String erros) {
        this.erros = erros;
    }
}
