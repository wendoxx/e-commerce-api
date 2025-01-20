# E-commerce API

Esta é uma API para um e-commerce desenvolvido com Java e o framework Spring, projetado para gerenciar informações de pedidos, produtos e compradores.

---

## Funcionalidades

- **Pedidos (Order)**:  
  - Criar pedidos (DEVE ESTAR AUTENTICADO)
  - Buscar pedidos pelo ID  
  - Listar todos os pedidos  
  - Atualizar pedidos (APENAS ADMIN)
  - Deletar pedidos pelo ID (APENAS ADMIN)  

- **Produtos (Product)**:  
  - Criar produtos (APENAS ADMIN)
  - Buscar produtos pelo ID, pelo nome e listar pela loja  
  - Listar todos os produtos  
  - Atualizar produtos (APENAS ADMIN)
  - Deletar produtos pelo ID (APENAS ADMIN)  

- **Login / Registro**:  
  - Registrar usuários  
  - Fazer login de usuários   

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

- **Segurança**:
  - `spring-boot-starter-security`: Configuração e gerenciamento de autenticação e autorização. 
  - `java-jwt (v4.4.0)`: Biblioteca para criação, assinatura e verificação de JSON Web Tokens (JWT).


- **Outras**:  
  - Lombok: Redução de código boilerplate.
  - Log4j: Adicionar Logs no console

---

## Estrutura de Pastas

```plaintext
src/
  main/
    java/
      org.example.ecommerce/
        controller/
          AuthController                # Controlador para a Login e Registro de usuários
          OrderController               # Controlador para a entidade Order
          ProductController             # Controlador para a entidade Product
        dto/
          request/
            ProductRequestDTO           # DTO para requisições relacionadas a produtos
            OrderRequestDTO             # DTO para requisições relacionadas a pedidos
            LoginRequestDTO             # DTO para requisições relacionadas a Logins de usuários
            RegisterRequestDTO          # DTO para requisições relacionadas a Registros de usuários
          response/
            ProductResponseDTO          # DTO para respostas relacionadas a produtos
            OrderResponseDTO            # DTO para respostas relacionadas a pedidos
            LoginResponseDTO            # DTO para respostas relacionadas a Logins de usuários 
            RegisterResponseDTO         # DTO para respostas relacionadas a Registros de usuários
        infra/
          config/
            OrderListIsEmptyException   # Exceção para caso a lista de pedidos esteja vazia
            OrderNotFoundException      # Exceção para caso o pedido não seja encontrado
            ProductListIsEmptyException # Exceção para caso a lista de produtos esteja vazia
            ProductNotFoundException    # Exceção para caso o produto não seja encontrado
          security/
            AuthService                 # Serviço para lógica de autenticação
            CustomUserDetailsService    # Implementação de UserDetailsService para carregar os detalhes do usuário a partir do banco de dados.
            SecurityConfig              # Configuração de segurança do Spring Security, definindo regras de acesso e integração com JWT.
            SecurityFilter              # Filtro personalizado para validação de tokens JWT em cada requisição.
            TokenService                # Serviço responsável por criar, assinar, verificar e renovar tokens JWT.
            UserRole                    # Enum que define os papéis de usuário (e.g., ADMIN, USER) para controle de permissões.
        model/
          User                          # Modelo representando um usuário
          Order                         # Modelo representando um pedido
          Product                       # Modelo representando um produto
        repository/
          OrderRepository               # Repositório JPA para a entidade Order
          ProductRepository             # Repositório JPA para a entidade Product
          UserRepository                # Repositório JPA para a entidade User
        service/
          OrderService                  # Serviço para lógica de negócios de pedidos
          ProductService                # Serviço para lógica de negócios de produtos
        utils/
          OpenAPIConfig                 # Configuração do Swagger
        EcommerceApplication            # Classe principal da aplicação
    resources/
      static/                           # Diretório para arquivos estáticos (não utilizado no momento)
      templates/                        # Diretório para templates (não utilizado no momento)
      application.properties            # Arquivo de configuração da aplicação
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
  - name = String
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
   spring.datasource.password=<SENHA>
```

# **Passos para Utilização da API**

## **1. Registrar um Usuário**

Para acessar as funcionalidades da API, você precisa registrar um usuário. Ao se registrar, você pode escolher se será um usuário comum(`USER`) ou um administrador (`ADMIN`).

### **Endpoint:**  
`POST /api/v1/register`

### **Exemplo de requisição:**
```json
{
  "username": "admin_user",
  "password": "secure_password",
  "role": "ADMIN"
}
```

## **2. Login do Usuário**

Para pegar o token (Você precisará caso usará as funcionalidades de um ADMIN) você deve fazer o Login. Para isso, deverá informar seu usuário e senha:

### **Endpoint:**  
`POST /api/v1/login`

### **Exemplo de requisição:**
```json
{
  "username": "admin_user",
  "password": "secure_password"
}
```
## **3. Criar produto**

Para criar um produto você deve estar logado e ter o token de ADMIN. Então deverá fazer a seguinte requisição:

### **Endpoint:**  
`POST /api/v1/product/save-product`

### **Exemplo de requisição:**
```json
{
  "name": "Smartphone XYZ",
  "soldBy": "TechStore",
  "price": 2999.90,
  "description": "Um smartphone de última geração."
}
```
## **4. Criar produto**
Para criar um pedido você precisará apenas estar autenticado. Com isso, você deve fazer a seguinte requisição:

### **Endpoint:**  
`POST /api/v1/order/save-order`

### **Exemplo de requisição:**
```json
{
  "products": [
    "123e4567-e89b-12d3-a456-426614174000"
  ],
  "expectedDate": "2025-01-17"
}
```
