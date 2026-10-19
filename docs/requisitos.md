# Agroelo — Requisitos e Regras de Negócio

> Versão 1.0 (rascunho para revisão) — Outubro/2026
> Projeto Integrador — Desenvolvimento de Software Multiplataforma (Fatec)

## 1. Introdução

O **Agroelo** é um sistema de gestão para cooperativas agrícolas. Ele centraliza cadastros, produção, notas fiscais, assistência técnica e eventos, atendendo três perfis de usuário.

Este documento define **o que o sistema deve fazer** (requisitos funcionais), **as regras que ele deve respeitar** (regras de negócio), **como ele deve se comportar** (requisitos não funcionais) e **quem pode fazer o quê** (permissões). Ele também serve de mapa para as rotas da API.

### 1.1 Arquitetura

- **Backend:** API REST em Java com Spring Boot, banco MySQL.
- **Frontend (fase posterior):** aplicação web responsiva (React + Vite). Administrador e técnico usam no computador; cooperado usa no celular (instalável como PWA).

### 1.2 Escopo do MVP

Os requisitos com prioridade **Alta** formam o MVP: login, cooperados, técnicos, produtos, produção, solicitações com atendimentos, eventos com inscrições e cadastro/consulta de notas fiscais. A emissão fiscal oficial **não** faz parte do escopo (ver seção 8).

### 1.3 Convenções

- **RF** = requisito funcional, **RN** = regra de negócio, **RNF** = requisito não funcional.
- **Prioridade:** Alta (MVP), Média (desejável), Baixa (se sobrar tempo).
- Nas listagens, o perfil **COOPERADO** enxerga automaticamente apenas os **próprios** registros.

## 2. Perfis de usuário

| Perfil | Descrição |
|---|---|
| **ADMIN** | Gerencia cadastros, acompanha toda a operação e emite as notas fiscais. |
| **COOPERADO** | Produtor rural. Registra produção, abre solicitações, se inscreve em eventos e consulta suas notas. |
| **TECNICO** | Analisa solicitações de assistência e registra os atendimentos. |

## 3. Requisitos funcionais

### 3.1 Autenticação e usuários

| ID | Requisito | Perfil | Prioridade |
|---|---|---|---|
| RF01 | O sistema deve permitir login com e-mail e senha e devolver um token de acesso. | Todos | Alta |
| RF02 | O sistema deve impedir o login de usuários inativos. | Todos | Alta |
| RF03 | O usuário deve poder alterar a própria senha informando a senha atual. | Todos | Média |
| RF04 | O administrador deve poder criar outros administradores. | ADMIN | Média |

### 3.2 Cooperativa

| ID | Requisito | Perfil | Prioridade |
|---|---|---|---|
| RF05 | O administrador deve poder cadastrar, consultar e editar cooperativas (razão social, nome fantasia, CNPJ, telefone, e-mail). | ADMIN | Alta |

### 3.3 Cooperados

| ID | Requisito | Perfil | Prioridade |
|---|---|---|---|
| RF06 | O administrador deve poder cadastrar um cooperado, informando seus dados e vinculando-o a uma cooperativa. O usuário de acesso é criado junto, na mesma operação. | ADMIN | Alta |
| RF07 | O administrador deve poder editar os dados de um cooperado. | ADMIN | Alta |
| RF08 | O administrador deve poder inativar e reativar um cooperado (sem excluí-lo). | ADMIN | Alta |
| RF09 | O sistema deve listar cooperados com paginação, busca por nome ou CPF e filtro por situação (ativo/inativo). | ADMIN, TECNICO | Alta |
| RF10 | O sistema deve exibir o detalhe de um cooperado com resumo: produção total, notas emitidas e solicitações abertas. | ADMIN | Média |
| RF11 | O cooperado deve poder consultar e atualizar seus próprios dados de contato (telefone). | COOPERADO | Média |

### 3.4 Técnicos

| ID | Requisito | Perfil | Prioridade |
|---|---|---|---|
| RF12 | O administrador deve poder cadastrar um técnico (dados, especialidade e cooperativa). O usuário de acesso é criado junto. | ADMIN | Alta |
| RF13 | O administrador deve poder editar, inativar e reativar técnicos. | ADMIN | Alta |
| RF14 | O sistema deve listar técnicos com paginação e filtros por especialidade e situação. | ADMIN | Média |

### 3.5 Produtos

| ID | Requisito | Perfil | Prioridade |
|---|---|---|---|
| RF15 | O administrador deve poder cadastrar e editar produtos (nome, descrição, unidade de medida). | ADMIN | Alta |
| RF16 | O administrador deve poder inativar e reativar produtos. | ADMIN | Alta |
| RF17 | Todo usuário autenticado deve poder consultar a lista de produtos (por padrão, apenas os ativos). | Todos | Alta |

### 3.6 Produção

| ID | Requisito | Perfil | Prioridade |
|---|---|---|---|
| RF18 | O cooperado deve poder registrar uma produção informando produto, quantidade, data de produção, data de colheita (opcional) e observações. | COOPERADO | Alta |
| RF19 | O administrador deve poder registrar produção em nome de um cooperado. | ADMIN | Média |
| RF20 | O cooperado deve poder editar e excluir seus próprios registros de produção. | COOPERADO | Alta |
| RF21 | O sistema deve listar produções com paginação e filtros por cooperado, produto e período. O administrador vê todas; o cooperado, só as suas. | ADMIN, COOPERADO | Alta |

### 3.7 Solicitações de assistência técnica

| ID | Requisito | Perfil | Prioridade |
|---|---|---|---|
| RF22 | O cooperado deve poder abrir uma solicitação de assistência informando assunto e descrição. | COOPERADO | Alta |
| RF23 | O cooperado deve poder acompanhar a situação das suas solicitações e cancelá-las enquanto estiverem abertas. | COOPERADO | Alta |
| RF24 | O sistema deve listar solicitações com paginação e filtro por situação. Administrador e técnico veem todas; o cooperado, só as suas. | Todos | Alta |
| RF25 | O técnico e o administrador devem poder alterar a situação de uma solicitação, respeitando as transições permitidas (RN08). | ADMIN, TECNICO | Alta |

### 3.8 Atendimentos técnicos

| ID | Requisito | Perfil | Prioridade |
|---|---|---|---|
| RF26 | O técnico deve poder registrar um atendimento em uma solicitação, informando data, orientações e observações (opcional). | TECNICO | Alta |
| RF27 | O sistema deve manter o histórico de atendimentos de cada solicitação, visível ao administrador, ao técnico e ao cooperado dono da solicitação. | Todos | Alta |

### 3.9 Eventos e inscrições

| ID | Requisito | Perfil | Prioridade |
|---|---|---|---|
| RF28 | O administrador deve poder cadastrar e editar eventos (título, descrição, data e hora, local, vagas). | ADMIN | Alta |
| RF29 | O administrador deve poder cancelar um evento e marcá-lo como realizado. | ADMIN | Alta |
| RF30 | O sistema deve listar eventos com paginação, filtro por situação e quantidade de vagas restantes. | Todos | Alta |
| RF31 | O cooperado deve poder se inscrever em um evento e cancelar sua inscrição. | COOPERADO | Alta |
| RF32 | O administrador deve poder consultar os inscritos de um evento e marcar quem participou. | ADMIN | Média |

### 3.10 Notas fiscais

| ID | Requisito | Perfil | Prioridade |
|---|---|---|---|
| RF33 | O administrador deve poder criar uma nota fiscal em rascunho para um cooperado, com número gerado automaticamente. | ADMIN | Alta |
| RF34 | O administrador deve poder adicionar, editar e remover itens de uma nota em rascunho (produto, quantidade, valor unitário). | ADMIN | Alta |
| RF35 | O administrador deve poder editar os dados de uma nota em rascunho e excluí-la. | ADMIN | Média |
| RF36 | O administrador deve poder emitir uma nota em rascunho (RN16) e cancelar uma nota emitida (RN17). | ADMIN | Alta |
| RF37 | O sistema deve listar notas fiscais com paginação e filtros por cooperado, situação e período. O administrador vê todas; o cooperado, só as suas, em modo somente leitura. | ADMIN, COOPERADO | Alta |

### 3.11 Painel (dashboard)

| ID | Requisito | Perfil | Prioridade |
|---|---|---|---|
| RF38 | O administrador deve ver um painel com: total de cooperados ativos, produção total por produto, produção dos últimos 6 meses, notas por situação, solicitações abertas e próximos eventos. | ADMIN | Média |
| RF39 | O cooperado deve ver um painel pessoal com: produção total, solicitações em andamento e próximas inscrições. | COOPERADO | Média |
| RF40 | O técnico deve ver um painel com as solicitações aguardando análise e as agendadas. | TECNICO | Baixa |

## 4. Regras de negócio

### 4.1 Cadastros e acesso

| ID | Regra |
|---|---|
| RN01 | O e-mail é único no sistema, guardado em minúsculas, e o login ignora diferença entre maiúsculas e minúsculas. |
| RN02 | CPF e CNPJ são únicos, guardados somente com dígitos e validados (dígitos verificadores). |
| RN03 | Todo cooperado e todo técnico possui **exatamente um** usuário, com o perfil correspondente (`COOPERADO` ou `TECNICO`). A criação do cadastro e do usuário acontece numa única transação: ou cria os dois, ou nenhum. |
| RN04 | Inativar um cooperado ou técnico inativa o seu usuário. Cadastros com histórico **nunca são excluídos fisicamente**. |
| RN05 | O último administrador ativo não pode ser inativado. |
| RN06 | Somente um administrador pode criar contas de perfil `ADMIN`. |
| RN07 | O cooperado só visualiza e altera seus próprios registros (produção, solicitações, inscrições e notas). |

### 4.2 Produtos e produção

| ID | Regra |
|---|---|
| RN08 | Produto inativo não pode ser usado em novas produções nem em novos itens de nota. Os registros antigos permanecem intactos. |
| RN09 | A quantidade produzida deve ser maior que zero. A data de colheita, quando informada, não pode ser anterior à data de produção, e a data de produção não pode ser futura. |

### 4.3 Solicitações e atendimentos

| ID | Regra |
|---|---|
| RN10 | Toda solicitação nasce com a situação `ABERTA`. |
| RN11 | Transições permitidas: `ABERTA → EM_ANALISE` ou `CANCELADA`; `EM_ANALISE → AGENDADA` ou `CANCELADA`; `AGENDADA → CONCLUIDA` ou `CANCELADA`. `CONCLUIDA` e `CANCELADA` são finais. Qualquer outra transição é recusada. |
| RN12 | O cooperado só pode cancelar a própria solicitação enquanto ela estiver `ABERTA`. |
| RN13 | Um atendimento só pode ser registrado em solicitação `EM_ANALISE` ou `AGENDADA`, por um técnico ativo. |
| RN14 | Atendimentos nunca são apagados (histórico). Uma solicitação pode ter vários atendimentos, do mesmo técnico ou de técnicos diferentes. |

### 4.4 Eventos e inscrições

| ID | Regra |
|---|---|
| RN15 | Todo evento nasce `AGENDADO`. O número de vagas deve ser maior que zero e a data deve ser futura no cadastro. |
| RN16 | A inscrição só é permitida em evento `AGENDADO`, com data futura e vagas disponíveis (inscrições `INSCRITO` menores que o total de vagas). |
| RN17 | Um cooperado não pode ter duas inscrições no mesmo evento. Se ele cancelou e quer voltar, a inscrição existente é **reativada** (`INSCRITO`), sem criar outra, e a regra de vagas é verificada de novo. |
| RN18 | Inscrição `CANCELADA` libera a vaga. `PARTICIPOU` só pode ser marcado em inscrição `INSCRITO` de um evento já `REALIZADO`. |
| RN19 | Ao cancelar um evento, as inscrições `INSCRITO` passam para `CANCELADA`. |
| RN20 | Não é possível reduzir as vagas de um evento abaixo do número de inscritos ativos. |

### 4.5 Notas fiscais

| ID | Regra |
|---|---|
| RN21 | Toda nota nasce `RASCUNHO`, sem data de emissão. Itens e dados da nota só podem ser alterados enquanto ela é rascunho. |
| RN22 | O número da nota é gerado pelo sistema, em sequência por cooperado e série. A combinação cooperado + série + número é única. |
| RN23 | O valor total do item é `quantidade × valor unitário`, arredondado em 2 casas (`HALF_UP`). O valor total da nota é a soma dos itens. Esses valores são sempre calculados pelo sistema e nunca informados pelo usuário. |
| RN24 | Quantidade e valor unitário do item devem ser maiores que zero, e o produto deve estar ativo. |
| RN25 | Para **emitir**, a nota precisa de pelo menos um item. Ao emitir, o sistema registra a data de emissão e a situação vira `EMITIDA`. |
| RN26 | Nota `EMITIDA` não pode ser editada nem excluída; só pode ser `CANCELADA`. `CANCELADA` é final. Somente rascunhos podem ser excluídos (junto com seus itens). |

### 4.6 Dados e segurança

| ID | Regra |
|---|---|
| RN27 | Senhas são guardadas apenas como hash BCrypt e nunca aparecem em respostas da API. |
| RN28 | Toda listagem é paginada, com tamanho de página padrão de 10 e máximo de 100. |

## 5. Requisitos não funcionais

| ID | Categoria | Requisito |
|---|---|---|
| RNF01 | Segurança | Autenticação sem estado, por token JWT com tempo de expiração. Em produção, tráfego somente por HTTPS. |
| RNF02 | Segurança | Autorização por perfil em **todas** as rotas, conforme a matriz da seção 6. |
| RNF03 | Segurança | Segredos (senha do banco, chave do JWT) vêm de variáveis de ambiente e nunca ficam no repositório. |
| RNF04 | Privacidade | Dados pessoais (CPF, telefone, e-mail) não aparecem em logs. |
| RNF05 | API | API REST em JSON, com códigos HTTP corretos: 200, 201, 204, 400 (validação), 401, 403, 404 e 409 (conflito ou violação de regra). |
| RNF06 | API | Erros em formato padronizado (data/hora, status, mensagem e lista de campos inválidos), por tratamento global de exceções. |
| RNF07 | API | Entidades nunca são expostas diretamente: a API usa DTOs, com validação de entrada (Bean Validation). |
| RNF08 | Desempenho | Listagens com paginação, busca e filtros feitos no banco; totais do painel por consultas agregadas. Meta: resposta em até 2 s com 10 mil registros por tabela. |
| RNF09 | Manutenibilidade | Arquitetura em camadas (controller → service → repository). Regras de negócio ficam **somente** na camada de serviço. |
| RNF10 | Qualidade | Testes automatizados para as regras críticas (transições de solicitação, vagas e inscrição, cálculo e emissão de nota). |
| RNF11 | Documentação | API documentada com Swagger/OpenAPI e README com instruções para rodar o projeto. |
| RNF12 | Banco de dados | MySQL com migrações versionadas (Flyway) antes da entrega final, passando `ddl-auto` para `validate`. |
| RNF13 | Usabilidade | Interface em português, responsiva (desktop e celular), datas e horas no fuso `America/Sao_Paulo`. |

## 6. Matriz de permissões

Legenda: ✅ permitido · ❌ negado · **próprio** = somente os próprios registros · **leitura** = apenas consulta.

| Ação | ADMIN | COOPERADO | TECNICO |
|---|---|---|---|
| Login e alterar a própria senha | ✅ | ✅ | ✅ |
| Gerenciar cooperativas | ✅ | ❌ | ❌ |
| Cadastrar, editar e inativar cooperados | ✅ | ❌ | ❌ |
| Listar e consultar cooperados | ✅ | próprio | leitura |
| Atualizar dados de contato | ✅ | próprio | ❌ |
| Gerenciar técnicos | ✅ | ❌ | ❌ |
| Gerenciar produtos | ✅ | ❌ | ❌ |
| Consultar produtos | ✅ | ✅ | ✅ |
| Registrar produção | ✅ (em nome do cooperado) | próprio | ❌ |
| Consultar produção | ✅ todas | próprio | ❌ |
| Abrir solicitação de assistência | ❌ | ✅ | ❌ |
| Consultar solicitações | ✅ todas | próprio | ✅ todas |
| Alterar situação da solicitação | ✅ | só cancelar (própria e `ABERTA`) | ✅ |
| Registrar atendimento | ❌ | ❌ | ✅ |
| Consultar atendimentos | ✅ | próprio | ✅ |
| Gerenciar eventos | ✅ | ❌ | ❌ |
| Consultar eventos | ✅ | ✅ | ✅ |
| Inscrever-se e cancelar inscrição | ❌ | ✅ | ❌ |
| Consultar inscritos e marcar presença | ✅ | ❌ | ❌ |
| Criar, editar, emitir e cancelar notas | ✅ | ❌ | ❌ |
| Consultar notas fiscais | ✅ todas | próprio (leitura) | ❌ |
| Painel | ✅ geral | ✅ pessoal | ✅ solicitações |

## 7. Rotas previstas da API (rascunho)

Prefixo `/api`. Todas exigem autenticação, exceto o login. Os perfis seguem a seção 6. Os nomes e caminhos podem mudar durante a implementação.

| Módulo | Método e rota | Descrição |
|---|---|---|
| Autenticação | `POST /auth/login` | Login |
| | `PUT /usuarios/me/senha` | Alterar a própria senha |
| | `POST /usuarios/admins` | Criar administrador |
| Cooperativas | `GET /cooperativas`, `POST /cooperativas` | Listar e criar |
| | `GET /cooperativas/{id}`, `PUT /cooperativas/{id}` | Consultar e editar |
| Cooperados | `GET /cooperados?busca=&ativo=&page=&size=` | Listar com filtros |
| | `POST /cooperados` | Criar cooperado e usuário |
| | `GET /cooperados/{id}`, `PUT /cooperados/{id}` | Consultar e editar |
| | `PATCH /cooperados/{id}/situacao` | Inativar e reativar |
| | `GET /cooperados/me`, `PUT /cooperados/me` | Dados do próprio cooperado |
| Técnicos | `GET /tecnicos`, `POST /tecnicos` | Listar e criar |
| | `GET /tecnicos/{id}`, `PUT /tecnicos/{id}` | Consultar e editar |
| | `PATCH /tecnicos/{id}/situacao` | Inativar e reativar |
| Produtos | `GET /produtos?ativo=`, `POST /produtos` | Listar e criar |
| | `GET /produtos/{id}`, `PUT /produtos/{id}` | Consultar e editar |
| | `PATCH /produtos/{id}/situacao` | Inativar e reativar |
| Produção | `GET /producoes?cooperadoId=&produtoId=&de=&ate=` | Listar com filtros |
| | `POST /producoes` | Registrar |
| | `GET /producoes/{id}`, `PUT /producoes/{id}`, `DELETE /producoes/{id}` | Consultar, editar e excluir |
| Solicitações | `GET /solicitacoes?situacao=`, `POST /solicitacoes` | Listar e abrir |
| | `GET /solicitacoes/{id}` | Detalhe com atendimentos |
| | `PATCH /solicitacoes/{id}/situacao` | Mudar situação |
| Atendimentos | `GET /solicitacoes/{id}/atendimentos` | Histórico |
| | `POST /solicitacoes/{id}/atendimentos` | Registrar atendimento |
| Eventos | `GET /eventos?situacao=`, `POST /eventos` | Listar e criar |
| | `GET /eventos/{id}`, `PUT /eventos/{id}` | Consultar e editar |
| | `PATCH /eventos/{id}/situacao` | Cancelar e marcar como realizado |
| Inscrições | `GET /eventos/{id}/inscricoes` | Inscritos do evento |
| | `POST /eventos/{id}/inscricoes` | Inscrever-se |
| | `PATCH /inscricoes/{id}/situacao` | Cancelar ou marcar presença |
| Notas fiscais | `GET /notas-fiscais?cooperadoId=&situacao=&de=&ate=` | Listar com filtros |
| | `POST /notas-fiscais` | Criar rascunho |
| | `GET /notas-fiscais/{id}`, `PUT /notas-fiscais/{id}`, `DELETE /notas-fiscais/{id}` | Consultar, editar e excluir rascunho |
| | `POST /notas-fiscais/{id}/itens` | Adicionar item |
| | `PUT /notas-fiscais/{id}/itens/{itemId}`, `DELETE /notas-fiscais/{id}/itens/{itemId}` | Editar e remover item |
| | `POST /notas-fiscais/{id}/emitir` | Emitir |
| | `POST /notas-fiscais/{id}/cancelar` | Cancelar |
| Painel | `GET /dashboard` | Resumo conforme o perfil logado |

## 8. Fora do escopo e evoluções futuras

**Fora do escopo desta versão**

- Emissão fiscal oficial (integração com a SEFAZ, validação tributária, NCM, CFOP, ICMS, chave de acesso). A nota fiscal do Agroelo é um **registro de gestão**.
- Cadastro de propriedades (área em hectares, localização), financeiro, estoque, contratos, certidões, impostos, insumos e tratos culturais.
- Aplicativo mobile nativo (a versão para o cooperado será web responsiva/PWA).

**Evoluções futuras**

- Várias cooperativas com isolamento de dados (administrador vinculado a uma cooperativa).
- Recuperação de senha por e-mail e notificações (solicitação atualizada, evento próximo).
- Relatórios gerenciais com exportação (PDF e Excel) e indicadores de produção.
- Aplicativo mobile nativo para o cooperado, reaproveitando a mesma API.
- Integração com serviços externos.
