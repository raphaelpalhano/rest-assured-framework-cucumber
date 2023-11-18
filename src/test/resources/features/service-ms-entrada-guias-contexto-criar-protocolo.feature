# language: pt

Funcionalidade: service-ms-entrada-guias-contexto-criar-protocolo

    Serviço interno do ms entrada guias para gerar o protocolo e persistir no banco de dados. Esse serviço será chamado pelo ms-integracao-tiss para gerir a vida das guias


    @AUTOMATED @env_ms-entradas-guias @path_url:protocolo @VSCM-2910
    Cenário: Criando protocolo com dados do protocolo válido
        Dado que o client side gera o protocolo "valido"
        Entao deve retornar o statuscode 201
        E respeitar o schema "protocolos,criar-protocolo"

    @AUTOMATED @env_ms-entradas-guias @path_url:protocolo @VSCM-2910
    Cenário: Criando protocolo sem o codigo prestador
        Dado que o client side gera o protocolo "sem-prestador"
        Entao deve retornar o statuscode 400

    @AUTOMATED @env_ms-entradas-guias @path_url:protocolo @VSCM-2910
    Cenário: Criando protocolo sem informar nenhum dado do protocolo
        Dado que o client side gera o protocolo "sem-dados"
        Entao deve retornar o statuscode 400

    @AUTOMATED @env_ms-entradas-guias @path_url:protocolo @VSCM-2910
    Cenário: Criando protocolo com nomearquivo inválido
        Dado que o client side gera o protocolo "nome-arquivo-invalido"
        Entao deve retornar o statuscode 400


    @AUTOMATED @env_ms-entradas-guias @path_url:protocolo @VSCM-2910
    Cenário: Criando protocolo com codigo origem invalido
        Dado que o client side gera o protocolo "codigo-origem-invalido"
        Entao deve retornar o statuscode 400


