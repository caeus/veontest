import axios1 from "axios";
import constants from "../constants";

//I had to change the base url for some testing
const axios = axios1

const errorHandler = (dispatch) => {
    return (e) => dispatch({
        type: constants.SET_ERROR,
        data: e
    })
}

const makeQuery = function (query) {
    return (dispatch) => {
        dispatch({
            type: constants.MAKE_QUERY,
            data: query
        });
        axios.get(`/v1/country/?q=${encodeURIComponent(query)}`)
            .then((countries) => {
                dispatch({
                    type: constants.SET_QUERY_RESULTS,
                    data: countries.data
                })
            }, errorHandler(dispatch))
    }
}

const countryByCode = function (code) {
    return (dispatch) => {
        dispatch({
            type: constants.GET_COUNTRY
        });
        axios.get(`/v1/country/${encodeURIComponent(code)}`)
            .then((resp) => {

                dispatch({
                    type: constants.SET_COUNTRY,
                    data: resp.data
                })
            }, errorHandler(dispatch))
    }
}


const airportCountTops = function () {
    return (dispatch) => {
        dispatch({
            type: constants.GET_AIRPORT_COUNTS_REPORT
        })
        axios.get('/v1/reports/airport-count-edges')
            .then((resp) => dispatch({
                type: constants.SET_AIRPORT_COUNTS_REPORT,
                data: resp.data
            }), errorHandler(dispatch))
    }
}
const runwayHistograms = function () {
    return (dispatch) => {
        dispatch({
            type: constants.GET_HISTOGRAMS_REPORT
        })
        axios.get('/v1/reports/runway-histograms')
            .then((resp) => dispatch({
                type: constants.SET_HISTOGRAMS_REPORT,
                data: resp.data
            }), errorHandler(dispatch))
    }
}
const runwayModes = function () {
    return (dispatch) => {
        dispatch({
            type: constants.GET_MODES_REPORT
        })
        axios.get('/v1/reports/runway-modes')
            .then((resp) => dispatch({
                type: constants.SET_MODES_REPORT,
                data: resp.data
            }), errorHandler(dispatch))
    }

}


export default {
    makeQuery,
    countryByCode,
    airportCountTops,
    runwayHistograms,
    runwayModes
}