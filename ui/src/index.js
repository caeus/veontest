import React from 'react';
import ReactDOM from 'react-dom';
import {Provider} from 'react-redux';
import reducers from "./reducers";
import {createStore, applyMiddleware} from 'redux';
import App from "./components/App.js";
import Query from "./containers/Query.js";
import Reports from "./containers/Reports.js";
import {Router, Route, Link, Switch, Redirect} from 'react-router';
import {HashRouter} from 'react-router-dom';
import thunkMiddleware from 'redux-thunk';
import bootstrap from "bootstrap/dist/css/bootstrap.css";

console.log(bootstrap)
const store = createStore(reducers, applyMiddleware(thunkMiddleware))

ReactDOM.render(<Provider store={store}>
    <HashRouter >
        <App>

            <Redirect from='/' to='/query'/>
            <Route path="/query" component={Query}></Route>
            <Route path="/reports" component={Reports}></Route>

        </App>
    </HashRouter>
</Provider>, document.getElementById('root'));