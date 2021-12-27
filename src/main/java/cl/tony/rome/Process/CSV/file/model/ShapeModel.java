package cl.tony.rome.Process.CSV.file.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "shape", schema = "shape")
@Data
@NoArgsConstructor
public class ShapeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String shape;
    private Float area;
    private Float perimeter;
    private String color;

    public String[] toArrayString( ){
        return new String[]{id.toString(), shape, area.toString(),
                perimeter.toString(), color};
    }
}
