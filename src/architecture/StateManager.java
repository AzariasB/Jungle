/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package architecture;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.Validate;

/**
 *
 */
public class StateManager {

    private final Application mApplication;

    StateManager(Application application) {
        mApplication = application;
        mStates = new HashMap<>();
    }

    void addState(AbstractApplicationState state) {
        Validate.notNull(state);
        state.setApplication(mApplication);
        AppStateEnum id = state.getStateId();
        Validate.isTrue(!mStates.containsKey(id), "The state's id already belongs to a previous added state.");
        mStates.put(id.name(), state);
    }

    void setStartingState(String id) {
        mCurrentState = mStates.get(id);
    }

    void initAll() {
        for (AbstractApplicationState state : mStates.values()) {
            state.initialize();
        }
        mCurrentState.notifyEntering();
    }

    AbstractApplicationState getCurrentState() {
        Validate.notNull(mCurrentState);
        return mCurrentState;
    }

    void goToState(String stateId) {
        Validate.isTrue(mStates.containsKey(stateId), "This state id has no associated state. Forgot to add the state ?");
        if (mCurrentState != null) {
            mCurrentState.notifyExiting();
        }
        mCurrentState = mStates.get(stateId);
        mCurrentState.notifyEntering();
    }

    private Map<String, AbstractApplicationState> mStates;
    private AbstractApplicationState mCurrentState;
}
