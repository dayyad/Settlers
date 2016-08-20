/**
 * Created by root on 21/08/16.
 */
public class TileFactory {

    /**
     * The Idea of this is to never call the Tile constructor
     * @param type
     * @param x
     * @param y
     * @param w
     * @param h
     * @return
     */
    public static Tile makeTile(String type, double x, double y, double w, double h){
        return new Tile(type, x, y, w, h);
    }
}
