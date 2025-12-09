package br.com.marmitaria.service.email;


public interface EmailService {
    void enviarEmail(String destinatario, String assunto, String mensagem);
}
