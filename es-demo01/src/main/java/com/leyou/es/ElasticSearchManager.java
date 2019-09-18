package com.leyou.es;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;

public class ElasticSearchManager {

    TransportClient client = null;

    @Before
    public void initClient() throws Exception{

       client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
    }

    @Test
    public void testQuery(){
        QueryBuilder qb= QueryBuilders.termQuery("goodsName","小米");//构建term查询

        SearchResponse searchResponse = client.prepareSearch("heima66")//执行查询的索引库
                                   .setQuery(qb)//把构建的term查询  放入到请求中生效
                                   .get();//执行

        SearchHit[] searchHits = searchResponse.getHits().getHits();
        for (SearchHit searchHit : searchHits) {
            String sourceAsString = searchHit.getSourceAsString();
            System.out.println(sourceAsString);
        }
    }
    @After
    public void end(){
        client.close();
    }
}
