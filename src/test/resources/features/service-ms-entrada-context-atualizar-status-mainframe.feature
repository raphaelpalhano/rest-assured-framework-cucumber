# language: pt

Funcionalidade: service-ms-entrada-context-atualizar-status-mainframe

    endpoint para atualizar os status das criticas do processamento do mainframe


    @service_ms-entradas-guias @path_url:protocolo @AUTOMATED @VSCM-3029
    Esquema do Cenário: Atualizando os status do returno do mainframe
        Dado que o client side gera o protocolo "valido"
        Quando que o client side atualiza o protocolo "valido"
        E que o client side faz request com payload do mainframe "<payload-mainframe>"
        Então deve retornar o statuscode <status-code>
        
        Exemplos:
        |payload-mainframe			|status-code|
        |processado-sucesso			| 204		|
        |processando	    		| 204		|
        |processado-erro-total		| 204		|
        |processado-erro-parcial	| 204		|

    @service_ms-entradas-guias @path_url:status_mainframe @AUTOMATED @VSCM-3029
    Esquema do Cenário: Validacoes do payload do endpoint contas-medicas
        Dado que o client side faz request com payload com erro do mainframe "<payload-mainframe>"
        Então deve retornar o statuscode <status-code>
        
        Exemplos:
        |payload-mainframe			|status-code|
        |processado-sem-status-lote	| 400		|
        |processado-sem-chave-guia	| 400		|
        |processado-fora-do-enum	| 400		|

