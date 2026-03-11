package net.eckener.dungeon_crawler.logic;

public enum EntityDirection {
        LEFT(-1, 0),
        RIGHT(1, 0),
        UP(0, 1),
        DOWN(0, -1);

        private final int dx;
        private final int dy;

        EntityDirection(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }

        public int dx() { return dx; }
        public int dy() { return dy; }
}
