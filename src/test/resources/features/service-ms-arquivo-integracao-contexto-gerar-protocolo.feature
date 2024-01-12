# language: pt
Funcionalidade: service-ms-integracao-guias-contexto-gerar-protocolo

    Serviço ms-arquivo-integracao gerar protocolo

    Contexto: Que eu tenho o servico ms-arquivo-integracao
        Dado que configuro o microsservico "ms-integracao"


    @AUTOMATED
    Cenário: gerando protocolo valido
        Dado que o client side gera o protocolo "valido" pela url assinada
        Entao deve retornar o statuscode 200


    @AUTOMATED
    Cenário: Gerando protocolo com prestador invalido
        Dado que o client side gera o protocolo "codigo-prestador-invalido" pela url assinada
        Entao deve retornar o statuscode 403


    @AUTOMATED
    Cenário: Gerando protocolo com quantidade de arquivo invalido
        Dado que o client side gera o protocolo "quantidade-arquivo-invalido" pela url assinada
        Entao deve retornar o statuscode 400