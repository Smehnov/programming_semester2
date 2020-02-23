public class Coordinates {
    private Integer x; //Поле не может быть null
    private Long y; //Поле не может быть null

    public Coordinates(Integer x, Long y){
        this.x = x;
        this.y = y;
    }

    public Integer getX(){
        return this.x;
    }

    public Long getY() {
        return this.y;
    }
}