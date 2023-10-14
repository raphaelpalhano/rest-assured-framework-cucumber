#language: pt
#encondig: UTF-8
Funcionalidade: movimentações na API Pet


	 @env_pet @path_url:inclusao
  Cenário: Cadastrar novo pet
	Dado que realizo a inserção do pet com o payload "add_pet"
    Entao o status code deve ser "200"
		
		
	
	
	@env_pet @path_url:alteracao
  Cenário: Alterar informacoes do pet
	Dado que realizo a alteração do pet com payload "petAlteracao"
  	Entao o status code deve ser "200"
  	E o status do pet deve estar "sold"
	
	
	@env_pet @path_url:consultaStatus
	Cenário: Consulta lista de pets cadastrados na API
	  Dado a requisicao de consulta por status "sold"
	  Entao o status code deve ser "200"
	 
	
	