package me.mikethesupertramp.toolkit.rfid;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RFIDReader implements NativeKeyListener {
    private static RFIDReader ourInstance = new RFIDReader();
    private long minInterval = 10;
    private long maxInterval = 20;
    private long minSequenceLength = 7;
    private String sequenceEndCharacter = "ENTER";
    private List<String> allowedCharacters = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
    private boolean enabled = true;
    private String sequence = "";
    private long lastPress;
    private List<RFIDListener> listeners = new ArrayList<>();

    private RFIDReader() {
    }

    private static void disableLogging() {
        // Get the logger for "org.jnativehook" and set the level to off.
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);

        // Don't forget to disable the parent handlers.
        logger.setUseParentHandlers(false);
    }

    public static void init() throws NativeHookException {
        disableLogging();
        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeKeyListener(ourInstance);
    }

    public static void shutdown() throws NativeHookException {
        GlobalScreen.unregisterNativeHook();
    }

    public static RFIDReader getInstance() {
        return ourInstance;
    }

    private void sequenceFinished() {
        if (sequence.length() >= minSequenceLength) {
            notifyListeners(sequence);
        }
        clean();
    }

    private void notifyListeners(String sequence) {
        listeners.forEach((l) -> l.onRFIDInput(sequence));
    }

    private void clean() {
        sequence = "";
    }

    private void addCharacter(String character) {
        sequence += character;
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        long interval = System.currentTimeMillis() - lastPress;
        lastPress = System.currentTimeMillis();
        String key = NativeKeyEvent.getKeyText(e.getKeyCode());

        if (interval > minInterval && interval < maxInterval) {
            if (key.equalsIgnoreCase(sequenceEndCharacter)) {
                sequenceFinished();
            } else {
                if (allowedCharacters.contains(key)) {
                    addCharacter(key);
                } else {
                    clean();
                }
            }
        } else {
            clean();
            if (allowedCharacters.contains(key)) {
                addCharacter(key);
            }
        }
    }

    public long getMinInterval() {
        return minInterval;
    }

    public void setMinInterval(long minInterval) {
        this.minInterval = minInterval;
    }

    public long getMaxInterval() {
        return maxInterval;
    }

    public void setMaxInterval(long maxInterval) {
        this.maxInterval = maxInterval;
    }

    public long getMinSequenceLength() {
        return minSequenceLength;
    }

    public void setMinSequenceLength(long minSequenceLength) {
        this.minSequenceLength = minSequenceLength;
    }

    public String getSequenceEndCharacter() {
        return sequenceEndCharacter;
    }

    public void setSequenceEndCharacter(String sequenceEndCharacter) {
        this.sequenceEndCharacter = sequenceEndCharacter;
    }

    public List<String> getAllowedCharacters() {
        return allowedCharacters;
    }

    public void setAllowedCharacters(List<String> allowedCharacters) {
        this.allowedCharacters = allowedCharacters;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void addListener(RFIDListener listener) {
        listeners.add(listener);
    }

    public void removeListener(RFIDListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {

    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {

    }

}
