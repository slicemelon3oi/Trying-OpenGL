package main;

import engine.graphics.*;
import engine.io.*;
import engine.maths.Vector2f;
import engine.maths.Vector3f;
import engine.objects.Camera;
import engine.objects.GameObject;
import org.lwjgl.glfw.GLFW;

public class Main implements Runnable {
    public Thread game; //For making 1 thread run this class.
    public Window window;
    public int WIDTH = 1280 , HEIGHT = 760;
    public Renderer renderer;
    public Shader shader;

    public Mesh mesh = new Mesh(new Vertex[]{
            //front
        new Vertex(new Vector3f( -0.5f ,  0.5f , 0.0f) , new Vector3f(1.0f , 1.0f , 1.0f) , new Vector2f(0.0f , 0.0f)),
        new Vertex(new Vector3f(  0.5f ,  0.5f , 0.0f) , new Vector3f(1.0f , 1.0f , 1.0f) , new Vector2f(1.0f , 0.0f)),
        new Vertex(new Vector3f(  0.5f , -0.5f , 0.0f) , new Vector3f(1.0f , 1.0f , 1.0f) , new Vector2f(1.0f , 1.0f)),
        new Vertex(new Vector3f( -0.5f , -0.5f , 0.0f) , new Vector3f(1.0f , 1.0f , 1.0f) , new Vector2f(0.0f , 1.0f)),

            //back
        new Vertex(new Vector3f( -0.5f ,  0.5f , 1.0f) , new Vector3f(1.0f , 1.0f , 1.0f) , new Vector2f(0.0f , 0.0f)),
        new Vertex(new Vector3f(  0.5f ,  0.5f , 1.0f) , new Vector3f(1.0f , 1.0f , 1.0f) , new Vector2f(1.0f , 0.0f)),
        new Vertex(new Vector3f(  0.5f , -0.5f , 1.0f) , new Vector3f(1.0f , 1.0f , 1.0f) , new Vector2f(1.0f , 1.0f)),
        new Vertex(new Vector3f( -0.5f , -0.5f , 1.0f) , new Vector3f(1.0f , 1.0f , 1.0f) , new Vector2f(0.0f , 1.0f)),

            //top
        new Vertex(new Vector3f( -0.5f ,  0.5f , 0.0f) , new Vector3f(1.0f , 1.0f , 1.0f) , new Vector2f(0.0f , 0.0f)),
        new Vertex(new Vector3f(  0.5f ,  0.5f , 0.0f) , new Vector3f(1.0f , 1.0f , 1.0f) , new Vector2f(1.0f , 0.0f)),
        new Vertex(new Vector3f(  0.5f ,  0.5f , 1.0f) , new Vector3f(1.0f , 1.0f , 1.0f) , new Vector2f(1.0f , 1.0f)),
        new Vertex(new Vector3f( -0.5f ,  0.5f , 1.0f) , new Vector3f(1.0f , 1.0f , 1.0f) , new Vector2f(0.0f , 1.0f)),

            //bottom
        new Vertex(new Vector3f( -0.5f , -0.5f , 0.0f) , new Vector3f(1.0f , 1.0f , 1.0f) , new Vector2f(0.0f , 0.0f)),
        new Vertex(new Vector3f(  0.5f , -0.5f , 0.0f) , new Vector3f(1.0f , 1.0f , 1.0f) , new Vector2f(1.0f , 0.0f)),
        new Vertex(new Vector3f(  0.5f , -0.5f , 1.0f) , new Vector3f(1.0f , 1.0f , 1.0f) , new Vector2f(1.0f , 1.0f)),
        new Vertex(new Vector3f( -0.5f , -0.5f , 1.0f) , new Vector3f(1.0f , 1.0f , 1.0f) , new Vector2f(0.0f , 1.0f)),

            //left
        new Vertex(new Vector3f( -0.5f ,  0.5f , 0.0f) , new Vector3f(1.0f , 1.0f , 1.0f) , new Vector2f(0.0f , 0.0f)),
        new Vertex(new Vector3f( -0.5f , -0.5f , 0.0f) , new Vector3f(1.0f , 1.0f , 1.0f) , new Vector2f(1.0f , 0.0f)),
        new Vertex(new Vector3f( -0.5f , -0.5f , 1.0f) , new Vector3f(1.0f , 1.0f , 1.0f) , new Vector2f(0.0f , 1.0f)),
        new Vertex(new Vector3f( -0.5f ,  0.5f , 1.0f) , new Vector3f(1.0f , 1.0f , 1.0f) , new Vector2f(1.0f , 1.0f)),

            //right
        new Vertex(new Vector3f( 0.5f ,  0.5f , 0.0f) , new Vector3f(1.0f , 1.0f , 1.0f) , new Vector2f(0.0f , 0.0f)),
        new Vertex(new Vector3f( 0.5f , -0.5f , 0.0f) , new Vector3f(1.0f , 1.0f , 1.0f) , new Vector2f(1.0f , 0.0f)),
        new Vertex(new Vector3f( 0.5f , -0.5f , 1.0f) , new Vector3f(1.0f , 1.0f , 1.0f) , new Vector2f(0.0f , 1.0f)),
        new Vertex(new Vector3f( 0.5f ,  0.5f , 1.0f) , new Vector3f(1.0f , 1.0f , 1.0f) , new Vector2f(1.0f , 1.0f)),
    } , new int[]{
        0 , 1 , 2 ,
        2 , 3 , 0 ,

        4 , 5 , 6 ,
        6 , 7 , 4 ,

        0 , 1 , 4 ,
        4 , 5 , 1 ,

        2 , 3 , 7 ,
        7 , 6 , 2 ,

        0 , 3 , 7 ,
        7 , 4 , 0 ,

        1 , 5 , 6 ,
        6 , 2 , 1
    } , new Material("/resources/textures/xyz.png"));

    public GameObject object = new GameObject(new Vector3f(0,0,0) , new Vector3f(0,0,0) , new Vector3f(1,1,1) , mesh);
    public Camera camera = new Camera(new Vector3f(0,0,1) , new Vector3f(0,0,0));

    //BOILERPLATE CODE

    //Start method to declare and initialize the thread
    public void start() {
        this.game = new Thread(this,"game");
        this.game.start();
    }

    //Initializes variables and objects
    public void init() {
        System.out.println("Initializing Game!");
        this.window = new Window(this.WIDTH , this.HEIGHT , "BOMBACLAT");
        this.window.setBackgroundColor(0.1f , 0.1f , 0.1f);
        this.window.create();
        this.shader = new Shader("/resources/shaders/mainVertex.glsl", "/resources/shaders/mainFragment.glsl");
        this.renderer = new Renderer(window , shader);
        this.mesh.create();
        this.shader.create();
    }

    //Override run()
    public void run() {
        this.init();
        this.window.createCallbacks();
        while (!this.window.shouldClose()) {
            this.render();
            this.update();
        }
        this.destroy();
    }

    //Updating
    boolean lock1 = false;
    private void update() {
        this.window.update();
        this.object.update();
        this.camera.update(lock1);
        if (Input.isMouseButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
            this.window.mouseState(true);
            lock1 = true;
        }
        if (Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
            this.window.mouseState(false);
            lock1 = false;
        }
        if (Input.isKeyDown(GLFW.GLFW_KEY_F11)) {
            System.out.println("F11 Pressed");
            this.window.setFullscreen(!this.window.isFullscreen());
        }
    }

    //Rendering
    private void render() {
        this.renderer.renderObject(this.object , this.camera);
        this.window.swapBuffers();
    }

    private void destroy() {
        this.window.destroy();
        this.shader.destroy();
        this.mesh.destroy();
    }

    //main method
    public static void main(String[] args) {
        new Main().start();
    }
}
