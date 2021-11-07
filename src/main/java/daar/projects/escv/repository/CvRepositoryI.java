package daar.projects.escv.repository;

import daar.projects.escv.models.Cv;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CvRepositoryI extends ElasticsearchRepository<Cv,String>
{
    Iterable<Cv> findByName(String name);
}
