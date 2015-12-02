//Seitenleiste; stellt Infos zu Pieces dar

package stages;

import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pieces.Piece;
import javafx.scene.control.Label;

public class Info {
	private Stage infoStage;
	private Scene infoScene;
	private GridPane infoPane;
	private Pane spring;  //für Gridpane: freier Platz oben
	private Pane spring2; //			: freier Platz links
	private Label[] labels;
	private ImageView pieceView;

	public Info() {
		pieceView = new ImageView();
		
		infoStage = new Stage();
		infoStage.setResizable(false);		
		infoStage.initStyle(StageStyle.UNDECORATED);
		
		infoPane = new GridPane();
		infoScene = new Scene(infoPane, 200, 535);
		infoStage.setScene(infoScene);
		
		spring = new Pane();
	    spring.minHeightProperty().setValue(30);
	    spring.setMinWidth(30);
	    spring2 = new Pane();
	    spring2.minHeightProperty().setValue(50);
	    
	    infoPane.add(spring, 0, 0);
	    infoPane.add(spring2, 0, 2);
	    labels = new Label[10];
	    
	    for(int i=0;i<10;i++){
	    	labels[i]=new Label();
	    	infoPane.add(labels[i],0,i+3,3,1);
	    }
	    
	    infoPane.add(pieceView, 1, 1);
	}
	
	//setzt die Leiste an die richtige Stelle
	public void setPosition(Stage stage){
		infoStage.setX((int) stage.getX()+ stage.getWidth()-2);
		infoStage.setY((int) stage.getY()+1);
	}
	
	//gibt Infos zu aktuellem Piece aus
	public void showInfo(Piece piece){
		pieceView.setImage(piece.getImage());
		pieceView.setScaleX(2.5);
		pieceView.setScaleY(2.5);
		
		labels[0].setText("Current Health: " + piece.getHealth());
		labels[1].setText("Max Health: " + piece.getPieceT().getMaxHealth());
		labels[2].setText("Ability Cooldown: " + piece.getAbilityCooldown());
		for (int i=0;i<piece.getStatusEffects().size()-1;i++){
			labels[i+3].setText(piece.getStatusEffects().get(i).getAbility().getStatusName()+"/"+
								piece.getStatusEffects().get(i).getAbility().getDuration());
		}
	}
	
	public void removeAbilityLabels(){
		for(int i=3;i<8;i++)
			labels[i].setText("");
	}
	
	
	// Methoden zum Schließen und Öffnen der Stage
		public void closeStage() {infoStage.close();}
		public void showStage() {infoStage.show();}
		public boolean isVisible() {return infoStage.isShowing();}
}
