package gameEngine.rendering.gui;

import java.util.HashSet;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import gameEngine.common.LowLevelLoader;
import gameEngine.common.Maths;
import gameEngine.rendering.WindowManager;
import gameEngine.rendering.gui.shaders.TextShader;

public class TextRenderer {
	
	private static final TextShader shader = new TextShader();
	private static final FontHandler handler = new FontHandler("basic_sans_serif_7.ttf");
	private static LowLevelLoader loader;
	
	private static HashSet<TextBox> textElements = new HashSet<TextBox>();
	
	public TextRenderer(LowLevelLoader loader) {
		this.loader = loader;
	}
	
	public void renderText() {
		shader.Start();
		
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL20.glEnableVertexAttribArray(0);
		
		double width = WindowManager.manager.getCurrentWidth(), height = WindowManager.manager.getCurrentHeight();
		
		for (TextBox textBox : textElements) {
			
			GL30.glBindVertexArray(textBox.getMesh(handler, loader));
			//GL13.glActiveTexture(GL13.GL_TEXTURE0);
			//GL11.glBindTexture(GL11.GL_TEXTURE_2D, guiTexture.getTexture());
			
			shader.loadTransformation(Maths.createTransformationMatrixGui(textBox.getScaledPosition(width, height), textBox.transform.rotation, textBox.getScaledSize(width, height), width, height));
			
			GL20.glDrawArrays(GL20.GL_TRIANGLE_STRIP, 0, textBox.vertexCount());
		}
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		textElements.clear();
		
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		
		shader.Stop();
	}
	
	public static void addTextBox(TextBox textBox) {
		textElements.add(textBox);
	}
	
	public void cleanUp() {
		shader.clean();
	}
}
