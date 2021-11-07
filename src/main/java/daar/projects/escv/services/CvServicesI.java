package daar.projects.escv.services;


import daar.projects.escv.models.Cv;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CvServicesI
{
    List<Cv> searchByStringCv (String pattern);
}
