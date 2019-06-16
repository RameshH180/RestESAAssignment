package com.spring.assignment.serviecimpl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.spring.assignment.model.User;
import com.spring.assignment.service.UserService;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RestHighLevelClient client;

    private static final String indexAndType = "users";

    public UserServiceImpl() {
    }

    @Override
    public List<User> getAllUsers(Integer pageNum) {
        int size = 2;
        List<User> users = new ArrayList<>();
        SearchRequest searchRequest = new SearchRequest(indexAndType);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        //can be moved to properties
        searchSourceBuilder.size(size);
        searchSourceBuilder.from(size * (pageNum - 1));
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            for (SearchHit hit : searchResponse.getHits().getHits()) {
                String sourceString = hit.getSourceAsString();
                if (sourceString != null) {
                    Gson gsn = new Gson();
                    users.add(gsn.fromJson(sourceString, User.class));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void createUser(User user) {
        Long id = user.getId();
        User tmp = getUser(id);
        Gson gsn = new Gson();
        IndexResponse indexResponse;
        IndexRequest request = new IndexRequest(indexAndType, indexAndType);
        request.id(id.toString());
        if (tmp != null) {
            JsonObject inputJson = gsn.fromJson(gsn.toJson(user), JsonObject.class);
            JsonObject jsonFromES = gsn.fromJson(gsn.toJson(tmp), JsonObject.class);
            //updating all the new values to existing User
            for (String key : inputJson.keySet()) {
                jsonFromES.add(key, inputJson.get(key));
            }
            request.source(jsonFromES.toString(), XContentType.JSON);
            try {
                indexResponse = client.index(request, RequestOptions.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            String jsonString = gsn.toJson(user);
            request.source(jsonString, XContentType.JSON);
            try {
                indexResponse = client.index(request, RequestOptions.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public User getUser(Long id) {
        GetRequest getRequest = new GetRequest(indexAndType, indexAndType, id.toString());
        getRequest.fetchSourceContext(new FetchSourceContext(true));
        try {
            GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
            if (!getResponse.isSourceEmpty()) {
                Gson gsn = new Gson();
                return gsn.fromJson(getResponse.getSourceAsString(), User.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteUser(Long id) {
        DeleteRequest request = new DeleteRequest(
                indexAndType, indexAndType,
                id.toString());
        try {
            DeleteResponse deleteResponse = client.delete(
                    request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> searchUsers(String text) {
        List<User> users = new ArrayList<>();
        Field[] declaredFields = User.class.getDeclaredFields();
        ArrayList<String> fields = new ArrayList<String>();
        for (Field field : declaredFields) {
            fields.add(field.getName());
        }
        MultiMatchQueryBuilder mmqb = QueryBuilders.multiMatchQuery(text, fields.toArray(new String[fields.size()]));
        SearchRequest searchRequest = new SearchRequest(indexAndType);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(mmqb);
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            for (SearchHit hit : searchResponse.getHits().getHits()) {
                String sourceString = hit.getSourceAsString();
                if (sourceString != null) {
                    Gson gsn = new Gson();
                    users.add(gsn.fromJson(sourceString, User.class));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }
}
