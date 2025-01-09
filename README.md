# E-commerce API

Esta é uma API para um e-commerce desenvolvido com Java e o framework Spring, projetado para gerenciar informações de pedidos, produtos e compradores.

---

## Funcionalidades

- **Pedidos (Order)**:  
  - Criar pedidos  
  - Buscar pedidos pelo ID  
  - Listar todos os pedidos  
  - Atualizar pedidos  
  - Deletar pedidos pelo ID  

- **Produtos (Product)**:  
  - Criar produtos  
  - Buscar produtos pelo ID, pelo nome e listar pela loja  
  - Listar todos os produtos  
  - Atualizar produtos  
  - Deletar produtos pelo ID  

---

## Tecnologias e Dependências

O projeto utiliza as seguintes tecnologias:  

- **Spring Boot**:  
  - `spring-boot-starter-data-jpa`: Persistência e manipulação de dados.  
  - `spring-boot-starter-web`: Criação de APIs RESTful.  
  - `spring-boot-devtools`: Ferramentas de desenvolvimento.  

- **Banco de Dados**:  
  - PostgreSQL: Banco de dados relacional.  

- **Documentação**:  
  - `springdoc-openapi-starter-webmvc-ui`: Geração automática da documentação API com Swagger.  

- **Outras**:  
  - Lombok: Redução de código boilerplate.  

---

## Estrutura de Pastas

```plaintext
src/
  main/
    java/
      org.example.ecommerce/
        config.swagger/
          OpenAPIConfig              # Configuração do Swagger
        controller/
          OrderController            # Controlador para a entidade Order
          ProductController          # Controlador para a entidade Product
        dto/
          request/
            ProductRequestDTO        # DTO para requisições relacionadas a produtos
            OrderRequestDTO          # DTO para requisições relacionadas a pedidos
          response/
            ProductResponseDTO       # DTO para respostas relacionadas a produtos
            OrderResponseDTO         # DTO para respostas relacionadas a pedidos
        model/
          Order                      # Modelo representando um pedido
          Product                    # Modelo representando um produto
        repository/
          OrderRepository            # Repositório JPA para a entidade Order
          ProductRepository          # Repositório JPA para a entidade Product
        service/
          OrderService               # Serviço para lógica de negócios de pedidos
          ProductService             # Serviço para lógica de negócios de produtos
        EcommerceApplication         # Classe principal da aplicação
    resources/
      static/                        # Diretório para arquivos estáticos (não utilizado no momento)
      templates/                     # Diretório para templates (não utilizado no momento)
      application.properties         # Arquivo de configuração da aplicação
```
## Modelo de dados
- Pedido (Order):
  - id = UUID
  - products = Product
  - expectedDate (Data esperada) = LocalDate
  - buyer (comprador) = String
  - total = double
- Produto (Product):
  - id = UUID
  - name = String -
  - soldBy (vendido por) = String
  - price (preço) = double
  - description (descrição) = String
## Configuração
### Pré-requisitos
- Java 21+
- Maven 4.0+
- PostgreSQL
- Spring Boot 3.4.0+

### Passos para configuração

1. **Clone este repositório**: 
`git clone git@github.com:wendoxx/e-commerce-api.git `

2. **Configure as credenciais do banco de dados**:
```
   spring.datasource.url=jdbc:postgresql://localhost:5432/<NOME_DO_BANCO>
   spring.datasource.username=<USUARIO> 
   spring.datasource.password=<SENHA> ```
