package cl.tony.rome.Process.CSV.file.controller;

import cl.tony.rome.Process.CSV.file.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;

@Controller
@RequestMapping(value = "/v1")
public class FileController {

    // TODO: Crear base de datos
    // TODO: Agregar Univocity dependency
    @Autowired
    private FileService fileService;

    @GetMapping(value = "/files")
    public ResponseEntity<byte[]> createFile(){
        ByteArrayOutputStream csv = fileService.createCsvFile();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=csv_result.csv");
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, "text/csv");
        return new ResponseEntity(csv.toByteArray(), httpHeaders ,HttpStatus.OK);
    }

    @PostMapping(value = "/files")
    public ResponseEntity processFile(@RequestParam(name = "file") MultipartFile file){
        // TODO: Ver como customizar excepcion DefaultHandlerExceptionResolver con nombre parametro distinto
        System.out.println("File uploaded: " + file.getName());
        fileService.processFile(file);
        return new ResponseEntity(HttpStatus.OK);
    }

}
