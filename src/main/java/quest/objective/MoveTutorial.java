package quest.objective;

import input.InputManager;
import quest.ObjectiveTransition;
import quest.QuestManager;

import java.awt.event.KeyEvent;

public class MoveTutorial extends Objective {

    public MoveTutorial() {
        super();
    }

    @Override
    protected ObjectiveTransition initializeTransition() {
        return new ObjectiveTransition(QuestManager.ObjectivePointer.SHOOT_TUTORIAL, this::requirement);
    }

    private boolean requirement() {
        return InputManager.getKeyboardListener().isKeyDown(KeyEvent.VK_W);
    }

    @Override
    public void update() {
    }

    @Override
    public String toString() {
        return "Current objective is move tutorial.";
    }
}
