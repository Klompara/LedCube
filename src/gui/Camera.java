package gui;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class Camera {
	public static void startCamera() {
		Camera cam = new Camera(0.0f, 0.0f, 0.0f);

		cam.createWindow();
		cam.initGL();

		float dx;
		float dy;
		float dt;
		float time;
		float lastTime = 0.0f;

		float mouseSensitivity = 0.15f;
		float movementSpeed = 20.0f;

		Mouse.setGrabbed(true);
		while (!Display.isCloseRequested()) {
			GL11.glLoadIdentity();
			cam.lookThrough();
			cam.renderGL();
			Display.update();

			time = Sys.getTime();
			dt = (time - lastTime) / 1000.0f;
			lastTime = time;

			dx = Mouse.getDX();
			dy = Mouse.getDY();

			if (Mouse.isGrabbed()) {
				cam.yaw(dx * mouseSensitivity);
				cam.pitch(dy * mouseSensitivity);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
				cam.moveForward(dt * movementSpeed);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
				cam.moveRight(dt * movementSpeed);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
				cam.moveLeft(dt * movementSpeed);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
				cam.moveBackwards(dt * movementSpeed);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
				cam.moveUp(dt * movementSpeed);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
				cam.moveDown(dt * movementSpeed);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
				Mouse.setGrabbed(false);
			}
			
		}

		cam.cleanUp();
	}

	private float x = 0;
	private float y = 0;
	private float z = 0;

	private float yaw = 0;
	private float pitch = 0;

	public Camera(float x, float y, float z) {
		move(x, y, z);
	}

	private void initGL() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GLU.gluPerspective(45.0f, (1280.0f) / ((float) 720.0f), 0.1f, 100.0f);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		GL11.glClearDepth(1.0f);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
	}

	// Setters
	public void yaw(float amount) {
		yaw += amount;
	}

	public void pitch(float amount) {
		pitch -= amount;
	}

	public void move(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void moveForward(float distance) {
		x -= distance * (float) Math.sin(Math.toRadians(yaw));
		z += distance * (float) Math.cos(Math.toRadians(yaw));
	}

	public void moveRight(float distance) {
		x -= distance * (float) Math.sin(Math.toRadians(yaw + 90));
		z += distance * (float) Math.cos(Math.toRadians(yaw + 90));
	}

	public void moveLeft(float distance) {
		x -= distance * (float) Math.sin(Math.toRadians(yaw - 90));
		z += distance * (float) Math.cos(Math.toRadians(yaw - 90));
	}

	public void moveBackwards(float distance) {
		x += distance * (float) Math.sin(Math.toRadians(yaw));
		z -= distance * (float) Math.cos(Math.toRadians(yaw));
	}

	public void moveUp(float distance) {
		y -= distance;
	}

	public void moveDown(float distance) {
		y += distance;
	}

	public void lookThrough() {
		GL11.glRotatef(pitch, 1.0f, 0.0f, 0.0f);
		GL11.glRotatef(yaw, 0.0f, 1.0f, 0.0f);
		GL11.glTranslatef(x, y, z);
	}

	public void renderGL() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glTranslatef(0.0f, 0.0f, -6.0f); // Move Right And Into The Screen
		int cubeSize = 8;
		for (int x = 0; x < cubeSize; x++) {
			for (int y = 0; y < cubeSize; y++) {
				for (int z = 0; z < cubeSize; z++) {
					drawCube((float)x*5, (float)y*5, (float)z*5);
				}
			}
		}
		
	}

	public void drawCube(float x, float y, float z) {
		GL11.glTranslatef(-x, -y, -z);
		GL11.glBegin(GL11.GL_QUADS); // Start Drawing The Cube
		GL11.glColor3f(0.0f, 1.0f, 0.0f); // Set The Color To Green
		GL11.glVertex3f(1.0f, 1.0f, -1.0f); // Top Right Of The Quad (Top)
		GL11.glVertex3f(-1.0f, 1.0f, -1.0f); // Top Left Of The Quad (Top)
		GL11.glVertex3f(-1.0f, 1.0f, 1.0f); // Bottom Left Of The Quad (Top)
		GL11.glVertex3f(1.0f, 1.0f, 1.0f); // Bottom Right Of The Quad (Top)

		GL11.glColor3f(1.0f, 0.5f, 0.0f); // Set The Color To Orange
		GL11.glVertex3f(1.0f, -1.0f, 1.0f); // Top Right Of The Quad (Bottom)
		GL11.glVertex3f(-1.0f, -1.0f, 1.0f); // Top Left Of The Quad (Bottom)
		GL11.glVertex3f(-1.0f, -1.0f, -1.0f); // Bottom Left Of The Quad
												// (Bottom)
		GL11.glVertex3f(1.0f, -1.0f, -1.0f); // Bottom Right Of The Quad
												// (Bottom)

		GL11.glColor3f(1.0f, 0.0f, 0.0f); // Set The Color To Red
		GL11.glVertex3f(1.0f, 1.0f, 1.0f); // Top Right Of The Quad (Front)
		GL11.glVertex3f(-1.0f, 1.0f, 1.0f); // Top Left Of The Quad (Front)
		GL11.glVertex3f(-1.0f, -1.0f, 1.0f); // Bottom Left Of The Quad (Front)
		GL11.glVertex3f(1.0f, -1.0f, 1.0f); // Bottom Right Of The Quad (Front)

		GL11.glColor3f(1.0f, 1.0f, 0.0f); // Set The Color To Yellow
		GL11.glVertex3f(1.0f, -1.0f, -1.0f); // Bottom Left Of The Quad (Back)
		GL11.glVertex3f(-1.0f, -1.0f, -1.0f); // Bottom Right Of The Quad (Back)
		GL11.glVertex3f(-1.0f, 1.0f, -1.0f); // Top Right Of The Quad (Back)
		GL11.glVertex3f(1.0f, 1.0f, -1.0f); // Top Left Of The Quad (Back)

		GL11.glColor3f(0.0f, 0.0f, 1.0f); // Set The Color To Blue
		GL11.glVertex3f(-1.0f, 1.0f, 1.0f); // Top Right Of The Quad (Left)
		GL11.glVertex3f(-1.0f, 1.0f, -1.0f); // Top Left Of The Quad (Left)
		GL11.glVertex3f(-1.0f, -1.0f, -1.0f); // Bottom Left Of The Quad (Left)
		GL11.glVertex3f(-1.0f, -1.0f, 1.0f); // Bottom Right Of The Quad (Left)

		GL11.glColor3f(1.0f, 0.0f, 1.0f); // Set The Color To Violet
		GL11.glVertex3f(1.0f, 1.0f, -1.0f); // Top Right Of The Quad (Right)
		GL11.glVertex3f(1.0f, 1.0f, 1.0f); // Top Left Of The Quad (Right)
		GL11.glVertex3f(1.0f, -1.0f, 1.0f); // Bottom Left Of The Quad (Right)
		GL11.glVertex3f(1.0f, -1.0f, -1.0f); // Bottom Right Of The Quad (Right)
		GL11.glEnd();
		GL11.glTranslatef(x, y, z);
	}

	public void createWindow() {
		try {
			DisplayMode[] modes = Display.getAvailableDisplayModes();

			for (DisplayMode displayMode : modes) {
				if (displayMode.getWidth() == 1280 && displayMode.getHeight() == 720) {
					Display.setDisplayMode(displayMode);
					Display.setFullscreen(false);
					Display.setTitle("Led Cube");
					Display.create();
					break;
				}
			}
		} catch (LWJGLException e) {
			Sys.alert("Error", "Initialization failed!\n\n" + e.getMessage());
			System.exit(0);
		}
	}

	public void cleanUp() {
		Display.destroy();
		System.exit(0);
	}

}