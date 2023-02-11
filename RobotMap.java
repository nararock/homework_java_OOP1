package ru.gb.lesson1.game;

import java.util.ArrayList;
import java.util.List;

public class RobotMap {

    private int limitRobots = 2; // ограничение на количество роботов
    private final int n;
    private final int m;

    private final List<Robot> robots;

    public RobotMap(int n, int m) {
        validateSizeMap(n, m);
        this.n = n;
        this.m = m;
        this.robots = new ArrayList<>();
    }

    // коонструктор с количествоим роботов
    public RobotMap(int n, int m, int limitRobots) {
        this(n, m);
        this.limitRobots = limitRobots > 0 ? limitRobots : this.limitRobots;
    }

    public Robot createRobot(Point point) {
        checkLimitRobots();
        validatePoint(point);
        Robot robot = new Robot(point);
        robots.add(robot);

        return robot;
    }

    // проверка на превышение предела значения роботов
    private void checkLimitRobots() {
        if (robots.size() == limitRobots) {
            throw new IllegalStateException("Превышен предел числа роботов!");
        }
    }

    // валидация параметров при создании карты
    private void validateSizeMap(int n, int m) {
        if (n <= 0 || m <= 0) {
            throw new IllegalStateException("Некорректное значение размера карты!");
        }
    }

    private void validatePoint(Point point) {
        validatePointIsCorrect(point);
        validatePointIsFree(point);
    }

    private void validatePointIsCorrect(Point point) {
        if (point.x() < 0 || point.x() > n || point.y() < 0 || point.y() > m) {
            throw new IllegalStateException("Некорректное значение точки!");
        }
    }

    private void validatePointIsFree(Point point) {
        for (Robot robot : robots) {
            Point robotPoint = robot.point;
            if (robotPoint.equals(point)) {
                throw new IllegalStateException("Точка " + point + " занята!");
            }
        }
    }

    public class Robot {

        public static final Direction DEFAULT_DIRECTION = Direction.TOP;

        private Direction direction;
        private Point point;

        public Robot(Point point) {
            this.direction = DEFAULT_DIRECTION;
            this.point = point;
        }

        public void changeDirection(Direction direction) {
            this.direction = direction;
        }

        public void move() {
            Point newPoint = switch (direction) {
                case TOP -> new Point(point.x() - 1, point.y());
                case RIGHT -> new Point(point.x(), point.y() + 1);
                case BOTTOM -> new Point(point.x() + 1, point.y());
                case LEFT -> new Point(point.x(), point.y() - 1);
            };
            validatePoint(newPoint);

            System.out.println("Робот переместился с " + point + " на " + newPoint);
            this.point = newPoint;
        }

        // move с параметром
        public void move(int steps) {
            for (int i = 0; i < steps; i++) {
                move();
            }
        }

        @Override
        public String toString() {
            return point.toString() + ", [" + direction.name() + "]";
        }
    }

}
