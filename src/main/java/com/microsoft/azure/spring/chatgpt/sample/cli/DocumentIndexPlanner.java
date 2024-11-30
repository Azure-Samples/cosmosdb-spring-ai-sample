package com.microsoft.azure.spring.chatgpt.sample.cli;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.document.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

public class DocumentIndexPlanner {
    public DocumentIndexPlanner(EmbeddingModel client, VectorStore vectorStore) {
        this.client = client;
        this.vectorStore = vectorStore;
    }
    private final EmbeddingModel client;
    private final VectorStore vectorStore;

    private Logger log = Logger.getLogger(DocumentIndexPlanner.class.getName());
    public void buildFromFolder(String folderPath) throws IOException {
        if (folderPath == null) {
            throw new IllegalArgumentException("folderPath shouldn't be empty.");
        }
        SimpleFolderReader reader = new SimpleFolderReader(folderPath);
        TextSplitter splitter = new TextSplitter();

        reader.run((fileName, content) -> {

            log.info("String to process: "+ fileName);
            var textChunks = splitter.split(content);
            List<Document> documents = new ArrayList<Document>();
            for (var chunk: textChunks) {
                String key = UUID.randomUUID().toString();
                Document doc = new Document(key, chunk, Map.of("key1", "value1"));
                documents.add(doc);
            }
            vectorStore.add(documents);
            return null;
        });

        log.info("All documents are loaded to Cosmos DB NoSQL API vector store.");
    }
}
