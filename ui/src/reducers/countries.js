/**
 * Created by caeus on 25/06/17.
 */
import constants from "../constants";

const initialState = {
    query: undefined,
    matched: [],
    selected: undefined
}

export default (state = initialState, {type, data}) => {
    switch (type) {
        case constants.MAKE_QUERY:
            return Object.assign({}, state, {query: data,selected:undefined});
        case constants.SET_QUERY_RESULTS:
            return Object.assign({}, state, {matched: data});
        case constants.SET_COUNTRY:
            return Object.assign({}, state, {selected: data});
        default:

            return state
    }

}