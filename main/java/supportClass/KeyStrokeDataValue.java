package supportClass;

public class KeyStrokeDataValue {
    private String character;
    private long pressTime;
    private long releaseTime;

    @Override
    public String toString() {
        return "KeyStrokeDataValue{" +
                "character='" + character + '\'' +
                ", pressTime=" + pressTime +
                ", releaseTime=" + releaseTime +
                '}';
    }

    public KeyStrokeDataValue(String character, long pressTime, long releaseTime) {
        this.character = character;
        this.pressTime = pressTime;
        this.releaseTime = releaseTime;
    }

    public String getCharacter() {

        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public long getPressTime() {
        return pressTime;
    }

    public void setPressTime(long pressTime) {
        this.pressTime = pressTime;
    }

    public long getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(long releaseTime) {
        this.releaseTime = releaseTime;
    }
}
