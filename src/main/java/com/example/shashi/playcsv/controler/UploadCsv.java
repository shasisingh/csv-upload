package com.example.shashi.playcsv.controler;

import com.example.shashi.playcsv.model.BulkUpload;
import com.example.shashi.playcsv.service.CSVParser;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/administration/csv")
public class UploadCsv {

    private final CSVParser csvParser;

    @Autowired
    public UploadCsv(CSVParser csvParser) {
        this.csvParser = csvParser;
    }

    @ResponseBody
    @PostMapping(value = "/upload", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<BulkUpload> uploadCertificate(@RequestParam("upload") MultipartFile certificate) throws IOException {
        Reader targetReader = new InputStreamReader(certificate.getInputStream());
        if (validateSize(certificate.getSize())) {
            return csvParser.readCSVToBean(targetReader, CsvPreference.STANDARD_PREFERENCE);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "file size is too big. max=5 MB" + ",received:" + FileUtils.byteCountToDisplaySize(certificate.getSize()));
        }
    }

    public boolean validateSize(long bytes) {
        return bytes <= 5000000;
    }

}


