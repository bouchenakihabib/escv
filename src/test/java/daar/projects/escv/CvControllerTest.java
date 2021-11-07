package daar.projects.escv;

import daar.projects.escv.models.Cv;
import daar.projects.escv.services.CvServices;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EscvApplication.class)
public class CvControllerTest
{
    Cv cv = new Cv("Sample_CV", "CV used as reference to test CvController");

    @Autowired
    private CvServices cvService;


    @Test
    public void saveCv() {
        cvService.save(cv);
        assertNotNull(cvService.findById(cv.getName()));
    }

    @Test
    public void searchCv() {
        List<Cv> cvs = cvService.searchByStringCv("CvController");
        cvs.forEach(e -> assertEquals(e.getName(), cv.getName()));
    }

    @Test
    public void getCvByName() {
        Iterable<Cv> cvs = cvService.findByName("Sample_CV");
        cvs.forEach(e -> assertEquals(e.getName(), cv.getName()));
    }

    @Test
    public void deleteCv() {
        cvService.delete(cv);
        Iterable<Cv> cvs = cvService.findAll();
        cvs.forEach(e -> assertNotEquals(e.getCvId(), cv.getCvId()));
    }
}
