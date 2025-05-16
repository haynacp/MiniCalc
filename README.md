# MiniCalc

Uma calculadora simples desenvolvida como parte de um desafio técnico.


## 🧩 Contexto e proposta

O desafio propunha a criação de um projeto pessoal ou uma aplicação funcional simples, com liberdade temática. Escolhi desenvolver uma calculadora nativa em Android utilizando **ViewBinding**, **MVVM com Hilt**, **persistência local com DataStore** e **testes unitários com repositório fake**.

Meu objetivo foi simular um app funcional, escalável e bem estruturado mesmo em um projeto pequeno, priorizando:

- Clareza arquitetural
- Testabilidade
- Separação de responsabilidades


## 🔍 Funcionalidades

- Cálculo de expressões aritméticas básicas com formatação automática (`4.0` → `4`)
- Histórico de cálculos persistido localmente com **DataStore Preferences**
- Scroll para visualização do histórico diretamente na tela
- Botão para limpar histórico
- Testes unitários para:
  - Formatação de resultados
  - Cálculo de expressões válidas e inválidas
  - Salvamento e limpeza do histórico


## 🧱 Arquitetura

- **MVVM** com injeção de dependência via **Hilt**
- **ViewModel** injetável com `HistoryDataSource` via `AppModule`
- **Repository pattern** com `FakeHistoryRepository` nos testes
- **Coroutines** com `viewModelScope` para chamadas assíncronas
- **DataStore** para persistência simples, reativa e leve
- **Testes** com `runTest`, `advanceUntilIdle` e `MainDispatcherRule`


## 🧪 Testes

Os testes foram feitos em `MainViewModelTest.kt`, com foco em:

- Cálculo e formatação de resultados
- Salvamento e recuperação do histórico
- Limpeza total do histórico

Utilizei `kotlinx-coroutines-test` com `MainDispatcherRule` pra simular `Dispatchers.Main` durante testes.


## ⏳ Sobre tempo e escopo

Por limitação de tempo, foquei em uma única feature funcional, testável e bem estruturada. Com mais tempo, eu evoluiria:

- Design da UI com temas e estilo mais próximos de produto real
- Histórico persistido com timestamps e ordenação mais rica
- Modularização da camada de dados
- Animações/feedbacks de erro e sucesso
- Testes instrumentados com Espresso


## 🚀 Como rodar

- Android Studio Flamingo+ (compatível com Meerkat)
- Gradle 8.3+
- AGP 8.3+
- Compile SDK 35
- Min SDK 26

