package tools;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class RectTool extends Canvas
{
	private GraphicsContext gcCanvas;

	public RectTool(String color)
	{
		String colorN = color.toUpperCase();
		this.setHeight(700);
		this.setWidth(700);
		this.setStyle("-fx-border-color: "+colorN+";");
		gcCanvas = this.getGraphicsContext2D();
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
	
	public void drawRect(double x, double y){gcCanvas.rect(x, y, 50, 50);}
	public void clearRect(){gcCanvas.clearRect(this.getLayoutX(), this.getLayoutY(), this.getWidth(), this.getHeight());}
}
