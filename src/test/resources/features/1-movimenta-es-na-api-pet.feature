# language: pt
Funcionalidade: movimentações na API Pet

    @AUTOMATED @env_pet @path_url:inclusao @MT-14 
    Cenário: Cadastrar novo pet
        
        Dado que realizo a inserção do pet com o payload "add_pet"
        Entao o status code deve ser "200"

    @AUTOMATED @env_pet @path_url:alteracao @MT-14 
    Cenário: Alterar informacoes do pet
        
        Dado que realizo a alteração do pet com payload "petAlteracao"
        Entao o status code deve ser "200"
        E o status do pet deve estar "sold"

    @AUTOMATED @env_pet @path_url:consultaStatus @MT-14 
    Cenário: Consulta lista de pets cadastrados na API
        
        Dado a requisicao de consulta por status "sold"
        Entao o status code deve ser "200"

