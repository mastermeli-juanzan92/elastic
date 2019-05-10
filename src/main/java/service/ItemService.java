package service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Category;
import domain.Item;
import domain.Site;
import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ItemService {

    //The config parameters for the connection
    public static final String HOST = "localhost";
    public static final int PORT_ONE = 9200;
    public static final int PORT_TWO = 9201;
    public static final String SCHEME = "http";

    public static RestHighLevelClient restHighLevelClient;
    public static ObjectMapper objectMapper = new ObjectMapper();

    public static final String INDEX = "itemdata";
    public static final String TYPE = "item";

    /**
     * Implemented Singleton pattern here
     * so that there is just one connection at a time.
     *
     * @return RestHighLevelClient
     */
    public static synchronized RestHighLevelClient makeConnection() {

        if (restHighLevelClient == null) {
            restHighLevelClient = new RestHighLevelClient(
                    RestClient.builder(
                            new HttpHost(HOST, PORT_ONE, SCHEME),
                            new HttpHost(HOST, PORT_TWO, SCHEME)));
        }

        return restHighLevelClient;
    }

    public static synchronized void closeConnection() throws IOException {
        restHighLevelClient.close();
        restHighLevelClient = null;
    }


    public static Site insertSite(Site site) {
        site.setCode("MLA");
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("code", "MLA");
        dataMap.put("name", site.getName());
        IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, site.getCode())
                .source(dataMap);
        try {
            IndexResponse response = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        } catch (ElasticsearchException e) {
            e.getDetailedMessage();
        } catch (IOException ex) {
            ex.getLocalizedMessage();
        }
        return site;
    }

    public static Item insertItem(Item item) {
        item.setId(UUID.randomUUID().toString());
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("id", item.getId());
        dataMap.put("name", item.getName());
        dataMap.put("categoryID", getCategoryByID("MLA124").getId());
        IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, item.getId())
                .source(dataMap);
        try {
            IndexResponse response = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        } catch (ElasticsearchException e) {
            e.getDetailedMessage();
        } catch (IOException ex) {
            ex.getLocalizedMessage();
        }
        return item;
    }


    public static Category insertCategory(Category category) {
        category.setId("MLA124");
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("id", "MLA124");
        dataMap.put("name", "Computacion");
        dataMap.put("siteID", getSiteByCode("MLA").getCode());
        IndexRequest indexRequest = new IndexRequest(INDEX, TYPE, category.getId())
                .source(dataMap);
        try {
            IndexResponse response = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        } catch (ElasticsearchException e) {
            e.getDetailedMessage();
        } catch (IOException ex) {
            ex.getLocalizedMessage();
        }
        return category;
    }

    public static Item getItemById(String id) {
        GetRequest getItemRequest = new GetRequest(INDEX, TYPE, id);
        GetResponse getResponse = null;
        try {
            getResponse = restHighLevelClient.get(getItemRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.getLocalizedMessage();
        }
        return getResponse != null ?
                objectMapper.convertValue(getResponse.getSourceAsMap(), Item.class) : null;
    }

    public static Category getCategoryByID(String id) {
        GetRequest getItemRequest = new GetRequest(INDEX, TYPE, id);
        GetResponse getResponse = null;
        try {
            getResponse = restHighLevelClient.get(getItemRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.getLocalizedMessage();
        }
        return getResponse != null ?
                objectMapper.convertValue(getResponse.getSourceAsMap(), Category.class) : null;
    }

    public static Site getSiteByCode(String code) {
        GetRequest getSiteRequest = new GetRequest(INDEX, TYPE, code);
        GetResponse getResponse = null;
        try {
            getResponse = restHighLevelClient.get(getSiteRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.getLocalizedMessage();
        }
        return getResponse != null ?
                objectMapper.convertValue(getResponse.getSourceAsMap(), Site.class) : null;
    }


    public static Item updateItemById(String id, Item item) {
        UpdateRequest updateRequest = new UpdateRequest(INDEX, TYPE, id)
                .fetchSource(true);    // Fetch Object after its update
        try {
            String personJson = objectMapper.writeValueAsString(item);
            updateRequest.doc(personJson, XContentType.JSON);
            UpdateResponse updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
            return objectMapper.convertValue(updateResponse.getGetResult().sourceAsMap(), Item.class);
        } catch (JsonProcessingException e) {
            e.getMessage();
        } catch (IOException e) {
            e.getLocalizedMessage();
        }
        System.out.println("Unable to update Item");
        return null;
    }

    public static void deleteItemById(String id) {
        DeleteRequest deleteRequest = new DeleteRequest(INDEX, TYPE, id);
        try {
            DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.getLocalizedMessage();
        }
    }


}
