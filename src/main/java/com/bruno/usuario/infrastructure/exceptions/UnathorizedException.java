package com.bruno.usuario.infrastructure.exceptions;

import javax.naming.AuthenticationException;

public class UnathorizedException extends AuthenticationException {

    public UnathorizedException(String mensagem){super(mensagem);}
    public UnathorizedException(String mensagem , Throwable throwable) { super(mensagem);}
}
