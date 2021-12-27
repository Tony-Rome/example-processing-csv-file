package cl.tony.rome.Process.CSV.file.service;

import cl.tony.rome.Process.CSV.file.dto.ShapeDTO;
import cl.tony.rome.Process.CSV.file.model.ShapeModel;
import cl.tony.rome.Process.CSV.file.repository.ShapeRepository;
import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    /*
    *  TODO:
    *   1 - Recibir archivo
    *   2 - Procesar rows y mapearlos a objetos
    *   3  Persistir datos
    *   4 - Obtener datos
    *   5 - Mapear hacia rows y escribir archivo CSV
    *   6 - Exportar archivo
    *   IMPORTANTE servicios se crean como restfull
    * */

    /*
    * TODO:
    *  Servicios que se deben tener en este mini proyecto:
    *   - Leer y parsear simple CSV a java beans - LISTO
    *   - Leer de forma anidada respecto a un valor pivote por columna
    *   - Escribir un archivo CSV con Univocity con datos extraidos de DB, h2 es el más simple
    *   -
    * */
    private final ShapeRepository shapeRepository;

    @Autowired
    public FileService(ShapeRepository shapeRepository){
        this.shapeRepository = shapeRepository;
    }

    public void processFile(MultipartFile file){

        BeanListProcessor<ShapeDTO> processor = new BeanListProcessor<>(ShapeDTO.class);

        CsvParserSettings settings = new CsvParserSettings();
        settings.setAutoClosingEnabled(true);
        settings.setLineSeparatorDetectionEnabled(true);
        settings.setHeaderExtractionEnabled(true);
        settings.setProcessor(processor);

        settings.setDelimiterDetectionEnabled(true, ";".toCharArray());

        CsvParser parser = new CsvParser(settings);

        List<ShapeDTO> rows = null;

        try(InputStream inputStream = file.getInputStream()){
            parser.parse(inputStream);
             rows = processor.getBeans();
            parser.stopParsing();
            processor.processEnded(parser.getContext());

            rows.stream().forEach( row -> System.out.println("Row processed: " + row.toString()));
        }catch(IOException exception) {
            System.out.println(exception.fillInStackTrace());
        }

        // TODO: Si no es null, persisitir, Refactorizar esto.
        this.persistRows(rows);
    }

    public void transformRow(){
        List<ShapeModel> shapeModelList = shapeRepository.findAll();
    }

    public ByteArrayOutputStream createCsvFile(){
        List<String> headers = shapeRepository.getHeaders();

        headers.stream().forEach(header -> System.out.println("Headers: " + header));
        List<Object[]> rows = new ArrayList<>();

        List<ShapeModel> shapeModelList = shapeRepository.findAll();

        shapeModelList.stream().forEach(shapeModel -> System.out.println("shapeModel: " + shapeModel));

        shapeModelList.stream().forEach(shapeModel -> {
            String[] row = shapeModel.toArrayString();
            rows.add(row);
        });

        ByteArrayOutputStream csv = new ByteArrayOutputStream();
        Writer writer = new OutputStreamWriter(csv);
        CsvWriter csvWriter = new CsvWriter(writer, new CsvWriterSettings());
        csvWriter.writeHeaders(headers);
        csvWriter.writeRowsAndClose(rows);
        return csv;
    }

    public void persistRows(List<ShapeDTO> shapeDTOList){
        final int shapeDTOListSize = shapeDTOList.size();

        for(int index = 0; index < shapeDTOListSize; index++){
            ShapeDTO shapeDTO = shapeDTOList.get(index);

            ShapeModel shapeModel = new ShapeModel();
            shapeModel.setShape(shapeDTO.getShape());
            shapeModel.setColor(shapeDTO.getColor());
            shapeModel.setArea(shapeDTO.getArea());
            shapeModel.setPerimeter(shapeDTO.getPerimeter());
            shapeRepository.save(shapeModel);
        }
    }
}
