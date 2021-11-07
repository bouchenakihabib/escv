package daar.projects.escv.controllers;

import daar.projects.escv.models.Cv;
import daar.projects.escv.services.CvServices;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static daar.projects.escv.utils.Convert.convertPdfToTxt;
import static daar.projects.escv.utils.Convert.convertWordToTxt;

@RestController
public class CvController
{
    @Autowired
    private CvServices cvServices;

    @CrossOrigin(origins = "http://localhost:8081")
    @PostMapping(value = "cvs/add")
    public List<String> addCv(@RequestParam("file") MultipartFile mFile)
    {
        try
        {
            String extension = Objects.requireNonNull(mFile.getContentType()).split("/")[1];
            String content;
            File file = new File("src/main/resources/tempFile.tmp");
            try(OutputStream os = new FileOutputStream(file))
            {
                os.write(mFile.getBytes());
            }

            switch(extension) {
                case "pdf":
                    content = convertPdfToTxt(file);
                    cvServices.save(new Cv(mFile.getOriginalFilename(), content));
                    break;
                case "vnd.openxmlformats-officedocument.wordprocessingml.document":
                    content = convertWordToTxt(file);
                    cvServices.save(new Cv(mFile.getOriginalFilename(), content));
                    break;
                default:
                    break;
            }
            if(!file.delete())
                throw new IOException("problem when deleting temp file.");
        }catch(IOException e)
        {
            e.printStackTrace();
        }
        Iterable<Cv> iterable = cvServices.findAll();
        List<String> result = new ArrayList<>();
        iterable.forEach(cv -> result.add(cv.getName()));

        return result;
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping(value = "cvs/find/{pattern}",
                produces = "application/json")
    public List<Cv> searchByPattern(@PathVariable String pattern)
    {
        List<Cv> cvList = cvServices.searchByStringCv(pattern);
        return cvList;
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping(value = "cvs/findByName/{name}")
    public Iterable<Cv> searchByName(@PathVariable String name)
    {
        return cvServices.findByName(name);
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping(value = "cvs/get/{cvId}")
    public Optional<Cv> searchById(@PathVariable String cvId)
    {
        return cvServices.findById(cvId);
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping(value = "cvs/getAll")
    public List<String> findAllCv()
    {
        Iterable<Cv> cvs = cvServices.findAll();
        List<String> result = new ArrayList<>();
        cvs.forEach(cv -> result.add(cv.getName()));
        return result;
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @DeleteMapping(value = "cvs/removeByName/{name}")
    public void deleteCvByName (@PathVariable String name)
    {
        Iterable<Cv> cvs = cvServices.findByName(name);
        cvs.forEach(cv -> cvServices.delete(cv));
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @DeleteMapping(value = "cvs/removeById/{cvId}")
    public void deleteCvById( @PathVariable String cvId)
    {
        Optional<Cv> cv = cvServices.findById(cvId);
        cv.ifPresent(value -> cvServices.delete(value));
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @DeleteMapping(value = "cvs/removeAll")
    public void deleteAll()
    {
        Iterable<Cv> cvs = cvServices.findAll();
        cvs.forEach(cv -> cvServices.delete(cv));
    }

}
