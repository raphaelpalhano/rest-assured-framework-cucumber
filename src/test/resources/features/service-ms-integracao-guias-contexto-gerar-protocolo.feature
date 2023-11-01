# language: pt
Funcionalidade: service-ms-integracao-guias-contexto-gerar-protocolo

    Serviço interno do ms entrada guias para gerar o protocolo e persistir no banco de dados. Esse serviço será chamado pelo ms-integracao-tiss para gerir a vida das guias
    Client side está autenticado via keycloak

    @AUTOMATED @env_ms-integracao @path_url:prestadores 
    Cenário: Upload de guias validas
        Dado que o client side gera o protocolo "valido" pelo endpoint "/guias/arquivos/url-assinada"
        Quando o client side faz upload do arquivo tipo "guias-sadt" com os dados
        """
        ans:codigoPrestadorNaOperadora=100000017475
        """
        Entao deve retornar o statuscode 200
        E retonar o codigo protocolo registrado

    @AUTOMATED @env_ms-integracao @path_url:prestadores 
    Cenário: Upload de guias com codigo do prestador invalido
        Dado que o client side gera o protocolo "valido" pelo endpoint "/guias/arquivos/url-assinada"
        Quando o client side faz upload do arquivo tipo "guias-sadt" com os dados
        """
        ans:codigoPrestadorNaOperadora=100000017474
        """
        Entao deve retornar o statuscode 200
        E retonar o codigo protocolo registrado
   
  
    @AUTOMATED @env_ms-integracao @path_url:prestadores 
    Cenário: Upload de guias com arquivo nao zipado
        Dado que o client side gera o protocolo "valido" pelo endpoint "/guias/arquivos/url-assinada"
        Quando o client faz upload do arquivo de um arquivo "nao-zipado" do tipo "guias-sadt"
        Entao deve retornar o statuscode 200
        E retornar o codigo status do protocolo "BLOQUEADO"

   

   
