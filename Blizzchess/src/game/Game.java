//handlet switch-case

package game;
import stages.View;

public class Game{

	public static void main(String[] args) {
        new Thread() {
            @Override
            public void run() {
                javafx.application.Application.launch(View.class);
            }
        }.start();
        
        
    }
}
