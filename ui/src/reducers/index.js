/**
 * Created by caeus on 25/06/17.
 */
import {combineReducers} from "redux";
import countries from "./countries.js";
import reports from "./reports.js";
import { syncHistoryWithStore, routerReducer } from 'react-router-redux'

export default combineReducers({
    countries,
    reports,
    routing:routerReducer
})
