package quest.objective;

import entity.ai.AITransition;
import input.InputManager;
import input.MouseInputListener;
import quest.ObjectiveTransition;
import quest.QuestManager;

public class ShootTutorial extends Objective {
    @Override
    protected ObjectiveTransition initializeTransition() {
        return new ObjectiveTransition(QuestManager.ObjectivePointer.NULL, this::requirement);
    }

    private boolean requirement() {
        return InputManager.getMouseInputListener().isButtonDown(MouseInputListener.MouseButton.LEFT_BUTTON);
    }

    @Override
    public void update() {
    }

    @Override
    public String toString() {
        return "Current objective is shoot tutorial.";
    }
}
