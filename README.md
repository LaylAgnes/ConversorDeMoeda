# 🌍 Conversor de Moedas - Desafio ONE Oracle + Alura

## 🚀 Descrição do Projeto

Este projeto é uma solução para o **Desafio Conversor de Moedas** proposto pelo programa ONE (Oracle Next Education) em parceria com a Alura. O objetivo é construir uma aplicação de console (terminal) em Java capaz de consumir uma API externa de taxas de câmbio (ExchangeRate-API) e realizar conversões entre diferentes moedas.

O programa foi desenvolvido para ser robusto, amigável e inclui funcionalidades extras que valorizam a experiência do usuário.

## ✨ Funcionalidades

O programa oferece um menu interativo com as seguintes capacidades:

* **Conversões Padrão:** Opções pré-definidas para as moedas mais comuns (USD, BRL, EUR, JPY, CAD, etc.).
* **Conversão Livre:** Opção para o usuário digitar qualquer código de moeda de origem e destino (Opção 7).
* **Histórico de Transações:** (✨ **Funcionalidade Extra**): Armazena em memória todas as conversões realizadas durante a sessão e permite visualizá-las a qualquer momento (Opção 8).
* **Tratamento de Erros Robusto:** Lida com entradas inválidas do usuário (`NumberFormatException`), problemas de conexão e códigos de moeda não encontrados na API (com mensagem clara ao usuário).
* **Formatação de Moeda:** (✨ **Melhoria**): Exibe os resultados usando a formatação de moeda correta (ex: `R$ 100,00` ou `US$ 19,80`), usando a classe `java.text.NumberFormat`.

## 💻 Tecnologias Utilizadas

* **Linguagem:** Java (Recomendado JDK 17+).
* **Gerenciador de Dependências:** Maven.
* **Consumo de API:** `java.net.http.HttpClient` (Biblioteca nativa do Java).
* **Processamento de JSON:** Google Gson (`com.google.code.gson`).
* **API Externa:** [Exchange Rate API (v6)](https://www.exchangerate-api.com/).
