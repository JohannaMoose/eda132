package game;

/**
 * Created by Johanna on 2016-02-03.
 */
public class Disk {

    private Color _color;

    public Disk(Color color){
        _color = color;
    }

    public void flip(){
        if(_color == Color.Black)
            _color = Color.White;
        else
            _color = Color.Black;
    }

    public Color getColor(){
        return _color;
    }

    public enum Color {
        Black,
        White
    }
}
