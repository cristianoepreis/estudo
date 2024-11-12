package com.estudos.desafio.tratamento_erros.infrastructure.configs.error;

import com.estudos.desafio.tratamento_erros.business.service.EmailService;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class NotificacaoErroAspect {

    private final EmailService emailService;

    public NotificacaoErroAspect(EmailService emailService) {
        this.emailService = emailService;
    }

    @Pointcut("@within(com.javanautas.fakeapius.infrastructure.configs.error.NotificacaoErro) || @annotation(com.javanautas.fakeapius.infrastructure.configs.error.NotificacaoErro)")
    public void notificacaoErroPointCut() {}

    @AfterThrowing(pointcut = "notificacaoErroPointCut()", throwing = "e")
    public void notificacaoErro(final Exception e){
        emailService.enviaEmailExcecao(e);
    }


}