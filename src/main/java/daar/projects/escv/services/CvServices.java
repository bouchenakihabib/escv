package daar.projects.escv.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import daar.projects.escv.models.Cv;
import daar.projects.escv.repository.CvRepositoryI;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Component
public class CvServices implements CvServicesI
{
    RestHighLevelClient client;
    private CvRepositoryI cvRep;

    @Autowired
    private ObjectMapper objMapper;

    public CvServices(@Value("${elasticsearch.host}") String host,
                      @Value("${elasticsearch.port}") Integer port)
    {
        this.client = new RestHighLevelClient(RestClient.builder(new HttpHost(host, port, "http")));

    }

    @Autowired
    public void setRepository (CvRepositoryI cvRep)
    {
        this.cvRep = cvRep;
    }

    public void save (Cv cv) { cvRep.save(cv); }

    public void delete (Cv cv)
    {
        cvRep.delete(cv);
    }

    public Optional<Cv> findById(String id)
    {
        return cvRep.findById(id);
    }

    public Iterable<Cv> findAll()
    {
        return cvRep.findAll();
    }

    public Iterable<Cv> findByName(String name)
    {
        return cvRep.findByName(name);
    }

    @Override
    public List<Cv> searchByStringCv(String pattern)
    {
        SearchRequest request = new SearchRequest();
        request.indices("cvs");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        sourceBuilder.query(QueryBuilders.queryStringQuery(pattern).field("content"));
        request.source(sourceBuilder);
        SearchResponse res;

        List<Cv> results = new ArrayList<>();

        try
        {
            res = client.search(request, RequestOptions.DEFAULT);
            if(res.getHits().getTotalHits().value > 0)
            {
                SearchHit[] searchHits = res.getHits().getHits();
                for(SearchHit hit : searchHits)
                {
                    Map<String,Object> map = hit.getSourceAsMap();
                    results.add(objMapper.convertValue(map,Cv.class));
                }
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }

        return results;
    }
}
