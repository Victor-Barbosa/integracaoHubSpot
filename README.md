# **Integração HubSpot**
## **Descrição do Projeto**
Esta aplicação integra-se com o HubSpot, utilizando o Spring Boot 3.4.3 e um conjunto de serviços do Spring Cloud para facilitar comunicação via APIs e autenticação OAuth 2.0. A arquitetura do projeto segue boas práticas com validação, controle de chamadas externas por rate limit e suporte a testes com Mockito.
## **Como Clonar o Projeto**

Para clonar este repositório em sua máquina local, siga os passos abaixo:

1. **Certifique-se de ter o Git instalado em sua máquina.**
    - Para verificar se o Git está instalado, execute o seguinte comando no terminal:
      ```bash
      git --version
      ```
    - Caso o Git não esteja instalado, você pode baixá-lo [aqui](https://git-scm.com/).

2. **Clone o repositório usando o seguinte comando:**
   ```bash
   git clone https://github.com/Victor-Barbosa/integracaoHubSpot.git
   ```

3. **Acesse o diretório do projeto clonado:**
   ```bash
   cd integracaoHubSpot
   ```

Agora você já tem o repositório clonado localmente e está pronto para configurar e rodar o projeto. Veja as instruções de configuração e execução abaixo para os próximos passos.

## **Requisitos**
1. **Java 21**: Certifique-se de ter o Java 21 instalado, que é usado via um `Toolchain` especificado no `build.gradle`.
    - Para verificar sua versão do Java:
``` bash
     java --version
```
**Gradle**: O Gradle Wrapper já está incluído no projeto. Assim, você pode usar o comando `./gradlew` para executar tarefas específicas mesmo sem instalar o Gradle globalmente.

**Configuração do Ambiente**: Certifique-se de definir corretamente as propriedades da aplicação para conectar-se ao HubSpot. Essas configurações estão no arquivo `application.properties`, encontrado no diretório `src/main/resources/`.

## **Configurações**
### Configuração de Dependências
As dependências principais são:
- **Spring Boot Starter Web**: Para criar a API REST.
- **Spring Boot Starter OAuth2 Client**: Para autenticação OAuth 2.0.
- **Spring Cloud OpenFeign**: Para chamadas de APIs externas.
- **Bucket4j**: Implementação de controle de taxa (rate limiting).

Dependências para teste:
- **Spring Boot Starter Test**: Suporta testes baseados em Spring.
- **Mockito**: Para mock de objetos e testes unitários.

Certifique-se de que elas sejam baixadas automaticamente executando `gradle build` (instruções abaixo).

## **Como Rodar o Projeto**
Existem duas opções para rodar o projeto:
### **1ª Opção: Utilizando as Configurações Atuais do `application.properties`**
Na configuração padrão do `application.properties` fornecida no projeto, a aplicação funcionará corretamente. 
No entanto, utilizando essa abordagem, **o endpoint do webhook: https://localhost:8443/webhook não será capaz de receber notificações de eventos como `contact.creation` do HubSpot**. Essa limitação ocorre por conta da ausência de configurações específicas de webhook requeridas pelo HubSpot.
Essa configuração é indicada para **fins de teste básico ou desenvolvimento local**, sem necessidade de notificações automáticas de eventos do HubSpot.
É necessário uma Conta de teste do desenvolvedor no HubSpot para poder testar a aplicação.
Siga a documentação oficial do HubSpot para criar uma Conta de teste do desenvolvedor: [Documentação HubSpot - Tipos de conta da HubSpot](https://br.developers.hubspot.com/docs/getting-started/account-types#app-developer-accounts)
### **2ª Opção: Criar um App Público no HubSpot**
Para ativar notificações de eventos, como a criação de contatos no HubSpot, será necessário criar um aplicativo público, gerar credenciais específicas e configurar a aplicação. Siga os passos abaixo:
1. **Criar um Aplicativo Público no HubSpot**
   Siga a documentação oficial do HubSpot para criar um aplicativo: [Documentação HubSpot - Aplicativos Públicos](https://br.developers.hubspot.com/docs/guides/apps/public-apps/overview)
2. Durante o processo de criação, você precisará definir uma URL de redirecionamento para o fluxo de autenticação do OAuth.
2. **Definir a URL de Redirecionamento no HubSpot**
   No campo `Redirect URLs` do HubSpot, configure a seguinte URL obrigatória:
``` 
   http://localhost:8443/auth-callback
```
Essa URL será usada como ponto de retorno após a autenticação no fluxo OAuth.
1. **Adicionar Escopos Necessários**
   É necessário incluir os seguintes escopos obrigatórios para garantir as permissões adequadas no aplicativo:
    - `oauth`
    - `crm.objects.contacts.write`
    - `crm.objects.contacts.read`
   Caso seja adcionados scopos além desses é necessario atualizar no arquivo `application.properties`, o seguinte campo:
``` properties
    hubspot.oauth.scopes=oauth crm.objects.contacts.write crm.objects.contacts.read
```
Certifique-se de configurar os escopos durante a criação do aplicativo no HubSpot. Esses escopos garantem que a aplicação tenha permissão para leitura e escrita de contatos, além de utilizar o modelo de autenticação OAuth.
2. **Atualizar as Configurações de Autenticação no `application.properties`**
   Após configurar o aplicativo no HubSpot, insira as credenciais recebidas no arquivo `application.properties`, preenchendo os seguintes campos:
``` properties
   hubspot.oauth.clientId=<SEU_CLIENT_ID>
   hubspot.oauth.clientSecret=<SEU_CLIENT_SECRET>
```
Certifique-se de substituir `<SEU_CLIENT_ID>` e `<SEU_CLIENT_SECRET>` com os valores obtidos durante a criação do app.

3. **Configurar o ngrok para Receber Notificações do Webhook**

    Para que sua aplicação local possa receber notificações de Webhooks do HubSpot, é necessário criar um túnel público utilizando o **ngrok**. Abaixo estão as instruções detalhadas para instalar, configurar e iniciar o ngrok.
### **3.1. Instalar o ngrok**
1. Acesse o site oficial do ngrok:
   [https://ngrok.com/download](https://ngrok.com/download)
2. Faça o download do binário adequado ao seu sistema operacional (Windows, macOS ou Linux).
3. Extraia o arquivo baixado e adicione o executável do `ngrok` ao seu PATH para facilitar o uso no terminal (opcional).
4. Para verificar se o `ngrok` foi instalado corretamente, execute o comando:
``` bash
   ngrok --version
```
### **3.2. Criar uma Conta no ngrok**
1. Acesse o site oficial do ngrok:
   [https://ngrok.com/](https://ngrok.com/)
2. Crie uma conta gratuita ou faça login, se já tiver uma.
3. Após criar a conta, acesse o painel de controle e copie seu **ngrok Authtoken**, que será usado para autenticar a ferramenta no seu ambiente local.

### **3.3. Configurar o ngrok no Ambiente Local**
1. No terminal, configure o ngrok para ativar o Authtoken:
``` bash
   ngrok config add-authtoken <SEU_AUTHTOKEN>
```
Substitua `<SEU_AUTHTOKEN>` pelo token copiado no painel da sua conta do ngrok.
### **3.4. Criar o Túnel para Sua Aplicação**
1. Para permitir o tráfego público em sua aplicação local, certifique-se de que ela está configurada para rodar na porta `8443` (ou a porta configurada nos Webhooks).
2. Inicie o túnel com o seguinte comando:
``` bash
   ngrok http https://localhost:8443
```
1. O ngrok gerará um URL público semelhante a este:
``` 
   https://<subdomínio>.ngrok.io
```

### **5. Configuração Final no HubSpot**
1. Acesse as configurações do aplicativo no HubSpot Developer Dashboard.
   documentação oficial do HubSpot para criar um aplicativo: [Documentação HubSpot - Webhooks](https://br.developers.hubspot.com/docs/guides/api/app-management/webhooks)
2. No campo de **Target URL** , insira o URL gerado pelo ngrok, como no exemplo:
``` 
   https://35b3-2804-1e68-c211-e2aa-182b-9d4a-2542-cb66.ngrok-free.app/webhook
```
1. Salve as alterações e crie um subscription para contact.creation.
Obs: Talvez seja necessario ativar o Event subscriptions caso não fique ativo automaticamente

### **6. Iniciar a Aplicação Local**
Certifique-se de que a aplicação esteja rodando localmente na porta `8443` para que o webhook seja entregue corretamente ao endpoint configurado. Use o seguinte comando no terminal para garantir que o servidor local esteja ativo:
``` bash
./gradlew bootRun
```

Outras configurações podem incluir:
- Informações adicionais baseadas no OAuth2.
- Limites para implementação de Bucket4j, se for configurado dinamicamente.

## **Como Executar a Aplicação**
### 1. **Baixar as Dependências**
No terminal, na raiz do projeto, execute:
``` bash
./gradlew build
```
Isto:
- Baixará e configurará todas as dependências necessárias;
- Validará o código e executará testes configurados.

### 2. **Iniciar o Servidor**
Inicie a aplicação com o comando:
``` bash
./gradlew bootRun
```
A aplicação estará disponível na porta padrão `8443` (ou conforme definida no seu `application.properties`).
### 3. **Testar a Aplicação**
Execute os testes configurados no projeto com:
``` bash
./gradlew test
```
Certifique-se de que todos estejam passando antes de realizar qualquer implementação no sistema.

## **APIs Disponíveis**
Quando a aplicação estiver em funcionamento, você pode testar os endpoints disponíveis:
1. Utilize um cliente HTTP, como **Postman**, **Insomnia** ou **cURL**, para interagir com a API. 
    Certifique-se de **desabilitar a verificação do certificado SSL** no cliente escolhido, pois a aplicação utiliza um certificado autoassinado para conexões seguras.
2. Para iniciar o processo de autenticação basta colar a seguinte url: https://localhost:8443/authorization no navegador de sua escolha
logar na Conta de teste do desenvolvedor do HubSpot e autorizar o app. Após autorizar já é possivel cadastrar um contato.
3. Para cadastrar com contato utilize o seguinte curl no cliente HTTP 
```
   curl --request POST \
   --url https://localhost:8443/contacts \
   --header 'Content-Type: application/json' \
   --data '{
	"email": "Victor@hubspot.com",
	"firstName": "Victor",
    "lastName": "Barbosa",
    "phone": "(555) 222-5555"
}'
```
## **Testes**
- **Testes**:
    - Os testes utilizam JUnit e Mockito.
    - Para adicionar novos testes, utilize boas práticas de mocking com `MockitoJUnit`.

## **MELHORIAS FUTURAS**
- Configurar um banco de dados para salvar o **accessToken** e o **refreshToken** criptografados.
- Criar um novo **controller** para atualizar o **accessToken** quando ele expirar, utilizando o **refreshToken** e o **clientSecret** para gerar um novo token e atualizar no banco de dados.
- Ajustar o endpoint de cadastro de contato para:
    - Buscar o **accessToken** e realizar o cadastro do contato.
    - Caso a API do HubSpot retorne que o **accessToken** expirou, o endpoint de cadastro deverá acionar o novo **controller** para atualizar o **accessToken** e tentar reenviar o cadastro do contato.
 