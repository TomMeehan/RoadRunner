package com.ut3.roadrunner.game;

import android.graphics.Point;

import com.ut3.roadrunner.R;
import com.ut3.roadrunner.game.model.Bonus;
import com.ut3.roadrunner.game.model.Direction;
import com.ut3.roadrunner.game.model.GameObject;
import com.ut3.roadrunner.game.model.MovingObstacle;
import com.ut3.roadrunner.game.model.Obstacle;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ObjectGenerator {

    private final int N_COLUMNS = 4;

    private final int OBSTACLE_SPAWN_THRESHOLD = 30;

    private final int MOVING_OBSTACLE_SPAWN_THRESHOLD = 80;

    private final Point windowSize;

    private final int SIZE;

    public ObjectGenerator(Point windowSize){
        this.windowSize = windowSize;
        this.SIZE = windowSize.x/N_COLUMNS;
    }

    public void generate(List<GameObject> currentObjects){
        Random rand = new Random();
        int movingObstacleValue = rand.nextInt(100);

        if (movingObstacleValue > MOVING_OBSTACLE_SPAWN_THRESHOLD){
            currentObjects.add(generateMovingObstacle());
        } else {
            int nObjects = rand.nextInt(2)+1;

            List<GameObject> newObjects = new LinkedList<>();
            for (int i = 0; i < nObjects; i++){
                newObjects.add(generateObject());
            }

            setObjectsPositions(newObjects);
            currentObjects.addAll(newObjects);
        }
    }

    private GameObject generateObject(){
        GameObject object;
        Random rand = new Random();
        int spawnValue = rand.nextInt(100);

        if (spawnValue > OBSTACLE_SPAWN_THRESHOLD){
            object = generateObstacle();
        } else {
            object = generateBonus();
        }

        return object;
    }

    private Obstacle generateObstacle(){
        return new Obstacle(R.drawable.ic_rock,0, 0, SIZE, SIZE, windowSize);
    }

    private MovingObstacle generateMovingObstacle(){
        Random rand = new Random();

        int randDir = rand.nextInt(2);
        int randSpeed = rand.nextInt(3);

        Direction direction = randDir == 0 ? Direction.RIGHT : Direction.LEFT;
        int speedMultiplier = randSpeed == 0 ? 1 : 2;
        int x = direction.equals(Direction.LEFT) ? 0 - SIZE : this.windowSize.x;


        return new MovingObstacle(R.drawable.ic_rock, x,0, SIZE, SIZE, direction, speedMultiplier, windowSize);
    }

    private Bonus generateBonus(){
        Random rand = new Random();

        int randType = rand.nextInt(2);

        int scoreMultiplier = 1;
        int speedMultiplier = 1;

        if (randType == 0) scoreMultiplier = 2;
        else speedMultiplier = 2;

        return new Bonus(R.drawable.ic_rock, 0, 0, SIZE, SIZE, scoreMultiplier, speedMultiplier, windowSize);

    }

    private void setObjectsPositions(List<GameObject> objects){
        Random rand = new Random();
        List<Integer> possiblePositions = IntStream.range(0, N_COLUMNS).boxed().collect(Collectors.toList());
        int index;

        for(GameObject obj : objects){
            index = rand.nextInt(possiblePositions.size());
            obj.setX(possiblePositions.get(index) * SIZE);
            possiblePositions.remove(index);
        }

    }

}
