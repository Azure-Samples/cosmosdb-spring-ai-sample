# AI Chat with RAG using Spring AI and Azure Cosmos DB

This sample shows how to build a chat assistant with Spring AI using Azure Cosmos DB as a vector store for retrieval augmented generation (RAG), allowing you to use your private data to answer questions.

![Demo](./media/demo.gif)

### Application Architecture

This application utilizes the following Azure resources:

- [**Spring AI Framework**](https://docs.spring.io/spring-ai/reference/1.0/index.html) to orchestrate and simplify vector embeddings, vector search, and chat completion.
- [**Azure OpenAI**](https://docs.microsoft.com/azure/cognitive-services/openai/) for chat completion and embedding APIs.
- [**Azure Cosmos DB NoSQL API**](https://learn.microsoft.com/azure/cosmos-db/nosql/vector-search) as the vector store database.


## Getting Started

### Prerequisites

The following prerequisites are required to use this application. Please ensure that you have them all installed locally.

- [Git](http://git-scm.com/).
- [Java 17 or later](https://learn.microsoft.com/java/openjdk/install)
- [Azure Cosmos DB NoSQL API account](https://learn.microsoft.com/azure/cosmos-db/nosql/how-to-create-account)
- An Azure OpenAI account (see more [here](https://customervoice.microsoft.com/Pages/ResponsePage.aspx?id=v4j5cvGGr0GRqy180BHbR7en2Ais5pxKtso_Pz4b1_xUOFA5Qk1UWDRBMjg0WFhPMkIzTzhKQ1dWNyQlQCN0PWcu))

### Quickstart

1. Git clone this repo.
2. You need to create the following `system environment variables` with the appropriate values. 

   ```shell
   set AZURE_OPENAI_ENDPOINT=<Your Azure OpenAI endpoint>
   set AZURE_OPENAI_APIKEY=<Your Azure OpenAI API key>
   set COSMOSDB_AI_ENDPOINT=<Cosmos DB NoSQL Account URI>
   set COSMOSDB_AI_KEY=<Cosmos DB NoSQL Account Key>
   ```
3. Check `src/main/resources/application.properties` for other Azure OpenAI default values that are set, and ensure you have the right values for chat completion and embedding (see [here](https://docs.spring.io/spring-ai/reference/1.0/api/chat/azure-openai-chat.html) for more information on default values used by Azure OpenAI in Spring AI framework).

4. Build the application (this will create two "fat" shaded jars, `webapi-application.jar` for the AI Chat bot, and `cli-application.jar` for loading your private data):

   ```shell
   mvn clean package
   ```  

4. The following command will read and process your own private text documents, create an Azure Cosmos DB NoSQL API collection with [vector indexing](https://learn.microsoft.com/azure/cosmos-db/nosql/vector-search#vector-indexing-policies) and [embeddings](https://learn.microsoft.com/azure/cosmos-db/nosql/vector-search#container-vector-policies) policies, and load the processed documents into it:

   ```shell
   java -jar target/cli-application.jar --from=C:/<path you your private text docs>
   ```

5. Run the following command to build and run the application:

   ```shell
   java -jar target/webapi-application.jar
   ```
6. Open your browser and navigate to `http://localhost:8080/`. You should see the Chat Bot above. Test it out by typing in a question and clicking `Send`.