# language: pt

Funcionalidade: upload de guias por meio do services do lumis

    @AUTOMATED @env_ms-arquivos @path_url:prestadores 
    Cenário: upload de guias validas
        Dado que o client side gera o protocolo "valido" pelo endpoint "/guias/arquivos/url-assinada"
        #Quando o client faz upload do arquivo "valido"
        #Entao o status code deve ser "200"
        #E retonar o codigo status "FATURADO"

   

