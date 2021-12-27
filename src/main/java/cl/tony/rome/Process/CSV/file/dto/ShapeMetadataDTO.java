package cl.tony.rome.Process.CSV.file.dto;

import com.univocity.parsers.annotations.Parsed;

public class ShapeMetadataDTO {

    @Parsed(index = 1)
    private String name;

    @Parsed(index = 2)
    private String color;

    @Parsed(index = 3)
    private float area;

    @Parsed(index = 4)
    private float perimeter;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    public float getPerimeter() {
        return perimeter;
    }

    public void setPerimeter(float perimeter) {
        this.perimeter = perimeter;
    }

    @Override
    public String toString() {
        return "ShapeMetadataDTO{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", area=" + area +
                ", perimeter=" + perimeter +
                '}';
    }
}
