/**
 * Created by caeus on 25/06/17.
 */
import constants from "../constants";

const initialState = {
    shown: undefined,
    airportCountTops: undefined,
    runwayHistograms: undefined,
    runwayModes: undefined,
}

export default (state = initialState, {type, data}) => {
    console.log(type)
    switch (type) {
        case constants.SET_AIRPORT_COUNTS_REPORT:
            return Object.assign({}, state, {
                shown: 'airportCountTops',
                airportCountTops: data
            });
        case constants.SET_HISTOGRAMS_REPORT:
            return Object.assign({}, state, {
                shown: 'runwayHistograms',
                runwayHistograms: data
            });
        case constants.SET_MODES_REPORT:
            return Object.assign({}, state, {
                shown: 'runwayModes',
                runwayModes: data
            });
        default:
            return state
    }

}