import axios from 'axios'
import React from 'react'
import {useState, useEffect} from 'react'
import uuid from 'react-uuid'
import ReadTable from './ReadTable';
import ReadLocationsTable from './ReadLocationsTable';


const Table = () => {

    const [items, setItems] = useState([])

    const [locations, setLocations] = useState([])

    const [addItem, setAddItem] = useState( {
        id: 10,
        name: '',
        description: '',
        price: '',
        quantity: '',
        reorderDays: '',
        location: ''
    })

    const [addLocation, setAddLocation] = useState( {
        locationId: 10,
        locationName: ''
    })
    
    //Get ID
    const [editItemId, setEditItemId] = useState('')
    const [editLocationId, setEditLocationId] = useState('')
    
    const [editFormData, setEditFormData] = useState( {
        id: 10,
        name: '',
        description: '',
        price: '',
        quantity: '',
        reorderDays: '',
        location: ''
    })

    const [editLocationFormData, setEditLocationFormData] = useState( {
        locationId: 10,
        locationName: ''
    })

    

    // Get form values
    const handleChange = (input) => (e) => {
        e.preventDefault();
        setAddItem({...addItem, [input]: e.target.value});
    }

    const handleChangeLocData = (input) => (e) => {
        e.preventDefault();
        setAddLocation({...addLocation, [input]: e.target.value});
    }

    const handleAddLocation = (e) => {
        e.preventDefault();
        
        const newLocation = {
            locationId: uuid(),
            locationName: addLocation.locationName
        }

        const newLocations = [...locations, newLocation]
        setLocations(newLocations)

        
        axios.post('http://localhost:8125/locations', newLocation)

    }
    //Add data to table
    const handleAddItem = (e) => {
        e.preventDefault();

        const newItem = {
            id: uuid(),
            name: addItem.name,
            description: addItem.description,
            price: parseFloat(addItem.price),
            quantity: parseInt(addItem.quantity),
            reorderDays: parseInt(addItem.reorderDays),
            location: addItem.location
        }

        const newItems = [...items, newItem]
        setItems(newItems)

        axios.post('http://localhost:8125/inventory', newItem)
    }


    // Edit Data Value
    const handleEditChange = (input) => (e) => {
        e.preventDefault();

        setEditFormData({...editFormData, [input]: e.target.value})
    }

    const handleEditLocationChange = (input) => (e) => {
        e.preventDefault();

        setEditLocationFormData({...editLocationFormData, [input]: e.target.value})
    }

    // edit location modal data
    const handleEditLocationForm = (e, location) => {
        e.preventDefault();
        setEditLocationId(location.locationId)

        const formValues = {
            locationName: location.locationName
        }

        setEditLocationFormData(formValues)
    }

    const handleLocationSave = (e) => {
        e.preventDefault();

        const saveLocation = {
            locationId: editLocationId,
            locationName: editLocationFormData.locationName
        }
        const newLocations = [...locations]
        const locationFormIndex = locations.findIndex((location) => location.locationId === editLocationId)

        newLocations[locationFormIndex] = saveLocation
        setLocations(newLocations)

        axios.put("http://localhost:8125/locations/" + editLocationId, saveLocation);
        setEditLocationId('')
    }


    
    // edit modal data
    const handleEditItemForm = (e, item) => {
        e.preventDefault();

        setEditItemId(item.id);

        const formValues = {
            name: item.name,
            description: item.description,
            price: parseFloat(item.price),
            quantity: parseInt(item.quantity),
            reorderDays: parseInt(item.reorderDays),
            location: item.location
        }

        setEditFormData(formValues);
    }

    // save form data
    const handleFormSave = (e) => {
        e.preventDefault();

        const saveItem = {
            id: editItemId,
            name: editFormData.name,
            description: editFormData.description,
            price: editFormData.price,
            quantity: editFormData.quantity,
            reorderDays: editFormData.reorderDays,
            location: editFormData.location
        }

        const newItems = [...items]

        const formIndex = items.findIndex((item) => item.id === editItemId)
        newItems[formIndex] = saveItem
        
        setItems(newItems)

        axios.put("http://localhost:8125/inventory/" + editItemId, saveItem)
        setEditItemId('')
    }

    // delete data
    const handleDelete = (e) => {
        e.preventDefault();

        const newItems = [...items]

        const formIndex = items.findIndex((item) => item.id === editItemId)

        newItems.splice(formIndex, 1)
        axios.delete("http://localhost:8125/inventory/" + editItemId)
        setItems(newItems)
    }

    // delete location
    const handleDeleteLocation = (e) => {
        e.preventDefault();

        const newLocations = [...locations]

        const formIndex = locations.findIndex((location) => location.locationId === editLocationId)

        newLocations.splice(formIndex, 1)
        axios.delete("http://localhost:8125/locations/" + editLocationId)
        setLocations(newLocations)
    }



    // get data from api data source
    const fetchUrl = "http://localhost:8125/inventory"
    const locationsUrl = "http://localhost:8125/locations"
    
    useEffect(() => {
        async function fetchData() {
            const data = await axios.get(fetchUrl)
            setItems(data.data)
        }

        async function fetchLocations() {
            const locationData = await axios.get(locationsUrl)
            setLocations(locationData.data)
        }

        fetchData();
        fetchLocations();
    }, [fetchUrl])
    
    
    return(
        <div>

            <div className="d-flex flex-row">
                <button type="button" 
                className="me-3 btn btn-primary ml-auto d-block mb-2"
                data-bs-toggle="modal"
                data-bs-target="#addModalForm">
                    Add Item +
                </button>
            </div>
            <table className="table table-bordered border-primary table-responsive"> 
                <thead>
                    <tr>
                        <th scope="col">Inventory Id</th>
                        <th scope="col">Name</th>
                        <th scope="col">Description</th>
                        <th scope="col">Price</th>
                        <th scope="col">Quantity</th>
                        <th scope="col">Reorder Days</th>
                        <th scope="col">Location</th>
                        <th scope="col">Action</th>
                    </tr>
                </thead>
                <tbody>
                    <ReadTable items={items}
                    handleEditItemForm={handleEditItemForm}/>
                </tbody>
            </table>

            <br></br>
            <br></br>

            <table className="table table-bordered border-primary table-responsive"> 
                <thead>
                    <tr>
                        <th scope="col">Location Id</th>
                        <th scope="col">Location Name</th>
                        <th scope="col">Action</th>
                    </tr>
                </thead>
                <tbody>
                    <ReadLocationsTable handleEditLocationForm={handleEditLocationForm} locations={locations} />
                </tbody>
            </table>


            <div className="d-flex flex-row">
                <button type="button" 
                className="me-3 btn btn-success ml-auto d-block mb-2"
                data-bs-toggle="modal"
                data-bs-target="#locationModalForm">
                    Add Location +
                </button>
            </div>

            {/* Location Modal */}
            <div className="modal fade" id="locationModalForm" tabIndex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div className="modal-dialog">
                <div className="modal-content">
                    <div className="modal-header">
                    <h5 className="modal-title" id="exampleModalLabel">Add New Location</h5>
                    <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div className="modal-body">
                    <form onSubmit={handleAddLocation}>
                        <div className="mb-3">
                        <label className="form-label">Location ID</label>
                        <input
                            type="text"
                            className="form-control"
                            name="inventoryId"
                            placeholder="Location id"
                            required
                            disabled
                            onChange={handleChangeLocData("locationId")}
                        />
                        </div>
                        <div className="mb-3">
                        <label className="form-label">Location Name</label>
                        <input
                            type="text"
                            className="form-control"
                            name="name"
                            placeholder="Name"
                            required
                            onChange={handleChangeLocData("locationName")}
                        />
                        </div>
                        <div className="modal-footer d-block">
                        <button type="submit" data-bs-dismiss="modal" className="btn btn-warning float-end">Submit</button>
                        </div>
                    </form>
                    </div>
                </div>
                </div>
            </div>


            {/* Edit Location Modal */}
            <div className="modal fade" id="editLocationModalForm" tabIndex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div className="modal-dialog">
                <div className="modal-content">
                    <div className="modal-header">
                    <h5 className="modal-title" id="exampleModalLabel">Edit Location</h5>
                    <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div className="modal-body">
                    <form onSubmit={handleLocationSave}>
                        <div className="mb-3">
                        <label className="form-label">Location ID</label>
                        <input
                            type="text"
                            className="form-control"
                            name="locationId"
                            placeholder="Location id"
                            required
                            disabled
                            value={editLocationId}
                            onChange={handleEditLocationChange("locationId")}
                        />
                        </div>
                        <div className="mb-3">
                        <label className="form-label">Location Name</label>
                        <input
                            type="text"
                            className="form-control"
                            name="locationName"
                            placeholder="Location Name"
                            required
                            value={editLocationFormData.locationName}
                            onChange={handleEditLocationChange("locationName")}
                        />
                        </div>
                        <div className="modal-footer d-block">
                        <button type="submit" data-bs-dismiss="modal" className="btn btn-success float-end">Save Row</button>
                        
                        <button type="submit" onClick={handleDeleteLocation} data-bs-dismiss="modal" className="float-start btn btn-danger float-end">Delete Row</button>
                        </div>
                    </form>
                    </div>
                </div>
                </div>
            </div>
      
            {/*Edit Modal */}
            <div className="modal fade" id="editModalForm" tabIndex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div className="modal-dialog">
                <div className="modal-content">
                    <div className="modal-header">
                    <h5 className="modal-title" id="exampleModalLabel">Edit Row</h5>
                    <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div className="modal-body">
                    <form onSubmit={handleFormSave}>
                        <div className="mb-3">
                        <label className="form-label">Inventory ID</label>
                        <input
                            type="text"
                            className="form-control"
                            name="inventoryId"
                            placeholder="Inventory id"
                            required
                            disabled
                            value={editItemId}
                            onChange={handleEditChange("id")}
                        />
                        </div>
                        <div className="mb-3">
                        <label className="form-label">Name</label>
                        <input
                            type="text"
                            className="form-control"
                            name="name"
                            placeholder="Name"
                            required
                            value={editFormData.name}
                            onChange={handleEditChange("name")}
                        />
                        </div>
                        <div className="mb-3">
                        <label className="form-label">Description</label>
                        <textarea
                            rows="4"
                            cols="50"
                            className="form-control"
                            name="description"
                            placeholder="Description"
                            required
                            value={editFormData.description}
                            onChange={handleEditChange("description")}
                        ></textarea>
                        <div className="mb-3">
                        <label className="form-label">Price</label>
                        <input
                            type="text"
                            className="form-control"
                            name="price"
                            placeholder="Price"
                            required
                            value={editFormData.price}
                            onChange={handleEditChange("price")}
                        />
                        </div>
                        <div className="mb-3">
                        <label className="form-label">Quantity</label>
                        <input
                            type="text"
                            className="form-control"
                            name="quantity"
                            placeholder="Quantity"
                            required
                            value={editFormData.quantity}
                            onChange={handleEditChange("quantity")}
                        />
                        </div>
                        <div className="mb-3">
                        <label className="form-label">Reorder Days</label>
                        <input
                            type="text"
                            className="form-control"
                            name="reorderDays"
                            placeholder="Reorder Days"
                            required
                            value={editFormData.reorderDays}
                            onChange={handleEditChange("reorderDays")}
                        />
                        </div>
                        <div className="mb-3">
                        <label className="form-label">Location</label>
                        <select value={editFormData.location}
                        onChange={handleEditChange("location")}>
                         <option></option>
                        {locations.map((location) => 
                    <option key={location.locationName}>{location.locationName}
                        </option>
                   
                      )}
                    </select>
                        </div>
                        </div>
                        <div className="modal-footer d-block">
                        <button type="submit" data-bs-dismiss="modal" className="btn btn-success float-end">Save Row</button>
                        
                        <button type="submit" onClick={handleDelete} data-bs-dismiss="modal" className="float-start btn btn-danger float-end">Delete Row</button>
                        </div>
                    </form>
                    </div>
                </div>
                </div>
            </div>


            {/*Add Modal */}
            <div className="modal fade" id="addModalForm" tabIndex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div className="modal-dialog">
                <div className="modal-content">
                    <div className="modal-header">
                    <h5 className="modal-title" id="exampleModalLabel">Add New Item</h5>
                    <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div className="modal-body">
                    <form onSubmit={handleAddItem}>
                        <div className="mb-3">
                        <label className="form-label">Inventory ID</label>
                        <input
                            type="text"
                            className="form-control"
                            name="inventoryId"
                            placeholder="Inventory id"
                            required
                            disabled
                            onChange={handleChange("id")}
                        />
                        </div>
                        <div className="mb-3">
                        <label className="form-label">Name</label>
                        <input
                            type="text"
                            className="form-control"
                            name="name"
                            placeholder="Name"
                            required
                            onChange={handleChange("name")}
                        />
                        </div>
                        <div className="mb-3">
                        <label className="form-label">Description</label>
                        <textarea
                            rows="4"
                            cols="50"
                            className="form-control"
                            name="description"
                            placeholder="Description"
                            required
                            onChange={handleChange("description")}
                        ></textarea>
                        <div className="mb-3">
                        <label className="form-label">Price</label>
                        <input
                            type="text"
                            className="form-control"
                            name="price"
                            placeholder="Price"
                            required
                            onChange={handleChange("price")}
                        />
                        </div>
                        <div className="mb-3">
                        <label className="form-label">Quantity</label>
                        <input
                            type="text"
                            className="form-control"
                            name="quantity"
                            placeholder="Quantity"
                            required
                            onChange={handleChange("quantity")}
                        />
                        </div>
                        <div className="mb-3">
                        <label className="form-label">Reorder Days</label>
                        <input
                            type="text"
                            className="form-control"
                            name="reorderDays"
                            placeholder="Reorder Days"
                            required
                            onChange={handleChange("reorderDays")}
                        />
                        </div>
                        <div className="mb-3">
                        <label className="form-label">Location</label>
                        <select
                        onChange={handleChange("location")}>
                         <option></option>
                        {locations.map((location) => 
                    <option key={location.locationName}>{location.locationName}
                        </option>
                   
                      )}
                    </select>
                        </div>
                        </div>
                        <div className="modal-footer d-block">
                        <button type="submit" data-bs-dismiss="modal" className="btn btn-warning float-end">Submit</button>
                        </div>
                    </form>
                    </div>
                </div>
                </div>
            </div>
        </div>
    )
}

export default Table