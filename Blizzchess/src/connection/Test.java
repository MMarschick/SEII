package connection;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Test extends Canvas
{
	private GraphicsContext gcCanvas;
	public GraphicsContext getGcCanvas(){return gcCanvas;}
	public Test(String color)
	{
		String colorN = color.toUpperCase();
		super.setHeight(700);
		super.setWidth(700);
		super.setStyle("-fx-border-color: "+colorN+";");
		gcCanvas = super.getGraphicsContext2D();
		gcCanvas.setLineWidth(3);
		switch(colorN)
		{
		case "RED":
			gcCanvas.setStroke(Color.RED);
			break;
		case "GREEN":
			gcCanvas.setStroke(Color.LAWNGREEN);
			break;
		case "DARKORCHID":
			gcCanvas.setStroke(Color.DARKORCHID);
			break;
		}
	}
}
