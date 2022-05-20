import React from 'react'

const ReadLocationsTable = ({locations, handleEditLocationForm}) => {
    return (
            <>
            {locations.map((location) => 
                <tr key={location.locationId}>
                    <td> {location.locationId} </td>
                    <td> {location.locationName} </td>
                    <td><div className="d-flex flex-row">
            <button type="button" 
            className="me-3 btn btn-primary ml-auto d-block mb-2"
            data-bs-toggle="modal"
            data-bs-target="#editLocationModalForm"
            onClick={(e) => handleEditLocationForm(e, location)}>
                
                Edit 
            </button>
            </div></td>
                </tr>
                )}

            </>
        )
    }
    export default ReadLocationsTable