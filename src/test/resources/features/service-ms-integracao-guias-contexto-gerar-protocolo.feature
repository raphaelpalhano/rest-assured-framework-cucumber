# language: pt
Funcionalidade: service-ms-integracao-guias-contexto-gerar-protocolo

    Serviço ms-arquivo-integracao gerar protocolo

    @AUTOMATED @env_ms-integracao @path_url:prestadores 
    Cenário: gerando protocolo valido
        Dado que o client side gera o protocolo "valido" pelo endpoint "/guias/arquivos/url-assinada"
        Entao deve retornar o statuscode 200


    @AUTOMATED @env_ms-integracao @path_url:prestadores
    Cenário: Gerando protocolo com prestador invalido
        Dado que o client side gera o protocolo "codigo-prestador-invalido" pelo endpoint "/guias/arquivos/url-assinada"
        Entao deve retornar o statuscode 403


    @AUTOMATED @env_ms-integracao @path_url:prestadores
    Cenário: Gerando protocolo com quantidade de arquivo invalido
        Dado que o client side gera o protocolo "quantidade-arquivo-invalido" pelo endpoint "/guias/arquivos/url-assinada"
        Entao deve retornar o statuscode 400