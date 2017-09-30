import {connect} from 'react-redux';
import React from 'react';
import actions from '../actions/index';

const mapStateToProps = (state, ownProps) => {
    return {
        reports: state.reports
    }
}

const mapDispatchToProps = (dispatch, ownProps) => {
    return {
        airportCountTops: () => {
            dispatch(actions.airportCountTops())
        },
        runwayHistograms: () => {
            dispatch(actions.runwayHistograms())
        },
        runwayModes: () => {
            dispatch(actions.runwayModes())
        }
    }
}
const renderers = {
    airportCountTops: (report) => {
        return (
            <div>
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th>Top</th>
                        <th>Bottom</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>
                            <ul>
                                {report.top.map((country,index) => (
                                    <li key={index}><span>{country.name}</span> -count: {country.airportCount}</li>
                                ))}
                            </ul>
                        </td>
                        <td>
                            <ul>
                                {report.bottom.map((country,index) => (
                                    <li key={index}><span>{country.name}</span> -count: {country.airportCount}</li>
                                ))}
                            </ul>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        );
    },
    runwayHistograms: (report) => {
        return (
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>Country</th>
                    <th>Runway histogram</th>
                </tr>
                </thead>
                <tbody>
                    {report.map((country,i)=>(
                        <tr key={i}><td>{country.name}</td><td>{JSON.stringify(country.data,null,2)}</td></tr>
                    ))}
                </tbody>
            </table>
        );
    },
    runwayModes: (report) => {
        console.log(report)
        return (
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>Runway ident</th>
                    <th>Count</th>
                </tr>
                </thead>
                <tbody>
                {report.map((runway,i)=>(
                    <tr key={i}><td>{runway.ident}</td>
                        <td>{runway.count}</td></tr>
                ))}
                </tbody>
            </table>
        );
    },
}

const component = ({airportCountTops, runwayHistograms, runwayModes, reports}) => (<div>
    <button onClick={(e) => airportCountTops()}>Airports count tops</button>
    <button onClick={(e) => runwayHistograms()}>Runways histogram</button>
    <button onClick={(e) => runwayModes()}>Runway Modes</button>
    {reports.shown ? <h1>Report</h1> : null}
    <div>
        {reports.shown}
        {reports.shown ? renderers[reports.shown](reports[reports.shown]) : null}
    </div>
</div>);


export default connect(mapStateToProps,
    mapDispatchToProps)(component)