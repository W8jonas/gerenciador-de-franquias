# Todo

### Configuração gerais do projeto
- [x] Definir estrutura do projeto
- [x] Instalar libs do java, swing, JUnit.
- [x] Criar classes gerais (User, Produto, Compra, Endereço, Cliente)
- [x] Criar tela inicial de login
- [x] Criar tela de criar primeira conta (Owner)

### Todo do Dono
- [x] CRUD owner - Um dono deve conseguir editar suas próprias informações
  - [x] Tela de edição (Swing)
  - [x] Controller
  - [x] UseCases: Criar primeiro e único dono e editar informações.
  - [x] Persistência via ownerRepository
  - [ ] Testes: criação, remoção, edição e validação (não podem existir 2 donos)

- [x] CRUD franchises - Tela para criar, editar dados e excluir franquias
  - [x] View: Listagem das franquias
  - [x] View: Formulário com os dados da franquias (edição e remoção)
  - [x] Controller
  - [x] UseCases: CRUD Franquias, Adicionar gerente responsável (1 por franquia)
  - [x] Repo: franchiseRepository
  - [ ] Testes: cadastro, remoção, listagem, vínculo Gerente

- [ ] CRUD Managers
  - [ ] View: Listagem dos gerentes cadastrados no sistema
  - [ ] View: Formulário com os dados dos gerentes (edição e remoção)
  - [ ] Controller
  - [ ] UseCases: CRUD gerentes
  - [ ] Repo: managerRepository
  - [ ] Testes: cadastro, remoção, listagem


### Todo do Gerente
- [ ] CRUD sellers - Um gerente deve conseguir cadastrar vendedores na sua franquia
  - [ ] View: Listagem dos vendedores cadastrados no sistema (sua franquia) (em ordem de volume de vendas)
  - [ ] View: Formulário com os dados dos vendedores (edição e remoção)
  - [ ] Controller
  - [ ] UseCases: CRUD vendedores, vínculo com franquia
  - [ ] Persistência via sellerRepository
  - [ ] Testes: criação, remoção, edição e validação (gerente n pode vincular vendedor a outra franquia)

- [ ] CRUD orders - Um gerente pode cadastrar, editar e remover pedidos de compras de todos os vendedores
  - [ ] View: Listagem dos pedidos cadastrados no sistema
  - [ ] View: Formulário com os dados dos pedidos (edição e remoção)
  - [ ] Controller
  - [ ] UseCases: CRUD pedidos, avaliar solicitação de edição/remoção de algum vendedor
  - [ ] Repo: orderRepository
  - [ ] Testes: cadastro, remoção, listagem

- [ ] CRUD products - Um gerente pode cadastrar, editar e remover produtos da sua franquia
  - [ ] View: Listagem dos produtos cadastrados no sistema (em ordem de menor qnt estoque)
  - [ ] View: Formulário com os dados dos produtos (edição e remoção)
  - [ ] Controller
  - [ ] UseCases: CRUD produtos
  - [ ] Repo: productRepository
  - [ ] Testes: cadastro, remoção, listagem

- [ ] Relatórios vendas e clientes
  - [ ] View: Mostrar relatório de clientes e suas compras


### Todo do Vendedor
- [ ] CRUD order - Um vendedor pode cadastrar, editar e remover compras
  - [ ] View: Listagem das suas próprias vendas
  - [ ] View: Cadastrar novas vendas ou editar venda já realizada (solicita aprovação)
  - [ ] Controller
  - [ ] UseCases: CRUD vendas
  - [ ] Repo: orderRepository
  - [ ] Testes: cadastro, remoção, listagem

- [ ] CRUD client - Um vendedor pode cadastrar e editar clientes
  - [ ] View: Listagem dos clientes
  - [ ] View: Cadastrar novos clientes ou editar cliente já cadastrado
  - [ ] Controller
  - [ ] UseCases: CRUD clientes
  - [ ] Repo: clientRepository
  - [ ] Testes: cadastro, remoção, listagem

## Diagrama de classes:
``` mermaid
---
config:
  theme: default
  look: neo
  layout: dagre
---
classDiagram
direction TB
class User {
  +String id
  +String name
  +String email
  +String password
  +boolean active
}
class Seller {
  +int totalSalesCount
  +double totalSalesAmount
}
class Manager {
  +void assignSeller(Seller)
  +void editOrder(Order)
}
class Owner {
  +void createFranchise(String, Address, Manager)
  +void removeFranchise(Franchise)
  +void reassignManager(Franchise, Manager)
}
class Address {
  +String id
  +String street
  +String number
  +String district
  +String city
  +String state
  +String zipCode
  +String country
}
class Franchise {
  +String id
  +String name
  +Address address
  +double revenueAccumulated
  +int getTotalOrders()
  +double getAverageTicket()
}
class Product {
  +String id
  +String franchiseId
  +String name
  +String description
  +String sku
  +double price
  +int stockQty
  +boolean isLowStock(int threshold)
  +void decreaseStock(int qty)
  +void increaseStock(int qty)
}
class Customer {
  +String id
  +String name
  +String email
  +String phone
  +Address address
}
class Order {
  +String id
  +String sellerId
  +String customerId
  +String franchiseId
  +LocalDateTime createdAt
  +double total
  +List<OrderItem> orderItems
  +void addItem(Product, String, int, double)
  +void removeItem(OrderItem)
  +double calculateTotal()
}
class OrderItem {
  +String id
  +String orderId
  +String name
  +int quantity
  +double unitPrice
}
User <|-- Seller
Seller <|-- Manager
Manager <|-- Owner
```


## Organização do projeto:

```
gestor-franquias/
├─ README.md
├─ pom.xml
└─ src/
   ├─ main/
   │  └─ java/
   │    ├─ domain/
   │    │  └─ model/
   │    │     ├─ user/
   │    │     ├─ franchise/
   │    │     ├─ product/
   │    │     ├─ order/
   │    │     ├─ customer/
   │    │     └─ ...
   │    │
   │    ├─ useCases/
   │    │  ├─ user/
   │    │  ├─ franchise/
   │    │  ├─ manager/
   │    │  └─ seller/
   │    │
   │    ├─ controller/
   │    │  ├─ user/
   │    │  ├─ franchise/
   │    │  ├─ manager/
   │    │  └─ seller/
   │    │
   │    ├─ repository/
   │    │  ├─ user/
   │    │  ├─ franchise/
   │    │  ├─ manager/
   │    │  └─ seller/
   │    │  └─ utils/
   │    │
   │    └─ views/
   │       ├─ login/
   │       ├─ owner/
   │       ├─ manager/
   │       └─ seller/
   │
   └─ test/
      └─ java/
            ├─ domain/
            └─ useCases/

```

