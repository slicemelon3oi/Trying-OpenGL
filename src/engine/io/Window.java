package engine.io;

import engine.maths.Matrix4f;
import engine.maths.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL46;

public class Window {
    private int width , height;
    private final String title;
    private long window;
    private GLFWVidMode videoMode;
    private Vector3f background = new Vector3f(0,0,0);
    private GLFWWindowSizeCallback windowSizeCallback;
    private boolean isResized;
    private boolean isFullscreen;
    public int frames;
    public double time;
    public Input input;
    public Matrix4f projection;

    //Constructor
    public Window(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.projection = Matrix4f.projection(90.0f , (float)width/(float)height , 0.1f , 1000.0f);
    }

    public Matrix4f getProjectionMatrix() {
        return projection;
    }

    //Method to create window
    public void create() {
        if (!GLFW.glfwInit()) {
            System.err.println("ERROR: glfw couldn't initialize.");
            return;
        }
        this.input = new Input();

        this.window = GLFW.glfwCreateWindow(this.width , this.height , this.title , isFullscreen ? GLFW.glfwGetPrimaryMonitor() : 0 , 0);
        System.out.println("long window = " + this.window);
        if (this.window == 0) {
            System.err.println("ERROR: game window couldn't be created.");
        }

        videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        assert videoMode != null;
        GLFW.glfwSetWindowPos(this.window , (videoMode.width() - this.width)/2 , (videoMode.height() - this.height)/2);

        GLFW.glfwMakeContextCurrent(this.window);
        GL.createCapabilities();
        GL46.glEnable(GL46.GL_DEPTH_TEST);

        GLFW.glfwShowWindow(this.window);
        GLFW.glfwSwapInterval(1);

        this.time = GLFW.glfwGetTime();
        this.frames = 0;

        GLFW.glfwSetWindowTitle(this.window, this.title + " | " + "FPS: " + this.frames + " | TIME: " + (long) GLFW.glfwGetTime());
    }

    //For initializing callbacks
    public void createCallbacks() {
        this.windowSizeCallback = new GLFWWindowSizeCallback() {
            public void invoke(long window, int w, int h) {
                width = w;
                height = h;
                isResized = true;
            }
        };
        GLFW.glfwSetKeyCallback(this.window , this.input.getKeyboardCallback());
        GLFW.glfwSetMouseButtonCallback(this.window , this.input.getMouseButtonsCallback());
        GLFW.glfwSetCursorPosCallback(this.window , this.input.getMouseMoveCallback());
        GLFW.glfwSetScrollCallback(this.window , this.input.getScrollCallback());
        GLFW.glfwSetWindowSizeCallback(this.window , this.windowSizeCallback);
    }

    //For updating every frame
    public void update() {
        if (this.isResized) {
            GL46.glViewport(0, 0, this.width, this.height);
            this.isResized = false;
        }

        GLFW.glfwPollEvents();
        GL46.glClearColor(background.getX() , background.getY() , background.getZ() , 1.0f);
        GL46.glClear(GL46.GL_COLOR_BUFFER_BIT | GL46.GL_DEPTH_BUFFER_BIT);
        this.frames++;

        if (GLFW.glfwGetTime() >= this.time + 1.0d) {
            GLFW.glfwSetWindowTitle(this.window, this.title + " | " + "FPS: " + this.frames + " | TIME: " + (long) GLFW.glfwGetTime()); //Title
            this.time = GLFW.glfwGetTime();
            if (this.width == 1920 && this.height == 1080) {
                System.out.println(this.frames);
            }
            this.frames = 0;
        }
    }

    //for swapping screen buffer every frame
    public void swapBuffers() {
        GLFW.glfwSwapBuffers(this.window);
    }

    //For closing the game window
    public boolean shouldClose() {
        return GLFW.glfwWindowShouldClose(this.window);
    }

    //Set background color
    public void setBackgroundColor(float r , float g , float b) {
        background.set(r , g , b);
    }

    //Destroys everything
    public void destroy() {
        this.input.destroy();
        windowSizeCallback.free();
        GLFW.glfwWindowShouldClose(this.window);
        GLFW.glfwDestroyWindow(this.window);
        GLFW.glfwTerminate();
        System.out.println("Exiting Game!");
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getTitle() {
        return title;
    }

    public long getWindow() {
        return window;
    }

    public boolean isFullscreen() {
        return isFullscreen;
    }

    public void setFullscreen(boolean fullscreen) {
        this.isFullscreen = fullscreen;
        this.isResized = true;
        if (isFullscreen) {
            this.width = 1920;
            this.height = 1080;
            GLFW.glfwSetWindowMonitor(this.window , GLFW.glfwGetPrimaryMonitor() , 0 , 0 , this.width , this.height , GLFW.GLFW_DONT_CARE);
        } else {
            this.width = 1280;
            this.height = 760;
            GLFW.glfwSetWindowMonitor(this.window , 0 , (videoMode.width() - this.width)/2 , (videoMode.height() - this.height)/2 , this.width , this.height , GLFW.GLFW_DONT_CARE);
        }
    }

    public void mouseState(boolean lock) {
        GLFW.glfwSetInputMode(window , GLFW.GLFW_CURSOR , lock ? GLFW.GLFW_CURSOR_DISABLED : GLFW.GLFW_CURSOR_NORMAL);
    }
}
