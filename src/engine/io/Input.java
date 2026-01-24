package engine.io;

import org.lwjgl.glfw.*;

public class Input {
    private static final boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST]; //Stores all key values supported by GLFW
    private static final boolean[] buttons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST]; //Stores all mouse button values supported by GLFW
    private static double mouseX , mouseY; //Stores posX and posY of mouse
    private static double scrollX , scrollY; //Stores

    private GLFWKeyCallback keyboard;
    private GLFWCursorPosCallback mouseMove;
    private GLFWMouseButtonCallback mouseButtons;
    private GLFWScrollCallback mouseScroll;

    //Constructor for Input initialization
    public Input() {
        this.keyboard = new GLFWKeyCallback() {
            public void invoke(long window , int key , int scancode , int action , int mods) {
                keys[key] = (action != GLFW.GLFW_RELEASE);
            }
        };
        this.mouseMove = new GLFWCursorPosCallback() {
            public void invoke(long window , double xPos , double yPos) {
                mouseX = xPos;
                mouseY = yPos;
            }
        };
        this.mouseButtons = new GLFWMouseButtonCallback() {
            public void invoke(long window , int button , int action , int mods) {
                buttons[button] = (action != GLFW.GLFW_RELEASE);
            }
        };
        this.mouseScroll = new GLFWScrollCallback() {
            public void invoke(long window, double xoffset, double yoffset) {
                scrollX += xoffset;
                scrollY += yoffset;
            }
        };
    }

    public static boolean isKeyDown(int key) {
        return keys[key];
    }

    public static boolean isMouseButtonDown(int button) {
        return buttons[button];
    }

    public void destroy() {
        keyboard.free();
        mouseMove.free();
        mouseButtons.free();
        mouseScroll.free();
    }

    public static double getMouseX() {
        return mouseX;
    }

    public static double getMouseY() {
        return mouseY;
    }

    public static double getScrollX() {
        return scrollX;
    }

    public static double getScrollY() {
        return scrollY;
    }

    public GLFWKeyCallback getKeyboardCallback() {
        return keyboard;
    }

    public GLFWCursorPosCallback getMouseMoveCallback() {
        return mouseMove;
    }

    public GLFWMouseButtonCallback getMouseButtonsCallback() {
        return mouseButtons;
    }

    public GLFWScrollCallback getScrollCallback() {
        return mouseScroll;
    }
}