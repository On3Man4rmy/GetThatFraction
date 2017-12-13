import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener {
    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("Key typed: " +  e.getKeyChar());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Key pressed: " +  e.getKeyChar());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("Key released: " +  e.getKeyChar());
    }

    public Input() {
    }
}
