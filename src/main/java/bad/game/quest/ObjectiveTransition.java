package bad.game.quest;

import java.util.Optional;

/**
 * This is the pointer class of each subclass of {@code Objective}. This class contains a reference of the next
 * objective in Objective-ObjectiveTRansition LinkedList (OOLL) and a reference of {@link Requirement} interface.
 *
 * @author Mr. GodDavid
 * @apiNote Use lambda expression for Requirement parameter when constructing this class.
 * @since 4/10/2026
 */
public final class ObjectiveTransition {

    private final QuestManager.ObjectivePointer nextState;
    private final Requirement requirement;

    public ObjectiveTransition(QuestManager.ObjectivePointer nextObjective, Requirement requirement) {
        this.nextState = nextObjective;
        this.requirement = requirement;
    }

    /**
     * Checks if all requirements of current objective have met.
     *
     * @return true if all requirements of current objective are met.
     */
    public boolean shouldTransition() {
        Optional<Requirement> optionalRequirement = Optional.ofNullable(requirement);
        return optionalRequirement.map(Requirement::isMet).orElse(false);
    }

    public QuestManager.ObjectivePointer getNextState() {
        return nextState;
    }
}
