# language: pt
Funcionalidade: service-ms-entrada-guias-contexto-atualizar-protocolo

    Serviço interno do ms entrada guias para atualizar o protocolo e gerar chave forte de lote das guias

    Contexto: Que eu tenho o servico ms-entrada
        Dado que configuro o microsservico "ms-entradas-guias"
        Quando que o client side gera o protocolo "valido"



    @AUTOMATED @VSCM-2967
    Cenário: Atualizando protocolo com dados do protocolo válido
        Dado que o client side atualiza o protocolo "valido"
        Entao deve retornar o statuscode 200


    @AUTOMATED  @VSCM-2967
    Cenário: Atualizando protocolo com chave forte inexistente
        Dado que o client side atualiza o protocolo "chave-forte-invalida"
        Entao deve retornar o statuscode 404

    @AUTOMATED @VSCM-2967
    Cenário: Atualizando protocolo com campos do protocolo invalido
        Dado que o client side atualiza o protocolo "invalido"
        Entao deve retornar o statuscode 400


