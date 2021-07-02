public final class Square {
    private final int x;
    private final int y;

    public Square(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof Square)) {
            return false;
        }

        Square otherSquare= (Square) object;
        return this.x == otherSquare.x && this.y == otherSquare.y;
    }

    @Override
    public int hashCode() {
        int result = 17; // any prime number
        result = 31 * result + Integer.valueOf(this.x).hashCode();
        result = 31 * result + Integer.valueOf(this.y).hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "[" + String.valueOf(x) + " " + String.valueOf(y) + "] ";
    }
}