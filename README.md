# O problema

Há uma lanchonete de bairro que está expandindo devido seu grande sucesso. Porém o estabelecimento não estava preparado para sua expansão, com isso os pedidos sem um sistema que os gerencie se tornaram confusos e complicados, os funcionários começaram a perder os papéis que eram anotados os pedidos, a cozinha não tinha um direcionamento claro do que era cada pedido e os próprios antendentes não conseguiam lidar com a demanda total de clientes.

Com o crescimento da lanchonete e a adoção de uma arquitetura baseada em microsserviços, cada domínio do sistema passou a ser isolado para garantir escalabilidade, manutenção e evolução independentes.

## Solução Proposta

Este serviço é responsável por gerenciar os cadastros essenciais do sistema, incluindo categorias de produtos, produtos disponíveis no cardápio e clientes. Ele também expõe endpoints públicos para o totem de autoatendimento, permitindo a exibição de informações atualizadas sobre os itens disponíveis para pedido.

Ao centralizar esses cadastros em um único microsserviço, garantimos uma fonte única de verdade para os dados exibidos ao cliente final, simplificando a manutenção e promovendo a consistência entre os sistemas internos e interfaces públicas.

#### Principais Responsabilidades:
- Criação de pedidos realizados pelo cliente no totem ou outro canal
- Busca de pedidos por ID, permitindo o rastreamento individual do pedido
- Atualização do status do pedido ao longo de seu ciclo de vida (ex: Recebido, Em Preparo, Pronto, Finalizado)

## Desenho Técnico Microsserviços
<div align="center">
  <img src="https://i.ibb.co/dsPzD37s/arquitetura.png" alt="Arquitetura de Microsserviços">
</div>

## Evidência de testes no SonarCloud
<div align="center">
  <img src="https://i.ibb.co/3ymjcJhf/order-service.jpg" alt="Evidência de testes no SonarCloud">
</div>


## Tecnologias
- **Spring Boot**: Framework para construção de aplicações Java.
    - `spring-boot-starter-web`: Para construir aplicações web.
    - `spring-boot-starter-data-jpa`: Para integração com JPA (Java Persistence API).
    - `spring-boot-starter-validation`: Para validação de dados.
- **MongoDB**: é um banco de dados NoSQL orientado a documentos, que armazena os dados em formato JSON/BSON. Ele oferece alta flexibilidade na modelagem de dados, sendo ideal para aplicações que exigem agilidade na evolução do modelo, como sistemas baseados em microsserviços. Por ser um banco esquemaless (sem schema fixo), permite armazenar documentos com estruturas diferentes na mesma coleção, além de suportar alta escalabilidade horizontal e operações reativas quando integrado com frameworks como Spring WebFlux.
- **Lombok**: Biblioteca para reduzir o código boilerplate.
- **Springdoc OpenAPI**: Para gerar documentação da API.
- **Kubernates**: Para orquestrar contêineres de maneira eficiente e automatizada
- **Terraform**: Para gerenciamento do nosso IaC
- **AWS**: Toda a nossa infraestrutura em nuvem.

## Requisitos

- Java 21
- Docker
- Minikube e Kubernetes configurados localmente

## Estrutura do Projeto

- payment-service: responsável por toda a gestão de pagamentos, incluindo a integração com o gateway do Mercado Pago e o processamento de transações.
- register-service: gerencia os cadastros do sistema, como clientes, produtos e usuários.
- production-service: opera no ambiente da cozinha, sendo responsável por acompanhar e atualizar o status dos pedidos em produção.
- order-service: lida com a criação e o gerenciamento dos pedidos realizados pelos clientes, atuando como ponto central no fluxo de consumo.

## Documentação da API

- Após a aplicação estar em execução, a documentação estará disponível em:

```
http://<url_service>/swagger-ui/index.html
```

## Considerações Finais

Este projeto utiliza o Minikube para rodar uma instância local do Kubernetes, que gerencia a implantação dos serviços de backend e banco de dados. Certifique-se de ter o Minikube e o kubectl configurados corretamente para evitar problemas na execução.
