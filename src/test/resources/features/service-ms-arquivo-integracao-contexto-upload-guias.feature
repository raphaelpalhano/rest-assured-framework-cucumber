# language: pt
@env_homolog
Funcionalidade: service-ms-integracao-guias-contexto-gerar-protocolo

    Serviço ms-arquivo-integracao upload de arquivos


    # Guias sadt

    @AUTOMATED @service_ms-integracao @path_url:prestadores
    Cenário: Upload de guias validas
        Dado que o client side gera o protocolo "valido" pelo endpoint "/guias/arquivos/url-assinada"
        Quando o client side faz upload do arquivo tipo "guias-sadt-valido" com os dados
        """
        ans:codigoPrestadorNaOperadora=100000010577
        """
        Entao retonar o codigo protocolo registrado


    @AUTOMATED @env_ms-integracao @path_url:prestadores 
    Cenário: Upload de guias com codigo do prestador invalido
        Dado que o client side gera o protocolo "valido" pelo endpoint "/guias/arquivos/url-assinada"
        Quando o client side faz upload do arquivo tipo "guias-sadt-invalido" com os dados
        """
        ans:codigoPrestadorNaOperadora=100000010279
        ans:dataRegistroTransacao=2023-10-06
        """
        Entao retornar o codigo status do protocolo "BLOQUEADO"


    @AUTOMATED @env_ms-integracao @path_url:prestadores
    Cenário: Upload de guias com codigo beneficiario e data dataRegistroTransacao invalidos
        Dado que o client side gera o protocolo "valido" pelo endpoint "/guias/arquivos/url-assinada"
        Quando o client side faz upload do arquivo tipo "guias-sadt-invalido" com os dados
        """
        ans:codigoPrestadorNaOperadora=1000ad00017475
        ans:dataRegistroTransacao=2001-10-06
        """
        Entao retornar o codigo status do protocolo "BLOQUEADO"



    @AUTOMATED @env_ms-integracao @path_url:prestadores
    Cenário: Upload de guias com arquivo nao zipado
        Dado que o client side gera o protocolo "valido" pelo endpoint "/guias/arquivos/url-assinada"
        Quando o client faz upload do arquivo de um arquivo "nao-zipado" do tipo "guias-consulta-valido"
        Então retornar o codigo status do protocolo "BLOQUEADO"


    # Guias Consultas

    @AUTOMATED @env_ms-integracao @path_url:prestadores
    Cenário: Upload de guias consulta com tiss versao 3.0
        Dado que o client side gera o protocolo "valido" pelo endpoint "/guias/arquivos/url-assinada"
        Quando o client side faz upload do arquivo tipo "guias-consulta-valido" com os dados
        """
        ans:codigoPrestadorNaOperadora=100000017475
        """
        Então retonar o codigo protocolo registrado


  
    @AUTOMATED @env_ms-integracao @path_url:prestadores
    Cenário: Upload de de guias com formato do xml fora do padrao tiss
        Dado que o client side gera o protocolo "valido" pelo endpoint "/guias/arquivos/url-assinada"
        Quando o client side faz upload do arquivo tipo "guias-consulta-formato-invalido" com os dados
        """
        ans:codigoPrestadorNaOperadora=100000010577
        ans:tipoTransacao=ENVIO_LOTE_GUIAS
        """
        Entao retonar o codigo protocolo registrado
        E retornar o codigo status do protocolo "BLOQUEADO"


