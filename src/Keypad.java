package homesafe.digital;

import javafx.scene.layout.GridPane;

public class Keypad extends GridPane {
    private int width;
    private int height;
    private Key[][] keyGrid;
    private AccessSafe safe;

    /**
     * Keypad() - constructor for the keypad class
     * @param height - height of keypad
     * @param width - width of keypad
     */
    public Keypad(int height, int width, AccessSafe safe){
        this.width = width;
        this.height = height;
        this.safe = safe;
        this.keyGrid = new Key[height][width];
        this.setVgap(10);
        this.setHgap(20);
        setupKeypad(height,width,this);
        setupStyle(this);
    }

    /**
     * setupKeypad() -sets up the keypad
     * @param height - height of the keypad
     * @param width - width of keypad
     * @param keypad - keypad to construct
     */
    public void setupKeypad(int height, int width,Keypad keypad){
        int tempHeight = height - 2;
        int tempCol = width - 1;
        char count = '1';
        for(int i = tempHeight; i >= 0; i--){
            for(int j = 0; j <= tempCol; j++){
                System.out.println(i);
                System.out.println(j);
                System.out.println();
                Key key = new Key(count,i,j,this);
                keypad.getKeyGrid()[i][j] = key;
                keypad.add(key,j,i);
                count++;
            }
        }
        //setting up the lower 3 keys in the keypad
        keypad.add(new Key('*',height,0,keypad),0,height);
        keypad.add(new Key('0',height,1,keypad),1,height);
        keypad.add(new Key('#',height,2,keypad),2,height);
    }

    public void setupStyle(Keypad keypad){
        keypad.setMaxWidth(350);
        keypad.setMinHeight(500);
        keypad.setStyle("-fx-background-color: #434343; -fx-background-radius: 20px;");
    }
    public void pressKey(Key key){
        this.safe.pressKey(key);
    }

    public Key getKey(int row, int col){
        return this.keyGrid[row][col];
    }

    public Key[][] getKeyGrid() {
        return keyGrid;
    }
}
