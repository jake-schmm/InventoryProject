import React from 'react'

const ReadTable = ({items, handleEditItemForm}) => {
    return (
        <>
        {items.map((item) => 
                    <tr key={item.id}>
                        <td> {item.id} </td>
                        <td> {item.name} </td>
                        <td> {item.description} </td>
                        <td>{item.price}</td>
                        <td>{item.quantity}</td>
                        <td>{item.reorderDays}</td>
                        <td>{item.location}</td>
                        <td><div className="d-flex flex-row">
                <button type="button" 
                className="me-3 btn btn-primary ml-auto d-block mb-2"
                data-bs-toggle="modal"
                data-bs-target="#editModalForm"
                onClick={(e) => handleEditItemForm(e, item)}>
                    Edit
                </button>
            </div></td>
                    </tr>
            )}
        </>
    )
}

export default ReadTable