package com.SER216.BattleShip;

import com.SER216.Random.IRandomWrapper;
import com.SER216.Random.RandomWrapper;


import java.io.IOException;
import java.util.Random;
import java.util.Stack;

import static com.SER216.BattleShip.Util.boardWidth;
import static com.SER216.BattleShip.Util.reverse;

/**
 * Created by zawata on 4/12/2017.
 */
public class CPU extends Player {
    private Random rand = new Random();

    public CPU(String name) {
        super(name);
        rand.setSeed(0);
    }

    private enum Direction {
        Up(0, -1),
        Right(1, 0),
        Down(0, 1),
        Left(-1, 0);

        private final int Xvalue;
        private final int Yvalue;

        Direction(final int Xvalue, final int Yvalue) {
            this.Xvalue = Xvalue;
            this.Yvalue = Yvalue;
        }

        public int getXvalue() {
            return Xvalue;
        }

        public int getYvalue() {
            return Yvalue;
        }

        @SuppressWarnings("Duplicates")
        public Direction getCW() {
            switch (this) {
                case Up:
                    return Right;
                case Right:
                    return Down;
                case Down:
                    return Left;
                case Left:
                    return Up;
                default:
                    return this;
            }
        }

        @SuppressWarnings("Duplicates")
        public Direction getCCW() {
            switch (this) {
                case Up:
                    return Left;
                case Right:
                    return Up;
                case Down:
                    return Right;
                case Left:
                    return Down;
                default:
                    return this;
            }
        }

        @SuppressWarnings("Duplicates")
        public Direction getOpposite() {
            switch (this) {
                case Up:
                    return Down;
                case Right:
                    return Left;
                case Down:
                    return Up;
                case Left:
                    return Right;
                default:
                    return this;
            }
        }
    }

    @SuppressWarnings("Duplicates")
    public void placeShips() {
        int direction;
        int x, y;

        Ship ship;

        for (Ship.ShipType shiptype : Ship.ShipType.values()) {
            //attempt to add a ship until it succeeds
            while (true) {
                direction = rand.nextInt(2);

                if (direction == Ship.Direction.Vertical.getValue()) {
                    x = rand.nextInt(boardWidth) + 1;
                    y = rand.nextInt(boardWidth - shiptype.getSize()) + 1;
                    ship = new Ship(shiptype, Ship.Direction.Vertical);

                    //if the ship intersects with another then start over
                    boolean valid = true;
                    for (int i = 0; i < ship.getSize(); i++) {
                        if (getShipBoardValue(x, (i + y)) != MainGUI.TileColors.Blank.getValue())
                            valid = false;
                    }
                    if (valid) {
                        //place ship if things are good
                        ship.place(x, y);
                        addShip(ship);
                        for (int i = 0; i < ship.getSize(); i++) {
                            setShipBoardValue(x, (i + y), MainGUI.TileColors.Green.getValue());
                        }
                        break;
                    }
                } else {
                    if (direction == Ship.Direction.Horizontal.getValue()) {
                        x = rand.nextInt(boardWidth - shiptype.getSize()) + 1;
                        y = rand.nextInt(boardWidth) + 1;
                        ship = new Ship(shiptype, Ship.Direction.Horizontal);

                        //if the ship intersects with another then start over
                        boolean valid = true;
                        for (int i = 0; i < ship.getSize(); i++) {
                            if (getShipBoardValue((i + x), y) != MainGUI.TileColors.Blank.getValue())
                                valid = false;
                        }
                        if (valid) {
                            //place ship if things are good
                            ship.place(x, y);
                            addShip(ship);
                            for (int i = 0; i < ship.getSize(); i++) {
                                setShipBoardValue((i + x), y, MainGUI.TileColors.Green.getValue());
                            }
                            break;
                        }
                    }
                }
            }
        }
    }

    private Stack<hitStackElem> hitStack = new Stack<>();
    private boolean reversed = false;
    private boolean shotSet = false;

    @SuppressWarnings("Duplicates")
    public boolean fire(Player opponent) throws IOException {
        int x, y, retVal;

        x = (rand.nextInt(boardWidth) + 1);
        y = (rand.nextInt(boardWidth) + 1);

        while (!(MainGUI.getColorByValue(getShotBoardValue(x, y)).equals(MainGUI.TileColors.Blank))) {
            x = (rand.nextInt(boardWidth) + 1);
            y = (rand.nextInt(boardWidth) + 1);
        }

        if (hitStack.size() == 0) {
            System.out.println("no hits");
            retVal = opponent.receiveShot(x, y);
            switch (retVal) {
                case 0: //miss
                    System.out.println("Random miss");
                    this.setShotBoardValue(x, y, MainGUI.TileColors.Grey.getValue());
                    return true;
                case 1: //hit
                    System.out.println("Random hit");
                    setShotBoardValue(x, y, MainGUI.TileColors.Red.getValue());
                    hitStack.push(new hitStackElem(
                            x,
                            y,
                            x + Direction.Up.getXvalue(),
                            y + Direction.Up.getYvalue(),
                            Direction.Up));
                    return true;
                default:
                    throw new IOException("Unhandled ReceiveShot Number: " + retVal);
            }
        } else {
            System.out.println("hitStack size: " + hitStack.size());
            int newShotX = 0;
            int newShotY = 0;

            for (int i = 0; i <= 4 && !shotSet; i++) {
                if (i == 4) {
                    if (hitStack.size() > 1) {
                        Direction old_direction = hitStack.peek().direction;
                        hitStackElem origin_elem = hitStack.elementAt(0);
                        for (Object elem : reverse(hitStack)) {
                            hitStackElem HTElem = (hitStackElem) elem;
                            if (old_direction.equals(HTElem.direction)) {
                                origin_elem = HTElem;
                            } else {
                                break;
                            }
                        }
                        hitStackElem top = hitStack.pop();
                        origin_elem.reverse();
                        top.NextX = origin_elem.CurrX + origin_elem.direction.getXvalue();
                        top.NextY = origin_elem.CurrY + origin_elem.direction.getYvalue();
                        top.direction = origin_elem.direction;
                        hitStack.push(top);
                        break;
                    } else {
                        throw new IOException("Size one ship?");
                    }
                }
                hitStackElem calcNext = new hitStackElem(hitStack.peek());
                calcNext.CurrX = hitStack.peek().CurrX + hitStack.peek().direction.getXvalue();
                calcNext.CurrY = hitStack.peek().CurrY + hitStack.peek().direction.getYvalue();
                if (calcNext.CurrX != hitStack.peek().NextX || calcNext.CurrY != hitStack.peek().NextY) {
                    newShotX = hitStack.peek().NextX;
                    newShotY = hitStack.peek().NextY;
                    break;
                } else {
                    newShotX = calcNext.CurrX;
                    newShotY = calcNext.CurrY;
                    for (int j = 0; j <= 4; j++) {
                        if (j == 4) {
                            if (hitStack.size() > 1) {
                                Direction old_direction = hitStack.peek().direction;
                                hitStackElem origin_elem = hitStack.elementAt(0);
                                for (Object elem : reverse(hitStack)) {
                                    hitStackElem HTElem = (hitStackElem) elem;
                                    if (old_direction.equals(HTElem.direction)) {
                                        origin_elem = HTElem;
                                    } else {
                                        break;
                                    }
                                }
                                hitStackElem top = hitStack.pop();
                                origin_elem.reverse();
                                top.NextX = origin_elem.CurrX + origin_elem.direction.getXvalue();
                                top.NextY = origin_elem.CurrY + origin_elem.direction.getYvalue();
                                top.direction = origin_elem.direction;
                                hitStack.push(top);
                                break;
                            } else {
                                throw new IOException("Size one ship?");
                            }
                        }
                        newShotX = hitStack.peek().CurrX + hitStack.peek().direction.getXvalue();
                        newShotY = hitStack.peek().CurrY + hitStack.peek().direction.getYvalue();
                        System.out.println("boundsCheck: " + (checkBounds(newShotX) & checkBounds(newShotY)));
                        //System.out.println("tileCheck: " + (MainGUI.getColorByValue(this.getShotBoardValue(newShotX, newShotY)).equals(MainGUI.TileColors.Blank)));
                        if (!(checkBounds(newShotX) & checkBounds(newShotY)) ||
                                !(MainGUI.getColorByValue(this.getShotBoardValue(newShotX, newShotY)).equals(MainGUI.TileColors.Blank))) {
                            hitStackElem temp = hitStack.pop();
                            temp.turnRight();
                            hitStack.push(temp);
                        }
                        else {
                            shotSet = true;
                            break;
                        }
                    }
                }
            }
            shotSet = false;
            retVal = opponent.receiveShot(newShotX, newShotY);
            switch (retVal) {
                case 0: //miss
                    this.setShotBoardValue(newShotX, newShotY, MainGUI.TileColors.Grey.getValue());
                    if (hitStack.size() > 1 && !reversed) {
                        Direction old_direction = hitStack.peek().direction;
                        hitStackElem origin_elem = hitStack.elementAt(0);
                        for (Object elem : reverse(hitStack)) {
                            hitStackElem HTElem = (hitStackElem) elem;
                            if (old_direction.equals(HTElem.direction)) {
                                origin_elem = HTElem;
                            } else {
                                break;
                            }
                        }
                        hitStackElem top = hitStack.pop();
                        origin_elem.reverse();
                        top.NextX = origin_elem.CurrX + origin_elem.direction.getXvalue();
                        top.NextY = origin_elem.CurrY + origin_elem.direction.getYvalue();
                        top.direction = origin_elem.direction;
                        hitStack.push(top);
                        reversed = true;
                    } else {
                        reversed = false;
                        hitStackElem temp = hitStack.pop();
                        temp.turnRight();
                        hitStack.push(temp);
                    }
                    return true;
                case 1: //hit
                    setShotBoardValue(newShotX, newShotY, MainGUI.TileColors.Red.getValue());
                    hitStack.push(new hitStackElem(
                            newShotX,
                            newShotY,
                            newShotX + hitStack.peek().direction.getXvalue(),
                            newShotY + hitStack.peek().direction.getYvalue(),
                            hitStack.peek().direction));
                    return true;
                case 2: //sunk Patrol Boat
                case 3: //sunk Submarine or Destroyer
                case 4: //sunk Battleship
                case 5: //sunk Carrier
                    System.out.print("Sunk size:" + retVal);
                    setShotBoardValue(newShotX, newShotY, MainGUI.TileColors.Red.getValue()); //if its not displaying then we don't need to mark it black

                    // the number of values on the stack is one less than the size
                    // as the last hit is a sunk and doesn't get put on the stack
                    for(int i = 0; i < retVal - 1; i++) {
                        hitStack.pop();
                    }
                    if(hitStack.size() != 0) {
                        hitStackElem temp = hitStack.pop();
                        temp.turnRight();
                        temp.NextX = temp.CurrX + temp.direction.getXvalue();
                        temp.NextY = temp.CurrY + temp.direction.getYvalue();
                        hitStack.push(temp);
                    }
                    return true;
                default:
                    throw new IOException("Unhandled ReceiveShot Number: " + retVal);
            }
        }
    }
    private boolean checkBounds(int i) {
        return (i > 0) && (i <= boardWidth);
    }

    //used purely in this class
    //basically just a C Struct in Java;
    private class hitStackElem {

        //since its just a small private type I'm not following
        // object oriented principals and making everything public
        public int CurrX;
        public int CurrY;

        public int NextX;
        public int NextY;
        public Direction direction;

        public hitStackElem(int currX, int currY, int nextX, int nextY, Direction direction) {
            this.CurrX = currX;
            this.CurrY = currY;
            this.NextX = nextX;
            this.NextY = nextY;
            this.direction = direction;
        }

        public hitStackElem(hitStackElem HTE) {
            this.CurrX = HTE.CurrX;
            this.CurrY = HTE.CurrY;
            this.NextX = HTE.NextX;
            this.NextY = HTE.NextY;
            this.direction = HTE.direction;
        }

        public void turnRight() {
            direction = direction.getCW();
        }

        public void reverse() {
            direction = direction.getOpposite();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            hitStackElem that = (hitStackElem) o;

            if (CurrX != that.CurrX) return false;
            if (CurrY != that.CurrY) return false;
            return direction == that.direction;
        }

        @Override
        public int hashCode() {
            int result = CurrX;
            result = 31 * result + CurrY;
            result = 31 * result + direction.hashCode();
            return result;
        }
    }
}
