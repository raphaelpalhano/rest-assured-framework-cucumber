# language: pt
Funcionalidade: service-ms-integracao-guias-contexto-gerar-protocolo

    Serviço ms-arquivo-integracao upload de arquivos

    Contexto: Que eu tenho o servico ms-arquivo-integracao
        Dado que configuro o microsservico "ms-integracao"


    @AUTOMATED
    Cenário: Upload de guias sadt tiss 3.05 validas
        Dado que o client side gera o protocolo "valido" pela url assinada
        Quando o client side faz upload do arquivo tipo "guias-sadt-valid-3" com os dados
        """
        ans:codigoPrestadorNaOperadora=100000010577
        ans:dataRegistroTransacao=2023-11-10
        ans:dataSolicitacao=2023-10-10
        """
        Entao retonar o codigo protocolo registrado

    @AUTOMATED
    Cenário: Upload de guias sadt tiss 4.0 validas
        Dado que o client side gera o protocolo "valido" pela url assinada
        Quando o client side faz upload do arquivo tipo "guias-sadt-valid-4" com os dados
        """
        ans:codigoPrestadorNaOperadora=100000010577
        ans:dataRegistroTransacao=2023-11-10
        ans:dataSolicitacao=2023-10-10
        """
        Entao retonar o codigo protocolo registrado

    @AUTOMATED
    Cenário: Upload de guias consulta tiss 3.05 validas
        Dado que o client side gera o protocolo "valido" pela url assinada
        Quando o client side faz upload do arquivo tipo "guias-consulta-valid-3" com os dados
        """
        ans:codigoPrestadorNaOperadora=100000010577
        ans:dataRegistroTransacao=2023-11-10
        ans:dataSolicitacao=2023-10-10
        """
        Entao retonar o codigo protocolo registrado


    #@AUTOMATED
    #Cenário: Upload de guias arquivo maior que 1mb
    #    Dado que o client side gera o protocolo "valido" pela url assinada
    #    Quando o client side faz upload do arquivo tipo "internacao-arquivo-grande" com os dados
    #    """
    #    ans:codigoPrestadorNaOperadora=100000010577
    #    ans:dataRegistroTransacao=2023-12-10
    #    ans:dataSolicitacao=2023-11-10
    #    ans:dataAutorizacao=2023-11-01
    #    ans:dataExecucao=2023-11-11
    #    ans:dataValidadeSenha2023-11-01
    #    ans:dataInicioFaturamento=2023-11-20
    #    ans:dataFinalFaturamento=2023-11-25
    #    """
    #    Entao retonar o codigo protocolo registrado

    @AUTOMATED
    Cenário: Upload de guias com codigo do prestador invalido
        Dado que o client side gera o protocolo "valido" pela url assinada
        Quando o client side faz upload do arquivo tipo "guias-sadt-invalido" com os dados
        """
        ans:codigoPrestadorNaOperadora=100000010279
        ans:dataRegistroTransacao=2023-11-10
        ans:dataSolicitacao=2023-10-10
        """
        Entao retornar o codigo status do protocolo "BLOQUEADO"


    @AUTOMATED
    Cenário: Upload de guias com codigo beneficiario e data dataRegistroTransacao invalidos
        Dado que o client side gera o protocolo "valido" pela url assinada
        Quando o client side faz upload do arquivo tipo "guias-sadt-invalido" com os dados
        """
        ans:codigoPrestadorNaOperadora=100000010578
        ans:dataRegistroTransacao=2023-11-10
        """
        Entao retornar o codigo status do protocolo "BLOQUEADO"


    @AUTOMATED
    Cenário: Upload de guias com arquivo nao zipado
        Dado que o client side gera o protocolo "valido" pela url assinada
        Quando o client faz upload do arquivo de um arquivo "nao-zipado" do tipo "guias-sadt-valid-3"
        Então retornar o codigo status do protocolo "BLOQUEADO"


  
    @AUTOMATED
    Cenário: Upload de de guias com formato do xml fora do padrao tiss
        Dado que o client side gera o protocolo "valido" pela url assinada
        Quando o client side faz upload do arquivo tipo "guias-consulta-formato-invalido" com os dados
        """
        ans:codigoPrestadorNaOperadora=100000010577
        ans:tipoTransacao=ENVIO_LOTE_GUIAS
        """
        Entao retonar o codigo protocolo registrado
        E retornar o codigo status do protocolo "BLOQUEADO"
