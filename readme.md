# Todo

### Configuração gerais do projeto
- [ ] Definir estrutura do projeto
- [ ] Diagrama de classes
- [ ] Instalar libs do java, swing, JUnit.
- [ ] Criar classes gerais (User, Produto, Compra, Endereço, Cliente)
- [ ] Criar tela de inicial de login


### 1. Todo do Dono
- [ ] 1.1 CRUD owner - Um dono deve conseguir editar suas próprias informações
  - [ ] Tela de edição (Swing)
  - [ ] Controller
  - [ ] UseCases: Criar primeiro e único dono e editar informações.
  - [ ] Persistência via ownerRepository
  - [ ] Testes: criação, remoção, edição e validação (não podem existir 2 donos)

- [ ] 1.2 CRUD franchises - Tela para criar, editar dados e excluir franquias
  - [ ] View: Listagem das franquias
  - [ ] View: Formulário com os dados da franquias (edição e remoção)
  - [ ] Controller
  - [ ] UseCases: CRUD Franquias, Adicionar gerente responsável (1 por franquia)
  - [ ] Repo: franchiseRepository
  - [ ] Testes: cadastro, remoção, listagem, vínculo Gerente

- [ ] 1.3 CRUD Managers
  - [ ] View: Listagem dos gerentes cadastrados no sistema
  - [ ] View: Formulário com os dados dos gerentes (edição e remoção)
  - [ ] Controller
  - [ ] UseCases: CRUD gerentes
  - [ ] Repo: managerRepository
  - [ ] Testes: cadastro, remoção, listagem


### 2. Todo do Gerente
- [ ] 2.1 CRUD sellers - Um gerente deve conseguir cadastrar vendedores na sua franquia
  - [ ] View: Listagem dos vendedores cadastrados no sistema (sua franquia) (em ordem de volume de vendas)
  - [ ] View: Formulário com os dados dos vendedores (edição e remoção)
  - [ ] Controller
  - [ ] UseCases: CRUD vendedores, vínculo com franquia
  - [ ] Persistência via sellerRepository
  - [ ] Testes: criação, remoção, edição e validação (gerente n pode vincular vendedor a outra franquia)

- [ ] 2.2 CRUD orders - Um gerente pode cadastrar, editar e remover pedidos de compras de todos os vendedores
  - [ ] View: Listagem dos pedidos cadastrados no sistema
  - [ ] View: Formulário com os dados dos pedidos (edição e remoção)
  - [ ] Controller
  - [ ] UseCases: CRUD pedidos, avaliar solicitação de edição/remoção de algum vendedor
  - [ ] Repo: orderRepository
  - [ ] Testes: cadastro, remoção, listagem

- [ ] 2.3 CRUD products - Um gerente pode cadastrar, editar e remover produtos da sua franquia
  - [ ] View: Listagem dos produtos cadastrados no sistema (em ordem de menor qnt estoque)
  - [ ] View: Formulário com os dados dos produtos (edição e remoção)
  - [ ] Controller
  - [ ] UseCases: CRUD produtos
  - [ ] Repo: productRepository
  - [ ] Testes: cadastro, remoção, listagem

- [ ] 2.4 Relatórios vendas e clientes
  - [ ] View: Mostrar relatório de clientes e suas compras


### 3. Todo do Vendedor
- [ ] 3.1 CRUD order - Um vendedor pode cadastrar, editar e remover compras
  - [ ] View: Listagem das suas próprias vendas
  - [ ] View: Cadastrar novas vendas ou editar venda já realizada (solicita aprovação)
  - [ ] Controller
  - [ ] UseCases: CRUD vendas
  - [ ] Repo: orderRepository
  - [ ] Testes: cadastro, remoção, listagem

- [ ] 3.2 CRUD client - Um vendedor pode cadastrar e editar clientes
  - [ ] View: Listagem dos clientes
  - [ ] View: Cadastrar novos clientes ou editar cliente já cadastrado
  - [ ] Controller
  - [ ] UseCases: CRUD clientes
  - [ ] Repo: clientRepository
  - [ ] Testes: cadastro, remoção, listagem


### Estrutura de organização do projeto

### Diagrama de classes
