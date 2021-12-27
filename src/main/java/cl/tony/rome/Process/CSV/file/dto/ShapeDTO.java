package cl.tony.rome.Process.CSV.file.dto;

import cl.tony.rome.Process.CSV.file.model.ShapeModel;
import com.univocity.parsers.annotations.Parsed;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class ShapeDTO {

    @Parsed(index = 0)
    private String shape;
    @Parsed(index = 2)
    private Float area;
    @Parsed(index = 3)
    private Float perimeter;
    @Parsed(index = 1)
    private String color;
    /**
    @Nested
    private ShapeMetadataDTO shapeMetadataDTO;
    */
    @Override
    public String toString() {
        return "ShapeDTO{" +
                "shape='" + shape + '\'' +
                ", area=" + area +
                ", perimeter=" + perimeter +
                ", color='" + color + '\'' +
                '}';
    }

    public String[] toArrayString(ShapeModel shapeModel){// TODO: ELiminar
        ShapeDTO shapeDTO = new ShapeDTO(shapeModel);
        return new String[]{shapeDTO.shape, shapeDTO.area.toString(),
                shapeDTO.perimeter.toString(), shapeDTO.color};
    }

    public ShapeDTO (ShapeModel shapeModel){ // TODO: ELiminar
        this.shape = shapeModel.getShape();
        this.area = shapeModel.getArea();
        this.perimeter = shapeModel.getPerimeter();
        this.color = shapeModel.getColor();
    }
}
