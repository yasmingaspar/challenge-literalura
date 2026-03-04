# LiterAlura - Challenge Alura + Oracle Next Education 📚

O **LiterAlura** é um catálogo de livros interativo desenvolvido em Java com Spring Boot. O projeto realiza buscas na API Gutendex, converte dados JSON para objetos Java e persiste as informações em um banco de dados relacional.

## 🛠️ Tecnologias Utilizadas
* **Java 25**
* **Spring Boot 4.0.3**
* **Spring Data JPA**
* **PostgreSQL**
* **Jackson** (Mapeamento de dados)

## 🚀 Funcionalidades
O menu interativo oferece as seguintes opções:
1. **Buscar livro pelo título**: Consulta a API Gutendex e salva o livro e seu autor no banco de dados.
2. **Listar livros registrados**: Exibe todos os títulos salvos na sua biblioteca local.
3. **Listar autores registrados**: Lista todos os autores salvos (ex: Machado de Assis, Bram Stoker).
4. **Listar autores vivos em um determinado ano**: Filtra autores com base no ano de nascimento e falecimento.
5. **Listar livros em um determinado idioma**: Filtra e conta a quantidade de livros por código de idioma (ex: pt, en).

## 📊 Banco de Dados
O projeto utiliza o PostgreSQL. Certifique-se de configurar o seu `application.properties` com as credenciais corretas. 
*Nota: Por segurança, não compartilhe sua senha real no repositório público.*

## 📸 Demonstração
O sistema utiliza logs do Hibernate para confirmar a persistência e consulta dos dados, garantindo que as relações entre Livros e Autores sejam mantidas via ID.

<img width="1143" height="918" alt="Captura de tela 2026-03-04 124125" src="https://github.com/user-attachments/assets/3d125c78-678a-4860-959d-d332d8253aa9" />
