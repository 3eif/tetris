import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Score extends Text{
    private int scoreVal;

    public Score() {
        scoreVal = 0;
        setFont(new Font(30));
        setStyle("-fx-text-fill: green; -fx-font-size: 16px;");
        updateDisplay();
    }

    public void updateDisplay() {
        this.setText("Score: " + scoreVal);
    }

    public int getScoreVal() {
        return scoreVal;
    }

    public void setScoreVal(int val) {
        scoreVal = val;
        updateDisplay();
    }
}
