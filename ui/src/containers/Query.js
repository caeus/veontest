import {connect} from "react-redux";
import React from "react";
import actions from "../actions/index";

const mapStateToProps = (state, ownProps) => {
    return {
        countries: state.countries
    }
}

const mapDispatchToProps = (dispatch, ownProps) => {
    return {
        makeQuery: (query) => {
            dispatch(actions.makeQuery(query))
        },
        countryByCode: (code) => {
            dispatch(actions.countryByCode(code))
        }
    }
}

const domCountries = (countryByCode, countries) => {

    return (countries && countries.matched && countries.matched.length > 0) ?
        <ul>{
            countries.matched.map((country, index) => (
                <li key={index}><a href="#" onClick={(e) => {
                    countryByCode(country.code)
                    e.preventDefault()
                }}>{country.name}</a></li>
            ))}</ul> : <p>No results</p>;
    ;
}
const domCountry = (countries) => {
    return (countries && countries.selected) ? (
            <div><h1>{countries.selected.name}</h1>
                Airports:
                <ul>
                    {countries.selected.airports
                        .map((airport) => {
                            return <div>
                                <h3>{airport.name}</h3>
                                |{airport.runways.map((runway) => (
                                [<span>{runway.le.ident}</span>, "|"]
                            ))}
                            </div>
                        })}
                </ul>
            </div>
        ) :
        null
}

const component = ({makeQuery, countryByCode, countries}) => {
    let inputEl;

    return (
        <div>
            <form onSubmit={(e) => {
                makeQuery(inputEl.value)
                e.preventDefault()
            }}>
                <input onChange={(e) => {
                    query = e.target.value
                }} defaultValue={countries.query}
                       ref={(el)=> inputEl=el}
                />
                <button>Search!</button>
            </form>
            {domCountry(countries)}
            {domCountries(countryByCode, countries)}
        </div>)
};


export default connect(
    mapStateToProps,
    mapDispatchToProps
)(component)
