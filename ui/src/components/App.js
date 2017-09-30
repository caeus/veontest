import React from "react";
import {NavLink} from "react-router-dom";
export default (props)=>(
    <div class="container">
        <nav class="navbar navbar-default">
            <div class="container-fluid">
                <div id="navbar" class="navbar-collapse collapse">
                    <ul class="nav navbar-nav">
                        <li class="active"><NavLink to="/query">Query</NavLink></li>
                        <li><NavLink to="/reports">Reports</NavLink></li>

                    </ul>
                </div>
            </div>
        </nav>

        <div class="jumbotron">
            {props.children}
        </div>
    </div>
)
export const x= (props) => {
    return (<div style={{textAlign: 'center'}}>

        <h1>Verblijfplaats</h1>
        <ul>
            <li><NavLink to="/query">Query</NavLink></li>
            <li><NavLink to="/reports">Reports</NavLink></li>
        </ul>

        <div>
            {props.children}
        </div>
    </div>)
}