import java.util.*;
public class EnemyPlane {
        private int x;
        private int y;
        private int width = 50;
        private int height = 50;
        public EnemyPlane(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void move() {
            y += 3;  // Adjust the speed as needed
        }
        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }
       
}
