/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invincibagel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Kevin Kimaru Chege
 */
public class CastingDirector {

    private final List<Actor> CURRENT_CAST;
    private final List<Actor> COLLIDE_CHECK_LIST;
    private final Set<Actor> REMOVED_ACTORS;

    public CastingDirector() {
        CURRENT_CAST = new ArrayList<>();
        COLLIDE_CHECK_LIST = new ArrayList<>();
        REMOVED_ACTORS = new HashSet<>();
    }

    public List<Actor> getCurrentCast() {
        return CURRENT_CAST;
    }

    public void addCurrentCast(Actor... actors) {
        if (actors.length > 1) {
            CURRENT_CAST.addAll(Arrays.asList(actors));
        } else {
            CURRENT_CAST.add(actors[0]);
        }
    }

    public void removeCurrentCast(Actor... actors) {
        CURRENT_CAST.removeAll(Arrays.asList(actors));
    }

    public void resetCurrentCast() {
        CURRENT_CAST.clear();
    }

    public List<Actor> getCollideCheckList() {
        return COLLIDE_CHECK_LIST;
    }

    public void resetCollideCheckList() {
        COLLIDE_CHECK_LIST.clear();
        COLLIDE_CHECK_LIST.addAll(CURRENT_CAST);
    }

    public void addToRemovedActors(Actor... actors) {
        if (actors.length > 1) {
            REMOVED_ACTORS.addAll(Arrays.asList((Actor[]) actors));
        } else {
            REMOVED_ACTORS.add(actors[0]);
        }
    }

    public Set getRemovedActors() {
        return REMOVED_ACTORS;
    }

    public void resetRemovedActors() {
        CURRENT_CAST.removeAll(REMOVED_ACTORS);
        REMOVED_ACTORS.clear();
    }
}
