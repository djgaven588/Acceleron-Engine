package gameEngine.rendering.gui;

import java.util.HashSet;

import org.lwjgl.opengl.*;

import gameEngine.common.*;
import gameEngine.rendering.gui.shaders.GuiShader;
import gameEngine.rendering.meshData.MeshLowLevel;

public class GuiRendererHandler {
	
	private final MeshLowLevel guiQuad;
	private final GuiShader shader;
	
	private static HashSet<GuiTexture> guiElements = new HashSet<GuiTexture>();
	
	public GuiRendererHandler(LowLevelLoader loader) {
		double[] quadVertPositions = new double[] { -1, 1, 0, -1, -1, 0, 1, 1, 0, 1, -1, 0 };
		
		guiQuad = loader.loadToVAO(quadVertPositions);
		shader = new GuiShader();
	}
	
	public void renderUI() {
		shader.Start();
		
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL30.glBindVertexArray(guiQuad.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		
		for (GuiTexture guiTexture : guiElements) {
			
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, guiTexture.getTexture());
			
			shader.loadTransformation(Maths.createTransformationMatrix(guiTexture.transform.position, guiTexture.transform.rotation, guiTexture.transform.size));
			
			GL20.glDrawArrays(GL20.GL_TRIANGLE_STRIP, 0, guiQuad.getVertexCount());
		}
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		guiElements.clear();
		
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		
		shader.Stop();
	}
	
	public static void addUIElement(GuiTexture toRender) {
		guiElements.add(toRender);
	}
	
	public void cleanUp() {
		shader.clean();
	}
}
