# language: pt
Funcionalidade: service-ms-entrada-context-buscar-protocolo

    endpoints para buscar protocolo pelo idDocument, cod prestador

    Contexto: Que eu tenho o servico ms-entrada
        Dado que configuro o microsservico "ms-entradas-guias"


    @AUTOMATED @VSCM-3351
    Esquema do Cenário: O client side faz buscas pelo idDocument
        Dado que o client side gera o protocolo "valido"
        Quando que o client side atualiza o protocolo "valido"
        E que o client side busca o protocolo pelo idDocument "<idDocument>"
        Entao deve retornar o statuscode <status>
        #E respeitar o schema "<schema>"

        Exemplos:
            |idDocument			 		  | status 	  | schema 						 |
            |                     		  | 	200	  | protocolos,buscar-protocolo  |
            |asd12219wuj91211133112		  | 	404	  | 						     |

    @AUTOMATED @VSCM-3351-filtro
    Esquema do Cenário: O client side faz buscas pelos filtros do protocolo
        Dado que o client side gera o protocolo "valido"
        Quando que o client side atualiza o protocolo "valido"
        E que o client side busca o protocolo usando o "<prestador>" com filtro "<filtro>"
        Entao deve retornar o statuscode <status>
       # E respeitar o schema "<schema>"
        
        Exemplos:
        |prestador			 |    filtro 																									            | status  | schema 						  |
        |valido			     | 	codigo-status=BLOQUEADO&periodo-inicio-envio=2023-11-19&periodo-fim-envio=2023-12-19		 				            |	206	  | protocolos,buscar-protocolos  |
        |valido			     | 	codigo-status=FATURADO&periodo-inicio-envio=2023-11-30&periodo-fim-envio=2023-12-30		 				            |	206	  | protocolos,buscar-protocolos  |
        |valido			     | 	codigo-status=TIMEOUT&periodo-inicio-envio=2023-11-19&periodo-fim-envio=2023-12-19		 					            |	206	  | protocolos,buscar-protocolos  |
        |valido			     | 	codigo-status=BLOQUEADO&periodo-inicio-envio=2023-11-19&periodo-fim-envio=2023-12-19&pagina=1&tamanho-pagina=1 		 	|	206	  | protocolos,buscar-protocolos  |
        |invalido			 | 	codigo-status=BLOQUEADO&periodo-inicio-envio=2023-11-19&periodo-fim-envio=2023-12-19 				 	                |	206   | 							  |
        |valido			     | 	tipo-guia=S&periodo-inicio-envio=2023-11-19&periodo-fim-envio=2023-12-19		 							            |	206	  | protocolos,buscar-protocolos  |
        |valido			     | 	tipo-guia=H&periodo-inicio-envio=2023-11-19&periodo-fim-envio=2023-12-19		 							            |	206	  | protocolos,buscar-protocolos  |


    @AUTOMATED  @VSCM-3351
    Esquema do Cenário: O client side faz buscas pelo protocolo e codigo prestador
        Dado que o client side gera o protocolo "valido"
        Quando que o client side atualiza o protocolo "valido"
        E que o client side busca o protocolo pelo protocolo "<protocolo>" e prestador "<prestador>"
        Entao deve retornar o statuscode <status>
       # E respeitar o schema "<schema>"
        
        Exemplos:
        |protocolo |prestador	 | status | schema 						 |
        | valido   |valido       | 	200	  | protocolos,buscar-protocolo  |
        | valido   |invalido     | 	404	  | 	 						 |
        | invalido |valido       | 	404	  | 	 						 |


    @VSCM-3617
    Esquema do Cenario: O client side faz buscas pelo historico de status do protocolo
        Dado que o client side gera o protocolo "valido"
        Quando que o client side atualiza o protocolo "valido"
        E que o client side faz request pelo endpoint "status/" com protocolo "<condicao>"
        Entao deve retornar o statuscode <status>
        #E deve ter o historico de status ate "<statusProtocolo>"
        #E respeitar o schema "<schema>"

        Exemplos:
            |condicao		  	| status 	     |statusProtocolo                   | schema 						 		 |
            |       		  	| 	200	  	     | historico-protocolo-atualizar    | protocolos,historico-status-protocolo  |
            |4213213213		  	| 	404	  	     |                                   |                                        |
            |1aa12312xxcm   	| 	400	         |                                   | 						    		     |


    @VSCM-3852
    Esquema do Cenario: O client side faz download do xml de guias
        Dado que configuro o microsservico "ms-integracao"
        Dado que o client side gera o protocolo "valido" pela url assinada
        Quando o client side faz upload do arquivo tipo "guias-sadt-valid-4" com os dados
        """
        ans:codigoPrestadorNaOperadora=100000010577
        ans:dataRegistroTransacao=2023-11-10
        ans:dataSolicitacao=2023-10-10
        """
        E que configuro o microsservico "ms-entradas-guias"
        E que o client side faz request pelo endpoint "download/" com protocolo "<condicao>"
        Entao deve retornar o statuscode <status>


        Exemplos:
            |condicao		  	| status 	     |
            |       		  	| 	200	  	     |
            |9999911999919		| 	404	  	     |
            |1aa12312xxcm   	| 	400	         |


    @VSCM-3613
    Esquema do Cenario: O client side faz consulta do protcolo pelo codigo protocolo
        Dado que o client side gera o protocolo "valido"
        Quando que o client side atualiza o protocolo "valido"
        E que o client side faz request pelo endpoint "codigo/" com protocolo "<condicao>"
        Entao deve retornar o statuscode <status>
        #E respeitar o schema "<schema>"

        Exemplos:
            |condicao		  	| status 	     |  schema 						 		 |
            |       		  	| 	200	  	     | protocolos,protocolo-codigo-protocolo |
            |4213213213		  	| 	404	  	     |                                       |
            |1aa12312xxcm   	| 	400	         |                                       |


    @3615
    Esquema do Cenario: O client side faz busca por protocolo usando grd
        Dado que o client side busca o protocolo usando o "valido" com filtro "codigo-status=FATURADO&periodo-inicio-envio=2023-11-30&periodo-fim-envio=2023-12-30"
        Quando o client side faz request com grd "<grd>" com prestador "<prestador>"
        Entao deve retornar o statuscode <status>
        #E respeitar o schema "<schema>"

        Exemplos:
            |grd		|prestador		  	| status 	     |  schema 						 		 |
            |			| 100000010577    	| 	200	  	     | protocolos,protocolo-grd				 |
            | 231313	|4213213213		  	| 	404	  	     |                                       |
            | sad11		|1aa12312xxcm   	| 	400	         |                                       |

    @3615
    Esquema do Cenario: O client side busca de detalhamento das guias
        Dado que o client side gera o protocolo "valido"
        Quando que o client side atualiza o protocolo "valido"
        E o client side faz request no endpoint "guias" com idDocument "<idDocument>"
        Entao deve retornar o statuscode <status>
        #E respeitar o schema "<schema>"

        Exemplos:
            |idDocument	|status 	     |  schema 						 		 |
            |			| 	200	  	     | protocolos,guias-detalhes    	     |
            | 231313	| 	404	  	     |                                       |


    @3615
    Esquema do Cenario: O client side busca de criticas das guias
        Dado que o client side gera o protocolo "valido"
        Quando que o client side atualiza o protocolo "valido"
        E o client side faz request no endpoint "criticas" com idDocument "<idDocument>"
        Entao deve retornar o statuscode <status>
       # E respeitar o schema "<schema>"

        Exemplos:
            |idDocument	| status 	     |  schema 						 		 |
            |			| 	200	  	     | protocolos,protocolo-criticas	     |
            | 231313	| 	404	  	     |                                       |
